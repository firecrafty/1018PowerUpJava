package com.pikerobodevils.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.pikerobodevils.lib.drivers.CANTalonSRX;
import com.pikerobodevils.lib.util.MathUtils;
import com.pikerobodevils.robot.RobotConstants;
import com.pikerobodevils.robot.commands.elevator.ElevatorHoldCommand;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Ryan Blue
 */
public class Elevator extends Subsystem {

    private CANTalonSRX master = CANTalonSRX.fromConfiguration(RobotConstants.ELEVATOR_MASTER_ID, new CANTalonSRX.Configuration() {
        {
            selectedSensorSlotZero = FeedbackDevice.QuadEncoder;
            peakOutputForward = 1;
            peakOutputReverse = -0.6;
            motionAcceleration = 4000;
            motionCruiseVelocity = 560;
            pidSlotZero = new CANTalonSRX.PIDSlotValues() {
                {
                    kP = 6;
                    kF = 1023 / 560;
                }
            };
        }
    });
    private CANTalonSRX slave = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.ELEVATOR_SLAVE_ID, master);
    /**
     * Banner retroreflective infrared sensor for detecting bottom of elevator
     */
    private DigitalInput bannerSensor = new DigitalInput(0);

    private double openLoopPower = 0;

    private Elevator() {
        super();
        /*bannerSensor.setUpSourceEdge(false, true);
        bannerSensor.requestInterrupts(new InterruptHandlerFunction<Object>() {
            @Override
            public void interruptFired(int mask, Object param) {
                master.setSelectedSensorPosition(0, 0, 0);
            }
        });*/
        //Initialize the controller to MotionMagic mode so getClosedLoopTarget doesn't fail
        //needs to happen before safety task starts
        master.set(ControlMode.MotionMagic, ElevatorSetpoint.FLOOR.value);
    }

    public double limitDirection(double speed) {
        boolean allowUp = allowOpenLoopUp(), allowDown = allowOpenLoopDown();
        if (allowUp && allowDown) {
            return speed;
        } else if (!allowUp) {
            return MathUtils.coerceInRange(speed, -1, 0);
        } else {
            return MathUtils.coerceInRange(speed, 0, 1);
        }
    }


    public void setOpenLoop(double speed) {
        openLoopPower = limitDirection(speed);
        if (openLoopPower == 0) {
            resumeClosedLoop();
        }
        master.set(ControlMode.PercentOutput, speed);
    }

    public void setClosedLoop(ElevatorSetpoint setpoint) {
        setClosedLoop(setpoint.value);
    }

    public void setClosedLoop(int height) {
        master.set(ControlMode.MotionMagic, MathUtils.coerceInRange(height, 0, 9000));
    }

    public void resumeClosedLoop() {
        openLoopPower = 0;
        setClosedLoop(master.getSelectedSensorPosition(0));
    }

    public void bumpDown() {
        setClosedLoop(getClosedLoopTarget() - 500);
    }

    public void bumpUp() {
        setClosedLoop(getClosedLoopTarget() + 500);
    }

    public void stop() {
        master.set(ControlMode.Disabled, 0);
    }

    public boolean isOpenLoop() {
        return master.getControlMode() == ControlMode.PercentOutput;
    }

    /**
     * Checks whether elevator is at the bottom (stow) position
     *
     * @return true when the elevator is at the bottom.
     */
    public boolean isAtBottom() {
        return !bannerSensor.get();
    }

    public int getClosedLoopTarget() {
        return master.getClosedLoopTarget(0);
    }

    /**
     * Gets the current height of the {@link Elevator} in STUs
     *
     * @return the current height in STUs
     */
    public int getHeight() {
        return master.getSelectedSensorPosition(0);
    }

    /**
     * Checks whether the end effector of the elevator is within the wrist/ear avoidance range
     *
     * @return true when elevator is in the avoidance range
     */
    public boolean elevatorHeightWithinAvoidanceRange() {
        int height = getHeight();
        return MathUtils.isInRange(height, 200, 3500);
    }

    /**
     * Returns true when the elevator height is within (+/-) 100 STUs of the current setpoint
     *
     * @return true if the elevator is in range; otherwise false.
     */
    public boolean onTarget() {
        return MathUtils.isInRange(master.getClosedLoopError(0), -100, 100);
    }

    //Replaces Notifier
    @Override
    public void periodic() {
        updateElevatorSafety();
    }

    /**
     * Runs the elevator safety task
     * <p><ul>
     * <li>Resets the encoder if the retroreflective sensor detects that the frame is at the bottom
     * <li>If the elevator is in open-loop mode, limits the direction of the elevator
     * </ul>
     *
     * @see #setOpenLoop(double)
     * @see #limitDirection(double)
     */
    private void updateElevatorSafety() {
        if (isAtBottom()) {
            master.setSelectedSensorPosition(0, 0, 0);
        }
        if (master.getControlMode() == ControlMode.PercentOutput) {
            setOpenLoop(openLoopPower);
        }

    }

    private boolean allowOpenLoopUp() {
        return getHeight() <= 7700;
    }

    private boolean allowOpenLoopDown() {
        return !isAtBottom();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ElevatorHoldCommand());
    }

    public enum ElevatorSetpoint {
        FLOOR(0),
        SWITCH(940),
        EXCHANGE_PORTAL(1700),
        SCALE_LOW(3850),
        SCALE_MID(5100),
        SCALE_HIGH(6200),
        SCALE_LOW_TWO(5600),
        SCALE_MID_TWO(6300),
        SCALE_HIGH_TWO(7700);

        public final int value;

        private ElevatorSetpoint(int value) {
            this.value = value;
        }
    }

    private static Elevator mInstance;

    public static Elevator getInstance() {
        if (mInstance == null) {
            mInstance = new Elevator();
        }
        return mInstance;
    }


}

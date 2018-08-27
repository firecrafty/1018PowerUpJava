package com.pikerobodevils.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.pikerobodevils.lib.drivers.CANTalonSRX;
import com.pikerobodevils.lib.util.MathUtils;
import com.pikerobodevils.robot.RobotConstants;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.TreeMap;

/**
 * @author Ryan Blue
 */
public class Wrist extends Subsystem {

    private CANTalonSRX wristMotor = CANTalonSRX.fromConfiguration(RobotConstants.WRIST_ID, new CANTalonSRX.Configuration() {
        {
            selectedSensorSlotZero = FeedbackDevice.QuadEncoder;
            continuousCurrentLimit = 20;
            enableCurrentLimit = true;
            peakOutputForward = 0.6;
            peakOutputReverse = -0.75;
            pidSlotOne = new CANTalonSRX.PIDSlotValues() {
                {
                    kP = 4.5;
                    kI = 0.005;
                    kD = 1.5;
                    kF = 0;
                    integralZone = 50;
                }
            };
        }
    });
    Notifier wristSafetyTask = new Notifier(this::updateWristTick);

    private WristSetpoint requestedSetpoint;

    private Wrist() {
        wristSafetyTask.startPeriodic(0.01);
    }

    private int prevElevatorTarget;

    public void request(WristSetpoint setpoint) {
        requestedSetpoint = setpoint;
    }

    public WristSetpoint getRequestedSetpoint() {
        return requestedSetpoint;
    }

    public int getClosedLoopTarget() {
        return wristMotor.getClosedLoopTarget(0);
    }

    public int getPosition() {
        return wristMotor.getSelectedSensorPosition(0);
    }

    public boolean onTarget() {
        return MathUtils.isInRange(wristMotor.getClosedLoopError(0), -100, 100);
    }

    private void updateWristTick() {

        int actualSetpoint;

        if (Elevator.getInstance().elevatorHeightWithinAvoidanceRange()) {
            actualSetpoint = (int) MathUtils.coerceInRange(requestedSetpoint.getValue(), WristSetpoint.HALF_OUT.value, WristSetpoint.SCORE_INTAKE.value);
        } else {
            actualSetpoint = requestedSetpoint.getValue();
        }
        wristMotor.set(ControlMode.Position, actualSetpoint);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public enum WristSetpoint {
        STOW(0),
        HALF_OUT(400),
        SCORE_INTAKE(950);

        WristSetpoint(int value) {
            this.value = value;
        }

        private final int value;
        //Score angle lookup table
        //<Elevator height, Wrist angle>

        private static TreeMap<Integer, Integer> lookup = new TreeMap<>();

        static {
            lookup.put(0, 970);
            lookup.put(1, 800);
            lookup.put(3000, 600);
            lookup.put(7000, 500);
        }

        public int getValue() {
            return ordinal() == 2 ? getScoreAngle() : value;
        }

        private int getScoreAngle() {
            return lookup.lowerEntry(Elevator.getInstance().getClosedLoopTarget()).getValue();
        }
    }

    private static Wrist mInstance = new Wrist();

    public static Wrist getInstance() {
        return mInstance;
    }
}

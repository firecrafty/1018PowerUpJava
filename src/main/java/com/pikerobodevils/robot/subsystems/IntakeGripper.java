package com.pikerobodevils.robot.subsystems;

import com.pikerobodevils.robot.RobotConstants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeGripper extends Subsystem {

    private DoubleSolenoid solenoid = new DoubleSolenoid(RobotConstants.INTAKE_SOLENOID_FWD, RobotConstants.INTAKE_SOLENOID_REV);

    private State state = State.CLOSE;

    private IntakeGripper() {
        set(State.CLOSE);
    }

    private void open() {
        solenoid.set(DoubleSolenoid.Value.kForward);
    }

    private void close() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void set(State state) {
        if (state == null) {
            throw new NullPointerException("State cannot be null");
        }
        if (state == State.OPEN) {
            open();
        } else if (state == State.CLOSE) {
            close();
        }
    }

    public enum State {
        OPEN,
        CLOSE
    }

    @Override
    protected void initDefaultCommand() {

    }

    private static IntakeGripper mInstance = new IntakeGripper();

    public static IntakeGripper getInstance() {
        return mInstance;
    }
}

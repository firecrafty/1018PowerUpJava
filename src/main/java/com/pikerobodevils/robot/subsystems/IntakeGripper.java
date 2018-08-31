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

    public void set(State state) {
        if (state == null) {
            throw new NullPointerException("State cannot be null");
        }
        solenoid.set(state.value);
    }

    public enum State {
        OPEN(DoubleSolenoid.Value.kReverse),
        CLOSE(DoubleSolenoid.Value.kReverse);

        public final DoubleSolenoid.Value value;

        State(DoubleSolenoid.Value value) {
            this.value = value;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

    private static IntakeGripper mInstance;

    public static IntakeGripper getInstance() {
        if (mInstance == null) {
            mInstance = new IntakeGripper();
        }
        return mInstance;
    }
}

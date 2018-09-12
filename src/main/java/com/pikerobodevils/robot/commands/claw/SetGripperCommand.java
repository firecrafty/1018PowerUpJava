package com.pikerobodevils.robot.commands.claw;

import com.pikerobodevils.robot.subsystems.IntakeGripper;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetGripperCommand extends InstantCommand {
    private IntakeGripper gripper = IntakeGripper.getInstance();
    private IntakeGripper.State state;

    public SetGripperCommand(IntakeGripper.State state) {
        requires(gripper);
        this.state = state;
    }

    @Override
    protected void initialize() {
        gripper.set(state);
    }
}

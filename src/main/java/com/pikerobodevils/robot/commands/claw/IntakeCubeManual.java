package com.pikerobodevils.robot.commands.claw;

import com.pikerobodevils.robot.subsystems.IntakeRollers;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeCubeManual extends Command {
    IntakeRollers rollers = IntakeRollers.getInstance();
    public IntakeCubeManual() {
        requires(rollers);
    }

    @Override
    protected void initialize() {
        rollers.set(-1);
    }

    @Override
    protected void end() {
        rollers.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

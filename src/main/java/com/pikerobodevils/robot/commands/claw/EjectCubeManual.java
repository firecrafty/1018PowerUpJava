package com.pikerobodevils.robot.commands.claw;

import com.pikerobodevils.robot.subsystems.Elevator;
import com.pikerobodevils.robot.subsystems.IntakeRollers;
import com.pikerobodevils.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.Command;

public class EjectCubeManual extends Command {
    IntakeRollers rollers = IntakeRollers.getInstance();

    public EjectCubeManual() {
        requires(rollers);
    }

    @Override
    protected void initialize() {
        double speed;
        if (Wrist.getInstance().getRequestedSetpoint() == Wrist.WristSetpoint.HALF_OUT) {
            speed = 0.52;
        } else if (Elevator.getInstance().isAtBottom()) {
            speed = 1;
        } else {
            speed = 0.5;
        }
        rollers.set(speed);
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

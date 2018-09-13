package com.pikerobodevils.robot.commands.elevator;

import com.pikerobodevils.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorBumpUpContinuousCommand extends Command {
    public ElevatorBumpUpContinuousCommand() {
        requires(Elevator.getInstance());
    }

    @Override
    protected void initialize() {
        Elevator.getInstance().setOpenLoop(1);
    }

    @Override
    protected void end() {
        Elevator.getInstance().resumeClosedLoop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

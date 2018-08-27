package com.pikerobodevils.robot.commands.elevator;

import com.pikerobodevils.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorBumpUpCommand extends Command {
    Elevator elevator = Elevator.getInstance();

    public ElevatorBumpUpCommand() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.bumpUp();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

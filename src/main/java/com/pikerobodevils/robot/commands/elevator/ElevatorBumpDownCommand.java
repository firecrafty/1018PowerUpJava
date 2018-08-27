package com.pikerobodevils.robot.commands.elevator;

import com.pikerobodevils.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorBumpDownCommand extends Command {
    Elevator elevator = Elevator.getInstance();

    public ElevatorBumpDownCommand() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.bumpDown();
    }

    @Override
    protected boolean isFinished() {
        return elevator.onTarget();
    }
}

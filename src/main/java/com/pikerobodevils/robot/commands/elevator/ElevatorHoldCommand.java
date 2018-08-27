package com.pikerobodevils.robot.commands.elevator;

import com.pikerobodevils.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorHoldCommand extends Command {
    public ElevatorHoldCommand() {
        requires(Elevator.getInstance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

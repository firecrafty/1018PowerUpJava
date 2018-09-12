package com.pikerobodevils.robot.commands.superstructure;

import com.pikerobodevils.robot.commands.elevator.ElevatorSetHeightCommand;
import com.pikerobodevils.robot.commands.wrist.WristSetAngleCommand;
import com.pikerobodevils.robot.subsystems.Elevator;
import com.pikerobodevils.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SuperstructureStowCommand extends CommandGroup {
    public SuperstructureStowCommand() {
        addParallel(new ElevatorSetHeightCommand(Elevator.ElevatorSetpoint.FLOOR));
        addParallel(new WristSetAngleCommand(Wrist.WristSetpoint.STOW));
    }
}

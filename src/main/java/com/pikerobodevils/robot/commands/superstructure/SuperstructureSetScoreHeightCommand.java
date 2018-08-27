package com.pikerobodevils.robot.commands.superstructure;

import com.pikerobodevils.robot.commands.elevator.ElevatorSetHeightCommand;
import com.pikerobodevils.robot.commands.wrist.WristSetAngleCommand;
import com.pikerobodevils.robot.subsystems.Elevator;
import com.pikerobodevils.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SuperstructureSetScoreHeightCommand extends CommandGroup {
    public SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint elevatorSetpoint) {
        addParallel(new ElevatorSetHeightCommand(elevatorSetpoint));
        addParallel(new WristSetAngleCommand(Wrist.WristSetpoint.SCORE_INTAKE));
    }
}

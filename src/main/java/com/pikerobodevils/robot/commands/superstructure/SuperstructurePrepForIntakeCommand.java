package com.pikerobodevils.robot.commands.superstructure;

import com.pikerobodevils.robot.commands.claw.SetGripperCommand;
import com.pikerobodevils.robot.commands.elevator.ElevatorSetHeightCommand;
import com.pikerobodevils.robot.commands.wrist.WristSetAngleCommand;
import com.pikerobodevils.robot.subsystems.Elevator;
import com.pikerobodevils.robot.subsystems.IntakeGripper;
import com.pikerobodevils.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SuperstructurePrepForIntakeCommand extends CommandGroup {
    public SuperstructurePrepForIntakeCommand() {
        addParallel(new ElevatorSetHeightCommand(Elevator.ElevatorSetpoint.FLOOR));
        addParallel(new WristSetAngleCommand(Wrist.WristSetpoint.SCORE_INTAKE));
        addParallel(new SetGripperCommand(IntakeGripper.State.OPEN));
    }
}

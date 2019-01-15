package com.pikerobodevils.robot.commands.auto;

import com.pikerobodevils.lib.motion.DrivetrainProfile;
import com.pikerobodevils.lib.motion.SimpleProfileParser;
import com.pikerobodevils.robot.commands.MotionProfileProgressCommand;
import com.pikerobodevils.robot.commands.MotionProfileSequenceCommand;
import com.pikerobodevils.robot.commands.drivetrain.RunTalonMotionProfileCommand;
import com.pikerobodevils.robot.commands.elevator.ElevatorSetHeightCommand;
import com.pikerobodevils.robot.commands.superstructure.SuperstructureSetScoreHeightCommand;
import com.pikerobodevils.robot.subsystems.Drivetrain;
import com.pikerobodevils.robot.subsystems.Elevator;
import com.pikerobodevils.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchOneCubeCommand extends CommandGroup {
    public SwitchOneCubeCommand() {
        addParallel(new RunTalonMotionProfileCommand(SimpleProfileParser.getDrivetrainProfile("ADSf")));
        addParallel(new MotionProfileSequenceCommand(
                new MotionProfileProgressCommand(0, () -> new SuperstructureSetScoreHeightCommand(Elevator.ElevatorSetpoint.SCALE_MID, Wrist.WristSetpoint.SCORE_INTAKE), Elevator.getInstance()),
                new MotionProfileProgressCommand(50, () -> System.out.println("YEET"))

        ));

    }
}

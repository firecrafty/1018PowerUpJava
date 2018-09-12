package com.pikerobodevils.robot.commands.drivetrain;

import com.pikerobodevils.lib.util.drive.DifferentialDriveJoystickMap;
import com.pikerobodevils.lib.util.drive.DriveSignal;
import com.pikerobodevils.robot.ControlBoard;
import com.pikerobodevils.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {
    ControlBoard controlBoard = ControlBoard.getInstance();
    Drivetrain drivetrain = Drivetrain.getInstance();
    DifferentialDriveJoystickMap mixer = new DifferentialDriveJoystickMap();

    public TeleopDrive() {
        requires(drivetrain);
    }

    @Override
    protected void execute() {
        DriveSignal signal = mixer.arcadeDrive(controlBoard.getThrottle(), controlBoard.getTurn());
        drivetrain.setOpenLoop(signal);
    }

    @Override
    protected void end() {
        drivetrain.setOpenLoop(DriveSignal.NEUTRAL);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

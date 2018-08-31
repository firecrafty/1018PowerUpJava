package com.pikerobodevils.robot;

import com.pikerobodevils.lib.util.drive.DifferentialDriveJoystickMap;
import com.pikerobodevils.lib.util.drive.DriveSignal;
import com.pikerobodevils.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends TimedRobot {
    ControlBoard controlBoard;
    Drivetrain drivetrain;
    DifferentialDriveJoystickMap mixer = new DifferentialDriveJoystickMap();

    @Override
    public void robotInit() {
        controlBoard = ControlBoard.getInstance();
        drivetrain = Drivetrain.getInstance();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void testInit() {
    }


    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        DriveSignal signal = mixer.arcadeDrive(controlBoard.getThrottle(), controlBoard.getTurn());
        //drivetrain.setOpenLoop(signal);
        //drivetrain.drive(controlBoard.getThrottle(), controlBoard.getTurn());
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {

    }
}

package com.pikerobodevils.robot;

import com.pikerobodevils.lib.util.drive.DifferentialDriveJoystickMap;
import com.pikerobodevils.robot.subsystems.Drivetrain;

import org.pmw.tinylog.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends TimedRobot {

    ControlBoard controlBoard;
    Drivetrain drivetrain;
    DifferentialDriveJoystickMap mixer = new DifferentialDriveJoystickMap();

    @Override
    public void robotInit() {
        RobotLogger.logRobotInit();
        while (DriverStation.getInstance().isDSAttached()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controlBoard = ControlBoard.getInstance();
        drivetrain = Drivetrain.getInstance();
        RobotLogger.logRobotInit();
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
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {

    }

    @Override
    protected void loopFunc() {
        try {
            super.loopFunc();
        } catch (Exception e) {
            Logger.error(e);
        }

    }
}

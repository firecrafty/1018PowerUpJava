package com.pikerobodevils.robot;

import com.pikerobodevils.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends TimedRobot {
    ControlBoard controlBoard;
    Drivetrain drivetrain;

    @Override
    public void robotInit() {
        setPeriod(0.01);
        controlBoard = ControlBoard.getInstance();
        drivetrain = Drivetrain.getInstance();
        CameraServer.getInstance().startAutomaticCapture();
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
        Scheduler.getInstance().run();
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
}

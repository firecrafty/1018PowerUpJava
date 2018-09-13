package com.pikerobodevils.robot;

import com.pikerobodevils.robot.subsystems.Drivetrain;

import org.pmw.tinylog.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends TimedRobot {

    ControlBoard controlBoard;
    Drivetrain drivetrain;

    @Override
    public void robotInit() {
        RobotLogger.logRobotInit();
        while (DriverStation.getInstance().isDSAttached()) {
            Logger.warn("Waiting for DS connection...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setPeriod(0.01);
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

    @Override
    protected void loopFunc() {
        try {
            super.loopFunc();
        } catch (Exception e) {
            Logger.error(e);
        }

    }
}

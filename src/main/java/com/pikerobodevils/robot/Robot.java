package com.pikerobodevils.robot;

import com.pikerobodevils.lib.util.RobotMode;
import com.pikerobodevils.robot.subsystems.Drivetrain;

import org.pmw.tinylog.Logger;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends TimedRobot {

    ControlBoard controlBoard;
    Drivetrain drivetrain;

    @Override
    public void robotInit() {
        RobotLogger.logRobotStart();
        //probably not the smartest idea
        /**while (DriverStation.getInstance().isDSAttached()) {
         Logger.warn("Waiting for DS connection...");
         try {
         Thread.sleep(2000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         }*/
        setPeriod(0.01);
        controlBoard = ControlBoard.getInstance();
        RobotLogger.logRobotInitComplete();
        CameraServer.getInstance().startAutomaticCapture();
    }

    @Override
    public void disabledInit() {
        RobotLogger.logStateTransition(RobotMode.DISABLED);
    }

    @Override
    public void autonomousInit() {
        RobotLogger.logStateTransition(RobotMode.AUTONOMOUS);
    }

    @Override
    public void teleopInit() {
        RobotLogger.logStateTransition(RobotMode.TELEOP);
    }

    @Override
    public void testInit() {
        RobotLogger.logStateTransition(RobotMode.TEST);
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

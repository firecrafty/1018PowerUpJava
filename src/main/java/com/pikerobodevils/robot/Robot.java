package com.pikerobodevils.robot;

import com.pikerobodevils.lib.motion.DrivetrainProfile;
import com.pikerobodevils.lib.motion.ProfileGenerator;
import com.pikerobodevils.lib.motion.SimpleProfileParser;
import com.pikerobodevils.robot.subsystems.Drivetrain;

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
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
        System.out.println("LOADING");
        DrivetrainProfile profile = SimpleProfileParser.getDrivetrainProfile("Center-LeftSwitch");
        System.out.println("LOADED: " + profile.getRight().length()  + " poiints");
        drivetrain.loadMotionProfile(profile);
        drivetrain.startMotionProfile();
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
        if(drivetrain.isProfileComplete()) {
            System.out.println("STOPPED");
            drivetrain.holdMotionProfile();
        }
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

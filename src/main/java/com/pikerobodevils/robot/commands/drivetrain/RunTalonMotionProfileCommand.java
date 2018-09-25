package com.pikerobodevils.robot.commands.drivetrain;

import com.pikerobodevils.lib.motion.DrivetrainProfile;
import com.pikerobodevils.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class RunTalonMotionProfileCommand extends Command {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private DrivetrainProfile profile;
    private boolean disableOnFinish;

    public RunTalonMotionProfileCommand(DrivetrainProfile profile) {
        this(profile, false);
    }

    public RunTalonMotionProfileCommand(DrivetrainProfile profile, boolean disableOnFinish) {
        this.profile = profile;
        this.disableOnFinish = disableOnFinish;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        drivetrain.loadMotionProfile(profile);
        drivetrain.startMotionProfile();
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.isProfileComplete();
    }

    @Override
    protected void end() {
        if (disableOnFinish) {
            drivetrain.disable();
        } else {
            drivetrain.holdMotionProfile();
        }
    }

    @Override
    protected void interrupted() {
        drivetrain.disable();
        drivetrain.resetMotionProfile();
    }
}

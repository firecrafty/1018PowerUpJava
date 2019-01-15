package com.pikerobodevils.robot.subsystems;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;
import com.pikerobodevils.lib.drivers.CANTalonSRX;
import com.pikerobodevils.lib.motion.DrivetrainProfile;
import com.pikerobodevils.lib.util.drive.DifferentialDriveJoystickMap;
import com.pikerobodevils.lib.util.drive.DriveSignal;
import com.pikerobodevils.robot.RobotConstants;
import com.pikerobodevils.robot.commands.drivetrain.TeleopDrive;

import org.apache.commons.lang3.tuple.Pair;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import jaci.pathfinder.Trajectory;

/**
 * @author Ryan Blue
 */
public class Drivetrain extends Subsystem {


    CANTalonSRX.Configuration masterConfig = new CANTalonSRX.Configuration() {
        {
            continuousCurrentLimit = 18;
            enableCurrentLimit = true;
            selectedSensorSlotOne = FeedbackDevice.QuadEncoder;
            motionControlFramePeriodMs = 10;
            targetsStatusFrameRateMs = 10;
        }
    };

    private CANTalonSRX leftMaster = CANTalonSRX.fromConfiguration(RobotConstants.LEFT_MASTER_ID, masterConfig);

    private CANTalonSRX leftSlaveA = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.LEFT_SLAVE_A_ID, leftMaster);
    private CANTalonSRX leftSlaveB = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.LEFT_SLAVE_B_ID, leftMaster);
    private CANTalonSRX rightMaster = CANTalonSRX.fromConfiguration(RobotConstants.RIGHT_MASTER_ID, masterConfig);
    private CANTalonSRX rightSlaveA = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.RIGHT_SLAVE_A_ID, rightMaster);
    private CANTalonSRX rightSlaveB = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.RIGHT_SLAVE_B_ID, rightMaster);
    private AHRS navX = new AHRS(SPI.Port.kMXP);
    private DifferentialDriveJoystickMap driveHelper = new DifferentialDriveJoystickMap();

    private DrivetrainProfile currentProfile;

    private Drivetrain() {
        super();
        //So for some reason wpilib thought it was a good idea to invert during joystick mapping...
        //But we have to invert these so closed loop works...
        rightMaster.setInverted(true);
        rightSlaveA.setInverted(true);
        rightSlaveB.setInverted(true);
        leftMaster.setNeutralMode(NeutralMode.Coast);
        leftSlaveA.setNeutralMode(NeutralMode.Coast);
        leftSlaveB.setNeutralMode(NeutralMode.Coast);
        rightMaster.setNeutralMode(NeutralMode.Coast);
        rightSlaveA.setNeutralMode(NeutralMode.Coast);
        rightSlaveB.setNeutralMode(NeutralMode.Coast);
    }

    public void drive(double xSpeed, double zRotation) {
        DriveSignal signal = driveHelper.arcadeDrive(xSpeed, zRotation);

    }

    public void setOpenLoop(DriveSignal signal) {
        //setControlMode(DrivetrainControlMode.OPEN_LOOP);
        leftMaster.set(ControlMode.PercentOutput, signal.left);
        //So we have to do this...
        rightMaster.set(ControlMode.PercentOutput, -signal.right);
    }

    public void loadMotionProfile(DrivetrainProfile profile) {
        leftMaster.loadMotionProfile(profile.getLeft());
        rightMaster.loadMotionProfile(profile.getRight());

        /**while (leftMaster.getMotionProfileStatus().btmBufferCnt < (profile.getLeft().length()/8) && rightMaster.getMotionProfileStatus().btmBufferCnt < (profile.getRight().length()/8)) {
         leftMaster.processMotionProfileBuffer();
         rightMaster.processMotionProfileBuffer();
         }*/
    }

    public void startMotionProfile() {
        leftMaster.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
        rightMaster.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
    }

    public void holdMotionProfile() {
        leftMaster.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
        rightMaster.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
    }

    public void resetMotionProfile() {
        leftMaster.resetMotionProfile();
        rightMaster.resetMotionProfile();
        currentProfile = new DrivetrainProfile(Pair.of(new Trajectory(0), new Trajectory(0)));
    }

    public void disable() {
        leftMaster.disable();
        rightMaster.disable();
    }

    public int pointsRemaining() {
        return leftMaster.motionProfileTotalCnt();
    }

    public boolean isProfileComplete() {
        return leftMaster.isProfileComplete() && rightMaster.isProfileComplete();
    }

    public double getProfilePercentComplete() {
        if(currentProfile.length() == 0) {
            return 0;
        }
        return (1 - (double)(pointsRemaining() / currentProfile.length())) * 100;
    }

    private static Drivetrain mInstance;

    public static Drivetrain getInstance() {
        if (mInstance == null) {
            mInstance = new Drivetrain();
        }
        return mInstance;
    }

    enum DrivetrainControlMode {
        OPEN_LOOP,
        MOTION_PROFILE;
    }

    @Override
    protected void initDefaultCommand() {
        //not sure why this isn't working
        setDefaultCommand(new TeleopDrive());
    }
}

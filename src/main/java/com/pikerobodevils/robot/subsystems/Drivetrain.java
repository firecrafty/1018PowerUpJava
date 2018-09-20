package com.pikerobodevils.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;
import com.pikerobodevils.lib.drivers.CANTalonSRX;
import com.pikerobodevils.lib.util.drive.DifferentialDriveJoystickMap;
import com.pikerobodevils.lib.util.drive.DriveSignal;
import com.pikerobodevils.robot.RobotConstants;
import com.pikerobodevils.robot.RobotLogger;
import com.pikerobodevils.robot.commands.drivetrain.TeleopDrive;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

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

    private CANTalonSRX leftMaster;

    private CANTalonSRX leftSlaveA;
    private CANTalonSRX leftSlaveB;
    private CANTalonSRX rightMaster;
    private CANTalonSRX rightSlaveA;
    private CANTalonSRX rightSlaveB;
    private AHRS navX;
    private DifferentialDriveJoystickMap driveHelper = new DifferentialDriveJoystickMap();

    private Drivetrain() {
        super();
        RobotLogger.logSubsystemConstructionStart(this);
        rightSlaveA = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.RIGHT_SLAVE_A_ID, rightMaster);
        rightSlaveB = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.RIGHT_SLAVE_B_ID, rightMaster);
        leftMaster = CANTalonSRX.fromConfiguration(RobotConstants.LEFT_MASTER_ID, masterConfig);
        leftSlaveA = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.LEFT_SLAVE_A_ID, leftMaster);
        leftSlaveB = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.LEFT_SLAVE_B_ID, leftMaster);
        //So for some reason wpilib thought it was a good idea to invert during joystick mapping...
        //But we have to invert these so closed loop works...
        rightMaster = CANTalonSRX.fromConfiguration(RobotConstants.RIGHT_MASTER_ID, masterConfig);
        rightMaster.setInverted(true);
        rightSlaveA.setInverted(true);
        rightSlaveB.setInverted(true);
        leftMaster.setNeutralMode(NeutralMode.Coast);
        leftSlaveA.setNeutralMode(NeutralMode.Coast);
        leftSlaveB.setNeutralMode(NeutralMode.Coast);
        rightMaster.setNeutralMode(NeutralMode.Coast);
        rightSlaveA.setNeutralMode(NeutralMode.Coast);
        rightSlaveB.setNeutralMode(NeutralMode.Coast);
        navX = new AHRS(SPI.Port.kMXP);
        RobotLogger.logSubsystemConstructionStart(this);
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

    public void loadMotionProfile() {

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

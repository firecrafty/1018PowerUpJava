package com.pikerobodevils.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import com.pikerobodevils.lib.drivers.CANTalonSRX;
import com.pikerobodevils.lib.util.drive.DifferentialDriveJoystickMap;
import com.pikerobodevils.lib.util.drive.DriveSignal;
import com.pikerobodevils.robot.RobotConstants;
import com.pikerobodevils.robot.commands.drivetrain.TeleopDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
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

    private CANTalonSRX leftMaster = CANTalonSRX.fromConfiguration(RobotConstants.LEFT_MASTER_ID, masterConfig);

    private CANTalonSRX leftSlaveA = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.LEFT_SLAVE_A_ID, leftMaster);
    private CANTalonSRX leftSlaveB = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.LEFT_SLAVE_B_ID, leftMaster);
    private CANTalonSRX rightMaster = CANTalonSRX.fromConfiguration(RobotConstants.RIGHT_MASTER_ID, masterConfig);
    private CANTalonSRX rightSlaveA = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.RIGHT_SLAVE_A_ID, rightMaster);
    private CANTalonSRX rightSlaveB = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.RIGHT_SLAVE_B_ID, rightMaster);
    private AHRS navX = new AHRS(SPI.Port.kMXP);
    private DifferentialDriveJoystickMap driveHelper = new DifferentialDriveJoystickMap();
    private Drivetrain() {
        rightMaster.setInverted(true);
        rightSlaveA.setInverted(true);
        rightSlaveB.setInverted(true);
    }

    public void drive(double xSpeed, double zRotation) {
        DriveSignal signal = driveHelper.arcadeDrive(xSpeed, zRotation);

    }

    public void setOpenLoop(DriveSignal signal) {
        //setControlMode(DrivetrainControlMode.OPEN_LOOP);
        leftMaster.set(ControlMode.PercentOutput, signal.left);
        rightMaster.set(ControlMode.PercentOutput, signal.right);
    }

    public void loadMotionProfile() {

    }

    public static Drivetrain mInstance = new Drivetrain();

    public static Drivetrain getInstance() {
        return mInstance;
    }

    enum DrivetrainControlMode {
        OPEN_LOOP,
        MOTION_PROFILE;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleopDrive());
    }
}

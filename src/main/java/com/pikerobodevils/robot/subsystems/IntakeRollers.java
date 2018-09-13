package com.pikerobodevils.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.pikerobodevils.lib.drivers.CANTalonSRX;
import com.pikerobodevils.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Ryan Blue
 */
public class IntakeRollers extends Subsystem {
    CANTalonSRX intakeMaster = CANTalonSRX.fromConfiguration(RobotConstants.INTAKE_MASTER_ID, new CANTalonSRX.Configuration() {
        {
            continuousCurrentLimit = 20;
            enableCurrentLimit = true;
            invert = true;
        }
    });
    CANTalonSRX intakeSlave = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.INTAKE_SLAVE_ID, intakeMaster);


    private IntakeRollers() {
        super();
    }

    public void set(double speed) {
        intakeMaster.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        set(0);
    }

    @Override
    protected void initDefaultCommand() {

    }

    private static IntakeRollers mInstance;

    public static IntakeRollers getInstance() {
        if (mInstance == null) {
            mInstance = new IntakeRollers();
        }
        return mInstance;
    }
}

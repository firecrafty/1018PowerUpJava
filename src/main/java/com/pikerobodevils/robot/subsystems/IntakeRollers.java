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
        }
    });
    CANTalonSRX intakeSlave = CANTalonSRX.newPermanentSlaveTalon(RobotConstants.INTAKE_SLAVE_ID, intakeMaster);


    private IntakeRollers() {

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

    private static IntakeRollers mInstance = new IntakeRollers();

    public static IntakeRollers getInstance() {
        return mInstance;
    }
}

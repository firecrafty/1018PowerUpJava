package com.pikerobodevils.robot.commands.wrist;

import com.pikerobodevils.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.Command;

public class WristSetAngleCommand extends Command {
    private Wrist wrist = Wrist.getInstance();
    private Wrist.WristSetpoint setpoint;

    public WristSetAngleCommand(Wrist.WristSetpoint setpoint) {
        requires(wrist);
        this.setpoint = setpoint;
    }

    @Override
    protected void initialize() {
        wrist.request(setpoint);
    }

    @Override
    protected boolean isFinished() {
        return wrist.onTarget();
    }
}

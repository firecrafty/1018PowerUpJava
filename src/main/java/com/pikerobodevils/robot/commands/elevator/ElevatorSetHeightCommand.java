package com.pikerobodevils.robot.commands.elevator;

import com.pikerobodevils.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSetHeightCommand extends Command {
    Elevator elevator = Elevator.getInstance();
    Elevator.ElevatorSetpoint setpoint;

    public ElevatorSetHeightCommand(Elevator.ElevatorSetpoint setpoint) {
        requires(elevator);
        this.setpoint = setpoint;
    }

    @Override
    protected void initialize() {
        elevator.setClosedLoop(setpoint);
    }

    @Override
    protected boolean isFinished() {
        return elevator.onTarget();
    }
}

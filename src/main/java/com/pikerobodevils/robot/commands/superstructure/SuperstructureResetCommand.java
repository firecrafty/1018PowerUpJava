package com.pikerobodevils.robot.commands.superstructure;

import com.pikerobodevils.robot.subsystems.Elevator;
import com.pikerobodevils.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.Command;

public class SuperstructureResetCommand extends Command {
    private Elevator elevator = Elevator.getInstance();
    private Wrist wrist = Wrist.getInstance();

    public SuperstructureResetCommand() {
        requires(elevator);
        requires(wrist);
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        wrist.request(Wrist.WristSetpoint.HALF_OUT);
        elevator.setOpenLoop(-0.4);
        //Set wrist out
    }

    @Override
    protected void execute() {

    }

    @Override
    protected void end() {
        elevator.stop();
        elevator.resumeClosedLoop();
    }

    @Override
    protected void interrupted() {
        elevator.stop();
    }

    @Override
    protected boolean isFinished() {
        return elevator.isAtBottom();
    }
}

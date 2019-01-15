package com.pikerobodevils.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MotionProfileProgressCommand extends InstantCommand implements Comparable<MotionProfileProgressCommand> {

    Runnable runnable;

    private double scheduledStart;

    public MotionProfileProgressCommand(double scheduledStart, Runnable runnable, Subsystem... requires) {
        for (Subsystem subsystem : requires) {
            requires(subsystem);
        }
        this.scheduledStart = scheduledStart;
        this.runnable = runnable;
    }

    @Override
    public int compareTo(MotionProfileProgressCommand o) {
        return (int) (scheduledStart - o.scheduledStart);
    }

    public double getScheduledStartPercent() {
        return scheduledStart;
    }

    @Override
    protected void initialize() {
        runnable.run();
    }
}

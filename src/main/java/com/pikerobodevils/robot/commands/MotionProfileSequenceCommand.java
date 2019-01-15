package com.pikerobodevils.robot.commands;

import com.pikerobodevils.robot.subsystems.Drivetrain;

import java.util.Map;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class MotionProfileSequenceCommand extends Command {

    TreeMap<Double, InstantCommand> commands = new TreeMap<>();

    public MotionProfileSequenceCommand(MotionProfileProgressCommand... commandsToRun) {
        for (MotionProfileProgressCommand command : commandsToRun) {
            commands.put(command.getScheduledStartPercent(), command);
        }
    }

    @Override
    protected void execute() {
        Map<Double, InstantCommand> toRun = commands.subMap(0d, Drivetrain.getInstance().getProfilePercentComplete());
        toRun.forEach((time, command) -> {
            command.start();
            commands.remove(time);
        });
    }

    @Override
    protected boolean isFinished() {
        return commands.isEmpty();
    }

    public final synchronized void addProgressCommand() {

    }
}

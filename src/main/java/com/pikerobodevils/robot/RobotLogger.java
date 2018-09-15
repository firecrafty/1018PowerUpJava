package com.pikerobodevils.robot;

import com.pikerobodevils.lib.util.RobotMetadata;
import com.pikerobodevils.lib.util.RobotMode;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.FileWriter;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RobotLogger {
    private static FileWriter fileWriter;
    private static ConsoleWriter consoleWriter;

    static {
        int logNumber;
        if (Preferences.getInstance().containsKey("prevLogNumber")) {
            logNumber = 0;
            Preferences.getInstance().putInt("prevLogNumber", 0);
        } else {
            logNumber = Preferences.getInstance().getInt("prevLogNumber", 0) + 1;
            Preferences.getInstance().putInt("prevLogNumber", logNumber);
        }

        File logDir = new File("/c/logs/");
        logDir.mkdirs();
        StringBuilder builder = new StringBuilder();
        builder.append("log_");
        builder.append(logNumber);
        builder.append(".log");
        /* Can't confirm if values are available before DS connection
        builder.append(DriverStation.getInstance().isFMSAttached() ? "match" : "test").append("-");
        builder.append(DriverStation.getInstance().getEventName()).append("-");
        builder.append(DriverStation.getInstance().getMatchType()).append("-");
        builder.append(DriverStation.getInstance().getMatchNumber()).append(".log");
        */
        String canonPath = "";
        try {
            canonPath = logDir.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileWriter = new FileWriter(canonPath + File.separator + builder.toString(), false, true);
        consoleWriter = new ConsoleWriter();
        Configurator.defaultConfig()
                .writingThread(true)
                .writer(fileWriter, Level.TRACE)
                .writer(consoleWriter, Level.INFO)
                .maxStackTraceElements(-1)
                .formatPattern("{date} [{thread}] {level} {class}.{method}(): {message}")
                .activate();
    }

    public static void logRobotStart() {
        Logger.info("STARTING LOG");
        Logger.debug("DEBUG INFO:");
        Logger.debug(RobotMetadata::getInstance);
    }

    public static void logRobotInitComplete() {

        Logger.info("Robot initialized! Ready!");
    }

    public static void logSubsystemConstructionStart(Subsystem subsystem) {
        Logger.debug("Configuring {} hardware...", subsystem::getName);
    }

    public static void logSubsystemConstructionFinish(Subsystem subsystem) {
        Logger.debug("{} hardware configured!", subsystem::getName);
    }

    public static void logStateTransition(RobotMode mode) {
        Logger.debug("Entering {} mode!", mode);
    }

}

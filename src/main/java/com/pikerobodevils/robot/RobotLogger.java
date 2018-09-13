package com.pikerobodevils.robot;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.FileWriter;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.DriverStation;

public class RobotLogger {
    private static FileWriter fileWriter;
    private static ConsoleWriter consoleWriter;

    static {

        File logDir = new File("/c/logs/");
        logDir.mkdirs();
        StringBuilder builder = new StringBuilder();
        builder.append(DriverStation.getInstance().isFMSAttached() ? "match" : "test").append("-");
        builder.append(DriverStation.getInstance().getEventName()).append("-");
        builder.append(DriverStation.getInstance().getMatchType()).append("-");
        builder.append(DriverStation.getInstance().getMatchNumber()).append(".log");
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

    public static void logRobotInit() {
        Logger.info("Robot initialized! Ready!");
    }

}

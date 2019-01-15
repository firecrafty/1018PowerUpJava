package com.pikerobodevils.lib.motion;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import jaci.pathfinder.Trajectory;

public class SimpleProfileParser {
    private static String pathFolder = "/c/Paths/";

    public static final String separator = ",";
    public static final String leftMarker = "_left";
    public static final String rightMarker = "_right";
    public static final String fileExt = ".csv";

    private SimpleProfileParser() {
    }

    public static DrivetrainProfile getDrivetrainProfile(String profileName) {
        return new DrivetrainProfile(getPathfinderProfile(profileName));
    }

    public static Pair<Trajectory, Trajectory> getPathfinderProfile(String profileName) {
        StringBuilder leftBuilder = new StringBuilder(pathFolder)
                .append(profileName)
                .append(File.separatorChar)
                .append(profileName);
        StringBuilder rightBuilder = new StringBuilder(leftBuilder.toString());
        leftBuilder.append(leftMarker).append(fileExt);
        rightBuilder.append(rightMarker).append(fileExt);

        String leftFile = leftBuilder.toString();
        String rightFile = rightBuilder.toString();
        return Pair.of(parseProfileCsv(leftFile), parseProfileCsv(rightFile));
    }


    public static Trajectory parseProfileCsv(String fileName) {
        BufferedReader br;
        String line;
        List<Trajectory.Segment> list = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                list.add(parseSegmentCsvString(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Logger.error(e, "Exception occurred while loading CSV path");
        }
        return new Trajectory(list.toArray(new Trajectory.Segment[0]));
    }

    public static Trajectory.Segment parseSegmentCsvString(String segmentString) {
        segmentString = segmentString.replaceAll("\\s+", "");
        String[] segmentPoints = segmentString.split(separator);
        double dt = Double.parseDouble(segmentPoints[2]) / 1000;
        double position = Double.parseDouble(segmentPoints[0]);
        double velocity = Double.parseDouble(segmentPoints[1]);
        System.out.println(dt + "," + position + "," + velocity);
        return new Trajectory.Segment(dt, 0, 0, position, velocity, 0, 0, 0);
    }

    public static void setPathFolder(String pathFolder) {
        SimpleProfileParser.pathFolder = pathFolder;
    }

    public static String getPathFolder() {
        return pathFolder;
    }
}

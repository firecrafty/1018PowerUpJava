package com.pikerobodevils.lib.motion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pikerobodevils.lib.util.HashGeneratorUtils;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class ProfileGenerator {
    private static final String cacheFolder = "./PATHS/";
    private static final String fileExtension = "csv";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static DrivetrainProfile getDrivetrainProfile(ProfileSettings profile) {
        return new DrivetrainProfile(getPathfinderProfile(profile));
    }

    public static Pair<Trajectory, Trajectory> getPathfinderProfile(ProfileSettings profile) {
        String serialized;
        try {
            serialized = objectMapper.writeValueAsString(profile);
        } catch (JsonProcessingException e) {
            serialized = null;
            e.printStackTrace();
        }
        File file = new File(cacheFolder + HashGeneratorUtils.generateMD5(serialized) + "." + fileExtension);
        Trajectory motionProfile;
        if (file.exists()) {
            motionProfile = Pathfinder.readFromCSV(file);
        } else {
            Waypoint[] waypoints = new Waypoint[profile.waypoints.size()];
            profile.waypoints.toArray(waypoints);
            Trajectory.Config config = new Trajectory.Config(
                    profile.fitMethod,
                    profile.sampleRate,
                    1.0 / profile.hz,
                    profile.maxVel,
                    profile.maxAcc,
                    profile.maxJerk
            );
            motionProfile = Pathfinder.generate(waypoints, config);
            Pathfinder.writeToCSV(file, motionProfile);
        }
        TankModifier modifier = new TankModifier(motionProfile).modify(profile.wheelbaseWidth);
        return Pair.of(modifier.getLeftTrajectory(), modifier.getRightTrajectory());
    }
}

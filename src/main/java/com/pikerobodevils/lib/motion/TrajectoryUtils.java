package com.pikerobodevils.lib.motion;

import jaci.pathfinder.Trajectory;

public class TrajectoryUtils {

    public static Trajectory invert(Trajectory trajectory) {
        Trajectory.Segment[] segments = trajectory.segments;
        for (Trajectory.Segment segment : segments) {

            segment.position = -segment.position;
            segment.velocity = -segment.velocity;
            segment.acceleration = -segment.acceleration;
            segment.jerk = -segment.jerk;
        }
        return new Trajectory(segments);
    }

    public static Trajectory reverse(Trajectory trajectory) {
        Trajectory.Segment[] segments = trajectory.segments;
        double endPos = segments[segments.length - 1].position;
        int size = segments.length;
        Trajectory.Segment[] newSegments = new Trajectory.Segment[size];

        for (Trajectory.Segment segment : segments) {
            segment.position = endPos - segment.position;
            segment.acceleration = -segment.acceleration;
            segment.jerk = -segment.jerk;
        }
        for (int i = 0; i < size; i++) {
            int index = (size - 1) - i;
            newSegments[index] = segments[i];
        }

        return new Trajectory(newSegments);
    }

    private TrajectoryUtils() {
    }
}

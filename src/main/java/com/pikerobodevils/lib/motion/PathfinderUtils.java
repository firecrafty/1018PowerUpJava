package com.pikerobodevils.lib.motion;

import com.ctre.phoenix.motion.TrajectoryPoint;

import jaci.pathfinder.Trajectory;

public class PathfinderUtils {

    public static Trajectory.Segment talonPointToPathfinderSegment(TrajectoryPoint point) {
        return new Trajectory.Segment(
                point.timeDur.value / 1000d,
                0,
                0,
                point.position,
                point.velocity,
                0,
                0,
                point.headingDeg
        );
    }

    public static TrajectoryPoint pathfinderSegmentToTalonPoint(Trajectory.Segment segment) {
        TrajectoryPoint point = new TrajectoryPoint();
        point.timeDur = TrajectoryPoint.TrajectoryDuration.valueOf((int) (segment.dt * 1000));
        point.position = segment.position;
        point.velocity = segment.velocity;
        point.headingDeg = segment.heading;
        return point;
    }

    private PathfinderUtils() {
    }
}

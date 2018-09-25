package com.pikerobodevils.lib.motion;

import com.ctre.phoenix.motion.TrajectoryPoint;

import org.junit.jupiter.api.Test;

import jaci.pathfinder.Trajectory;

import static com.pikerobodevils.lib.test.TestUtils.assertSegmentEquals;
import static com.pikerobodevils.lib.test.TestUtils.assertTrajectoryPointEquals;

public class TestPathfinderUtils {

    Trajectory.Segment segment = new Trajectory.Segment(0.05, 1, 2, 3, 4, 5, 6, 7);
    TrajectoryPoint point = new TrajectoryPoint() {
        {
            timeDur = TrajectoryDuration.Trajectory_Duration_50ms;
            position = 3;
            velocity = 4;
            headingDeg = 7;
        }
    };
    Trajectory.Segment lossySegment = new Trajectory.Segment(0.05, 0, 0, 3, 4, 0, 0, 7);


    @Test
    void testPathfinderSegmentToTalonPoint() {
        TrajectoryPoint result = PathfinderUtils.pathfinderSegmentToTalonPoint(segment);
        assertTrajectoryPointEquals(point, result);
    }

    @Test
    void testLossyTalonPointToPathfinderSegment() {
        Trajectory.Segment result = PathfinderUtils.talonPointToPathfinderSegment(point);
        assertSegmentEquals(lossySegment, result);
    }
}

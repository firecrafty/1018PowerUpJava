package com.pikerobodevils.lib.motion;

import org.junit.jupiter.api.Test;

import jaci.pathfinder.Trajectory;

import static com.pikerobodevils.lib.test.TestUtils.assertTrajectoryEquals;
import static jaci.pathfinder.Pathfinder.d2r;

public class TestTrajectoryUtils {

    Trajectory testPath = new Trajectory(new Trajectory.Segment[]{
            new Trajectory.Segment(0.05, 0, 0, 0, 0, 0, 0, d2r(45)),
            new Trajectory.Segment(0.05, 1, 1, 1, 1, 1, 1, d2r(45)),
            new Trajectory.Segment(0.05, 2, 2, 2, 1, 1, 0, d2r(45)),
            new Trajectory.Segment(0.05, 3, 3, 5, 3, 2, 1, d2r(45)),
            new Trajectory.Segment(0.05, 4, 4, 6, 1, -2, -4, d2r(45)),
            new Trajectory.Segment(0.05, 5, 5, 6, 0, 0, 0, d2r(45))
    });

    Trajectory comparisonInvertedPath = new Trajectory(new Trajectory.Segment[]{
            new Trajectory.Segment(0.05, 0, 0, 0, 0, 0, 0, d2r(45)),
            new Trajectory.Segment(0.05, 1, 1, -1, -1, -1, -1, d2r(45)),
            new Trajectory.Segment(0.05, 2, 2, -2, -1, -1, 0, d2r(45)),
            new Trajectory.Segment(0.05, 3, 3, -5, -3, -2, -1, d2r(45)),
            new Trajectory.Segment(0.05, 4, 4, -6, -1, 2, 4, d2r(45)),
            new Trajectory.Segment(0.05, 5, 5, -6, 0, 0, 0, d2r(45))
    });
    Trajectory comparisonReversePath = new Trajectory(new Trajectory.Segment[]{
            new Trajectory.Segment(0.05, 5, 5, 0, 0, 0, 0, d2r(45)),
            new Trajectory.Segment(0.05, 4, 4, 0, 1, 2, 4, d2r(45)),
            new Trajectory.Segment(0.05, 3, 3, 1, 3, -2, -1, d2r(45)),
            new Trajectory.Segment(0.05, 2, 2, 4, 1, -1, 0, d2r(45)),
            new Trajectory.Segment(0.05, 1, 1, 5, 1, -1, -1, d2r(45)),
            new Trajectory.Segment(0.05, 0, 0, 6, 0, 0, 0, d2r(45))
    });

    @Test
    void testInvertPath() {
        Trajectory invertedPath = TrajectoryUtils.invert(testPath);
        assertTrajectoryEquals(comparisonInvertedPath, invertedPath);
    }

    @Test
    void testReversePath() {
        Trajectory reversedPath = TrajectoryUtils.reverse(testPath);
        assertTrajectoryEquals(comparisonReversePath, reversedPath);
    }
}

package com.pikerobodevils.lib.test;

import com.ctre.phoenix.motion.TrajectoryPoint;

import jaci.pathfinder.Trajectory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {
    public static void assertTrajectoryEquals(Trajectory expected, Trajectory actual) {
        assertEquals(expected.length(), actual.length());
        for (int i = 0; i < expected.length(); i++) {
            //assertEquals does not work since Trajectory.equals() takes a Segment rather than an object, thus not overriding Object.equals()
            assertSegmentEquals(expected.get(i), actual.get(i));
        }
    }

    public static void assertSegmentEquals(Trajectory.Segment expected, Trajectory.Segment actual) {
        assertTrue(expected.equals(actual));
    }

    public static void assertTrajectoryPointEquals(TrajectoryPoint expected, TrajectoryPoint actual) {
        assertEquals(expected.isLastPoint, actual.isLastPoint);
        assertEquals(expected.auxiliaryPos, actual.auxiliaryPos);
        assertEquals(expected.headingDeg, actual.headingDeg);
        assertEquals(expected.position, actual.position);
        assertEquals(expected.velocity, actual.velocity);
        assertEquals(expected.timeDur, actual.timeDur);
        assertEquals(expected.profileSlotSelect0, actual.profileSlotSelect0);
        assertEquals(expected.profileSlotSelect1, actual.profileSlotSelect1);
        assertEquals(expected.zeroPos, actual.zeroPos);
    }
}

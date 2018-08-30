package com.pikerobodevils.lib.test;

import jaci.pathfinder.Trajectory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {
    public static void assertTrajectoryEquals(Trajectory expected, Trajectory actual) {
        assertEquals(expected.length(), actual.length());
        for (int i = 0; i < expected.length(); i++) {
            //assertEquals does not work since Trajectory.equals() takes a Segment rather than an object, thus not overriding Object.equals()
            assertTrue(expected.get(i).equals(actual.get(i)));
        }
    }
}
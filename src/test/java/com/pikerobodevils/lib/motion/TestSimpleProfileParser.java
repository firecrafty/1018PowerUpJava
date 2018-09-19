package com.pikerobodevils.lib.motion;

import org.junit.jupiter.api.Test;

import jaci.pathfinder.Trajectory;

import static com.pikerobodevils.lib.test.TestUtils.assertSegmentEquals;

public class TestSimpleProfileParser {

    @Test
    void testParseCsvString() {
        Trajectory.Segment expected = new Trajectory.Segment(0.05, 0, 0, 1, 2, 0, 0, 0);
        String segmentString = "1, 2, 50";
        Trajectory.Segment actual = SimpleProfileParser.parseSegmentCsvString(segmentString);
        assertSegmentEquals(expected, actual);
    }
}

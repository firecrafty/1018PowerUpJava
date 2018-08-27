package com.pikerobodevils.lib.motion;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.util.List;

public class ProfileSettings {
    public int hz;
    public double maxVel;
    public double maxAcc;
    public double maxJerk;
    public double wheelbaseWidth;
    public List<Waypoint> waypoints;
    public Trajectory.FitMethod fitMethod;
    public int sampleRate;

    public ProfileSettings(int hz, double maxVel, double maxAcc, double maxJerk, double wheelbaseWidth, List<Waypoint> waypoints, Trajectory.FitMethod fitMethod, int sampleRate) {
        this.hz = hz;
        this.maxVel = maxVel;
        this.maxAcc = maxAcc;
        this.maxJerk = maxJerk;
        this.wheelbaseWidth = wheelbaseWidth;
        this.waypoints = waypoints;
        this.fitMethod = fitMethod;
        this.sampleRate = sampleRate;
    }
}

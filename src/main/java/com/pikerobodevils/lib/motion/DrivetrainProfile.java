package com.pikerobodevils.lib.motion;

import org.apache.commons.lang3.tuple.Pair;

import jaci.pathfinder.Trajectory;

public class DrivetrainProfile {
    private Pair<Trajectory, Trajectory> path;

    public DrivetrainProfile(Pair<Trajectory, Trajectory> path) {
        this.path = path;
    }

    public Trajectory getLeft() {
        return path.getLeft();
    }

    public Trajectory getRight() {
        return path.getRight();
    }

    synchronized public DrivetrainProfile mirror() {
        path = Pair.of(path.getRight(), path.getLeft());
        return this;
    }

    synchronized public DrivetrainProfile invert() {
        path = Pair.of(TrajectoryUtils.invert(path.getRight()), TrajectoryUtils.invert(path.getLeft()));
        return this;
    }

    synchronized public DrivetrainProfile reverse() {
        path = Pair.of(TrajectoryUtils.reverse(path.getLeft()), TrajectoryUtils.reverse(path.getRight()));
        return this;
    }


}

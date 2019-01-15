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
        return new DrivetrainProfile(Pair.of(path.getRight(), path.getLeft()));
    }

    synchronized public DrivetrainProfile invert() {
        return new DrivetrainProfile(Pair.of(TrajectoryUtils.invert(path.getRight()), TrajectoryUtils.invert(path.getLeft())));
    }

    synchronized public DrivetrainProfile reverse() {
        return new DrivetrainProfile(Pair.of(TrajectoryUtils.reverse(path.getLeft()), TrajectoryUtils.reverse(path.getRight())));
    }

    public int length() {
        return Math.max(getLeft().length(), getRight().length());
    }


}

package com.pikerobodevils.lib.util.drive;

/**
 * @author Ryan Blue
 */
public class DriveSignal {
    public double left, right;

    public DriveSignal() {
    }

    public DriveSignal(double left, double right) {
        this.left = left;
        this.right = right;
    }

    public static DriveSignal NEUTRAL = new DriveSignal(0, 0);

    @Override
    public String toString() {
        //For debug
        return "DriveSignal{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}

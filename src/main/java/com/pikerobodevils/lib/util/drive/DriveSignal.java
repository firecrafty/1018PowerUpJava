package com.pikerobodevils.lib.util.drive;

/**
 * @author Ryan Blue
 */
public class DriveSignal {
    public double left, right;

    public static DriveSignal NEUTRAL = new DriveSignal(0, 0);

    public DriveSignal() {
    }

    public DriveSignal(double left, double right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        //For debug
        return "DriveSignal{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}

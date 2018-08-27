package com.pikerobodevils.lib.util;

public class MathUtils {
    private MathUtils() {
    }

    public static double coerceInRange(double value, double min, double max) {
        checkMaxMin(min, max);
        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        }
        return value;
    }

    public static boolean isInRange(double value, double min, double max) {
        checkMaxMin(min, max);
        return value >= min && value <= max;
    }

    public static void checkMaxMin(double min, double max) {
        if (max <= min) throw new ArithmeticException("Minimum must be less than maximum");
    }


}

package com.pikerobodevils.lib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMathUtils {

    @Test
    void testCoerceInRange() {
        assertEquals(5, MathUtils.coerceInRange(7, 0, 5));
        assertEquals(3, MathUtils.coerceInRange(3, 0, 5));
        assertEquals(0, MathUtils.coerceInRange(-14, 0, 5));
        assertThrows(ArithmeticException.class, () -> MathUtils.coerceInRange(2, 4, 0), "Minimum must be less than maximum");
    }

    @Test
    void testIsInRange() {
        assertEquals(false, MathUtils.isInRange(-13, 0, 5));
        assertEquals(true, MathUtils.isInRange(0, 0, 5));
        assertEquals(true, MathUtils.isInRange(3, 0, 5));
        assertEquals(true, MathUtils.isInRange(5, 0, 5));
        assertEquals(false, MathUtils.isInRange(15, 0, 5));
        assertThrows(ArithmeticException.class, () -> MathUtils.isInRange(2, 4, 0), "Minimum must be less than maximum");
    }

    @Test
    void testCheckMinMax() {
        assertThrows(ArithmeticException.class, () -> MathUtils.isInRange(0, 10, 0));
    }

}

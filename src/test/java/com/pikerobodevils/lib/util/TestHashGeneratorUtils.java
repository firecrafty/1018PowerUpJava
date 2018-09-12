package com.pikerobodevils.lib.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHashGeneratorUtils {
    @Test
    void testGenerateMD5() {
        assertEquals("1339129e7f8189be426f439c7e61e55c", HashGeneratorUtils.generateMD5("testGenerateMD5"));
        assertEquals("9c5d57a51020ce54975b7d362df07616", HashGeneratorUtils.generateMD5("1339129e7f8189be426f439c7e61e55c"));
    }

    @Test
    void testGenerateSHA1() {
        assertEquals("94ad9ec0c754fdd6dcfca417bd9fc059246672d6", HashGeneratorUtils.generateSHA1("testGenerateSHA1"));
        assertEquals("e3793f5801c336b64fbc25b3e4e9bab98e6f3c04", HashGeneratorUtils.generateSHA1("94ad9ec0c754fdd6dcfca417bd9fc059246672d6"));
    }

    @Test
    void testGenerateSHA256() {
        assertEquals("57dad9831a8b5dd5f0e1fe46489709c20afdabd3dd65fcbaec1834f3c4c4ac78", HashGeneratorUtils.generateSHA256("testGenerateSHA256"));
        assertEquals("90cc731d92335e8dd2e243570a6cd070d2184b204c8c0c11acaaaf8548d88805", HashGeneratorUtils.generateSHA256("57dad9831a8b5dd5f0e1fe46489709c20afdabd3dd65fcbaec1834f3c4c4ac78"));
    }
}

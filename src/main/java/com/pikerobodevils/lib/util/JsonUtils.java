package com.pikerobodevils.lib.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void printAsJson(Object object, String label) {
        try {
            System.out.println(label + objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void printAsJson(Object object) {
        printAsJson(object, "");
    }

    private JsonUtils() {
    }
}

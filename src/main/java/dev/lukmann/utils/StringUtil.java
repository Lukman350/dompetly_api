package dev.lukmann.utils;

import java.nio.ByteBuffer;

public class StringUtil {
    public static String encodeStringToBase64(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return java.util.Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static Long decodeBase64ToInteger(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            return null;
        }

        byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64String);
        ByteBuffer buffer = ByteBuffer.wrap(decodedBytes);
        buffer.order(java.nio.ByteOrder.BIG_ENDIAN);
        return buffer.getLong();
    }
}

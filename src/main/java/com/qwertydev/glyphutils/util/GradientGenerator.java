package com.qwertydev.glyphutils.util;

public class GradientGenerator {

    public static String generate(String text, String color1, String color2,
                                  boolean bold, boolean italic, boolean underline, boolean strikethrough) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        int[] rgb1 = hexToRgb(color1);
        int[] rgb2 = hexToRgb(color2);

        StringBuilder result = new StringBuilder();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);

            double ratio = length == 1 ? 0 : (double) i / (length - 1);

            int r = (int) (rgb1[0] + (rgb2[0] - rgb1[0]) * ratio);
            int g = (int) (rgb1[1] + (rgb2[1] - rgb1[1]) * ratio);
            int b = (int) (rgb1[2] + (rgb2[2] - rgb1[2]) * ratio);

            result.append(String.format("&#%02X%02X%02X", r, g, b));

            if (bold) result.append("&l");
            if (italic) result.append("&o");
            if (underline) result.append("&n");
            if (strikethrough) result.append("&m");

            result.append(c);
        }

        return result.toString();
    }

    private static int[] hexToRgb(String hex) {
        hex = hex.replace("&#", "").replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new int[]{r, g, b};
    }
}
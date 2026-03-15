package com.qwertydev.glyphutils.util;

import java.util.HashMap;
import java.util.Map;

public class SmallCapsConverter {
    
    private static final Map<Character, Character> CONVERSION_MAP = new HashMap<>();
    
    static {
        CONVERSION_MAP.put('a', 'ᴀ');
        CONVERSION_MAP.put('b', 'ʙ');
        CONVERSION_MAP.put('c', 'ᴄ');
        CONVERSION_MAP.put('ç', 'ç');
        CONVERSION_MAP.put('d', 'ᴅ');
        CONVERSION_MAP.put('e', 'ᴇ');
        CONVERSION_MAP.put('f', 'ғ');
        CONVERSION_MAP.put('g', 'ɢ');
        CONVERSION_MAP.put('ğ', 'ğ');
        CONVERSION_MAP.put('h', 'ʜ');
        CONVERSION_MAP.put('ı', 'ı');
        CONVERSION_MAP.put('i', 'ɪ');
        CONVERSION_MAP.put('j', 'ᴊ');
        CONVERSION_MAP.put('k', 'ᴋ');
        CONVERSION_MAP.put('l', 'ʟ');
        CONVERSION_MAP.put('m', 'ᴍ');
        CONVERSION_MAP.put('n', 'ɴ');
        CONVERSION_MAP.put('o', 'ᴏ');
        CONVERSION_MAP.put('ö', 'ö');
        CONVERSION_MAP.put('p', 'ᴘ');
        CONVERSION_MAP.put('q', 'ꞯ');
        CONVERSION_MAP.put('r', 'ʀ');
        CONVERSION_MAP.put('s', 's');
        CONVERSION_MAP.put('ş', 'ş');
        CONVERSION_MAP.put('t', 'ᴛ');
        CONVERSION_MAP.put('u', 'ᴜ');
        CONVERSION_MAP.put('ü', 'ü');
        CONVERSION_MAP.put('v', 'ᴠ');
        CONVERSION_MAP.put('w', 'ᴡ');
        CONVERSION_MAP.put('x', 'x');
        CONVERSION_MAP.put('y', 'ʏ');
        CONVERSION_MAP.put('z', 'ᴢ');
    }
    
    public static String convert(String input) {
        StringBuilder result = new StringBuilder();
        
        for (char c : input.toCharArray()) {
            char lowerChar = Character.toLowerCase(c);
            result.append(CONVERSION_MAP.getOrDefault(lowerChar, c));
        }
        
        return result.toString();
    }
}
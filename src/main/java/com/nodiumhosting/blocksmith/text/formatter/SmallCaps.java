package com.nodiumhosting.blocksmith.text.formatter;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class SmallCaps implements TextFormatter {
    private static final Map<Character, Character> LOOKUP = Map.ofEntries(
            Map.entry('a', 'ᴀ'),
            Map.entry('b', 'ʙ'),
            Map.entry('c', 'ᴄ'),
            Map.entry('d', 'ᴅ'),
            Map.entry('e', 'ᴇ'),
            Map.entry('f', 'ꜰ'),
            Map.entry('g', 'ɢ'),
            Map.entry('h', 'ʜ'),
            Map.entry('i', 'ɪ'),
            Map.entry('j', 'ᴊ'),
            Map.entry('k', 'ᴋ'),
            Map.entry('l', 'ʟ'),
            Map.entry('m', 'ᴍ'),
            Map.entry('n', 'ɴ'),
            Map.entry('o', 'ᴏ'),
            Map.entry('p', 'ᴘ'),
            Map.entry('q', 'ǫ'),
            Map.entry('r', 'ʀ'),
            Map.entry('s', 'ꜱ'),
            Map.entry('t', 'ᴛ'),
            Map.entry('u', 'ᴜ'),
            Map.entry('v', 'ᴠ'),
            Map.entry('w', 'ᴡ'),
            Map.entry('x', 'x'),
            Map.entry('y', 'ʏ'),
            Map.entry('z', 'ᴢ')
    );

    public String format(@NotNull String input) {
        return input
                .toLowerCase()
                .chars()
                .mapToObj(i -> (char) i)
                .map(c -> LOOKUP.getOrDefault(c, c))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}

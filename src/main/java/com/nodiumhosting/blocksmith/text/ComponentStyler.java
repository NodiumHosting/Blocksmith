package com.nodiumhosting.blocksmith.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.EnumSet;

public class ComponentStyler {
    private static final Style RESET_STYLE = Style.empty().decorations(EnumSet.allOf(TextDecoration.class), false);
    private static final Component RESET_COMPONENT = Component.empty().style(RESET_STYLE);
    public static Component prependReset(Component component) {
        return RESET_COMPONENT.append(component);
    }
}

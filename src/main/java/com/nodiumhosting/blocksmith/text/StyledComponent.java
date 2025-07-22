package com.nodiumhosting.blocksmith.text;

import com.nodiumhosting.blocksmith.text.formatter.TextFormatter;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

import java.util.List;

public record StyledComponent(@Getter @NonNull String text,
                              @Getter @NonNull Style style,
                              @NonNull List<TextFormatter> textFormatters) {
    public StyledComponent(@NonNull String text, @NonNull Style style, @NonNull List<TextFormatter> textFormatters) {
        this.text = text;
        this.style = style;
        this.textFormatters = textFormatters;
    }

    public StyledComponent(@NonNull String text, @NonNull Style style) {
        this(text, style, List.of());
    }

    public Component component() {
        String formattedText = textFormatters.stream()
                .reduce(text, (t, formatter) -> formatter.format(t), (t1, t2) -> t1);
        return Component.text(formattedText, style);
    }

    public static StyledComponent empty() {
        return new StyledComponent("", Style.empty());
    }
}
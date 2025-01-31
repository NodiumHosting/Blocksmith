package com.nodiumhosting.blocksmith.registry;

import lombok.Getter;
import net.kyori.adventure.text.Component;

@Getter
public abstract class ObjectTypeData {
    @Getter
    private final Component name;

    protected ObjectTypeData(Component name) {
        this.name = name;
    }
}

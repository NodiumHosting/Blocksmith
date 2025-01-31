package com.nodiumhosting.blocksmith.common;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public class Identifier {
    @Getter
    private final String namespace;
    @Getter
    private final String path;

    public Identifier(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    @Nullable
    public Identifier of(String id) {
        String[] split = id.split(":");
        if (split.length == 2) {
            return new Identifier(split[0], split[1]);
        } else if (split.length == 1) {
            return new Identifier("blocksmith", split[0]);
        }
        return null;
    }

    public String toString() {
        return namespace + ":" + path;
    }
}

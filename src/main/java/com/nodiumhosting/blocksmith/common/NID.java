package com.nodiumhosting.blocksmith.common;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public class NID {
    @Getter
    private final String namespace;
    @Getter
    private final String path;

    public NID(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    @Nullable
    public static NID of(String id) {
        String[] split = id.split(":");
        if (split.length == 2) {
            return new NID(split[0], split[1]);
        }
        /*
        else if (split.length == 1) {
            return new NID("blocksmith", split[0]);
        }
        */
        return null;
    }

    public String toString() {
        return namespace + ":" + path;
    }
}

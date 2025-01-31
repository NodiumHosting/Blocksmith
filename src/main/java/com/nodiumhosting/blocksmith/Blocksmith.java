package com.nodiumhosting.blocksmith;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Blocksmith {
    public static final Logger LOGGER = LoggerFactory.getLogger(Blocksmith.class);

    @Getter
    private final UUID uuid;
    public final BlocksmithResourcePack blocksmithResourcePack;

    public Blocksmith(UUID uuid) {
        this.uuid = uuid;
        this.blocksmithResourcePack = new BlocksmithResourcePack(uuid);
    }
}

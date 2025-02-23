package com.nodiumhosting.blocksmith;

import com.nodiumhosting.blocksmith.resourcepack.BlocksmithResourcePack;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Blocksmith {
    public static final Logger LOGGER = LoggerFactory.getLogger(Blocksmith.class);
    public final BlocksmithResourcePack blocksmithResourcePack;
    @Getter
    private final UUID uuid;

    public Blocksmith(UUID uuid) {
        this.uuid = uuid;
        this.blocksmithResourcePack = new BlocksmithResourcePack(uuid);
    }
}

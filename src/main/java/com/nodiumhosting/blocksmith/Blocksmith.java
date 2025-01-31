package com.nodiumhosting.blocksmith;

import com.nodiumhosting.blocksmith.registry.BBlockTypeRegistry;
import com.nodiumhosting.blocksmith.registry.BItemTypeRegistry;
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
    @Getter
    private final BBlockTypeRegistry blockTypeRegistry = new BBlockTypeRegistry();
    @Getter
    private final BItemTypeRegistry itemTypeRegistry = new BItemTypeRegistry();

    public Blocksmith(UUID uuid) {
        this.uuid = uuid;
        this.blocksmithResourcePack = new BlocksmithResourcePack(uuid);
    }
}

package com.nodiumhosting.blocksmith;

import com.nodiumhosting.blocksmith.block.BlocksmithBlockBreakListener;
import com.nodiumhosting.blocksmith.block.BlocksmithBlockManager;
import com.nodiumhosting.blocksmith.block.BlocksmithBlockPlaceListener;
import com.nodiumhosting.blocksmith.block.BlocksmithBlockTypeRegistry;
import net.minestom.server.instance.InstanceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Blocksmith {
    public static final Logger LOGGER = LoggerFactory.getLogger(Blocksmith.class);

    public InstanceManager instanceManager;

    private final BlocksmithResourcePack blocksmithResourcePack;
    private final BlocksmithBlockTypeRegistry blocksmithBlockTypeRegistry;
    private final BlocksmithBlockManager blocksmithBlockManager;

    public Blocksmith() {
        this.blocksmithResourcePack = new BlocksmithResourcePack();
        this.blocksmithBlockTypeRegistry = new BlocksmithBlockTypeRegistry(new HashMap<>());
        this.blocksmithBlockManager = new BlocksmithBlockManager();
    }

    public void init(InstanceManager instanceManager) {
        this.instanceManager = instanceManager;
        new BlocksmithBlockPlaceListener(this, blocksmithBlockManager, blocksmithBlockTypeRegistry);
        new BlocksmithBlockBreakListener(blocksmithBlockManager);
    }

    public BlocksmithResourcePack getPack() {
        return blocksmithResourcePack;
    }

    public BlocksmithBlockTypeRegistry getBlockTypeRegistry() {
        return blocksmithBlockTypeRegistry;
    }
}

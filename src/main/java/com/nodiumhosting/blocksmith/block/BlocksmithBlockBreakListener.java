package com.nodiumhosting.blocksmith.block;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerBlockBreakEvent;

public class BlocksmithBlockBreakListener {
    private final BlocksmithBlockManager blocksmithBlockManager;

    public BlocksmithBlockBreakListener(BlocksmithBlockManager blocksmithBlockManager) {
        this.blocksmithBlockManager = blocksmithBlockManager;
        MinecraftServer.getGlobalEventHandler().addListener(PlayerBlockBreakEvent.class, this::handle);
    }

    public void handle(PlayerBlockBreakEvent event) {
        BlocksmithBlock blocksmithBlock = blocksmithBlockManager.getBlock(event.getInstance(), event.getBlockPosition());
        if (blocksmithBlock == null) {
            return;
        }

        blocksmithBlockManager.breakBlock(event.getInstance(), event.getBlockPosition());
    }
}

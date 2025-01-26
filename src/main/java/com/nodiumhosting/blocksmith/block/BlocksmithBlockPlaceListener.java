package com.nodiumhosting.blocksmith.block;

import com.nodiumhosting.blocksmith.Blocksmith;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

public class BlocksmithBlockPlaceListener {
    private final Blocksmith blocksmith;
    private final BlocksmithBlockManager blocksmithBlockManager;
    private final BlocksmithBlockTypeRegistry blocksmithBlockTypeRegistry;

    public BlocksmithBlockPlaceListener(Blocksmith blocksmith, BlocksmithBlockManager blocksmithBlockManager, BlocksmithBlockTypeRegistry blocksmithBlockTypeRegistry) {
        this.blocksmith = blocksmith;
        this.blocksmithBlockManager = blocksmithBlockManager;
        this.blocksmithBlockTypeRegistry = blocksmithBlockTypeRegistry;
        MinecraftServer.getGlobalEventHandler().addListener(PlayerBlockPlaceEvent.class, this::handle);
    }

    public void handle(PlayerBlockPlaceEvent event) {

        ItemStack item = event.getPlayer().getItemInHand(event.getHand());
        if (!item.hasTag(Tag.String("blocksmith_block_type"))) {
            return;
        }

        BlocksmithBlockType blockType = blocksmithBlockTypeRegistry.get(item.getTag(Tag.String("blocksmith_block_type")));
        if (blockType == null) {
            return;
        }

        event.setCancelled(true);
        blocksmithBlockManager.placeBlock(blocksmith, event.getInstance(), event.getBlockPosition(), blockType);
    }
}

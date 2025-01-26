package com.nodiumhosting.blocksmith.block;

import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;

public class BlockSmithBlockItem {
    public static ItemStack of(BlocksmithBlockType blockSmithBlockType, BlocksmithBlockTypeRegistry blockSmithBlockTypeRegistry, Material material, int customModelData) {
        String blockSmithBlockTypeName = blockSmithBlockTypeRegistry.getName(blockSmithBlockType);
        if (blockSmithBlockTypeName == null) {
            return ItemStack.AIR;
        }

        return ItemStack.builder(material)
                .customModelData(customModelData)
                .set(Tag.String("blocksmith_block_type"), blockSmithBlockTypeName)
                .build();
    }
}

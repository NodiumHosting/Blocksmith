package com.nodiumhosting.blocksmith.block;

import com.nodiumhosting.blocksmith.Blocksmith;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlocksmithBlockManager {
    private final List<BlocksmithBlock> blocks;

    public BlocksmithBlockManager() {
        this.blocks = new ArrayList<>();
    }

    BlocksmithBlock placeBlock(Blocksmith blocksmith, Instance instance, BlockVec location, BlocksmithBlockType blockType) {
        BlocksmithBlock block = BlocksmithBlock.create(blocksmith, instance, location, blockType);
        blocks.add(block);

        return block;
    }

    @Nullable
    BlocksmithBlock getBlock(Instance instance, BlockVec location) {
        return blocks.stream().filter(b -> {
            BlockVec bLoc = b.getLocation();
            boolean locEq = bLoc.equals(location);
            UUID bIUuid = b.getInstanceUuid();
            boolean iUuidEq = bIUuid.equals(instance.getUniqueId());

            return locEq && iUuidEq;
        }).findFirst().orElse(null);
    }

    void breakBlock(Instance instance, BlockVec location) {
        BlocksmithBlock block = getBlock(instance, location);
        if (block == null) {
            return;
        }

        blocks.remove(block);
        block.breakBlock();
    }
}

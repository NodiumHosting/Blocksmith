package com.nodiumhosting.blocksmith.block;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BlocksmithBlockTypeRegistry {
    private final Map<String, BlocksmithBlockType> blockTypes;

    public BlocksmithBlockTypeRegistry(Map<String, BlocksmithBlockType> blockTypes) {
        this.blockTypes = blockTypes;
    }

    @Nullable
    public BlocksmithBlockType register(String name, BlocksmithBlockType blockType) {
        if (blockTypes.containsKey(name)) {
            return null;
        }
        blockTypes.put(name, blockType);
        return blockTypes.get(name);
    }

    @Nullable
    public BlocksmithBlockType get(String name) {
        return blockTypes.get(name);
    }

    @Nullable
    public String getName(BlocksmithBlockType blockType) {
        for (Map.Entry<String, BlocksmithBlockType> entry : blockTypes.entrySet()) {
            if (entry.getValue().equals(blockType)) {
                return entry.getKey();
            }
        }
        return null;
    }
}

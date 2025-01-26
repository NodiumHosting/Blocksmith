package com.nodiumhosting.blocksmith.block;

import com.nodiumhosting.blocksmith.display.DisplayStructure;
import lombok.Getter;
import net.minestom.server.instance.block.Block;

/**
 * This class should not be instantiated for anything other than registering a block type with
 * BlocksmithBlockTypeRegistry which will return the actual BlocksmithBlockType instance
 */
public class BlocksmithBlockType {
    @Getter
    private final Block realBlock;
    @Getter
    private final DisplayStructure displayStructure;

    public BlocksmithBlockType(Block realBlock, DisplayStructure displayStructure) {
        this.realBlock = realBlock;
        this.displayStructure = displayStructure;
    }
}

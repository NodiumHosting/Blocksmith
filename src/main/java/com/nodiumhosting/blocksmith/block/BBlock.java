package com.nodiumhosting.blocksmith.block;

import com.nodiumhosting.blocksmith.common.Tags;
import lombok.Getter;
import net.minestom.server.instance.block.Block;
import net.minestom.server.tag.Tag;

@Getter
public class BBlock {
    private final BBlockType blockType;

    public BBlock(BBlockType blockType) {
        this.blockType = blockType;
    }

    public Block getBlock() {
        return blockType.getData().getBaseBlock()
                .withTag(
                        Tag.String(Tags.Block.TYPE.getTagId()),
                        blockType.id.toString()
                )
                .withTag(
                        Tag.Integer(Tags.Block.REQUIRED_TOOL_TIER.getTagId()),
                        blockType.getData().getRequiredToolTier()
                )
                .withTag(
                        Tag.Integer(Tags.Block.HARDNESS.getTagId()),
                        blockType.getData().getHardness()
                );
    }
}

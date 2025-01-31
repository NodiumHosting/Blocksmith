package com.nodiumhosting.blocksmith.common;

import lombok.Getter;

public class Tags {
    @Getter
    public enum Block {
        TYPE(new Identifier("blocksmith", "block_type").toString()),
        REQUIRED_TOOL_TIER(new Identifier("blocksmith", "required_tool_tier").toString()),
        HARDNESS(new Identifier("blocksmith", "hardness").toString());

        private final String tagId;

        Block(String tagId) {
            this.tagId = tagId;
        }
    }

    @Getter
    public enum Item {
        TYPE(new Identifier("blocksmith", "item_type").toString()),
        TOOL_TIER(new Identifier("blocksmith", "tool_tier").toString()),
        MINING_SPEED(new Identifier("blocksmith", "mining_speed").toString());

        private final String tagId;

        Item(String tagId) {
            this.tagId = tagId;
        }
    }
}

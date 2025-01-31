package com.nodiumhosting.blocksmith.common;

import lombok.Getter;

public class Tags {
    @Getter
    public enum Block {
        TYPE(new Identifier("blocksmith", "block_type"));

        private final Identifier identifier;

        Block(Identifier identifier) {
            this.identifier = identifier;
        }
    }

    @Getter
    public enum Item {
        TYPE(new Identifier("blocksmith", "item_type"));

        private final Identifier identifier;

        Item(Identifier identifier) {
            this.identifier = identifier;
        }
    }
}

package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.block.BBlockType;
import com.nodiumhosting.blocksmith.block.BBlockTypeData;
import com.nodiumhosting.blocksmith.common.Identifier;
import org.jetbrains.annotations.Nullable;

public class BBlockTypeRegistry extends Registry<BBlockType, BBlockTypeData> {
    public @Nullable BBlockType register(Identifier id, BBlockTypeData data) {
        BBlockType blockType = new BBlockType(id, data);
        if (this.isRegistered(id)) {
            return null;
        }
        registry.put(id, blockType);
        return blockType;
    }

    public @Nullable BBlockType get(Identifier id) {
        return registry.get(id);
    }
}

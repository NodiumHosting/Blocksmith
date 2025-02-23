package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.block.BBlockType;
import com.nodiumhosting.blocksmith.block.BBlockTypeData;
import com.nodiumhosting.blocksmith.common.NID;
import org.jetbrains.annotations.Nullable;

public class BBlockTypeRegistry extends Registry<BBlockType, BBlockTypeData> {
    public @Nullable BBlockType register(NID id, BBlockTypeData data) {
        BBlockType blockType = new BBlockType(id, data);
        if (this.isRegistered(id)) {
            return null;
        }
        registry.put(id, blockType);
        return blockType;
    }

    public @Nullable BBlockType get(NID id) {
        return registry.get(id);
    }
}

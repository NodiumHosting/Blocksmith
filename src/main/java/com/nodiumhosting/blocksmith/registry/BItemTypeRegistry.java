package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.common.Identifier;
import com.nodiumhosting.blocksmith.item.BItemType;
import com.nodiumhosting.blocksmith.item.BItemTypeData;
import org.jetbrains.annotations.Nullable;

public class BItemTypeRegistry extends Registry<BItemType, BItemTypeData> {
    public @Nullable BItemType register(Identifier id, BItemTypeData data) {
        BItemType itemType = new BItemType(id, data);
        if (this.isRegistered(id)) {
            return null;
        }
        return registry.put(id, itemType);
    }

    public @Nullable BItemType get(Identifier id) {
        return registry.get(id);
    }
}
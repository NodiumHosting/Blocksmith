package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.common.NID;
import com.nodiumhosting.blocksmith.item.BItemType;
import com.nodiumhosting.blocksmith.item.BItemTypeData;
import org.jetbrains.annotations.Nullable;

public class BItemTypeRegistry extends Registry<BItemType, BItemTypeData> {
    public @Nullable BItemType register(NID id, BItemTypeData data) {
        BItemType itemType = new BItemType(id, data);
        if (this.isRegistered(id)) {
            return null;
        }
        registry.put(id, itemType);
        return itemType;
    }

    public @Nullable BItemType get(NID id) {
        return registry.get(id);
    }
}
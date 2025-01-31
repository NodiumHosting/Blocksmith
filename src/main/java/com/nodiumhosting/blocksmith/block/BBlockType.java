package com.nodiumhosting.blocksmith.block;

import com.nodiumhosting.blocksmith.common.Identifier;
import com.nodiumhosting.blocksmith.registry.ObjectType;

public class BBlockType extends ObjectType<BBlockTypeData> {
    public BBlockType(Identifier id, BBlockTypeData data) {
        super(id, data);
    }
}

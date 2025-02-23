package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.common.NID;
import lombok.Getter;

public abstract class ObjectType<TypeData extends ObjectTypeData> {
    public final NID id;
    @Getter
    private final TypeData data;

    public ObjectType(NID id, TypeData data) {
        this.id = id;
        this.data = data;
    }
}

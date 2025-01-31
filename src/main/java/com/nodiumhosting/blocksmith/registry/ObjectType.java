package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.common.Identifier;

public abstract class ObjectType<TypeData extends ObjectTypeData> {
    public final Identifier id;
    private final TypeData data;

    public ObjectType(Identifier id, TypeData data) {
        this.id = id;
        this.data = data;
    }
}

package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.common.Identifier;
import lombok.Getter;

public abstract class ObjectType<TypeData extends ObjectTypeData> {
    public final Identifier id;
    @Getter
    private final TypeData data;

    public ObjectType(Identifier id, TypeData data) {
        this.id = id;
        this.data = data;
    }
}

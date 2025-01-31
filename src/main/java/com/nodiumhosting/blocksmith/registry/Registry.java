package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.common.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class Registry<Type extends ObjectType<TypeData>, TypeData extends ObjectTypeData> {
    protected final Map<Identifier, Type> registry;

    public Registry() {
        this.registry = new HashMap<>();
    }

    @Nullable
    public abstract Type register(Identifier id, TypeData data);
    @Nullable
    public abstract Type get(Identifier id);

    protected boolean isRegistered(Identifier id) {
        return registry.containsKey(id);
    }
}

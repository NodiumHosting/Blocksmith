package com.nodiumhosting.blocksmith.registry;

import com.nodiumhosting.blocksmith.common.NID;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class Registry<Type extends ObjectType<TypeData>, TypeData extends ObjectTypeData> {
    protected final Map<NID, Type> registry;

    public Registry() {
        this.registry = new HashMap<>();
    }

    @Nullable
    public abstract Type register(NID id, TypeData data);
    @Nullable
    public abstract Type get(NID id);

    protected boolean isRegistered(NID id) {
        return registry.containsKey(id);
    }
}

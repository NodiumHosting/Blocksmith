package com.nodiumhosting.blocksmith.registry.item;

import com.nodiumhosting.blocksmith.registry.IRegistryEntry;

public record ItemRegistryEntry(String id) implements IRegistryEntry {
    @Override
    public boolean isValid() {
        return id != null;
    }
}

package com.nodiumhosting.blocksmith.registry.block;

import com.nodiumhosting.blocksmith.registry.IRegistryEntry;

public record BlockRegistryEntry(String id) implements IRegistryEntry {
    @Override
    public boolean isValid() {
        return id != null;
    }
}

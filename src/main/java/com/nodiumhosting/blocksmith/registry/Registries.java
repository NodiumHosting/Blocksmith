package com.nodiumhosting.blocksmith.registry;

import com.google.gson.Gson;
import com.nodiumhosting.blocksmith.registry.block.BlockRegistryEntry;
import com.nodiumhosting.blocksmith.registry.item.ItemRegistryEntry;

public class Registries {
    public final static Gson gson = new Gson();
    private static final String ITEM_PATH = "data/item";
    public static final Registry<ItemRegistryEntry> ITEM = new Registry<>(ITEM_PATH, ItemRegistryEntry.class);
    private static final String BLOCK_PATH = "data/block";
    public static final Registry<BlockRegistryEntry> BLOCK = new Registry<>(BLOCK_PATH, BlockRegistryEntry.class);
}

package com.nodiumhosting.blocksmith.item;

import net.kyori.adventure.text.Component;
import net.minestom.server.component.DataComponent;
import net.minestom.server.component.DataComponentMap;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;

import java.util.List;
import java.util.stream.Stream;

public class Item {
    public final Tag<String> BLOCKSMITH_ITEM_ID = Tag.String("blocksmith_item_id");
    public final Tag<Component> BLOCKSMITH_ITEM_RARITY = Tag.Component("blocksmith_item_rarity");

    private final String id;
    private final Material material;
    private final String name;
    private final Component rarity;
    private final String itemModel;
    private final List<Component> lore;
    private final DataComponentMap.PatchBuilder dataComponents;

    private Item(Builder builder) {
        this.id = builder.id;
        this.material = builder.material;
        this.name = builder.name;
        this.rarity = builder.rarity;
        this.itemModel = builder.itemModel;
        this.lore = builder.lore;
        this.dataComponents = builder.dataComponents;
    }

    public ItemStack itemStack() {
        ItemStack.Builder builder = ItemStack.of(this.material, this.dataComponents.build()).builder();

        builder.setTag(BLOCKSMITH_ITEM_ID, id);
        builder.setTag(BLOCKSMITH_ITEM_RARITY, rarity);

        builder.customName(Component.text(this.name, this.rarity.color()));

        if (!this.itemModel.isEmpty()) {
            builder.itemModel(this.itemModel);
        }

        builder.lore(
                this.lore.isEmpty()
                        ? List.of(this.rarity)
                        // add an empty line between lore and rarity
                        : Stream.concat(this.lore.stream(), Stream.of(Component.empty(), this.rarity)).toList()
        );

        return builder.build();
    }

    public static class Builder {
        private String id = "";
        private Material material = Material.DIRT;
        private String name = "";
        private Component rarity = Component.empty();
        private String itemModel = "";
        private List<Component> lore = List.of();
        private final DataComponentMap.PatchBuilder dataComponents = DataComponentMap.patchBuilder();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder rarity(Component rarity) {
            this.rarity = rarity;
            return this;
        }

        public Builder itemModel(String itemModel) {
            this.itemModel = itemModel;
            return this;
        }

        public Builder lore(List<Component> lore) {
            this.lore = lore;
            return this;
        }

        public <T> Builder putDataComponent(DataComponent<T> component, T value) {
            this.dataComponents.set(component, value);
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}

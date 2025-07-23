package com.nodiumhosting.blocksmith.item;

import com.nodiumhosting.blocksmith.text.ComponentStyler;
import com.nodiumhosting.blocksmith.text.StyledComponent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minestom.server.component.DataComponent;
import net.minestom.server.component.DataComponentMap;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class Item {
    public static final Tag<String> BLOCKSMITH_ITEM_ID_RAW_TAG = Tag.String("blocksmith_item_id");
    public static final Tag<Key> BLOCKSMITH_ITEM_ID_TAG = BLOCKSMITH_ITEM_ID_RAW_TAG.map(Key::key, Key::asString);
    public static final Tag<String> BLOCKSMITH_ITEM_RARITY_RAW_TAG = Tag.String("blocksmith_item_rarity_raw");
    public static final Tag<Component> BLOCKSMITH_ITEM_RARITY_TAG = Tag.Component("blocksmith_item_rarity");

    private final Key id;
    private final Material material;
    private final StyledComponent name;
    private final StyledComponent rarity;
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

    public ItemStack createStack(int amount) {
        ItemStack.Builder builder = ItemStack.of(this.material, amount, this.dataComponents.build()).builder();

        builder.setTag(BLOCKSMITH_ITEM_ID_RAW_TAG, id.asString());
        builder.setTag(BLOCKSMITH_ITEM_RARITY_RAW_TAG, rarity.getText());
        builder.setTag(BLOCKSMITH_ITEM_RARITY_TAG, rarity.component());

        builder.customName(ComponentStyler.prependReset(this.name.componentWithColor(this.rarity.getStyle().color())));

        if (!this.itemModel.isEmpty()) {
            builder.itemModel(this.itemModel);
        }

        List<Component> rawLore = this.lore.isEmpty()
                ? List.of(this.rarity.component())
                // add an empty line between lore and rarity
                : Stream.concat(this.lore.stream(), Stream.of(Component.empty(), this.rarity.component())).toList();

        List<Component> lore = rawLore.stream().map(ComponentStyler::prependReset).toList();

        builder.lore(lore);

        return builder.build();
    }

    public ItemStack createStack() {
        return createStack(1);
    }

    public static @Nullable Key readItemId(ItemStack itemStack) {
        if (!itemStack.hasTag(BLOCKSMITH_ITEM_ID_RAW_TAG) || !itemStack.hasTag(BLOCKSMITH_ITEM_ID_TAG)) return null;
        return itemStack.getTag(BLOCKSMITH_ITEM_ID_TAG);
    }

    public static class Builder {
        private Key id = Key.key("unknown:unknown");
        private Material material = Material.DIRT;
        private StyledComponent name = StyledComponent.empty();
        private StyledComponent rarity = StyledComponent.empty();
        private String itemModel = "";
        private List<Component> lore = List.of();
        private final DataComponentMap.PatchBuilder dataComponents = DataComponentMap.patchBuilder();

        public Builder id(Key id) {
            this.id = id;
            return this;
        }

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder name(StyledComponent name) {
            this.name = name;
            return this;
        }

        public Builder rarity(StyledComponent rarity) {
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

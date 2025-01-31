package com.nodiumhosting.blocksmith.item;

import com.nodiumhosting.blocksmith.registry.ObjectTypeData;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class BItemTypeData extends ObjectTypeData {
    private final ItemStack baseItem;

    @Nullable
    private Integer toolTier = null;

    @Nullable
    private Integer miningSpeed = null;

    public BItemTypeData(Component name, Material material) {
        super(name);
        this.baseItem = ItemStack.of(material);
    }

    public BItemTypeData(Component name, ItemStack baseItem) {
        super(name);
        this.baseItem = baseItem;
    }
}

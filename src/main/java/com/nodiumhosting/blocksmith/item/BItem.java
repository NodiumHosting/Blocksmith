package com.nodiumhosting.blocksmith.item;

import com.nodiumhosting.blocksmith.common.Tags;
import lombok.Getter;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

@Getter
public class BItem {
    private final BItemType itemType;

    public BItem(BItemType itemType) {
        this.itemType = itemType;
    }

    public ItemStack getItemStack() {
        return itemType.getData().getBaseItem().builder()
                .set(
                        ItemComponent.ITEM_NAME,
                        itemType.getData().getName()
                )
                .set(
                        Tag.String(Tags.Item.TYPE.getTagId()),
                        itemType.id.toString()
                )
                .set(
                        Tag.Integer(Tags.Item.TOOL_TIER.getTagId()),
                        itemType.getData().getToolTier()
                )
                .set(
                        Tag.Integer(Tags.Item.MINING_SPEED.getTagId()),
                        itemType.getData().getMiningSpeed()
                )
                .build();
    }
}

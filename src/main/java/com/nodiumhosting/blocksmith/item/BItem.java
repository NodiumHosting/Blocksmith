package com.nodiumhosting.blocksmith.item;

import com.nodiumhosting.blocksmith.common.Tags;
import lombok.Getter;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

@Getter
public class BItem {
    private final BItemType itemType;

    public BItem(BItemType itemType) {
        this.itemType = itemType;
    }

    public ItemStack getItemStack() {
        return itemType.getData().getBaseItem()
                .withCustomName(
                        itemType.getData().getName()
                )
                .withTag(
                        Tag.String(Tags.Item.TYPE.getIdentifier().toString()),
                        itemType.id.toString()
                );
    }
}

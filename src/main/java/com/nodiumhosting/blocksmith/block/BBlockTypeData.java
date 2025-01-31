package com.nodiumhosting.blocksmith.block;

import com.nodiumhosting.blocksmith.registry.ObjectTypeData;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class BBlockTypeData extends ObjectTypeData {
    private final Block baseBlock;

    @Nullable
    private Integer requiredToolTier = null;

    @Nullable
    private Integer hardness = null;

    public BBlockTypeData(Component name, Material material) {
        super(name);
        this.baseBlock = material.block();
    }

    public BBlockTypeData(Component name, Block baseBlock) {
        super(name);
        this.baseBlock = baseBlock;
    }
}

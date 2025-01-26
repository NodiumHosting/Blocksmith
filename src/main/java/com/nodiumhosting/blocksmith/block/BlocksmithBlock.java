package com.nodiumhosting.blocksmith.block;

import com.nodiumhosting.blocksmith.Blocksmith;
import com.nodiumhosting.blocksmith.display.DisplayStructure;
import lombok.Getter;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlocksmithBlock {
    @Getter
    private final Blocksmith blocksmith;
    @Getter
    private final UUID instanceUuid;
    @Getter
    private final BlocksmithBlockType blockType;
    @Getter
    private final BlockVec location;
    @Getter
    private final List<Entity> displayEntities;

    private BlocksmithBlock(Blocksmith blocksmith, Instance instance, BlocksmithBlockType blockType, BlockVec location, List<Entity> displayEntities) {
        this.blocksmith = blocksmith;
        this.instanceUuid = instance.getUniqueId();
        this.blockType = blockType;
        this.location = location;
        this.displayEntities = displayEntities;
    }

    static BlocksmithBlock create(Blocksmith blocksmith, Instance instance, BlockVec location, BlocksmithBlockType blockType) {
        Block realBlock = blockType.getRealBlock();
        List<Entity> displayEntities = new ArrayList<>();
        DisplayStructure displayStructureReal = blockType.getDisplayStructure().toRealPosition(location);

        instance.setBlock(location, realBlock);
        for (DisplayStructure.DisplayStructurePiece piece : displayStructureReal.getPieces()) {
            Entity entity = piece.getEntity();
            EntityMeta entityMeta = entity.getEntityMeta();
            Entity entityCopy = new Entity(entity.getEntityType());
            EntityMeta entityCopyMeta = entityCopy.getEntityMeta();
            if (entityMeta instanceof ItemDisplayMeta && entityCopyMeta instanceof ItemDisplayMeta) {
                ItemDisplayMeta itemDisplayMeta = (ItemDisplayMeta) entityMeta;
                ItemDisplayMeta itemDisplayMetaCopy = (ItemDisplayMeta) entityCopyMeta;
                itemDisplayMetaCopy.setHasNoGravity(itemDisplayMeta.isHasNoGravity());
                itemDisplayMetaCopy.setItemStack(itemDisplayMeta.getItemStack());
                itemDisplayMetaCopy.setCustomName(itemDisplayMeta.getCustomName());
                itemDisplayMetaCopy.setCustomNameVisible(itemDisplayMeta.isCustomNameVisible());
                // TODO: this is stupid as shit
            }

            entityCopy.setInstance(instance, piece.getOffset());
            displayEntities.add(entityCopy);
        }

        return new BlocksmithBlock(blocksmith, instance, blockType, location, displayEntities);
    }

    /**
     * This should only ever be called by BlocksmithBlockManager
     * else the block will not be removed from the manager
     */
    void breakBlock() {
        if (blocksmith.instanceManager == null) return;

        for (Entity entity : displayEntities) {
            entity.remove();
        }

        blocksmith.instanceManager.getInstance(instanceUuid).setBlock(location, Block.AIR);
    }
}

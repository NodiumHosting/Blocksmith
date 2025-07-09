package com.nodiumhosting.blocksmith.structure;

import com.nodiumhosting.blocksmith.Blocksmith;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Structure {
	BlockVec size;
	HashMap<BlockVec, Integer> blocks;
	List<Block> palette;

	Structure(BlockVec size, HashMap<BlockVec, Integer> blocks, List<Block> palette) {
		this.size = size;
		this.blocks = blocks;
		this.palette = palette;
	}

	public static Structure empty() {
		return new Structure(new BlockVec(0, 0, 0), new HashMap<>(), List.of());
	}

	public void place(InstanceContainer container, BlockVec position) {
		Blocksmith.LOGGER.info(palette.toString());
		for (BlockVec pos : blocks.keySet()) {
			container.setBlock(position.add(pos), (Block) palette.toArray()[blocks.get(pos)]);
		}
	}
}

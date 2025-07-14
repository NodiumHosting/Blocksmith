package com.nodiumhosting.blocksmith.schematic;

import com.nodiumhosting.blocksmith.Blocksmith;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

import java.util.Map;

public class SchematicPlacer {
	public static void placeSchematic(ISchematic schematic, BlockVec position, InstanceContainer instanceContainer) {
		Thread placeThread = new Thread(() -> {
			BlocksmithSchematic blocksmithSchematic = schematic.asBlocksmithSchematic();

			if (blocksmithSchematic.fill().isPresent()) {
				for (int x = 0; x < blocksmithSchematic.size().blockX(); x++) {
					for (int y = 0; y < blocksmithSchematic.size().blockY(); y++) {
						for (int z = 0; z < blocksmithSchematic.size().blockZ(); z++) {
							instanceContainer.setBlock(position.add(x, y, z), blocksmithSchematic.fill().get());
						}
					}
				}
			}

			int id = -1;
			Block block = null;
			for (Map.Entry<BlockVec, Integer> entry : blocksmithSchematic.blocks().entrySet()) {
				BlockVec blockVec = entry.getKey();
				Integer paletteId = entry.getValue();

				if (paletteId != id || block == null) {
					block = blocksmithSchematic.palette().get(paletteId);
					id = paletteId;
				}

				instanceContainer.setBlock(blockVec.add(position), block);
			}
		});
		placeThread.start();
	}
}

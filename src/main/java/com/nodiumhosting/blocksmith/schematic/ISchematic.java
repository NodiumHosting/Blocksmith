package com.nodiumhosting.blocksmith.schematic;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.minestom.server.instance.block.Block;

import java.util.HashMap;
import java.util.Map;

public interface ISchematic {
	BlocksmithSchematic asBlocksmithSchematic();

	void save(Key key);

	static String suffix() { return ""; }

	static ISchematic load(Key key) {
		return null;
	}

	static CompoundBinaryTag blockToNBT(Block block) {
		Map<String, StringBinaryTag> properties = new HashMap<>();
		block.properties().forEach((id, value) -> {
			properties.put(id, StringBinaryTag.stringBinaryTag(value));
		});
		CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder()
				.putString("Name", block.name());
		if (!block.properties().isEmpty()) {
			builder.put("Properties", CompoundBinaryTag.from(properties));
		}
		return builder.build();
	}

	static Block blockFromNBT(CompoundBinaryTag nbt) {
		Map<String, String> properties = new HashMap<>();

		nbt.getList("Properties").forEach((binaryTag) -> {
			if (!(binaryTag instanceof StringBinaryTag tag)) { return; }
			properties.put(tag.examinableName(), tag.value());
		});

		Block block = Block.fromKey(nbt.getString("Name"));
		if (block == null) { return null; }
		return block.withProperties(properties);
	}
}

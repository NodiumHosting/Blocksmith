package com.nodiumhosting.blocksmith.structure;

import com.nodiumhosting.blocksmith.Blocksmith;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.*;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.instance.block.Block;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class NbtStructureLoader {
	public static Optional<Structure> load(Key key) {
		InputStream inputStream = Blocksmith.class.getResourceAsStream("/" + key.value());

		CompoundBinaryTag nbt;
		try {
			nbt = BinaryTagIO.reader().read(inputStream, BinaryTagIO.Compression.GZIP);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Blocksmith.LOGGER.info(nbt.toString());

		ListBinaryTag sizeList = nbt.getList("size");
		BlockVec size = new BlockVec(
				sizeList.getInt(0),
				sizeList.getInt(1),
				sizeList.getInt(2)
		);

		HashMap<BlockVec, Integer> blocks = new HashMap<>();

		nbt.getList("blocks").forEach((binaryTag) -> {
			if (!(binaryTag instanceof CompoundBinaryTag compoundBinaryTag)) { return; }
			int state = compoundBinaryTag.getInt("state");
			ListBinaryTag posList = compoundBinaryTag.getList("pos");
			BlockVec pos = new BlockVec(
					posList.getInt(0),
					posList.getInt(1),
					posList.getInt(2)
			);
			blocks.put(pos, state);
		});


		List<Block> palette = new ArrayList<>();

		nbt.getList("palette").forEach((binaryTag -> {
			if (!(binaryTag instanceof CompoundBinaryTag compoundBinaryTag)) { return; }
			String name = compoundBinaryTag.getString("Name");
			ListBinaryTag propertyList = compoundBinaryTag.getList("Properties");
			Map<String, String> properties = new HashMap<>();
			propertyList.forEach((binaryTag1) -> {
				if (!(binaryTag1 instanceof StringBinaryTag stringBinaryTag)) { return; }
				properties.put(stringBinaryTag.examinableName(), stringBinaryTag.value());
			});
			palette.add(Objects.requireNonNull(Block.fromKey(name)).withProperties(properties));
		}));

		return Optional.of(new Structure(size, blocks, palette));
	}
}

package com.nodiumhosting.blocksmith.schematic;

import com.nodiumhosting.blocksmith.Blocksmith;
import lombok.extern.java.Log;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.*;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

public class SpongeSchematic implements ISchematic {
	private HashMap<String, Integer> blockPalette;
	private byte[] blockData;
	private int version;
	private short length;
	private short height;
	private short width;

	public static String suffix() { return "schem"; }

	public static SpongeSchematic create(
			HashMap<String, Integer> blockPalette,
			byte[] blockData,
			int version,
			short length,
			short height,
			short width
	) {
		SpongeSchematic schem = new SpongeSchematic();

		schem.blockPalette = blockPalette;
		schem.blockData = blockData;
		schem.version = version;
		schem.length = length;
		schem.height = height;
		schem.width = width;

		return schem;
	}

	@Override
	public BlocksmithSchematic asBlocksmithSchematic() {
		Point size = new BlockVec(unsignShort(this.width), unsignShort(this.height), unsignShort(this.length));
		HashMap<BlockVec, Integer> blocks = new HashMap<>();
		HashMap<Integer, Block> palette = new HashMap<>();

		this.blockPalette.forEach((value, id) -> palette.put(id, stringToBlock(value)));

		// This code is directly taken from Sponge's codebase, with some minor tweaks to work with our system
		// https://github.com/SpongePowered/Sponge/blob/aa2c8c53b4f9f40297e6a4ee281bee4f4ce7707b/src/main/java/org/spongepowered/common/data/persistence/SchematicTranslator.java#L147-L175
		int index = 0;
		int i = 0;
		while (i < this.blockData.length) {
			int value = 0;
			int varint_length = 0;

			while (true) {
				value |= (this.blockData[i] & 127) << (varint_length++ * 7);
				if (varint_length > 5) {
					throw new RuntimeException("VarInt too big (probably corrupted data)");
				}
				if ((this.blockData[i] & 128) != 128) {
					i++;
					break;
				}
				i++;
			}
			// index = (y * length + z) * width + x
			int y = index / (width * length);
			int z = (index % (width * length)) / width;
			int x = (index % (width * length)) % width;
			blocks.put(new BlockVec(x, y, z), value);

			index++;
		}

		return BlocksmithSchematic.create(size, blocks, palette);
	}

	@Override
	public void save(Key key) {
		Thread saveThread = new Thread(() -> {
			try {
				Path path = Path.of("data/%s/schematic/%s.%s".formatted(key.namespace(), key.value(), suffix()));
				Path dir = Path.of("data/%s/schematic/".formatted(key.namespace()));
				if (!dir.toFile().exists()) {
					boolean ignored = dir.toFile().mkdirs();
				}
				BinaryTagIO.writer().write(this.asNBT(), path, BinaryTagIO.Compression.GZIP);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		},"saveSchematic");
		saveThread.start();
	}

	public static SpongeSchematic load(Key key) {
		try {
			CompoundBinaryTag nbt = BinaryTagIO.reader().read(Path.of("data/%s/schematic/%s.%s".formatted(key.namespace(), key.value(), suffix())), BinaryTagIO.Compression.GZIP);
			return SpongeSchematic.fromNBT(nbt);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public CompoundBinaryTag asNBT() {
 		CompoundBinaryTag.Builder paletteTagBuilder = CompoundBinaryTag.builder();

		this.blockPalette.forEach(paletteTagBuilder::putInt);

		CompoundBinaryTag blocksTag = CompoundBinaryTag.builder()
				.put("Palette", paletteTagBuilder.build())
				.putByteArray("Data", this.blockData)
				.put("BlockEntities", ListBinaryTag.empty())
				.build();

		return CompoundBinaryTag.builder().put("Schematic", CompoundBinaryTag.builder()
				.put("Blocks", blocksTag)
				.putInt("Version", this.version)
				.putShort("Length", this.length)
				.putShort("Height", this.height)
				.putInt("DataVersion", 4325)
				.putShort("Width", this.width)
				.put("Entities", ListBinaryTag.empty())
				.build()
		).build();
	}

	public static SpongeSchematic fromNBT(CompoundBinaryTag nbt) {
		nbt = nbt.getCompound("Schematic");
		CompoundBinaryTag blocksTag = nbt.getCompound("Blocks");

		short length = nbt.getShort("Length");
		short height = nbt.getShort("Height");
		short width = nbt.getShort("Width");
		int version = nbt.getInt("Version");

		CompoundBinaryTag paletteTag = blocksTag.getCompound("Palette");
		HashMap<String, Integer> palette = new HashMap<>();
		for (String key : paletteTag.keySet()) {
			palette.put(key, paletteTag.getInt(key));
		}

		byte[] blockData = blocksTag.getByteArray("Data");

		return create(palette, blockData, version, length, height, width);
	}

	public static int unsignShort(short number) {
		return ((number % 65536) + 65536) % 65536;
	}

	public static Block stringToBlock(String input) {
		String[] splitInput = input.split("[\\[,\\]]");

		Block block = Block.fromKey(splitInput[0]);
		if (block == null) { return null; }

		HashMap<String, String> properties = new HashMap<>();
		Arrays.stream(splitInput).forEach((value) -> {
			String[] splitValue = value.split("=");
			if (splitValue.length < 2) { return; }
			properties.put(splitValue[0], splitValue[1]);
		});

		return block.withProperties(properties);
	}
}
package com.nodiumhosting.blocksmith.schematic;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.*;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

public class BlocksmithSchematic implements ISchematic {
	private Point size;
	private HashMap<BlockVec, Integer> blocks;
	private HashMap<Integer, Block> palette;
	private Optional<Block> fill;

	public Point size() {
		return this.size;
	}
	public HashMap<BlockVec, Integer> blocks() {
		return this.blocks;
	}
	public HashMap<Integer, Block> palette() {
		return this.palette;
	}
	public Optional<Block> fill() {
		return this.fill;
	}

	public static String suffix() { return "bschem"; }

	public static BlocksmithSchematic create(Point size, HashMap<BlockVec, Integer> blocks, HashMap<Integer, Block> palette, Optional<Block> fill) {
		BlocksmithSchematic schem = new BlocksmithSchematic();

		schem.size = size;
		schem.blocks = blocks;
		schem.palette = palette;
		schem.fill = fill;

		return schem;
	}

	public static BlocksmithSchematic create(Point size, HashMap<BlockVec, Integer> blocks, HashMap<Integer, Block> palette, Block fill) {
		return BlocksmithSchematic.create(size, blocks, palette, Optional.of(fill));
	}

	public static BlocksmithSchematic create(Point size, HashMap<BlockVec, Integer> blocks, HashMap<Integer, Block> palette) {
		return BlocksmithSchematic.create(size, blocks, palette, Optional.empty());
	}


	@Override
	public BlocksmithSchematic asBlocksmithSchematic() {
		return this;
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

	public static BlocksmithSchematic load(Key key) {
		try {
			CompoundBinaryTag nbt = BinaryTagIO.reader().read(Path.of("data/%s/schematic/%s.%s".formatted(key.namespace(), key.value(), suffix())), BinaryTagIO.Compression.GZIP);
			return BlocksmithSchematic.fromNBT(nbt);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public CompoundBinaryTag asNBT() {
		ListBinaryTag.Builder<BinaryTag> blocksTagBuilder = ListBinaryTag.builder();
		this.blocks.forEach((point, value) -> {
			blocksTagBuilder.add(
					CompoundBinaryTag.builder()
							.put("pos", ListBinaryTag.builder()
									.add(IntBinaryTag.intBinaryTag(point.blockX()))
									.add(IntBinaryTag.intBinaryTag(point.blockY()))
									.add(IntBinaryTag.intBinaryTag(point.blockZ()))
									.build())
							.putInt("state", value)
							.build().asBinaryTag()
			);
		});
		ListBinaryTag blocksTag = blocksTagBuilder.build();

		ListBinaryTag.Builder<BinaryTag> paletteTagBuilder = ListBinaryTag.builder();
		this.palette.forEach((id, block) -> {
			paletteTagBuilder.add(ISchematic.blockToNBT(block));
		});
		ListBinaryTag paletteTag = paletteTagBuilder.build();

		ListBinaryTag sizeTag = ListBinaryTag.builder()
				.add(IntBinaryTag.intBinaryTag(this.size.blockX()))
				.add(IntBinaryTag.intBinaryTag(this.size.blockY()))
				.add(IntBinaryTag.intBinaryTag(this.size.blockZ()))
				.build();

		CompoundBinaryTag.Builder nbtBuilder = CompoundBinaryTag.builder()
				.put("size", sizeTag)
				.put("blocks", blocksTag)
				.put("palette", paletteTag);

		fill.ifPresent(block -> nbtBuilder.put("fill", ISchematic.blockToNBT(block)));

		return nbtBuilder.build();
	}

	public static BlocksmithSchematic fromNBT(CompoundBinaryTag nbt) {
		ListBinaryTag sizeTag = nbt.getList("size");
		ListBinaryTag blocksTag = nbt.getList("blocks");
		ListBinaryTag paletteTag = nbt.getList("palette");
		CompoundBinaryTag fillTag = nbt.getCompound("fill");

		Point size = new BlockVec(sizeTag.getInt(0), sizeTag.getInt(1), sizeTag.getInt(2));
		HashMap<BlockVec, Integer> blocks = new HashMap<>();
		blocksTag.forEach((binaryTag) -> {
			if (!(binaryTag instanceof CompoundBinaryTag tag)) { return; }
			ListBinaryTag posTag = tag.getList("pos");
			blocks.put(new BlockVec(posTag.getInt(0), posTag.getInt(1), posTag.getInt(2)), tag.getInt("state"));
		});

		HashMap<Integer, Block> palette = new HashMap<>();
		for (int i = 0; i < paletteTag.size(); i ++) {
			BinaryTag binaryTag = paletteTag.get(0);
			if (!(binaryTag instanceof CompoundBinaryTag tag)) { continue; }
			palette.put(i, ISchematic.blockFromNBT(tag.getCompound("block")));
		}
		Optional<Block> fill = Optional.ofNullable(ISchematic.blockFromNBT(fillTag));

		return BlocksmithSchematic.create(size, blocks, palette, fill);
	}
}
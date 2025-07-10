package com.nodiumhosting.blocksmith;

import com.nodiumhosting.blocksmith.schematic.BlocksmithSchematic;
import com.nodiumhosting.blocksmith.schematic.VanillaSchematic;
import com.nodiumhosting.blocksmith.schematic.SchematicPlacer;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestServer {
	public static void main(String[] args) {
		MinecraftServer server = MinecraftServer.init();

		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		InstanceContainer mainContainer = instanceManager.createInstanceContainer();

		mainContainer.setChunkSupplier(LightingChunk::new);
		mainContainer.setGenerator((unit) -> {
			unit.modifier().fillHeight(0, 40, Block.SANDSTONE);
		});

		GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
		globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, (event) -> {
			final Player player = event.getPlayer();
			event.setSpawningInstance(mainContainer);
			player.setRespawnPoint(new Pos(0, 42, 0));
		});

		BlocksmithSchematic schem = BlocksmithSchematic.create(
				new BlockVec(14, 8, 9),
				new HashMap<>(Map.ofEntries(
						Map.entry(new BlockVec(0, 0, 0), 0),
						Map.entry(new BlockVec(1, 0, 0), 0),
						Map.entry(new BlockVec(0, 0, 1), 0),
						Map.entry(new BlockVec(0, 4, 0), 0),
						Map.entry(new BlockVec(0, 0, 2), 0),
						Map.entry(new BlockVec(0, 2, 0), 0),
						Map.entry(new BlockVec(0, 4, 8), 0)
				)),
				new HashMap<>(Map.ofEntries(
						Map.entry(0, Block.DIAMOND_BLOCK)
				)),
				Optional.of(Block.DRIED_KELP_BLOCK)
		);
		schem.save(Key.key("blocksmith", "testa"));
		SchematicPlacer.placeSchematic(schem, new BlockVec(0, 40, 0), mainContainer);

		server.start("127.0.0.1", 25565);
	}
}

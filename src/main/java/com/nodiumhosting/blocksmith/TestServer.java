package com.nodiumhosting.blocksmith;

import com.nodiumhosting.blocksmith.structure.Structure;
import com.nodiumhosting.blocksmith.structure.NbtStructureLoader;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

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

		Optional<Structure> structure = NbtStructureLoader.load(Key.key("blocksmith", "data/structure/mc_sandstone_tower.nbt"));
		structure.ifPresent(value -> value.place(mainContainer, new BlockVec(0, 40, 10)));

		server.start("127.0.0.1", 25565);
	}
}

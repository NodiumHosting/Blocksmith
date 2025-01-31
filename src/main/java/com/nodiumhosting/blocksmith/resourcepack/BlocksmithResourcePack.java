package com.nodiumhosting.blocksmith.resourcepack;

import com.nodiumhosting.blocksmith.Blocksmith;
import lombok.Getter;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.text.Component;
import team.unnamed.creative.BuiltResourcePack;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.metadata.pack.PackFormat;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;

import java.net.URI;
import java.util.UUID;

public class BlocksmithResourcePack {
    private static final Writable ICON = Writable.resource(Blocksmith.class.getClassLoader(), "blocksmith_icon.png");
    public final ResourcePack resourcePack = ResourcePack.resourcePack();
    @Getter
    private final UUID uuid;

    public BlocksmithResourcePack(UUID uuid) {
        this.uuid = uuid;

        resourcePack.packMeta(PackFormat.format(42), Component.text("Blocksmith Custom Server Resources"));
        resourcePack.icon(ICON);
    }

    public ResourcePackInfo getPackInfo(URI rpServer) {
        return ResourcePackInfo.resourcePackInfo(uuid, rpServer, build().hash());
    }

    public BuiltResourcePack build() {
        return MinecraftResourcePackWriter.minecraft().build(resourcePack);
    }
}

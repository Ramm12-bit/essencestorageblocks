package com.example.ars_compressura;

import com.example.ars_compressura.registry.ModRegistry;
// import com.example.ars_compressura.registry.EssenceBlocks; // <-- leave this commented until the file exists
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsCompressura.MODID)
public class ArsCompressura {
    public static final String MODID = "ars_compressura";

    private static final Logger LOGGER = LogManager.getLogger();

    public ArsCompressura(IEventBus modEventBus, ModContainer modContainer) {
        ModRegistry.registerRegistries(modEventBus);
        ArsNouveauRegistry.registerGlyphs();

        // ⭐ COMMENTED OUT UNTIL EssenceBlocks.java IS ADDED TO THE PROJECT
        // EssenceBlocks.BLOCKS.register(modEventBus);
        // EssenceBlocks.ITEMS.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        NeoForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ArsNouveauRegistry.registerSounds();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
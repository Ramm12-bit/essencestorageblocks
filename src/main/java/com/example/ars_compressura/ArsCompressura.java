package com.example.ars_compressura;

import com.example.ars_compressura.registry.ModRegistry;
import com.example.ars_compressura.registry.EssenceBlocks;
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

        
        EssenceBlocks.BLOCKS.register(modEventBus);
        EssenceBlocks.ITEMS.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ArsNouveauRegistry.registerSounds();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
        try {
            var server = event.getServer();
            LOGGER.info("server recipe manager: {}", server.getRecipeManager());
            var recipeManager = server.getRecipeManager();
            var crafting = recipeManager.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.CRAFTING);
            long[] count = {0};
            crafting.forEach(holder -> {
                var id = holder.id();
                if (id.getNamespace().equals(MODID)) {
                    count[0]++;
                    if (count[0] <= 30) {
                        LOGGER.info("ars_compressura recipe id: {}", id);
                    }
                }
            });
            LOGGER.info("ars_compressura crafting recipe count: {}", count[0]);

            var rm = server.getResourceManager();
            var recipeResourcesRecipes = rm.listResources("data/" + MODID + "/recipes", rl -> rl.toString().endsWith(".json"));
            var recipeResourcesRecipe = rm.listResources("data/" + MODID + "/recipe", rl -> rl.toString().endsWith(".json"));
            LOGGER.info("recipe resources for {}/recipes: {}", MODID, recipeResourcesRecipes.size());
            LOGGER.info("recipe resources for {}/recipe: {}", MODID, recipeResourcesRecipe.size());
            recipeResourcesRecipes.forEach((rl, r) -> LOGGER.info("resource recipes: {}", rl));
            recipeResourcesRecipe.forEach((rl, r) -> LOGGER.info("resource recipe: {}", rl));
        } catch (Exception e) {
            LOGGER.error("Recipe debug failed:", e);
        }
    }
}
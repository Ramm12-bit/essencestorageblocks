package com.example.ars_compressura.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.EnumMap;
import java.util.Map;

import static com.example.ars_compressura.ArsCompressura.MODID;

public class EssenceBlocks {

    public enum EssenceType {
        WATER,
        FIRE,
        EARTH,
        AIR,
        ANIMA,
        ABJURATION,
        CONJURATION,
        MANIPULATION
    }

    // NEW API — matches ModRegistry
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(MODID);

    public static final Map<EssenceType, DeferredHolder<Block, Block>> BLOCKS_BY_TYPE =
            new EnumMap<>(EssenceType.class);

    public static final Map<EssenceType, DeferredHolder<Item, Item>> ITEMS_BY_TYPE =
            new EnumMap<>(EssenceType.class);

    static {
        for (EssenceType type : EssenceType.values()) {
            String name = type.name().toLowerCase() + "_essence_block";

            DeferredHolder<Block, Block> block = BLOCKS.register(name,
                    () -> new Block(BlockBehaviour.Properties.of()
                            .strength(1.0f)
                            .sound(SoundType.AMETHYST)));

            DeferredHolder<Item, Item> item = ITEMS.register(name,
                    () -> new BlockItem(block.get(), new Item.Properties()));

            BLOCKS_BY_TYPE.put(type, block);
            ITEMS_BY_TYPE.put(type, item);
        }
    }
}

package net.atired.thedefused.block;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.block.custom.AshBlock;
import net.atired.thedefused.block.custom.GazerBlock;
import net.atired.thedefused.block.custom.ShaleBlock;
import net.atired.thedefused.block.custom.ShaleSlabBlock;
import net.atired.thedefused.item.Moditems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block>
            BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DefusedMod.MODID);
    public static final RegistryObject<Block> ASH_CUBE = registerBlock("ash_cube",
            () -> new AshBlock(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> CHISELED_DARK_PRISMARINE = registerBlock("chiseled_dark_prismarine",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DARK_PRISMARINE)));
    public static final RegistryObject<Block> COLD_SHALE = registerBlock("cold_shale",
            () -> new ShaleBlock(ModBlocks.SHALE,true, BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT)));
    public static final RegistryObject<Block> SHALE = registerBlock("shale",
            () -> new ShaleBlock(ModBlocks.COLD_SHALE,false, BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT)));
    public static final RegistryObject<Block> COLD_SHALE_SLAB = registerBlock("cold_shale_slab",
            () -> new ShaleSlabBlock(ModBlocks.SHALE_SLAB,true, BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT)));
    public static final RegistryObject<Block> SHALE_SLAB = registerBlock("shale_slab",
            () -> new ShaleSlabBlock(ModBlocks.COLD_SHALE_SLAB,false, BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT)));



    public static final RegistryObject<Block> GAZER = registerBlock("gazer",
            () -> new GazerBlock(BlockBehaviour.Properties.copy(Blocks.OBSERVER)));
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name,RegistryObject<T> block)
    {
        return Moditems.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties()));
    }
    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}

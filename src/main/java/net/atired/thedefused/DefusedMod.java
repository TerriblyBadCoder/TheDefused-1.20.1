package net.atired.thedefused;

import com.mojang.logging.LogUtils;
import net.atired.thedefused.block.ModBlocks;
import net.atired.thedefused.effect.ModEffects;

import net.atired.thedefused.enchantment.ModEnchantments;
import net.atired.thedefused.event.ClientEvents;
import net.atired.thedefused.event.CreeperLobotomyEvent;
import net.atired.thedefused.event.ShovelEnchants;
import net.atired.thedefused.event.loot.ModLootModifier;
import net.atired.thedefused.event.ModDropsHandler;
import net.atired.thedefused.item.Moditems;
import net.atired.thedefused.potion.Modpotions;
import net.atired.thedefused.util.Brewing;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DefusedMod.MODID)
public class DefusedMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "thedefused";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public DefusedMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        Moditems.register(modEventBus);
        ModBlocks.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        Modpotions.register(modEventBus);
        ModLootModifier.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEnchantments.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModDropsHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(new CreeperLobotomyEvent());
        MinecraftForge.EVENT_BUS.register(new ShovelEnchants());
        modEventBus.addListener(this::addCreative);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    BrewingRecipeRegistry.addRecipe(new Brewing(Potions.POISON,Moditems.COOKEDSHULKERMEAT.get(), Modpotions.TOXIC.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Potions.THICK,Moditems.GUARDIANEYE.get(), Modpotions.MINING_FATIGUE.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Modpotions.MINING_FATIGUE.get(),Items.FERMENTED_SPIDER_EYE, Modpotions.HASTE.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Modpotions.HASTE.get(),Items.REDSTONE, Modpotions.LONGHASTE.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Modpotions.MINING_FATIGUE.get(),Items.REDSTONE, Modpotions.LONGMINING_FATIGUE.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Modpotions.TOXIC.get(),Items.REDSTONE, Modpotions.LONGTOXIC.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Modpotions.TOXIC.get(),Items.GLOWSTONE, Modpotions.STRONGTOXIC.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Potions.LONG_POISON,Moditems.COOKEDSHULKERMEAT.get(), Modpotions.LONGTOXIC.get()));
        BrewingRecipeRegistry.addRecipe(new Brewing(Potions.STRONG_POISON,Moditems.COOKEDSHULKERMEAT.get(), Modpotions.STRONGTOXIC.get()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.accept(Moditems.COOKEDSHULKERMEAT);
            event.accept(Moditems.SHULKERMEAT);
            event.accept(Moditems.GUARDIANEYE);
            event.accept(Moditems.CHARGEDASH);

        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        {
            event.accept(ModBlocks.CHISELED_DARK_PRISMARINE);
        }
        if(event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS)
        {
            event.accept(ModBlocks.GAZER);
        }
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
        {
            event.accept(ModBlocks.GAZER);
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT)
        {
            event.accept(ModBlocks.ASH_CUBE);
            event.accept(Moditems.WARPEDEFFIGY);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
            event.accept(Moditems.PERFUME);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code

        }
    }




}

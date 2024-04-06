package net.atired.thedefused.item;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.item.custom.BejeveledApple;
import net.atired.thedefused.item.custom.ChargedAsh;
import net.atired.thedefused.item.custom.EnchantedBejeveledApple;
import net.atired.thedefused.item.custom.NeedleTickItem;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item>
            ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DefusedMod.MODID);

    public static final Rarity RARITY_VOID = Rarity.create("thedefused:void", ChatFormatting.GRAY);
    public  static final RegistryObject<Item>
            CHARGEDASH = ITEMS.register("charged_ash",()->new ChargedAsh(new Item.Properties()));
    public  static final RegistryObject<Item>
            ENCHANTEDBEJEVELEDFRUIT = ITEMS.register("enchanted_bejeveled_fruit",()->new EnchantedBejeveledApple(new Item.Properties().food(Modfoods.BEJEVELEDAPPLE).rarity(RARITY_VOID)));
    public  static final RegistryObject<Item>
            BEJEVELEDFRUIT = ITEMS.register("bejeveled_fruit",()->new BejeveledApple(new Item.Properties().food(Modfoods.BEJEVELEDAPPLE).rarity(RARITY_VOID)));
    public  static final RegistryObject<Item>
            PERFUME = ITEMS.register("perfume",()->new Item(new Item.Properties().stacksTo(16).rarity(RARITY_VOID)));
    public  static final RegistryObject<Item>
            WARPEDEFFIGY = ITEMS.register("warped_effigy",()->new Item(new Item.Properties().stacksTo(1).rarity(RARITY_VOID)));
    public  static final RegistryObject<Item>
            NEEDLETICK = ITEMS.register("needletick",()->new NeedleTickItem());
    public  static final RegistryObject<Item>
            GUARDIANEYE = ITEMS.register("guardianeye",()->new Item(new Item.Properties()));
    public  static final RegistryObject<Item>
            SHULKERMEAT = ITEMS.register("shulker_meat",()->new Item(new Item.Properties().food(Modfoods.SHULKERMEAT)));
    public  static final RegistryObject<Item>
            COOKEDSHULKERMEAT = ITEMS.register("cooked_shulker_meat",()->new Item(new Item.Properties()));
    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }

}

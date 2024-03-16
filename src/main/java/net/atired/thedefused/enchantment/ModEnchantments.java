package net.atired.thedefused.enchantment;

import net.atired.thedefused.DefusedMod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DefusedMod.MODID);
    public static final EnchantmentCategory SHOVEL = EnchantmentCategory.create("shovel", item -> item instanceof ShovelItem);
    public static RegistryObject<Enchantment> HIGHLANDER = ENCHANTMENTS.register("highlander",
            () -> new HighlanderEnchantment(Enchantment.Rarity.UNCOMMON,
                    SHOVEL, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> COMBUST = ENCHANTMENTS.register("combustible",
            () -> new CombustibleEnchant(Enchantment.Rarity.UNCOMMON,
                    EnchantmentCategory.TRIDENT, EquipmentSlot.MAINHAND));
    public static void register(IEventBus eventBus)
    {
        ENCHANTMENTS.register(eventBus);
    }
}

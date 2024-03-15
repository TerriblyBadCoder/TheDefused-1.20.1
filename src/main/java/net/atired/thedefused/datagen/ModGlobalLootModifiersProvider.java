package net.atired.thedefused.datagen;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.event.loot.AddItemModifier;
import net.atired.thedefused.item.Moditems;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.function.Predicate;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider
{
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, DefusedMod.MODID);
    }

    @Override
    protected void start()
        {

            add("shulkmeat_from_shulk", new AddItemModifier(new LootItemCondition[]
                    { new LootTableIdCondition.Builder(new ResourceLocation("entities/shulker")).build(),
                            LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.ANY).build(),
                            LootItemRandomChanceCondition.randomChance(100f).build() }, Moditems.SHULKERMEAT.get()));
            add("guardianeye_from_guardian", new AddItemModifier(new LootItemCondition[]
                    { new LootTableIdCondition.Builder(new ResourceLocation("entities/guardian")).build(),
                            LootItemRandomChanceCondition.randomChance(0.75f).build() }, Moditems.GUARDIANEYE.get()));

        }

}

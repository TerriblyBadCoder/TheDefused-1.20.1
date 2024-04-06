package net.atired.thedefused.item.custom;

import net.atired.thedefused.item.Moditems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class NeedleTickItem extends SwordItem {
    public NeedleTickItem() {
        super(Tiers.IRON, -1, -0.4F, (new Item.Properties()).rarity(Moditems.RARITY_VOID));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(super.hurtEnemy(pStack, pTarget, pAttacker)){
            float f1;
            if (pTarget instanceof LivingEntity) {
                f1 = EnchantmentHelper.getDamageBonus(pAttacker.getMainHandItem(), ((LivingEntity)pTarget).getMobType());
            } else {
                f1 = EnchantmentHelper.getDamageBonus(pAttacker.getMainHandItem(), MobType.UNDEFINED);
            }
            pTarget.getPersistentData().putFloat("thedefused:collapse",pTarget.getPersistentData().getFloat("thedefused:collapse")+((float)pAttacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue()+f1)*2.5F);
            pTarget.getPersistentData().putInt("thedefused:collapse_delay",pTarget.getPersistentData().getInt("thedefused:collapse_delay")+(int)pAttacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue()+(int)f1+10);
            System.out.println(pTarget.getPersistentData().getInt("thedefused:collapse_delay"));
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 384;
    }
}

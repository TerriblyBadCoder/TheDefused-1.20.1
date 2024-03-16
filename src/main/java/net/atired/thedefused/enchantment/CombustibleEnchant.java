package net.atired.thedefused.enchantment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.*;

public class CombustibleEnchant extends Enchantment {
    public CombustibleEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }


    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if(!pAttacker.level().isClientSide()) {

        }

        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 10 + 20 * (pEnchantmentLevel - 1);
    }
    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    @Override
    public boolean checkCompatibility(Enchantment pEnch) {
        return !(pEnch instanceof TridentLoyaltyEnchantment);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}

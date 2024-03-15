package net.atired.thedefused.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SimpleFoiledItem;

public class ChargedAsh extends Item {
    public ChargedAsh(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public boolean isFoil(ItemStack item)
    {
        return true;
    }



}

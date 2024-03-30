package net.atired.thedefused.mixin;


import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "appendHoverText", at = @At(value = "TAIL"), cancellable = true)
    public void append(ItemStack pStack, Level pLevel, List<Component> pTooltip, TooltipFlag pFlag, CallbackInfo ci)
    {
        if((CompoundTag)pStack.serializeNBT().get("tag") != null)
        {
            if((((CompoundTag)pStack.serializeNBT().get("tag")).getInt("potioncharges"))!=0)
                pTooltip.add((Component.literal(("Hits left: "+((CompoundTag)pStack.serializeNBT().get("tag")).getInt("potioncharges"))).withStyle(ChatFormatting.GRAY) ));
        }
        if (PotionUtils.getPotion(pStack) != Potions.EMPTY & pStack.getItem() instanceof SwordItem)
            PotionUtils.addPotionTooltip(pStack, pTooltip, 0.2F);

    }
    

}

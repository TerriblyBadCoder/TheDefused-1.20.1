package net.atired.thedefused.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.event.ModDropsHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class PotionChargeHudOverlay {

    private static final ResourceLocation POTIONCHARGE = new ResourceLocation(DefusedMod.MODID, "textures/toxicity/potion_charge.png");

    private static GuiGraphics guiGraphics = new GuiGraphics(Minecraft.getInstance().gameRenderer.getMinecraft(), Minecraft.getInstance().renderBuffers().bufferSource());
    public static final IGuiOverlay HUD_CHARGE = ((gui, poseStack, partialTick, width, height) -> {
        if(Minecraft.getInstance().player!= null)
        {

            if(!Minecraft.getInstance().gameRenderer.getMainCamera().isDetached() & Minecraft.getInstance().player.getMainHandItem().serializeNBT().get("tag") != null){
                ItemStack item = Minecraft.getInstance().player.getMainHandItem();
                int charges = ((CompoundTag)item.serializeNBT().get("tag")).getInt("potioncharges");
                int x = width / 2;
                int y = height/2;
                int offset = (charges-1)*8;
                if(charges>0)
                {
                    int color = PotionUtils.getColor(item);
                    int r = (color >> 16) & 0xff;
                    int g = (color >> 8) & 0xff;
                    int b = color & 0xff;
                    Vec3 vec3 = new Vec3(r,g,b);
                    vec3 = vec3.normalize();
                    vec3 = vec3.add(0.3,0.3,0.3);

                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderColor((float) vec3.x, (float) vec3.y, (float) vec3.z,0.8F);
                    RenderSystem.setShaderGlintAlpha(0.5);
                    RenderSystem.setShaderTexture(0, POTIONCHARGE);

                    for(int i = 0; i < charges; i++)
                        guiGraphics.blit(POTIONCHARGE,x-offset+(16*i)-8,y-8,0,0,16,16,16,16);

                }
            }

        }



    });
}

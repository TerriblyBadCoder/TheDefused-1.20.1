package net.atired.thedefused.client;

import ca.weblite.objc.Client;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.effect.ModEffects;
import net.atired.thedefused.event.CreeperLobotomyEvent;
import net.atired.thedefused.event.ModDropsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ToxicHudOverlay {
    private static final ResourceLocation TOXIC_HEART_FULL = new ResourceLocation(DefusedMod.MODID, "textures/toxicity/toxic_heart_full.png");
    private static final ResourceLocation TOXIC_HEART_EMPTY = new ResourceLocation(DefusedMod.MODID, "textures/toxicity/toxic_heart_empty.png");

    private static GuiGraphics guiGraphics = new GuiGraphics(Minecraft.getInstance().gameRenderer.getMinecraft(), Minecraft.getInstance().renderBuffers().bufferSource());
    public static final IGuiOverlay HUD_TOXIC = ((gui, poseStack, partialTick, width, height) -> {
        if(Minecraft.getInstance().player != null)
            if(Minecraft.getInstance().player.hasEffect(ModEffects.TOXIC.get()) & !Minecraft.getInstance().player.isSpectator() & !Minecraft.getInstance().player.isCreative()  ) {
            int x = width / 2;
            int y = height;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float) (((float)(int)Minecraft.getInstance().player.getHealth()/(float) Minecraft.getInstance().player.getMaxHealth()/4+0.75)));
            RenderSystem.setShaderGlintAlpha(0.5);
            RenderSystem.setShaderTexture(0, TOXIC_HEART_EMPTY);
            int off = ((int)Minecraft.getInstance().player.getMaxHealth()-10);

            for (int i = 0; i < ((int)Minecraft.getInstance().player.getHealth()); i++) {

                guiGraphics.blit(TOXIC_HEART_FULL, x - 91 + (i * 8) - off*4, y - 39, 0, 0, 9, 9,
                        9, 9);
            }
            for (int i = 0; i < ((int)Minecraft.getInstance().player.getMaxHealth()-(int)Minecraft.getInstance().player.getHealth()); i++) {

                guiGraphics.blit(TOXIC_HEART_EMPTY, x - 91 + (((int)Minecraft.getInstance().player.getHealth()+ i) * 8) - off*4, y - 39, 0, 0, 9, 9,
                        9, 9);
            }

        }


    });
}

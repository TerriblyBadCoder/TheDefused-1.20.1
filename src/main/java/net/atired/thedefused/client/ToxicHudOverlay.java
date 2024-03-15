package net.atired.thedefused.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.event.ModDropsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ToxicHudOverlay {
    private static final ResourceLocation TOXIC_HEART_FULL = new ResourceLocation(DefusedMod.MODID, "textures/toxicity/toxic_heart_full.png");
    private static final ResourceLocation TOXIC_HEART_EMPTY = new ResourceLocation(DefusedMod.MODID, "textures/toxicity/toxic_heart_empty.png");

    private static GuiGraphics guiGraphics = new GuiGraphics(Minecraft.getInstance().gameRenderer.getMinecraft(), Minecraft.getInstance().renderBuffers().bufferSource());
    public static final IGuiOverlay HUD_TOXIC = ((gui, poseStack, partialTick, width, height) -> {
        if(ModDropsHandler.hasToxicity & !ModDropsHandler.isCreative) {
            int x = width / 2;
            int y = height;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TOXIC_HEART_EMPTY);
            System.out.println("huh, eh? ha! heh heh");
            for (int i = 0; i < ModDropsHandler.health; i++) {
                int eh = (int)(Math.random()*3 - 1.5);
                guiGraphics.blit(TOXIC_HEART_FULL, x - 91 + (i * 8), y - 39, 0, 0, 9, 9,
                        9, 9);
            }
            for (int i = 0; i <(10- ModDropsHandler.health); i++) {

                guiGraphics.blit(TOXIC_HEART_EMPTY, x - 91 + ((ModDropsHandler.health + i) * 8), y - 39, 0, 0, 9, 9,
                        9, 9);
            }
        }

    });
}

package net.atired.thedefused.event;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.client.PotionChargeHudOverlay;
import net.atired.thedefused.client.ToxicHudOverlay;
import net.atired.thedefused.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.time.chrono.MinguoChronology;

public class ClientEvents {
    private static final ResourceLocation TEST_SHADER = new ResourceLocation(DefusedMod.MODID,"shaders/post/nightvis.json");

    @Mod.EventBusSubscriber(modid = DefusedMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {

            event.registerAboveAll("toxichearts", ToxicHudOverlay.HUD_TOXIC);
            event.registerAboveAll("potioncharges", PotionChargeHudOverlay.HUD_CHARGE);
        }
    }

        @SubscribeEvent
        public void postRenderStage(RenderLevelStageEvent event) {
            Entity player = Minecraft.getInstance().getCameraEntity();
            boolean firstPerson = Minecraft.getInstance().options.getCameraType().isFirstPerson();

            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {

                GameRenderer renderer = Minecraft.getInstance().gameRenderer;

            }

        }
        private static void attemptLoadShader(ResourceLocation resourceLocation) {
            GameRenderer renderer = Minecraft.getInstance().gameRenderer;
                renderer.loadEffect(resourceLocation);

        }

}

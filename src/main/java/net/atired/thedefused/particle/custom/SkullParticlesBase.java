package net.atired.thedefused.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkullParticlesBase<T extends DustParticleOptionsBase> extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected SkullParticlesBase(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, T pOptions, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = pSprites;
        this.xd *= 0.10000000149011612;
        this.yd *= 0.10000000149011612;
        this.zd *= 0.10000000149011612;

        this.rCol = pOptions.getColor().x();
        this.gCol = pOptions.getColor().y();
        this.bCol = pOptions.getColor().z();
        this.quadSize *= 3F * pOptions.getScale();
        this.alpha = 0.7F;
        this.lifetime = 7;
        this.setSpriteFromAge(pSprites);
    }



    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public float getQuadSize(float pScaleFactor) {
        return this.quadSize;
    }

    public void tick() {
        super.tick();
        this.roll = (float)(Math.sin((double)(this.age*20))/7);
        this.oRoll = this.roll;
        this.alpha -= 0.02F;
        this.setSpriteFromAge(this.sprites);
    }
}
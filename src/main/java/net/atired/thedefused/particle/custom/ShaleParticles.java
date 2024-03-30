package net.atired.thedefused.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShaleParticles extends TextureSheetParticle {
    private float rotSpeed;
    private final SpriteSet sprites;
    protected ShaleParticles(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.gravity = -0.2F;
        this.friction = 0.5F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.sprites = spriteSet;
        this.quadSize *= 1.5F + (float) Math.random() - 0.5F;
        this.lifetime = 9;
        this.rotSpeed = ((float) Math.random() - 0.5F) * 0.1F;
        this.roll = (float) Math.random() * ((float) Math.PI * 2F);
        this.oRoll = this.roll;
        this.rCol = 1f;

        this.gCol = 0.75f+ ((float) Math.random() - 0.5F)/2;
        this.bCol = 0.75f+ ((float) Math.random() - 0.5F)/2;
        this.setSpriteFromAge(spriteSet);

    }

    @Override
    public void tick() {

        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.oRoll = this.roll;
        if((float)this.lifetime/(float)this.age < 0.5)
        {
            this.alpha = ((float)this.age/(float)this.lifetime)*((float)this.age/(float)this.lifetime)*4;
        }
        else  this.alpha = (-(1/(float)lifetime) * age + 1.2F);


    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new ShaleParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
    @Override
    public int getLightColor(float pPartialTick) {
        return  253;
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}

package net.atired.thedefused.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class CombustionParticles extends TextureSheetParticle {

    private final SpriteSet sprites;
    protected CombustionParticles(ClientLevel pLevel, double pX, double pY, double pZ,SpriteSet spriteSet,double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.gravity = -0.1F;
        this.friction = 0.8F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.sprites = spriteSet;
        this.quadSize *= 4F;
        this.lifetime = 5;
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
        this.setSpriteFromAge(spriteSet);

    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.quadSize = 1.5F + ((float)this.age/(float)this.lifetime)*(float)0.75;
        if((float)this.lifetime/(float)this.age < 0.5)
        {
            this.alpha = ((float)this.age/(float)this.lifetime)*((float)this.age/(float)this.lifetime)*4;
        }
        else  this.alpha = (-(1/(float)lifetime) * age + 1);

        this.roll  = 1- (float)this.age/(float)this.lifetime/2;
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
            return new CombustionParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}

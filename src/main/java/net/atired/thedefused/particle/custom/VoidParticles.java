package net.atired.thedefused.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class VoidParticles extends TextureSheetParticle {
    private Vec3 speeds;
    private Vec3 randomness;
    private Random eheh = new Random();
    private final SpriteSet sprites;
    protected VoidParticles(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.gravity = 0F;
        this.friction = 0.5F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.randomness = new Vec3(eheh.nextDouble()-0.5,eheh.nextDouble()-0.5,eheh.nextDouble()-0.5).multiply(0.02,0.02,0.02);
        System.out.println(eheh.nextDouble());
        this.speeds = new Vec3(pXSpeed,pYSpeed,pZSpeed).multiply(-0.18,-0.18,-0.18);
        this.sprites = spriteSet;
        this.quadSize *= 1.8F + (float) Math.random() - 0.5F;
        this.lifetime = 12;
        this.speeds = this.speeds.multiply(1.1,1.1,1.1).add(this.randomness);
        this.rCol = 0.7f;
        this.bCol = 0.7f;
        this.gCol = 0.8f;
        this.setSpriteFromAge(spriteSet);

    }

    @Override
    public void tick() {
        this.xd += this.speeds.x;
        this.yd += this.speeds.y;
        this.zd += this.speeds.z;
        this.roll +=this.age/20F;
        this.oRoll = this.roll;
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.alpha -= 0.05f;
        this.quadSize *= 0.9f;
        this.rCol += 0.025f;
        this.bCol += 0.025f;
        this.gCol -= 0.025f;
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
            return new VoidParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
    @Override
    public int getLightColor(float pPartialTick) {
        return  253;
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}

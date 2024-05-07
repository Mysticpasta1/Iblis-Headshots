//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;

@OnlyIn(Dist.CLIENT)
public class ParticleHeadshot extends Particle
{
    private static final ResourceLocation TEXTURE;
    private static final VertexFormat VERTEX_FORMAT;
    private final TextureManager textureManager;
    private final float size;
    private int headshotParticleType;
    
    public ParticleHeadshot(final TextureManager textureManagerIn, final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final float sizeIn, final int headshotParticleTypeIn, final int particleMaxAgeIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.textureManager = textureManagerIn;
        this.size = sizeIn;
        this.particleMaxAge = particleMaxAgeIn;
        this.headshotParticleType = headshotParticleTypeIn;
        this.motionX = xSpeedIn * (Math.random() * 1.5 - 0.5);
        this.motionY = ySpeedIn * (Math.random() * 1.5 - 0.5);
        this.motionZ = zSpeedIn * (Math.random() * 1.5 - 0.5);
    }
    
    public int getBrightnessForRender(final float f) {
        return 61680;
    }
    
    public int getFXLayer() {
        return 3;
    }
    
    static {
        TEXTURE = new ResourceLocation("iblis_headshots:textures/particle/particles.png");
        VERTEX_FORMAT = new VertexFormat().addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
    }

    @Override
    public void render(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float v) {
        Minecraft.getInstance().textureManager.getTexture(ParticleHeadshot.TEXTURE);
        final float u1 = (this.headshotParticleType - 1) * 16.0f / 256.0f;
        final float u2 = u1 + 0.0625f;
        final float v1 = 0.69140625f;
        final float v2 = v1 + 0.0625f;
        final float f4 = 2.0f * this.size;
        final float f5 = (float)(this.xo + (this.x - this.xo) * v);
        final float f6 = (float)(this.yo + (this.y - this.yo) * v);
        final float f7 = (float)(this.zo + (this.z - this.zo) * v);
        GlStateManager._color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager._disableLighting();
        RenderHelper.setupFor3DItems();
        iVertexBuilder.vertex((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex((double)u2, (double)v2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
        iVertexBuilder.vertex((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex((double)u2, (double)v1).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
        iVertexBuilder.vertex((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex((double)u1, (double)v1).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
        iVertexBuilder.vertex((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex((double)u1, (double)v2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
        Tessellator.getInstance().end();
        GlStateManager._enableLighting();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return null;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots;

import net.minecraftforge.client.model.obj.*;
import net.minecraftforge.common.*;
import iblis_headshots.client.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import iblis_headshots.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.particle.*;

public class ClientProxy extends ServerProxy
{
    @Override
    void load() {
        OBJLoader.INSTANCE.addDomain("iblis_headshots");
        MinecraftForge.EVENT_BUS.register(new ItemTooltipEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
    }
    
    @Override
    public void init() {
    }
    
    public void spawnParticle(final double posX, final double posY, final double posZ, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final int maxAge) {
        if (this.headshotParticleType == 0) {
            return;
        }
        final Minecraft mc = Minecraft.getInstance();
        final Entity renderViewEntity = mc.getCameraEntity();
        double distance = renderViewEntity.distanceToSqr(posX, posY, posZ);
        distance = MathHelper.sqrt(distance);
        final Particle entityParticle = new ParticleHeadshot(mc.getTextureManager(), mc.level, posX, posY, posZ, xSpeedIn, ySpeedIn, zSpeedIn, (float)(distance / 1000.0 + 0.01) * this.headshotParticleSize, this.headshotParticleType, maxAge);
        mc.effectRenderer.addEffect(entityParticle);
    }
}

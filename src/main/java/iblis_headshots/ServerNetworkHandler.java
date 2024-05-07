//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.common.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.network.internal.*;
import io.netty.buffer.*;

public class ServerNetworkHandler
{
    protected static FMLEventChannel channel;
    
    public void load() {
        if (ServerNetworkHandler.channel == null) {
            (ServerNetworkHandler.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("iblis_headshots")).register((Object)this);
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void setServer(final MinecraftServer serverIn) {
    }
    
    public void spawnHeadshotParticle(final World world, final Vector3d pos, final Vector3d speed, final int maxAge) {
        final ByteBuf bb = Unpooled.buffer(36);
        final PacketBuffer byteBufOutputStream = new PacketBuffer(bb);
        byteBufOutputStream.writeByte(ClientCommands.SPAWN_HEADSHOT_PARTICLE.ordinal());
        byteBufOutputStream.writeDouble(pos.x);
        byteBufOutputStream.writeDouble(pos.y);
        byteBufOutputStream.writeDouble(pos.z);
        byteBufOutputStream.writeDouble(speed.x);
        byteBufOutputStream.writeDouble(speed.y);
        byteBufOutputStream.writeDouble(speed.z);
        byteBufOutputStream.writeInt(maxAge);
        ServerNetworkHandler.channel.sendToAllAround(new FMLProxyPacket(byteBufOutputStream, "iblis_headshots"), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.x, pos.y, pos.z, 64.0));
    }
    
    public enum ClientCommands
    {
        SPAWN_HEADSHOT_PARTICLE;
    }
}

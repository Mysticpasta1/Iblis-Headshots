//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots;

import net.minecraftforge.fml.common.network.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import io.netty.buffer.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import java.io.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ClientNetworkHandler extends ServerNetworkHandler
{
    @SubscribeEvent
    public void onPacketFromServerToClient(final FMLNetworkEvent.ClientCustomPacketEvent event) throws IOException {
        final Minecraft mc = Minecraft.getMinecraft();
        final ByteBuf data = event.getPacket().payload();
        final PacketBuffer byteBufInputStream = new PacketBuffer(data);
        final WorldClient world = mc.world;
        final EntityPlayerSP player = Minecraft.getMinecraft().player;
        switch (ClientCommands.values()[byteBufInputStream.readByte()]) {
            case SPAWN_HEADSHOT_PARTICLE: {
                final double posX = byteBufInputStream.readDouble();
                final double posY = byteBufInputStream.readDouble();
                final double posZ = byteBufInputStream.readDouble();
                final double xSpeed = byteBufInputStream.readDouble();
                final double ySpeed = byteBufInputStream.readDouble();
                final double zSpeed = byteBufInputStream.readDouble();
                final int maxAge = byteBufInputStream.readInt();
                final double posX2;
                final double posY2;
                final double posZ2;
                final double xSpeedIn;
                final double ySpeedIn;
                final double zSpeedIn;
                final int maxAge2;
                final WorldClient worldClient;
                final EntityPlayerSP entityPlayerSP;
                mc.addScheduledTask(() -> {
                    ((ClientProxy)IblisHeadshotsMod.proxy).spawnParticle(posX2, posY2, posZ2, xSpeedIn, ySpeedIn, zSpeedIn, maxAge2);
                    worldClient.playSound((EntityPlayer)entityPlayerSP, entityPlayerSP.posX, entityPlayerSP.posY, entityPlayerSP.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.25f, 1.0f);
                    return;
                });
                break;
            }
        }
    }
}

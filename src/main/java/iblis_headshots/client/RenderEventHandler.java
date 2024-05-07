package iblis_headshots.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import iblis_headshots.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RenderEventHandler
{
    @SubscribeEvent
    public static void onWorldRender(final RenderWorldLastEvent event) {
        if (!Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            return;
        }
        final PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        final double renderPosX = player.xOld + (player.getX() - player.xOld) * event.getPartialTicks();
        final double renderPosY = player.yOld + (player.getY() - player.yOld) * event.getPartialTicks();
        final double renderPosZ = player.zOld + (player.getZ() - player.zOld) * event.getPartialTicks();
        final World world = player.level;
        final List<Entity> entities = world.getEntities(player, player.getBoundingBox().inflate(6.0));
        GlStateManager._enableBlend();
        GlStateManager._blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SourceFactor.ONE.value, GlStateManager.DestFactor.ZERO.value);
        GlStateManager._lineWidth(2.0f);
        GlStateManager._disableTexture();
        GlStateManager._depthMask(false);
        for (final Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            final AxisAlignedBB aabb = HeadShotHandler.getHeadBox((LivingEntity) entity);
            WorldRenderer.renderLineBox(new MatrixStack(), new BufferBuilder(256).getVertexBuilder(), aabb.inflate(0.002).expandTowards(-renderPosX, -renderPosY, -renderPosZ), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        GlStateManager._depthMask(true);
        GlStateManager._enableTexture();
        GlStateManager._disableBlend();
    }
}

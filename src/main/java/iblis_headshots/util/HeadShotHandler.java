package iblis_headshots.util;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;

public class HeadShotHandler
{
    private static final AxisAlignedBB zero;
    private static final AxisAlignedBB slimeCore;
    private static final AxisAlignedBB shulkerCore;
    private static final AxisAlignedBB humanoidHead;
    private static final AxisAlignedBB huskHead;
    private static final AxisAlignedBB spiderHead;
    private static final AxisAlignedBB chickenHead;
    private static final AxisAlignedBB cowHead;
    private static final AxisAlignedBB donkeyHead;
    private static final AxisAlignedBB guardianEye;
    private static final AxisAlignedBB ghastEyes;
    private static final AxisAlignedBB polarBearHead;
    
    public static BlockRayTraceResult traceHeadShot(final LivingEntity entity, final Vector3d impactStart, final Vector3d impactEnd) {
        Vector3d headBox = getHeadBox(entity).clip(impactStart, impactEnd).get();

        return new BlockRayTraceResult(headBox, entity.getDirection(), new BlockPos(headBox.x, headBox.y, headBox.z), true);
    }
    
    public static AxisAlignedBB getHeadBox(final LivingEntity entity) {
        AxisAlignedBB box;
        final AxisAlignedBB collisionBoundingBox = entity.getBoundingBox();
        if (entity instanceof SlimeEntity) {
            box = shrinkBoxTo(collisionBoundingBox, HeadShotHandler.slimeCore);
        }
        else if (entity instanceof ShulkerEntity) {
            box = shrinkBoxTo(collisionBoundingBox, HeadShotHandler.shulkerCore);
        }
        else {
            if (entity instanceof BatEntity || entity instanceof EndermiteEntity || entity instanceof OcelotEntity || entity instanceof ParrotEntity || entity instanceof SilverfishEntity || entity instanceof SquidEntity) {
                return HeadShotHandler.zero;
            }
            if (entity instanceof SpiderEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.spiderHead, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
            }
            else if (entity instanceof ChickenEntity || entity instanceof RabbitEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.chickenHead, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
            }
            else if (entity instanceof CowEntity || entity instanceof PigEntity || entity instanceof SheepEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.cowHead, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
            }
            else if (entity instanceof DonkeyEntity || entity instanceof MuleEntity || entity instanceof LlamaEntity || entity instanceof HorseEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.donkeyHead, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
            }
            else if (entity instanceof GuardianEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.guardianEye, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
            }
            else if (entity instanceof GhastEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.ghastEyes, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
            }
            else if (entity instanceof PolarBearEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.polarBearHead, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
                if (entity.swingTime > 0.2f) {
                    box = box.expandTowards(0.0, entity.swingTime, 0.0);
                }
            }
            else if (entity instanceof WolfEntity) {
                final AxisAlignedBB headBoxRotated = rotateAroundY(HeadShotHandler.polarBearHead, entity.yBodyRot);
                box = shrinkBoxTo(collisionBoundingBox, headBoxRotated);
            }
            else if (entity instanceof HuskEntity) {
                box = shrinkBoxTo(collisionBoundingBox, HeadShotHandler.huskHead);
            }
            else {
                box = shrinkBoxTo(collisionBoundingBox, HeadShotHandler.humanoidHead);
            }
        }
        return box;
    }
    
    private static AxisAlignedBB rotateAroundY(final AxisAlignedBB headBox, final float yaw) {
        final float cos = MathHelper.cos(-yaw * 0.017453292f);
        final float sin = MathHelper.sin(-yaw * 0.017453292f);
        final float dminX = (float)(headBox.minX - 0.5);
        final float dmaxX = (float)(headBox.maxX - 0.5);
        final float dminZ = (float)(headBox.minZ - 0.5);
        final float dmaxZ = (float)(headBox.maxZ - 0.5);
        final float x00 = 0.5f + sin * dminX + cos * dminZ;
        final float z00 = 0.5f + cos * dminX + sin * dminZ;
        final float x2 = 0.5f + sin * dmaxX + cos * dmaxZ;
        final float z2 = 0.5f + cos * dmaxX + sin * dmaxZ;
        final float x3 = 0.5f + sin * dmaxX + cos * dminZ;
        final float z3 = 0.5f + cos * dmaxX + sin * dminZ;
        final float x4 = 0.5f + sin * dminX + cos * dmaxZ;
        final float z4 = 0.5f + cos * dminX + sin * dmaxZ;
        return new AxisAlignedBB((double)min(x00, x3, x4, x2), headBox.minY, (double)min(z00, z3, z4, z2), (double)max(x00, x3, x4, x2), headBox.maxY, (double)max(z00, z3, z4, z2));
    }
    
    private static float min(final float v1, final float v2, final float v3, final float v4) {
        float v5 = v1;
        v5 = ((v2 < v5) ? v2 : v5);
        v5 = ((v3 < v5) ? v3 : v5);
        v5 = ((v4 < v5) ? v4 : v5);
        return v5;
    }
    
    private static float max(final float v1, final float v2, final float v3, final float v4) {
        float v5 = v1;
        v5 = ((v2 > v5) ? v2 : v5);
        v5 = ((v3 > v5) ? v3 : v5);
        v5 = ((v4 > v5) ? v4 : v5);
        return v5;
    }
    
    private static AxisAlignedBB shrinkBoxTo(final AxisAlignedBB original, final AxisAlignedBB shrinkTo) {
        final double sizeX = original.maxX - original.minX;
        final double sizeY = original.maxY - original.minY;
        final double sizeZ = original.maxZ - original.minZ;
        return new AxisAlignedBB(original.minX + shrinkTo.minX * sizeX, original.minY + shrinkTo.minY * sizeY, original.minZ + shrinkTo.minZ * sizeZ, original.minX + shrinkTo.maxX * sizeX, original.minY + shrinkTo.maxY * sizeY, original.minZ + shrinkTo.maxZ * sizeZ);
    }
    
    static {
        zero = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        slimeCore = new AxisAlignedBB(0.4, 0.4, 0.4, 0.6, 0.6, 0.6);
        shulkerCore = new AxisAlignedBB(0.4, 0.1, 0.4, 0.6, 0.4, 0.6);
        humanoidHead = new AxisAlignedBB(0.1, 0.8, 0.1, 0.9, 1.0, 0.9);
        huskHead = new AxisAlignedBB(0.1, 0.8, 0.1, 0.9, 1.1, 0.9);
        spiderHead = new AxisAlignedBB(0.6, 0.5, 0.3, 1.2, 1.2, 0.7);
        chickenHead = new AxisAlignedBB(0.9, 0.8, 0.3, 1.4, 1.4, 0.7);
        cowHead = new AxisAlignedBB(0.9, 0.7, 0.2, 1.4, 1.2, 0.8);
        donkeyHead = new AxisAlignedBB(0.7, 0.7, 0.2, 1.0, 1.0, 0.8);
        guardianEye = new AxisAlignedBB(0.8, 0.4, 0.4, 1.0, 0.6, 0.6);
        ghastEyes = new AxisAlignedBB(0.8, 0.6, 0.2, 1.0, 0.7, 0.8);
        polarBearHead = new AxisAlignedBB(1.0, 0.6, 0.2, 1.5, 1.0, 0.8);
    }
}

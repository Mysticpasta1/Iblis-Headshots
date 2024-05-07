package iblis_headshots.event;

import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import iblis_headshots.*;
import com.google.common.collect.*;
import iblis_headshots.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class IblisHeadshotsEventHandler
{
    public static float nonProjectileHeadshotMinDistanceSq;
    public static float damageToItemMultiplier;
    public static float damageMultiplier;
    public static float missMultiplier;
    public static boolean playersHaveNoHeads;
    private boolean debug;
    private int lastHandledEntityId;

    public IblisHeadshotsEventHandler() {
        this.debug = false;
        this.lastHandledEntityId = -1;
    }
    
    @SubscribeEvent
    public void onLivingHurt(final LivingHurtEvent event) {
        if (event.isCanceled()) {
            return;
        }
        final float damage = event.getAmount();
        final LivingEntity victim = event.getEntityLiving();
        this.lastHandledEntityId = victim.getId();
        event.setAmount(this.recalculateDamage(damage, victim, event.getSource()));
    }
    
    @SubscribeEvent
    public void onLivingDamage(final LivingDamageEvent event) {
        final float damage = event.getAmount();
        final LivingEntity victim = event.getEntityLiving();
        if (this.lastHandledEntityId == victim.getId()) {
            this.lastHandledEntityId = -1;
            return;
        }
        event.setAmount(this.recalculateDamage(damage, victim, event.getSource()));
    }
    
    public float recalculateDamage(float damage, final LivingEntity victim, final DamageSource source) {
        final World world = victim.level;
        if (world.isClientSide || damage < 0.1f || !(world instanceof ServerWorld)) {
            return damage;
        }
        final Entity projectile = source.getDirectEntity();
        if (projectile == null) {
            return damage;
        }
        Vector3d start = new Vector3d(projectile.getX() - projectile.getMotionDirection().getStepX(), projectile.getY() - projectile.getMotionDirection().getStepY(), projectile.getZ() - projectile.getMotionDirection().getStepZ());
        Vector3d end = new Vector3d(projectile.getX() + projectile.getMotionDirection().getStepX(), projectile.getY() + projectile.getMotionDirection().getStepY(), projectile.getZ() + projectile.getMotionDirection().getStepZ());
        if (projectile instanceof PlayerEntity) {
            final double d = projectile.distanceToSqr((Entity)victim);
            if (d < IblisHeadshotsEventHandler.nonProjectileHeadshotMinDistanceSq) {
                return damage;
            }
            start = new Vector3d(projectile.getX(), projectile.getY() + projectile.getEyeHeight(), projectile.getZ());
            final Vector3d aim = projectile.getLookAngle();
            end = new Vector3d(projectile.getX() + aim.x * d, projectile.getY() + projectile.getEyeHeight() + aim.y * d, projectile.getZ() + aim.z * d);
        }
        if (this.debug) {
            for (int i = 20; i < 60; ++i) {
                final Vector3d iv = start.add(projectile.getMotionDirection().getStepX() * i * 0.05, projectile.getMotionDirection().getStepY() * i * 0.05, projectile.getMotionDirection().getStepZ() * i * 0.05);
                IblisHeadshotsMod.network.spawnHeadshotParticle(victim.level, iv, Vector3d.ZERO, 150);
            }
        }
        if ((!IblisHeadshotsEventHandler.playersHaveNoHeads || !(victim instanceof PlayerEntity)) && HeadShotHandler.traceHeadShot(victim, start, end) != null) {
            IblisHeadshotsMod.network.spawnHeadshotParticle(victim.level, new Vector3d(victim.getX(), victim.getY() + victim.getEyeHeight(), victim.getZ()), new Vector3d(0.0, 0.2, 0.0), 15);
            float multiplier = IblisHeadshotsEventHandler.damageMultiplier;
            final ItemStack headgear = victim.getItemBySlot(EquipmentSlotType.HEAD);
            final HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
            this.addModifierOfStackInSlot(victim, multimap, EquipmentSlotType.CHEST);
            this.addModifierOfStackInSlot(victim, multimap, EquipmentSlotType.LEGS);
            this.addModifierOfStackInSlot(victim, multimap, EquipmentSlotType.FEET);
            if (!multimap.isEmpty()) {
                victim.getAttributes().removeAttributeModifiers(multimap);
                final ServerWorld wserver = (ServerWorld)world;
                final HashMultimap<Attribute, AttributeModifier>[] multimap2 = new HashMultimap[1];
                wserver.tick(() -> {
                    multimap2[0] = HashMultimap.create();
                    this.addModifierOfStackInSlot(victim, multimap2[0], EquipmentSlotType.CHEST);
                    this.addModifierOfStackInSlot(victim, multimap2[0], EquipmentSlotType.LEGS);
                    this.addModifierOfStackInSlot(victim, multimap2[0], EquipmentSlotType.FEET);
                    victim.getAttributes().addTransientAttributeModifiers(multimap2[0]);
                    return true;
                });
            }
            if (!headgear.isEmpty()) {
                final float headGearDamageAbsorbMultiplier = IblisItemUtils.getHeadgearProtection(headgear);
                multiplier = 1.0f + Math.max(multiplier - 1.0f, 0.0f) * headGearDamageAbsorbMultiplier;
                headgear.setDamageValue((int)((victim.level.random.nextFloat() * 0.5 + 1.0) * damage * IblisHeadshotsEventHandler.damageToItemMultiplier));
            }
            damage *= multiplier;
            final Entity shooter = source.getDirectEntity();
            if (victim.getHealth() < damage && victim instanceof SlimeEntity && ((SlimeEntity)victim).getSize() > 1) {
                victim.setHealth(0);
            }
        }
        else {
            damage *= IblisHeadshotsEventHandler.missMultiplier;
        }
        return damage;
    }
    
    private void addModifierOfStackInSlot(final LivingEntity entity, final HashMultimap<Attribute, AttributeModifier> multimap, final EquipmentSlotType slot) {
        final ItemStack stack = entity.getItemBySlot(slot);
        if (!stack.isEmpty()) {
            multimap.putAll(stack.getAttributeModifiers(slot));
        }
    }
    
    static {
        IblisHeadshotsEventHandler.nonProjectileHeadshotMinDistanceSq = 16.0f;
        IblisHeadshotsEventHandler.damageToItemMultiplier = 4.0f;
        IblisHeadshotsEventHandler.damageMultiplier = 4.0f;
        IblisHeadshotsEventHandler.missMultiplier = 1.0f;
        IblisHeadshotsEventHandler.playersHaveNoHeads = false;
    }
}

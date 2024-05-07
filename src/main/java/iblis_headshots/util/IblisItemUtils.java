//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots.util;

import io.netty.util.AttributeMap;
import net.minecraft.item.*;
import iblis_headshots.item.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class IblisItemUtils
{
    private static AttributeMap attributeMap;
    private static IAttributeInstance armor;
    private static IAttributeInstance armorToughness;
    
    public static float getHeadgearProtection(final ItemStack stack) {
        final ResourceLocation registryName = stack.getItem().getRegistryName();
        if (HelmetsConfig.HELMETS_REGISTRY.containsKey((Object)registryName)) {
            return HelmetsConfig.HELMETS_REGISTRY.getFloat((Object)registryName);
        }
        final Multimap<String, AttributeModifier> aMods = (Multimap<String, AttributeModifier>)stack.getAttributeModifiers(EntityEquipmentSlot.HEAD);
        if (aMods.isEmpty()) {
            return 1.0f;
        }
        for (final AttributeModifier mod : aMods.get((Object)IblisItemUtils.armor.getAttribute().getName())) {
            IblisItemUtils.armor.applyModifier(mod);
        }
        for (final AttributeModifier mod : aMods.get((Object)IblisItemUtils.armorToughness.getAttribute().getName())) {
            IblisItemUtils.armorToughness.applyModifier(mod);
        }
        final float headGearDamageAbsorbMultiplier = CombatRules.getDamageAfterAbsorb(1.0f, (float)IblisItemUtils.armor.getAttributeValue(), (float)IblisItemUtils.armorToughness.getAttributeValue());
        float headGearDamageAbsorbMultiplier2 = headGearDamageAbsorbMultiplier * headGearDamageAbsorbMultiplier;
        headGearDamageAbsorbMultiplier2 *= headGearDamageAbsorbMultiplier2;
        headGearDamageAbsorbMultiplier2 *= headGearDamageAbsorbMultiplier2;
        headGearDamageAbsorbMultiplier2 *= headGearDamageAbsorbMultiplier2;
        for (final AttributeModifier mod2 : aMods.get((Object)IblisItemUtils.armor.getAttribute().getName())) {
            IblisItemUtils.armor.removeModifier(mod2);
        }
        for (final AttributeModifier mod2 : aMods.get((Object)IblisItemUtils.armorToughness.getAttribute().getName())) {
            IblisItemUtils.armorToughness.removeModifier(mod2);
        }
        return headGearDamageAbsorbMultiplier2;
    }
    
    static {
        IblisItemUtils.attributeMap = new AttributeMap();
        IblisItemUtils.armor = (IAttributeInstance)new ModifiableAttributeInstance((AbstractAttributeMap)IblisItemUtils.attributeMap, SharedMonsterAttributes.ARMOR);
        IblisItemUtils.armorToughness = (IAttributeInstance)new ModifiableAttributeInstance((AbstractAttributeMap)IblisItemUtils.attributeMap, SharedMonsterAttributes.ARMOR);
    }
}

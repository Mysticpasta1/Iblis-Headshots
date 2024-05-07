//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraftforge.event.entity.player.*;
import iblis_headshots.util.*;
import net.minecraft.util.math.*;
import iblis_headshots.event.*;
import net.minecraft.util.text.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

@SideOnly(Side.CLIENT)
public class ItemTooltipEventHandler
{
    AttributeMap attributeMap;
    IAttributeInstance armor;
    IAttributeInstance armorToughness;
    public static final String[] protectionLevels;
    
    public ItemTooltipEventHandler() {
        this.attributeMap = new AttributeMap();
        this.armor = (IAttributeInstance)new ModifiableAttributeInstance((AbstractAttributeMap)this.attributeMap, SharedMonsterAttributes.ARMOR);
        this.armorToughness = (IAttributeInstance)new ModifiableAttributeInstance((AbstractAttributeMap)this.attributeMap, SharedMonsterAttributes.ARMOR);
    }
    
    @SubscribeEvent
    public void onItemTooltipEvent(final ItemTooltipEvent event) {
        final ItemStack is = event.getItemStack();
        final float headGearDamageAbsorbMultiplier = IblisItemUtils.getHeadgearProtection(is);
        final int absorptionPercents = MathHelper.ceil((1.0f - headGearDamageAbsorbMultiplier) * 100.0f);
        if (headGearDamageAbsorbMultiplier != 1.0f && !IblisHeadshotsEventHandler.playersHaveNoHeads) {
            event.getToolTip().add(TextFormatting.LIGHT_PURPLE + I18n.format("iblis.headshot_protection", new Object[] { absorptionPercents }) + "%");
            addProtectionTooltip(event.getToolTip(), absorptionPercents / 10);
        }
    }
    
    public static void addProtectionTooltip(final List<String> tooltip, final int protectionRaw) {
        int protection = protectionRaw;
        if (protection < 0) {
            protection = 0;
        }
        if (protection >= ItemTooltipEventHandler.protectionLevels.length) {
            protection = ItemTooltipEventHandler.protectionLevels.length;
        }
        final String protectionLevel = ItemTooltipEventHandler.protectionLevels[protection];
        tooltip.add(TextFormatting.LIGHT_PURPLE + I18n.format(protectionLevel, new Object[0]));
    }
    
    static {
        protectionLevels = new String[] { "iblis.protectionLevel.no", "iblis.protectionLevel.weak", "iblis.protectionLevel.miserable", "iblis.protectionLevel.awful", "iblis.protectionLevel.bad", "iblis.protectionLevel.normal", "iblis.protectionLevel.good", "iblis.protectionLevel.excellent", "iblis.protectionLevel.marvelous", "iblis.protectionLevel.exceptional", "iblis.protectionLevel.perfect" };
    }
}

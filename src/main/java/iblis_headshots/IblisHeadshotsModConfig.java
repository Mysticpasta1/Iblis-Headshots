//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots;

import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import iblis_headshots.event.*;

public class IblisHeadshotsModConfig
{
    public Configuration configuration;
    
    public IblisHeadshotsModConfig(final Configuration configuration) {
        this.loadConfig(configuration);
        this.syncConfig();
    }
    
    public static String getNicelyFormattedName(final String name) {
        final StringBuffer out = new StringBuffer();
        final char char_ = '_';
        char prevchar = '\0';
        for (final char c : name.toCharArray()) {
            if (c != char_ && prevchar != char_) {
                out.append(String.valueOf(c).toLowerCase());
            }
            else if (c != char_) {
                out.append(String.valueOf(c));
            }
            prevchar = c;
        }
        return out.toString();
    }
    
    void loadConfig(final Configuration configuration) {
        this.configuration = configuration;
    }
    
    @SubscribeEvent
    public void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.getModID().equals("iblis_headshots")) {
            IblisHeadshotsMod.config.syncConfig();
        }
    }
    
    void syncConfig() {
        IblisHeadshotsMod.proxy.headshotParticleSize = this.configuration.getFloat("headshot_particle_size", "general", 10.0f, 0.0f, 1000000.0f, "Size of headshot particle");
        IblisHeadshotsMod.proxy.headshotParticleType = this.configuration.getInt("headshot_particle_type", "general", 1, 0, 3, "0 - no particle, 1 - skull, 2 - aim symbol, 3 - star");
        IblisHeadshotsEventHandler.nonProjectileHeadshotMinDistanceSq = this.configuration.getFloat("non_projectile_headshot_min_distance", "general", 16.0f, 0.0f, 1000000.0f, "Minimal distance at which non-projectile attack count as headshot. Affect Flans mod weapons as well.");
        IblisHeadshotsEventHandler.nonProjectileHeadshotMinDistanceSq *= IblisHeadshotsEventHandler.nonProjectileHeadshotMinDistanceSq;
        IblisHeadshotsEventHandler.damageMultiplier = this.configuration.getFloat("headshot_damage_mutiplier", "general", 4.0f, 0.0f, 1000000.0f, "Multiplier of damage caused by headshot.");
        IblisHeadshotsEventHandler.damageToItemMultiplier = this.configuration.getFloat("headgear_damage_mutiplier", "general", 4.0f, 0.0f, 1000000.0f, "Multiplier of damage to headgear item on headshot.");
        IblisHeadshotsEventHandler.missMultiplier = this.configuration.getFloat("bodyshot_damage_mutiplier", "general", 1.0f, 0.0f, 1000000.0f, "Multiplier of damage caused by regular shot (in everything except head).");
        IblisHeadshotsEventHandler.playersHaveNoHeads = this.configuration.getBoolean("players_have_no_heads", "general", false, "If true, players will not recieve headshots");
        if (this.configuration.hasChanged()) {
            this.configuration.save();
        }
    }
}

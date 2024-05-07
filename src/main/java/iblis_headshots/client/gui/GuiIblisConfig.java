//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots.client.gui;

import net.minecraftforge.fml.client.config.*;
import net.minecraft.client.gui.*;
import iblis_headshots.*;
import net.minecraftforge.common.config.*;

public class GuiIblisConfig extends GuiConfig
{
    public GuiIblisConfig(final GuiScreen parent) {
        super(parent, new ConfigElement(IblisHeadshotsMod.config.configuration.getCategory("general")).getChildElements(), "iblis_headshots", false, false, GuiConfig.getAbridgedConfigPath(IblisHeadshotsMod.config.configuration.toString()));
    }
}

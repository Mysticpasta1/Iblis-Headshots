//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots.client.gui;

import net.minecraftforge.fml.client.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class IblisGuiFactory implements IModGuiFactory
{
    public void initialize(final Minecraft minecraftInstance) {
    }
    
    public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
    
    public boolean hasConfigGui() {
        return true;
    }
    
    public GuiScreen createConfigGui(final GuiScreen parentScreen) {
        return (GuiScreen)new GuiIblisConfig(parentScreen);
    }
}

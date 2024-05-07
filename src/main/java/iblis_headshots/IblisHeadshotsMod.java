//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\water\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

//Decompiled by Procyon!

package iblis_headshots;

import org.apache.logging.log4j.*;
import iblis_headshots.event.*;
import net.minecraft.item.*;
import net.minecraftforge.common.config.*;
import iblis_headshots.init.*;
import net.minecraftforge.common.*;
import iblis_headshots.item.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

@Mod("iblis_headshots")
public class IblisHeadshotsMod
{
    public static final String MODID = "iblis_headshots";
    public static final String VERSION = "1.2.6";
    public static final String DEPENDENCIES = "after:landcore;after:hardcorearmor;after:tconstruct;after:silentgems";
    public static final String GUI_FACTORY = "iblis_headshots.client.gui.IblisGuiFactory";
    @SidedProxy(clientSide = "iblis_headshots.ClientProxy", serverSide = "iblis_headshots.ServerProxy")
    public static ServerProxy proxy;
    @SidedProxy(clientSide = "iblis_headshots.ClientNetworkHandler", serverSide = "iblis_headshots.ServerNetworkHandler")
    public static ServerNetworkHandler network;
    public static Logger log;
    public static IblisHeadshotsEventHandler eventHandler;
    public static ItemArmor.ArmorMaterial armorMaterialSteel;
    public static ItemArmor.ArmorMaterial armorMaterialParaAramid;
    public static boolean isRPGHUDLoaded;
    public static boolean isAppleCoreLoaded;
    public static boolean isAppleskinLoaded;
    public static IblisHeadshotsModConfig config;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        IblisHeadshotsMod.eventHandler = new IblisHeadshotsEventHandler();
        IblisHeadshotsMod.log = event.getModLog();
        IblisHeadshotsMod.proxy.load();
        IblisHeadshotsMod.network.load();
        IblisHeadshotsMod.config = new IblisHeadshotsModConfig(new Configuration(event.getSuggestedConfigurationFile()));
        IblisHeadshotsAdvancements.register();
        MinecraftForge.EVENT_BUS.register((Object)IblisHeadshotsMod.config);
        MinecraftForge.EVENT_BUS.register((Object)IblisHeadshotsMod.eventHandler);
        MinecraftForge.EVENT_BUS.register((Object)IblisHeadshotsMod.proxy);
        HelmetsConfig.load();
    }
    
    @Mod.EventHandler
    public void init(final FMLPostInitializationEvent event) {
        IblisHeadshotsMod.proxy.init();
        IblisHeadshotsMod.isRPGHUDLoaded = Loader.isModLoaded("rpghud");
    }
    
    @Mod.EventHandler
    public void serverStarting(final FMLServerStartingEvent event) {
        IblisHeadshotsMod.network.setServer(event.getServer());
    }
    
    static {
        IblisHeadshotsMod.isRPGHUDLoaded = false;
        IblisHeadshotsMod.isAppleCoreLoaded = false;
        IblisHeadshotsMod.isAppleskinLoaded = false;
    }
}

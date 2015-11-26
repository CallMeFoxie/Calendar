package foxie.calendar;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import foxie.calendar.commands.FixedCommandTime;
import foxie.calendar.proxy.ProxyCommon;

@Mod(modid = CalendarMod.MODID, name = CalendarMod.NAME, version = CalendarMod.VERSION)
public class CalendarMod {
   public static final String MODID   = "Calendar";
   public static final String NAME    = "Calendar";
   public static final String VERSION = "@VERSION@";

   @SidedProxy(clientSide = "foxie.calendar.proxy.ProxyClient", serverSide = "foxie.calendar.proxy.ProxyCommon", modId = MODID)
   public static ProxyCommon proxy;

   @Mod.Instance(MODID)
   public static CalendarMod INSTANCE;

   @Mod.EventHandler
   public void preinit(FMLPreInitializationEvent event) {
      proxy.preinit(event);
   }

   @Mod.EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.init(event);
   }

   @Mod.EventHandler
   public void postinit(FMLPostInitializationEvent event) {
      proxy.postinit(event);
   }

   @Mod.EventHandler
   public void serverStarting(FMLServerStartingEvent event) {
      event.registerServerCommand(new FixedCommandTime());
   }

   @Mod.EventHandler
   public void serverStarted(FMLServerStartedEvent event) {
   }

}

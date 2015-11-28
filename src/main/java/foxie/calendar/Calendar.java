package foxie.calendar;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.commands.CommandDate;
import foxie.calendar.commands.FixedCommandTime;
import foxie.calendar.implementation.CalendarImpl;
import foxie.calendar.implementation.SeasonProvider;
import foxie.calendar.proxy.ProxyCommon;

@Mod(modid = Calendar.MODID, name = Calendar.NAME, version = Calendar.VERSION)
public class Calendar {
   public static final String MODID   = "Calendar";
   public static final String NAME    = "Calendar";
   public static final String VERSION = "@VERSION@";

   @SidedProxy(clientSide = "foxie.calendar.proxy.ProxyClient", serverSide = "foxie.calendar.proxy.ProxyCommon", modId = MODID)
   public static ProxyCommon proxy;

   @Mod.Instance(MODID)
   public static Calendar INSTANCE;

   private static Config config;

   @Mod.EventHandler
   public void preinit(FMLPreInitializationEvent event) {
      proxy.preinit(event);
      config = new Config(event.getSuggestedConfigurationFile());

      // register default providers
      CalendarAPI.registerCalendarProvider(new CalendarImpl(0));
      CalendarAPI.registerSeasonProvider(new SeasonProvider());
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
      if (Config.enableFixedTimeCommand)
         event.registerServerCommand(new FixedCommandTime());

      if (Config.enableDateCommand)
         event.registerServerCommand(new CommandDate());
   }

   @Mod.EventHandler
   public void serverStarted(FMLServerStartedEvent event) {
   }

   public Config getConfig() {
      return config;
   }

}

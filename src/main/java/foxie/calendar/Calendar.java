package foxie.calendar;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.commands.CommandDate;
import foxie.calendar.commands.CommandSeason;
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

   private Events events;

   @Mod.EventHandler
   public void preinit(FMLPreInitializationEvent event) {
      events = new Events();
      proxy.preinit(event);
      config = new Config(event.getSuggestedConfigurationFile());

      // register default providers
      CalendarAPI.registerCalendarProvider(0, new CalendarImpl(0));
      CalendarAPI.registerSeasonProvider(0, new SeasonProvider());

      events.preinit();
   }

   @Mod.EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.init(event);
      events.init();
   }

   @Mod.EventHandler
   public void postinit(FMLPostInitializationEvent event) {
      proxy.postinit(event);
      events.postinit();
   }

   @Mod.EventHandler
   public void serverStarting(FMLServerStartingEvent event) {
      if (Config.enableFixedTimeCommand)
         event.registerServerCommand(new FixedCommandTime());

      if (Config.enableDateCommand)
         event.registerServerCommand(new CommandDate());

      if (Config.enableSeasonCommand)
         event.registerServerCommand(new CommandSeason());
   }

   @Mod.EventHandler
   public void serverStarted(FMLServerStartedEvent event) {
      events.serverStarted();
   }

   public Config getConfig() {
      return config;
   }

}

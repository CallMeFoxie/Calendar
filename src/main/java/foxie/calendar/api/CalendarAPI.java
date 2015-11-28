package foxie.calendar.api;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class CalendarAPI {
   public static final String MODNAME = "Calendar API";
   public static final String VERSION = "1.0";

   private static ISeasonProvider   seasonProvider;
   private static ICalendarProvider calendarProvider;

   // TODO make the API world specific
   public static ISeasonProvider getSeasonProvider() {
      return seasonProvider;
   }

   public static void registerSeasonProvider(ISeasonProvider provider) {
      if (seasonProvider != null) {
         FMLLog.info("[" + MODNAME + "] Season provider already registered! Replacing on request then by mod " +
                 Loader.instance().activeModContainer().getModId());
      }

      seasonProvider = provider;
   }

   public static void registerCalendarProvider(ICalendarProvider provider) {
      calendarProvider = provider;
   }

   public static ICalendarProvider getCalendarInstance(World world) {
      return calendarProvider.create(world);
   }

   public static ICalendarProvider getCalendarProvider(WorldProvider provider) {
      return calendarProvider.create(provider);
   }

   public static ICalendarProvider getCalendarProvider(long time) {
      return calendarProvider.create(time);
   }

   public static ICalendarProvider getCalendarProvider() {
      return calendarProvider.create(0);
   }
}

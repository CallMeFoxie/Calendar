package foxie.calendar;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.DateTimeEvent;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

public class Events {
   public static Events                          INSTANCE;
   public        Map<Integer, ICalendarProvider> providerMap;

   public Events() {
      INSTANCE = this;
   }

   public void preinit() {

   }

   public void init() {
      FMLCommonHandler.instance().bus().register(INSTANCE);
      MinecraftForge.EVENT_BUS.register(INSTANCE);
   }

   public void postinit() {
   }

   public void serverStarted() {
      INSTANCE.providerMap = new HashMap<Integer, ICalendarProvider>();

      for (int i = 0; i < MinecraftServer.getServer().worldServers.length; i++) {
         WorldServer server = MinecraftServer.getServer().worldServers[i];
         INSTANCE.providerMap.put(server.provider.getDimensionId(), CalendarAPI.getCalendarInstance(server.provider.getWorldTime()));
      }
   }

   @Mod.EventHandler
   public void worldTick(TickEvent.WorldTickEvent event) {
      if (event.phase != TickEvent.Phase.END)
         return;

      ICalendarProvider newCalendar = CalendarAPI.getCalendarInstance(event.world.provider.getWorldTime());
      ICalendarProvider previousCalendar = providerMap.get(event.world.provider.getDimensionId());

      if (previousCalendar.getDay() != newCalendar.getDay()) {
         fireEventNewDay(event.world, previousCalendar);
      }

      if (previousCalendar.getMonth() != newCalendar.getMonth()) {
         fireEventNewMonth(event.world, previousCalendar);
      }

      if (previousCalendar.getYear() != newCalendar.getYear()) {
         fireEventNewYear(event.world, previousCalendar);
      }

      ISeason previousSeason = CalendarAPI.getSeasonProvider().getSeason(previousCalendar);
      ISeason newSeason = CalendarAPI.getSeasonProvider().getSeason(newCalendar);

      if (previousCalendar != newSeason) {
         fireEventNewSeason(event.world, previousCalendar, previousSeason);
      }

      providerMap.put(event.world.provider.getDimensionId(), newCalendar);
   }

   private void fireEventNewDay(World world, ICalendarProvider calendar) {
      MinecraftForge.EVENT_BUS.post(new DateTimeEvent.NewDayEvent(world, calendar));
   }

   private void fireEventNewMonth(World world, ICalendarProvider calendar) {
      MinecraftForge.EVENT_BUS.post(new DateTimeEvent.NewMonthEvent(world, calendar));
   }

   private void fireEventNewYear(World world, ICalendarProvider calendar) {
      MinecraftForge.EVENT_BUS.post(new DateTimeEvent.NewYearEvent(world, calendar));
   }

   private void fireEventNewSeason(World world, ICalendarProvider calendar, ISeason season) {
      MinecraftForge.EVENT_BUS.post(new DateTimeEvent.NewSeasonEvent(world, calendar, season));
   }
}

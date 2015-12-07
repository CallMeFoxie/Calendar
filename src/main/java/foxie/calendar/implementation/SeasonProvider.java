package foxie.calendar.implementation;

import foxie.calendar.Calendar;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import foxie.calendar.api.ISeasonProvider;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeasonProvider implements ISeasonProvider {

   List<ISeason> seasons;

   public SeasonProvider() {
      // init the 4 seasons
      ICalendarProvider baseCalendar = CalendarAPI.getCalendarInstance();
      // load them from the config

      // init with default values
      seasons = new ArrayList<ISeason>();
      seasons.add(new Season("winter", CalendarAPI.getCalendarInstance().setDay(21).setMonth(11), 270, 240, 260));
      seasons.add(new Season("summer", CalendarAPI.getCalendarInstance().setDay(21).setMonth(5), 285, 303, 295));
      seasons.add(new Season("spring", CalendarAPI.getCalendarInstance().setDay(21).setMonth(2), 260, 280, 285));
      seasons.add(new Season("autumn", CalendarAPI.getCalendarInstance().setDay(21).setMonth(8), 295, 280, 270));

      // now actually read them from the config
      Configuration cfg = Calendar.INSTANCE.getConfig().getConfig();
      String[] seasonNames = new String[seasons.size()];
      for (int i = 0; i < seasons.size(); i++)
         seasonNames[i] = seasons.get(i).getName();

      // load the actual season names from the config file
      seasonNames = cfg.getStringList("seasons", "seasons", seasonNames, "Names of the seasons");

      // reset
      for (String season : seasonNames) {
         ISeason foundSeason = null;
         for (ISeason iSeason : seasons) {
            if (iSeason.getName().equals(season))
               foundSeason = iSeason;
         }
         if (foundSeason == null) {
            foundSeason = new Season(season);
            seasons.add(foundSeason);
         }

         ((Season) foundSeason).getConfig(cfg);
      }

      // now sort them by date
      Collections.sort(seasons);

      if (cfg.hasChanged())
         cfg.save();
   }

   @Override
   public ISeason getSeason(ICalendarProvider calendar) {
      calendar = calendar.copy();
      calendar.setYear(0);

      for (int i = 0; i < seasons.size() - 1; i++) {
         if (calendar.getTime() >= seasons.get(i).getBeginningDate().getTime() && calendar.getTime() < seasons.get(i + 1).getBeginningDate().getTime())
            return seasons.get(i);
      }

      return seasons.get(seasons.size() - 1);
   }

   @Override
   public float getSeasonProgress(ICalendarProvider calendar) {
      ICalendarProvider beginning;
      ICalendarProvider ending;

      calendar = calendar.copy();
      calendar.setYear(0);

      for (int i = 0; i < seasons.size() - 1; i++) {
         if (calendar.getTime() >= seasons.get(i).getBeginningDate().getTime() && calendar.getTime() < seasons.get(i + 1).getBeginningDate().getTime()) {
            beginning = seasons.get(i).getBeginningDate();
            ending = seasons.get(i + 1).getBeginningDate();
         }
      }

      beginning = seasons.get(seasons.size() - 1).getBeginningDate();
      ending = seasons.get(0).getBeginningDate();

      if (calendar.getTime() < beginning.getTime())
         calendar.addYears(1);

      if (ending.getTime() < beginning.getTime())
         ending.addYears(1);

      return (calendar.getTime() - beginning.getTime()) / (ending.getTime() - beginning.getTime());
   }

   @Override
   public ISeason[] getAllSeasons() {
      return seasons.toArray(new ISeason[seasons.size()]);
   }

   @Override
   public int getTemperature(World world, int x, int y, int z) {
      // presume that equator = z 0
      // TODO
      return 0;
   }
}

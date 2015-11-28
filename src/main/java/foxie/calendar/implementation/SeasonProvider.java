package foxie.calendar.implementation;

import foxie.calendar.Calendar;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import foxie.calendar.api.ISeasonProvider;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeasonProvider implements ISeasonProvider {

   List<ISeason> seasons;

   public SeasonProvider() {
      // init the 4 seasons
      ICalendarProvider baseCalendar = CalendarAPI.getCalendarProvider();
      // load them from the config

      // init with default values
      seasons = new ArrayList<ISeason>();
      seasons.add(new Season("winter", CalendarAPI.getCalendarProvider().setDay(21).setMonth(11)));
      seasons.add(new Season("summer", CalendarAPI.getCalendarProvider().setDay(21).setMonth(5)));
      seasons.add(new Season("spring", CalendarAPI.getCalendarProvider().setDay(21).setMonth(2)));
      seasons.add(new Season("autumn", CalendarAPI.getCalendarProvider().setDay(21).setMonth(8)));

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
         if (calendar.getInTicks() >= seasons.get(i).getBeginningDate().getInTicks() && calendar.getInTicks() < seasons.get(i + 1).getBeginningDate().getInTicks())
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
         if (calendar.getInTicks() >= seasons.get(i).getBeginningDate().getInTicks() && calendar.getInTicks() < seasons.get(i + 1).getBeginningDate().getInTicks()) {
            beginning = seasons.get(i).getBeginningDate();
            ending = seasons.get(i + 1).getBeginningDate();
         }
      }

      beginning = seasons.get(seasons.size() - 1).getBeginningDate();
      ending = seasons.get(0).getBeginningDate();

      if (calendar.getInTicks() < beginning.getInTicks())
         calendar.addYears(1);

      if (ending.getInTicks() < beginning.getInTicks())
         ending.addYears(1);

      return (calendar.getInTicks() - beginning.getInTicks()) / (ending.getInTicks() - beginning.getInTicks());
   }

   @Override
   public ISeason[] getAllSeasons() {
      return seasons.toArray(new ISeason[seasons.size()]);
   }
}

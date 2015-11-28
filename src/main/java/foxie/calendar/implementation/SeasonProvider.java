package foxie.calendar.implementation;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import foxie.calendar.api.ISeasonProvider;

public class SeasonProvider implements ISeasonProvider {

   public SeasonProvider() {
      // init the 4 seasons
      ICalendarProvider baseCalendar = CalendarAPI.getCalendarProvider();

   }

   @Override
   public ISeason getSeason(ICalendarProvider calendar) {
      return null;
   }

   @Override
   public float getSeasonProgress(ICalendarProvider calendar) {
      // TODO
      return 0;
   }

   @Override
   public String[] getAllSeasons() {
      return new String[]{"Spring", "Summer", "Autumn", "Winter"};
   }
}

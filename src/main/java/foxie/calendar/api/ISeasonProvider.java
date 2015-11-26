package foxie.calendar.api;

import foxie.calendar.implementation.CalendarImpl;

public interface ISeasonProvider {
   String getSeasonName(CalendarImpl calendar);

   int getSeasonOrdinal(CalendarImpl calendar);

   float getSeasonProgress(CalendarImpl calendar);

   String[] getAllSeasons();
}

package foxie.calendar.api;

public interface ISeasonProvider {
   ISeason getSeason(ICalendarProvider calendar);

   /**
    * Get season progress (0 - 1, how far has the season progressed)
    *
    * @param calendar
    * @return
    */
   float getSeasonProgress(ICalendarProvider calendar);

   /**
    * All the season names!
    *
    * @return
    */
   ISeason[] getAllSeasons();
}

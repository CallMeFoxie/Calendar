package foxie.calendar.api;

public interface ISeasonProvider {
   /**
    * Gets a season for a given month
    *
    * @param calendar calendar date to fetch the season for
    * @return season
    */
   ISeason getSeason(ICalendarProvider calendar);

   /**
    * Get season progress (0 - 1), how far has the season progressed
    *
    * @param calendar calendar to calculate the progress for
    * @return progress (0 - 1)
    */
   float getSeasonProgress(ICalendarProvider calendar);

   /**
    * All the seasons! (used for sorting, look ups, listing etc)
    *
    * @return seasons
    */
   ISeason[] getAllSeasons();

   float getAverageTemperature(ICalendarProvider provider, boolean withDayOffset);

   /**
    * Get temperature
    *
    * @param calendar calendar instance
    * @param x     xCoord
    * @param y     yCoord
    * @param z     zCoord
    * @return temperature in Kelvines
    */
   float getTemperature(ICalendarProvider calendar, int x, int y, int z);
}

package foxie.calendar.api;

public interface ISeason extends Comparable<ISeason> {
   /**
    * Fetches a name for the season (localized right now, localizations are not in just yet)
    *
    * @return localized month name
    */
   String getName();

   /**
    * Gets the beginning date for the season. Has to be in a year 0 otherwise it will not work.
    *
    * @return beginning date
    */
   ICalendarProvider getBeginningDate();

   /**
    * Gets a temperature for the season in Kelvin
    *
    * @param progress progress (0 - 1) of the season
    * @return current temperature in Kelvin
    */
   float getTemperature(float progress);

   /**
    * Gets a temperature for the season in Kelvin at a given progress of the day
    * @param progress progress (0 - 1) of the season
    * @param time  time of the day to calculate it for
    * @return
    */
   float getTemperature(float progress, ICalendarProvider time);
}

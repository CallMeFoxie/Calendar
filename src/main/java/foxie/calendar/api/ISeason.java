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
}

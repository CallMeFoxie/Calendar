package foxie.calendar.api;

public interface ISeason extends Comparable<ISeason> {
   String getName();

   ICalendarProvider getBeginningDate();
}

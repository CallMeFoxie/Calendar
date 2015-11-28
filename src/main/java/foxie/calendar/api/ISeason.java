package foxie.calendar.api;

public interface ISeason {
   String getName();

   ICalendarProvider getBeginningDate();

   ICalendarProvider getEndDate();
}

package foxie.calendar.implementation;

import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;

public class Season implements ISeason {

   private String            name;
   private ICalendarProvider beginDate;
   private ICalendarProvider endDate;

   public Season(String name, ICalendarProvider beginDate, ICalendarProvider endDate) {

   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public ICalendarProvider getBeginningDate() {
      return beginDate;
   }

   @Override
   public ICalendarProvider getEndDate() {
      return endDate;
   }
}

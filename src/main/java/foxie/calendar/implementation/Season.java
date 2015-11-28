package foxie.calendar.implementation;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import net.minecraftforge.common.config.Configuration;

public class Season implements ISeason {

   private String            name;
   private ICalendarProvider beginDate;

   public Season(String name, ICalendarProvider beginDate) {
      this.name = name;
      this.beginDate = beginDate;
   }

   public Season(String name) {
      this.name = name;
      this.beginDate = CalendarAPI.getCalendarProvider(0);
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public ICalendarProvider getBeginningDate() {
      return beginDate.copy();
   }

   public void getConfig(Configuration cfg) {
      cfg.getInt(name + "_month_begin", "seasons", getBeginningDate().getMonth(), 0, getBeginningDate().getNumberOfMonths(), "Beginning month for " + name);
      cfg.getInt(name + "_day_begin", "seasons", getBeginningDate().getDay(), 0, getBeginningDate().getDaysInMonth(getBeginningDate().getMonth()), "Beginning day for " + name);
   }

   @Override
   public int compareTo(ISeason iSeason) {
      if (iSeason == null)
         return -1;

      if (getBeginningDate().getMonth() == iSeason.getBeginningDate().getMonth())
         return getBeginningDate().getDay() - iSeason.getBeginningDate().getDay();

      return getBeginningDate().getMonth() - iSeason.getBeginningDate().getMonth();
   }
}

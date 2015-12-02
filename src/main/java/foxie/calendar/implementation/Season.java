package foxie.calendar.implementation;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import net.minecraftforge.common.config.Configuration;

public class Season implements ISeason {

   private String            name;
   private ICalendarProvider beginDate;

   private int beginTemp, midTemp, endTemp;

   public Season(String name, ICalendarProvider beginDate, int beginTemp, int midTemp, int endTemp) {
      this.name = name;
      this.beginDate = beginDate;
      this.beginTemp = beginTemp;
      this.midTemp = midTemp;
      this.endTemp = endTemp;
   }

   public Season(String name) {
      this.name = name;
      this.beginDate = CalendarAPI.getCalendarInstance((long) 0);
      this.beginTemp = 0;
      this.midTemp = 0;
      this.endTemp = 0;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public ICalendarProvider getBeginningDate() {
      return beginDate.copy();
   }

   @Override
   public int getTemperature(ICalendarProvider provider, float progress) {
      int minTemp = beginTemp;
      int maxTemp = midTemp;

      if (progress > 0.5) {
         minTemp = midTemp;
         maxTemp = endTemp;
         progress -= 0.5;
      }

      double leftHalf = progress;
      double rightHalf = 0.5 - progress;
      int tempDef = Math.max(minTemp, maxTemp) - Math.min(minTemp, maxTemp);

      return (int) (tempDef * leftHalf + minTemp);
   }

   public void getConfig(Configuration cfg) {
      cfg.getInt(name + "_month_begin", "seasons", getBeginningDate().getMonth(), 0, getBeginningDate().getNumberOfMonths(), "Beginning month for " + name);
      cfg.getInt(name + "_day_begin", "seasons", getBeginningDate().getDay(), 0, getBeginningDate().getDaysInMonth(getBeginningDate().getMonth()), "Beginning day for " + name);
      cfg.getInt(name + "_begin_temp", "seasons", beginTemp, 0, 1000, "Starting temperature of the season (K)");
      cfg.getInt(name + "_mid_temp", "seasons", midTemp, 0, 1000, "Temperature in the middle of the season (K)");
      cfg.getInt(name + "_end_temp", "seasons", endTemp, 0, 1000, "Temperature at the end of the season (K)");
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

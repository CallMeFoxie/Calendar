package foxie.calendar.implementation;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import net.minecraftforge.common.config.Configuration;

public class Season implements ISeason {

   private String name;
   private ICalendarProvider beginDate;

   private int beginTemp = 0, midTemp = 0, endTemp = 0, dropTemp = 0;

   public Season(String name, ICalendarProvider beginDate, int beginTemp, int midTemp, int endTemp, int dropTemp) {
      this.name = name;
      this.beginDate = beginDate;
      this.beginTemp = beginTemp;
      this.midTemp = midTemp;
      this.endTemp = endTemp;
      this.dropTemp = dropTemp;
   }

   public Season(String name) {
      this.name = name;
      this.beginDate = CalendarAPI.getCalendarInstance((long) 0);
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
   public float getAverageTemperature(float progress) {
      if (progress > 0.5) {
         return (int) (midTemp + ((progress - 0.5) * 2) * (endTemp - midTemp));
      } else {
         return (int) (beginTemp + (progress * 2) * (beginTemp - midTemp));
      }
   }

   @Override
   public float getTemperature(float progress, ICalendarProvider time) {

      double daytimeSin = (Math.sin(1f / 12 * Math.PI * (time.getHour() - 8)) + 1) / 2;

      return (float) (getAverageTemperature(progress) + daytimeSin * dropTemp);
   }

   public void getConfig(Configuration cfg) {
      cfg.getInt(name + "_month_begin", "seasons", getBeginningDate().getMonth(), 0, getBeginningDate().getNumberOfMonths(), "Beginning month for " + name);
      cfg.getInt(name + "_day_begin", "seasons", getBeginningDate().getDay(), 0, (int) getBeginningDate().getDaysInMonth(getBeginningDate().getMonth()), "Beginning day for " + name);
      cfg.getInt(name + "_begin_temp", "seasons", beginTemp, 0, 1000, "Starting temperature of the season (K)");
      cfg.getInt(name + "_mid_temp", "seasons", midTemp, 0, 1000, "Temperature in the middle of the season (K)");
      cfg.getInt(name + "_end_temp", "seasons", endTemp, 0, 1000, "Temperature at the end of the season (K)");
      cfg.getInt(name + "_drop_temp", "seasons", dropTemp, 0, 1000, "How much K can drop during the night");
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

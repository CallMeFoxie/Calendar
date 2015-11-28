package foxie.calendar.api;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public interface ICalendarProvider {
   /**
    * Gets total sum of days in a year
    *
    * @return days in a year
    */
   int getDaysInYear();

   int getTicksPerYear();

   long getInTicks();

   /**
    * Gets a day in a given month. Days are calculated from 0
    *
    * @return day of the month
    */
   int getDay();

   ICalendarProvider setDay(int newDay);

   /**
    * Gets a month for a given time. Months are calculated from 0
    *
    * @return month of the year
    */
   int getMonth();

   ICalendarProvider setMonth(int newMonth);

   /**
    * Gets a year. Years are calculated from 0
    *
    * @return year
    */
   int getYear();

   ICalendarProvider setYear(int newYear);

   /**
    * Calculates current hour. Day begins at 00:00 and ends at 23:59
    *
    * @return hour of the day
    */
   int getHour();

   ICalendarProvider setHour(int newHour);

   /**
    * Calculates current minute. Note that there are 50 minutes, but it is rescaled to 60 minutes per hour!
    *
    * @return minute of the day
    */
   int getMinute();

   /**
    * Sets minutes. UNSCALED!
    *
    * @param newMinutes
    */
   ICalendarProvider setMinute(int newMinutes);

   int getScaledMinute();

   ICalendarProvider setScaledMinute(int newMinute);

   int getScaledSecond();

   ICalendarProvider setScaledSecond(int newSecond);

   int getSecond();

   ICalendarProvider setSecond(int newSecond);

   ICalendarProvider addScaledSeconds(int seconds);

   ICalendarProvider addSeconds(int seconds);

   ICalendarProvider addScaledMinutes(int minutes);

   ICalendarProvider addMinutes(int minutes);

   ICalendarProvider addHours(int hours);

   ICalendarProvider addDays(int days);

   ICalendarProvider addMonths(int months);

   ICalendarProvider addYears(int years);

   int getDaysInMonth(int month);

   /*
    * These are used to create a new instance of these calendars
    */
   ICalendarProvider create(World world);

   ICalendarProvider create(WorldProvider provider);

   ICalendarProvider create(long time);

   int getNumberOfMonths();

   String[] getListOfMonthsString();

   ICalendarProvider copy();
}

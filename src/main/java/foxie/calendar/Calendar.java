package foxie.calendar;

import net.minecraft.world.WorldProvider;

public class Calendar {
   private WorldProvider provider;
   private long          worldTicks;

   /**
    * New Calendar instance. Provides 1/3rd of the magic. (The rest is set via config and fired events.)
    *
    * @param provider provider to calculate magic for
    */
   public Calendar(WorldProvider provider) {
      this.provider = provider;
   }

   /**
    * New Calendar instance. Provides 1/3rd of the magic. (The rest is set via config and fired events.)
    *
    * @param worldTicks world ticks to calculate date for
    */
   public Calendar(long worldTicks) {
      this.worldTicks = worldTicks;
   }


   /**
    * Gets a day in a given month. Days are calculated from 1
    *
    * @return day of the month
    */
   public int getDay() {
      int relativeDays = (int) ((getWorldTicks() / 24000) % getDaysInYear());
      int month = getMonth(relativeDays);

      for (int i = 0; i < month; i++) {
         relativeDays -= Config.days[i];
      }

      return relativeDays + 1;
   }

   /**
    * Gets a month for a given time. Months are calculated from 1
    *
    * @return month of the year
    */
   public int getMonth() {
      return getMonth((int) ((getWorldTicks() / 24000) % getDaysInYear())) + 1;
   }

   /**
    * Gets a year. Years are calculated from 0
    *
    * @return year
    */
   public int getYear() {
      return (int) (getWorldTicks() / (getDaysInYear() * 24000));
   }

   /**
    * Calculates current hour. Day begins at 06:00 and ends at 05:59
    *
    * @return hour of the day
    */
   public int getHour() {
      return ((int) getTodaysTicks()) / 1000;
   }

   /**
    * Calculates current minute
    *
    * @return minute of the day
    */
   public int getMinute() {
      return (int) ((getTodaysTicks() % 1000) / 20f * (60f / 50f));
   }

   /**
    * Gets current second. Note that they are going by 3 as there are 20 ticks per real second!
    *
    * @return seconds of the minute
    */
   public int getSecond() {
      return (int) (getWorldTicks() % 20 * 3);
   }


   /**
    * Gets current day ticks offset
    *
    * @return current day's tick
    */
   private long getTodaysTicks() {
      return getWorldTicks() % 24000;
   }

   /**
    * Gets total sum of days in a year
    *
    * @return days in a year
    */
   public int getDaysInYear() {
      int sum = 0;
      for (int days : Config.days)
         sum += days;

      return sum;
   }

   /**
    * Fetches month from current year's relative day (months are calculated from 1)
    *
    * @param relativeDate relative day of the year
    * @return month
    */
   private int getMonth(int relativeDate) {
      int tmpSum = 0;
      for (int i = 0; i < Config.days.length; i++)
         if (relativeDate < tmpSum + Config.days[i])
            return i;
         else
            tmpSum += Config.days[i];

      return Config.days.length - 1;
   }

   private long getWorldTicks() {
      if (provider == null)
         return worldTicks;

      return provider.getWorldTime();
   }

}

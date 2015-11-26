package foxie.calendar;

import com.sun.javaws.exceptions.InvalidArgumentException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class Calendar implements Comparable<Calendar> {
   public static final int TICKS_PER_MINUTE = 20;
   public static final int TICKS_PER_HOUR   = TICKS_PER_MINUTE * 50;
   public static final int TICKS_PER_DAY    = TICKS_PER_HOUR * 24;

   private WorldProvider provider;
   private long          worldTicks;

   /**
    * New Calendar instance. Provides 1/3rd of the magic. (The rest is set via config and fired events.)
    *
    * @param world world to do the magic on
    */
   public Calendar(World world) {
      this(world.provider);
   }

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

   public Calendar() {
      this(0);
   }

   /**
    * Gets total sum of days in a year
    *
    * @return days in a year
    */
   public static int getDaysInYear() {
      int sum = 0;
      for (int days : Config.days)
         sum += days;

      return sum;
   }

   public static int getTicksPerYear() {
      int daysInYear = getDaysInYear();
      return daysInYear * TICKS_PER_DAY;
   }

   public static int getDaysInMonth(int month) throws InvalidArgumentException {
      if (month >= Config.days.length || month < 0) {
         throw new InvalidArgumentException(new String[]{"Invalid month!"});
      }

      return Config.days[month];
   }

   public long getInTicks() {
      return provider != null ? provider.getWorldTime() : worldTicks;
   }

   /**
    * Gets a day in a given month. Days are calculated from 0
    *
    * @return day of the month
    */
   public int getDay() {
      int relativeDays = (int) ((getWorldTicks() / 24000) % getDaysInYear());
      int month = getMonth(relativeDays);

      for (int i = 0; i < month; i++) {
         relativeDays -= Config.days[i];
      }

      return relativeDays;
   }

   public void setDay(int newDay) throws InvalidArgumentException {
      if (newDay > Config.days[getMonth()])
         throw new InvalidArgumentException(new String[]{"There is no such day in this month!"});


      if (provider == null)
         worldTicks += (newDay - getDay()) * TICKS_PER_DAY;
      else
         provider.setWorldTime(provider.getWorldTime() + (newDay - getDay()) * TICKS_PER_DAY);
   }

   /**
    * Gets a month for a given time. Months are calculated from 0
    *
    * @return month of the year
    */
   public int getMonth() {
      return getMonth((int) ((getWorldTicks() / 24000) % getDaysInYear()));
   }

   public void setMonth(int newMonth) throws InvalidArgumentException {
      if (newMonth < 0 || newMonth >= Config.days.length)
         throw new InvalidArgumentException(new String[]{"Month has to be in the range of 0 - " + Config.days.length});

      if (Config.days[newMonth] < getDay())
         throw new InvalidArgumentException(new String[]{"There is no day " + getDay() + " in month " + getMonth()});

      int startMonth = getMonth();
      int endMonth = newMonth;
      if (endMonth < startMonth) { // reverse if needed
         endMonth = getMonth();
         startMonth = newMonth;
      }
      long toDeduct = 0;
      for (int i = startMonth; i < endMonth; i++) { // add for every month enough ticks
         toDeduct += Config.days[i];
      }

      toDeduct *= TICKS_PER_DAY;


      if (getMonth() > newMonth)
         toDeduct *= -1;

      if (provider == null)
         worldTicks += toDeduct;
      else
         provider.setWorldTime(provider.getWorldTime() + toDeduct);
   }

   /**
    * Gets a year. Years are calculated from 0
    *
    * @return year
    */
   public int getYear() {
      return (int) (getWorldTicks() / (getDaysInYear() * 24000));
   }

   public void setYear(int newYear) throws InvalidArgumentException {
      if (newYear < 0)
         throw new InvalidArgumentException(new String[]{"Hour has to be in the range of 0 - 23"});

      if (provider == null) {
         worldTicks += (newYear - getYear()) * getTicksPerYear();

      } else
         provider.setWorldTime(provider.getWorldTime() + (newYear - getYear()) * getTicksPerYear());
   }

   /**
    * Calculates current hour. Day begins at 00:00 and ends at 23:59
    *
    * @return hour of the day
    */
   public int getHour() {
      return ((int) getTodaysTicks()) / 1000;
   }

   public void setHour(int newHour) throws InvalidArgumentException {
      if (newHour < 0 || newHour > 23)
         throw new InvalidArgumentException(new String[]{"Hour has to be in the range of 0 - 23"});

      if (provider == null)
         worldTicks += (newHour - getHour()) * TICKS_PER_HOUR;
      else
         provider.setWorldTime(provider.getWorldTime() + (newHour - getHour()) * TICKS_PER_HOUR);
   }

   /**
    * Calculates current minute. Note that there are 50 minutes, but it is rescaled to 60 minutes per hour!
    *
    * @return minute of the day
    */
   public int getMinute() {
      return (int) ((getTodaysTicks() % 1000) / 20f);
   }

   /**
    * Sets minutes. UNSCALED!
    *
    * @param newMinutes
    * @throws InvalidArgumentException
    */
   public void setMinute(int newMinutes) throws InvalidArgumentException {
      if (newMinutes < 0 || newMinutes > 49)
         throw new InvalidArgumentException(new String[]{"Minutes have to be in the range of 0 - 49"});

      if (provider == null)
         worldTicks += (newMinutes - getMinute()) * TICKS_PER_MINUTE;
      else
         provider.setWorldTime(provider.getWorldTime() + (newMinutes - getMinute()) * TICKS_PER_MINUTE);
   }

   public int getScaledMinute() {
      return (int) ((getTodaysTicks() % 1000) / 20f * (60f / 50f));
   }

   public void setScaledMinute(int newMinute) throws InvalidArgumentException {
      setMinute((int) (newMinute * (5f / 6f)));
   }

   public int getScaledSecond() {
      return (int) (getWorldTicks() % 20 * 3);
   }

   public void setScaledSecond(int newSecond) throws InvalidArgumentException {
      setSecond(newSecond / 3);
   }

   /**
    * Gets current second. Note that they are going by 3 as there are 20 ticks per real second!
    *
    * @return seconds of the minute
    */
   public int getSecond() {
      return (int) (getWorldTicks() % 20);
   }

   public void setSecond(int newSecond) throws InvalidArgumentException {
      if (newSecond < 0 || newSecond > 19)
         throw new InvalidArgumentException(new String[]{"Seconds have to be in the range of 0 - 19"});

      if (provider == null)
         worldTicks += (newSecond - getSecond());
      else
         provider.setWorldTime(provider.getWorldTime() + (newSecond - getSecond()));
   }

   ////////////// ================= increment/decrement classes
   public void addScaledSeconds(int seconds) throws InvalidArgumentException {
      addSeconds(seconds / 3);
   }

   public void addSeconds(int seconds) throws InvalidArgumentException {
      int newSeconds = getSecond() + seconds;
      while (newSeconds > 19) {
         addMinutes(1);
         newSeconds -= 20;
      }

      while (newSeconds < 0) {
         addMinutes(-1);
         newSeconds += 20;
      }

      setSecond(newSeconds);
   }

   public void addScaledMinutes(int minutes) throws InvalidArgumentException {
      addMinutes((int) (minutes * (5f / 6f)));
   }

   public void addMinutes(int minutes) throws InvalidArgumentException {
      int newMinute = getMinute() + minutes;
      while (newMinute > 49) {
         addHours(1);
         newMinute -= 50;
      }
      while (newMinute < 0) {
         addHours(-1);
         newMinute += 50;
      }

      setMinute(newMinute);
   }

   public void addHours(int hours) throws InvalidArgumentException {
      int newHour = getHour() + hours;
      while (newHour > 23) {
         addDays(1);
         newHour -= 24;
      }

      while (newHour < 0) {
         addDays(-1);
         newHour += 24;
      }

      setHour(newHour);
   }

   public void addDays(int days) throws InvalidArgumentException {
      int newDay = getDay() + days;
      setDay(0);

      while (newDay < 0) {
         newDay += Config.days[getMonth()];
         addMonths(-1);
      }
      while (newDay >= Config.days[getMonth()]) {
         newDay -= Config.days[getMonth()];
         addMonths(1);
      }

      setDay(newDay);
   }

   public void addMonths(int months) throws InvalidArgumentException {
      int newMonth = getMonth() + months;
      setMonth(0);
      while (newMonth >= Config.days.length) {
         addYears(1);
         newMonth -= Config.days.length;
      }
      while (newMonth < 0) {
         addYears(-1);
         newMonth += Config.days.length;
      }

      setMonth(newMonth);

   }

   public void addYears(int years) throws InvalidArgumentException {
      setYear(getYear() + years);
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

   @Override
   public int compareTo(Calendar calendar) {
      if (calendar == null)
         return 1;

      return (int) (getInTicks() - calendar.getInTicks());
   }
}

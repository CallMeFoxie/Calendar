package foxie.calendar.implementation;


import foxie.calendar.Config;
import foxie.calendar.api.DayTimeDescriptor;
import foxie.calendar.api.ICalendarProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

import java.util.ArrayList;
import java.util.List;

public class CalendarImpl implements Comparable<CalendarImpl>, ICalendarProvider {
   public static final int TICKS_PER_MINUTE = 20;
   public static final int TICKS_PER_HOUR   = TICKS_PER_MINUTE * 50;
   public static final int TICKS_PER_DAY    = TICKS_PER_HOUR * 24;

   private long worldTicks;

   public CalendarImpl(World world) {
      this(world.provider);
   }

   public CalendarImpl(WorldProvider provider) {
      this(provider.getWorldTime());
   }

   public CalendarImpl(long worldTicks) {
      this.worldTicks = worldTicks;
   }

   public CalendarImpl() {
      this(0);
   }

   @Override
   public double getDaysInMonth(int month) {
      if (month >= Config.days.length || month < 0) {
         throw new IllegalArgumentException("Invalid month!");
      }

      return Config.days[month];
   }

   @Override
   public int getDaysInMonth(int month, int year) {
      return (int) getDaysInMonth(month);
   }

   @Override
   public double getDaysInYear() {
      int sum = 0;
      for (int days : Config.days)
         sum += days;

      return sum;
   }

   @Override
   public int getDaysInYear(int year) {
      return (int) getDaysInYear();
   }

   @Override
   public long getTicksPerYear(int year) {
      return (long) getTicksPerYear();
   }

   @Override
   public double getTicksPerYear() {
      double daysInYear = getDaysInYear();
      return daysInYear * TICKS_PER_DAY;
   }

   @Override
   public long getTime() {
      return worldTicks + 6000;
   }

   private void setTime(long newTime) {
      worldTicks = newTime - 6000;
   }

   @Override
   public int getDay() {
      int relativeDays = (int) ((getTime() / 24000) % getDaysInYear());
      int month = getMonth(relativeDays);

      for (int i = 0; i < month; i++) {
         relativeDays -= Config.days[i];
      }

      return relativeDays;
   }

   @Override
   public ICalendarProvider setDay(int newDay) {
      if (newDay >= Config.days[getMonth()] || newDay < 0)
         throw new IllegalArgumentException("There is no such day in this month!");

      setTime(getTime() + (newDay - getDay()) * TICKS_PER_DAY);

      return this;
   }

   @Override
   public int getMonth() {
      return getMonth((int) ((getTime() / 24000) % getDaysInYear()));
   }

   @Override
   public ICalendarProvider setMonth(int newMonth) {
      if (newMonth < 0 || newMonth >= Config.days.length)
         throw new IllegalArgumentException("Month has to be in the range of 0 - " + (Config.days.length - 1));

      if (Config.days[newMonth] < getDay())
         throw new IllegalArgumentException("There is no day " + getDay() + " in month " + getMonth());

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

      setTime(getTime() + toDeduct);

      return this;
   }

   @Override
   public int getYear() {
      return (int) (getTime() / (getDaysInYear() * 24000));
   }

   @Override
   public ICalendarProvider setYear(int newYear) {
      if (newYear < 0)
         throw new IllegalArgumentException("Hour has to be in the range of 0 - 23");

      setTime(getTime() + (long) (newYear - getYear()) * (long) getTicksPerYear());

      return this;
   }

   @Override
   public int getHour() {
      return ((int) getTodaysTicks()) / 1000;
   }

   @Override
   public ICalendarProvider setHour(int newHour) {
      if (newHour < 0 || newHour > 23)
         throw new IllegalArgumentException("Hour has to be in the range of 0 - 23");

      setTime(getTime() + (newHour - getHour()) * TICKS_PER_HOUR);

      return this;
   }

   @Override
   public int getMinute() {
      return (int) ((getTodaysTicks() % 1000) / 20f);
   }

   @Override
   public ICalendarProvider setMinute(int newMinutes) {
      if (newMinutes < 0 || newMinutes > 49)
         throw new IllegalArgumentException("Minutes have to be in the range of 0 - 49");

      setTime(getTime() + (newMinutes - getMinute()) * TICKS_PER_MINUTE);

      return this;
   }

   @Override
   public int getSecond() {
      return (int) (getTime() % 20);
   }

   @Override
   public ICalendarProvider setSecond(int newSecond) {
      if (newSecond < 0 || newSecond > 19)
         throw new IllegalArgumentException("Seconds have to be in the range of 0 - 19");

      setTime(getTime() + (newSecond - getSecond()));

      return this;
   }

   ////////////// ================= increment/decrement methods

   @Override
   public ICalendarProvider addSeconds(int seconds) {
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

      return this;
   }

   @Override
   public ICalendarProvider addMinutes(int minutes) {
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

      return this;
   }

   @Override
   public ICalendarProvider addHours(int hours) {
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

      return this;
   }

   @Override
   public ICalendarProvider addDays(int days) {
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

      return this;
   }

   @Override
   public ICalendarProvider addMonths(int months) {
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

      return this;
   }

   @Override
   public ICalendarProvider addYears(int years) {
      setYear(getYear() + years);
      return this;
   }

   @Override
   public ICalendarProvider create(World world) {
      return new CalendarImpl(world);
   }

   @Override
   public ICalendarProvider create(WorldProvider provider) {
      return new CalendarImpl(provider);
   }

   @Override
   public ICalendarProvider create(long time) {
      return new CalendarImpl(time);
   }

   @Override
   public int getNumberOfMonths() {
      return Config.days.length;
   }

   @Override
   public String[] getListOfMonthsString() {
      return Config.months;
   }

   private long getTodaysTicks() {
      return getTime() % 24000;
   }

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
      return worldTicks;
   }

   @Override
   public int compareTo(CalendarImpl calendar) {
      if (calendar == null)
         return 1;

      return (int) (getTime() - calendar.getTime());
   }

   @Override
   public ICalendarProvider copy() {
      return new CalendarImpl(getWorldTicks());
   }

   @Override
   public void apply(World world) {
      world.provider.setWorldTime(worldTicks);
   }

   @Override
   public List<DayTimeDescriptor> getDayTimeDescriptors(int tolerance) {
      List<DayTimeDescriptor> list = new ArrayList<DayTimeDescriptor>();
      ICalendarProvider bottomTolerance = copy().addSeconds(-tolerance);
      ICalendarProvider topTolerance = copy().addSeconds(tolerance);
      // TODO

      // MIDNIGHT

      // MIDDAY

      // MORNING

      // AFTERNOON

      // EVENING

      // DAWN

      // DUSK

      return list;
   }
}

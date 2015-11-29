package foxie.calendar;

import foxie.calendar.implementation.CalendarImpl;
import org.junit.Assert;
import org.junit.Test;

public class TestsCalendar {
   private static void resetDays() {
      Config.days = new int[]{12, 9, 12, 10, 12, 10, 12, 12, 10, 12, 10, 12}; // reset to known value
   }

   private static CalendarImpl getCalendar() {
      resetDays();

      // maths:
      // let's say it is November 8 2, 19:23:33 (unscaled minutes)
      // year 2: 133 days in a year * 24000 * 2 (year) = 6384000
      // November 1st: 111 days, November 8th: 118 days. 118 * 24000 = 2832000 ticks
      // 19 hours: 1000 * 19 = 19000
      // 23 minutes unscaled = 20 * 28 = 460
      // 33 seconds re-scaled = 11
      // ========== 9229471

      return new CalendarImpl(9235471);
   }

   private static CalendarImpl getCalendar2() {
      resetDays();

      // maths:
      // let's say it is December 12 4, 23:49:57 (unscaled minutes)
      // year 2: 133 days in a year * 24000 * 4 (year) = 12768000
      // December 1st: 121 days, November 12th: 132 days. 132 * 24000 = 3168000 ticks
      // 19 hours: 1000 * 23 = 23000
      // 49 minutes unscaled = 20 * 49 = 980
      // 57 seconds re-scaled = 19
      // ========== 15953999

      return new CalendarImpl(15959999);
   }

   private static CalendarImpl getStartingCalendar() {
      return new CalendarImpl(0);
   }

   @Test
   public void testYear() {
      Assert.assertEquals("Bad count of days per year", 133, getStartingCalendar().getDaysInYear());

      Assert.assertEquals("Invalid starting year", 0, getStartingCalendar().getYear());
      Assert.assertEquals("Invalid year", 2, getCalendar().getYear());
      Assert.assertEquals("Invalid year", 4, getCalendar2().getYear());
   }

   @Test
   public void testMonth() {
      Assert.assertEquals("Invalid starting month", 0, getStartingCalendar().getMonth());
      Assert.assertEquals("Invalid month", 10, getCalendar().getMonth());
      Assert.assertEquals("Invalid month", 11, getCalendar2().getMonth());
   }

   @Test
   public void testDay() {
      Assert.assertEquals("Invalid starting day", 0, getStartingCalendar().getDay());
      Assert.assertEquals("Invalid day", 7, getCalendar().getDay());
      Assert.assertEquals("Invalid day", 11, getCalendar2().getDay());
   }

   @Test
   public void testHour() {
      Assert.assertEquals("Invalid starting hour", 0, getStartingCalendar().getHour());
      Assert.assertEquals("Invalid hour", 19, getCalendar().getHour());
      Assert.assertEquals("Invalid hour", 23, getCalendar2().getHour());
   }

   @Test
   public void testMinute() {
      Assert.assertEquals("Invalid starting minute", 0, getStartingCalendar().getScaledMinute());
      Assert.assertEquals("Invalid minute", 28, getCalendar().getScaledMinute());
      Assert.assertEquals("Invalid minute", 59, getCalendar2().getScaledMinute());
   }

   @Test
   public void testSecond() {
      Assert.assertEquals("Invalid starting second", 0, getStartingCalendar().getScaledSecond());
      Assert.assertEquals("Invalid second", 33, getCalendar().getScaledSecond());
      Assert.assertEquals("Invalid second", 57, getCalendar2().getScaledSecond());
   }

   @Test
   public void advancedTest() {
      resetDays();
      CalendarImpl calendar = new CalendarImpl();
      calendar.setYear(4);
      calendar.setMonth(11);
      calendar.setDay(11);
      calendar.setHour(23);
      calendar.setScaledMinute(59);
      calendar.setScaledSecond(57);


      Assert.assertEquals(getCalendar2().getYear(), calendar.getYear());
      Assert.assertEquals(getCalendar2().getMonth(), calendar.getMonth());
      Assert.assertEquals(getCalendar2().getDay(), calendar.getDay());
      Assert.assertEquals(getCalendar2().getHour(), calendar.getHour());
      Assert.assertEquals(getCalendar2().getMinute(), calendar.getMinute());
      Assert.assertEquals(getCalendar2().getSecond(), calendar.getSecond());

      Assert.assertEquals(getCalendar2().getTime(), calendar.getTime());

      CalendarImpl calendar2 = new CalendarImpl();
      calendar2.setYear(5);
      calendar2.setMinute(5);
      calendar.addScaledMinutes(6);
      calendar.addScaledSeconds(3);

      Assert.assertEquals(calendar2.getTime(), calendar.getTime());

      calendar.addSeconds(-1);
      calendar.addMinutes(-5);

      Assert.assertEquals(getCalendar2().getTime(), calendar.getTime());

      calendar2 = new CalendarImpl();
      calendar2.setHour(5);
      calendar2.setMinute(10);
      calendar2.setDay(1);
      calendar2.setMonth(1);
      calendar2.setYear(1);

      CalendarImpl calendar3 = new CalendarImpl();
      calendar3.setHour(5);
      calendar3.setMinute(10);

      Assert.assertEquals(calendar3.getMinute(), calendar2.getMinute());
      Assert.assertEquals(calendar3.getHour(), calendar2.getHour());
   }
}

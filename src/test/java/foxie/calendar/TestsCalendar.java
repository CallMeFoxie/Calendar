package foxie.calendar;

import org.junit.Assert;
import org.junit.Test;

public class TestsCalendar {
   private static Calendar getCalendar() {
      Config.days = new int[]{12, 9, 12, 10, 12, 10, 12, 12, 10, 12, 10, 12}; // reset to known value

      // maths:
      // let's say it is November 8 2, 19:23:33 (unscaled minutes)
      // year 2: 133 days in a year * 24000 * 2 (year) = 6384000
      // November 1st: 111 days, November 8th: 118 days. 118 * 24000 = 2832000 ticks
      // 19 hours: 1000 * 19 = 19000
      // 23 minutes unscaled = 20 * 28 = 460
      // 33 seconds re-scaled = 11
      // ========== 9229471

      return new Calendar(9235471);
   }

   private static Calendar getCalendar2() {
      Config.days = new int[]{12, 9, 12, 10, 12, 10, 12, 12, 10, 12, 10, 12}; // reset to known value

      // maths:
      // let's say it is December 12 4, 23:49:57 (unscaled minutes)
      // year 2: 133 days in a year * 24000 * 4 (year) = 12768000
      // December 1st: 121 days, November 12th: 132 days. 132 * 24000 = 3168000 ticks
      // 19 hours: 1000 * 23 = 23000
      // 49 minutes unscaled = 20 * 49 = 980
      // 57 seconds re-scaled = 19
      // ========== 15953999

      return new Calendar(15959999);
   }

   private static Calendar getStartingCalendar() {
      return new Calendar(0);
   }

   @Test
   public void testYear() {
      Assert.assertEquals("Bad count of days per year", 133, getCalendar().getDaysInYear());

      Assert.assertEquals("Invalid starting year", 0, getStartingCalendar().getYear());
      Assert.assertEquals("Invalid year", 2, getCalendar().getYear());
      Assert.assertEquals("Invalid year", 4, getCalendar2().getYear());
   }

   @Test
   public void testMonth() {
      Assert.assertEquals("Invalid starting month", 1, getStartingCalendar().getMonth());
      Assert.assertEquals("Invalid month", 11, getCalendar().getMonth());
      Assert.assertEquals("Invalid month", 12, getCalendar2().getMonth());
   }

   @Test
   public void testDay() {
      Assert.assertEquals("Invalid starting day", 1, getStartingCalendar().getDay());
      Assert.assertEquals("Invalid day", 8, getCalendar().getDay());
      Assert.assertEquals("Invalid day", 12, getCalendar2().getDay());
   }

   @Test
   public void testHour() {
      Assert.assertEquals("Invalid starting hour", 0, getStartingCalendar().getHour());
      Assert.assertEquals("Invalid hour", 19, getCalendar().getHour());
      Assert.assertEquals("Invalid hour", 23, getCalendar2().getHour());
   }

   @Test
   public void testMinute() {
      Assert.assertEquals("Invalid starting minute", 0, getStartingCalendar().getMinute());
      Assert.assertEquals("Invalid minute", 28, getCalendar().getMinute());
      Assert.assertEquals("Invalid minute", 59, getCalendar2().getMinute());
   }

   @Test
   public void testSecond() {
      Assert.assertEquals("Invalid starting second", 0, getStartingCalendar().getSecond());
      Assert.assertEquals("Invalid second", 33, getCalendar().getSecond());
      Assert.assertEquals("Invalid second", 57, getCalendar2().getSecond());
   }
}

package foxie.calendar;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.api.ISeason;
import foxie.calendar.implementation.CalendarImpl;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class Tools {
   public static CalendarImpl getCalendar(ICommandSender sender) {
      return new CalendarImpl(sender.getEntityWorld());
   }

   public static List getListOfDaysInMonth(String sMonth) {

      int month = getIntFromString(sMonth);
      if (month >= Config.days.length || month < 0)
         return null;

      List list = new ArrayList();

      for (int i = 1; i <= Config.days[month]; i++)
         list.add(String.valueOf(i));

      return list;
   }

   public static List getListOfMonths() {
      List list = new ArrayList();

      for (int i = 1; i <= Config.days.length; i++)
         list.add(i);

      return list;
   }

   public static int getIntFromString(String something) {
      try {
         return Integer.parseInt(something);
      } catch (Exception e) {
         return -1;
      }
   }

   public static void sendCurrentDateTime(ICommandSender sender, ICalendarProvider calendar) {
      sender.addChatMessage(new TextComponentString("It is " + (calendar.getDay()) + ". " + (calendar.getMonth()) + ". " + calendar.getYear()
              + ", " + calendar.getHour() + ":" + calendar.getMinute()));
   }

   public static void listMonths(ICommandSender sender) {
      for (int i = 0; i < CalendarAPI.getCalendarInstance().getNumberOfMonths(); i++) {
         sender.addChatMessage(new TextComponentString("  " + (i + 1) + ": " + CalendarAPI.getCalendarInstance().getListOfMonthsString()[i]));
      }
   }

   public static ISeason getSeason(ICalendarProvider calendar) {
      return CalendarAPI.getSeasonProvider().getSeason(calendar);
   }
}

package foxie.calendar;

import foxie.calendar.implementation.CalendarImpl;
import net.minecraft.command.ICommandSender;

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
}

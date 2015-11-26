package foxie.calendar.commands;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandDate extends CommandBase {
   @Override
   public String getCommandName() {
      return "date";
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.date.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) {
      ICalendarProvider calendar = CalendarAPI.getCalendarInstance(sender.getEntityWorld());

      try {
         if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText(calendar.getDay() + ". " + calendar.getMonth() + ". " + calendar.getYear()));
         } else if (args.length == 1 || args.length > 4)
            throw new WrongUsageException("commands.date.usage");
         else if (!args[0].equals("set"))
            throw new WrongUsageException("commands.date.usage");
         else {
            calendar.setDay(Integer.parseInt(args[1]));
            if (args.length > 2) calendar.setMonth(Integer.parseInt(args[2]));
            if (args.length > 3) calendar.setYear(Integer.parseInt(args[3]));
         }
      } catch (Exception e) {
         throw new WrongUsageException("commands.date.usage");
      }
   }

   @Override
   public List addTabCompletionOptions(ICommandSender sender, String[] params) {
      // TODO named months API
      switch (params.length) {
         case 0:
            return getListOfStringsMatchingLastWord(params, "set");
         case 2:
            ArrayList<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList(CalendarAPI.getCalendarProvider().getListOfMonthsNumeric()));
            list.addAll(Arrays.asList(CalendarAPI.getCalendarProvider().getListOfMonthsString()));
            return list;
      }
      return null;
   }
}
package foxie.calendar.commands;

import foxie.calendar.api.CalendarAPI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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

package foxie.calendar.commands;

import foxie.calendar.Config;
import foxie.calendar.Tools;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.versionhelpers.AbstractCommand;
import foxie.calendar.versionhelpers.TextComponentString;
import foxie.calendar.versionhelpers.TextComponentTranslation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandDate extends AbstractCommand {
   @Override
   public String getCommandName() {
      return "date";
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.date.usage";
   }

   @Override
   public void doCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      if(!Config.enableDateCommand)
         return;

      ICalendarProvider calendar = CalendarAPI.getCalendarInstance(sender.getEntityWorld());

      try {
         if (args.length == 0) {
            sender.addChatMessage(new TextComponentString(calendar.getDay() + ". " + calendar.getMonth() + ". " + calendar.getYear()));
         } else if (args.length == 1) {
            if (args[0].equals("list")) {
               sender.addChatMessage(new TextComponentTranslation("commands.date.listing"));
               Tools.listMonths(sender);
            }
         } else if (args.length == 2 || args.length > 4)
            throw new WrongUsageException("commands.date.usage");
         else if (!args[0].equals("set"))
            throw new WrongUsageException("commands.date.usage");
         else {
            calendar.setDay(Integer.parseInt(args[1]));
            if (args.length > 2) calendar.setMonth(Integer.parseInt(args[2]));
            if (args.length > 3) calendar.setYear(Integer.parseInt(args[3]));
            Tools.sendCurrentDateTime(sender, calendar);
            calendar.apply(sender.getEntityWorld());
         }
      } catch (IllegalArgumentException e) {
         sender.addChatMessage(new TextComponentTranslation("commands.date.nosuchday"));
      } catch (Exception e) {
         sender.addChatMessage(new TextComponentTranslation("commands.date.usage"));
      }
   }

   @Override
   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, int x, int y, int z) {
      // TODO named months API
      switch (args.length) {
         case 0:
            return getListOfStringsMatchingLastWord(args, "set");
         case 2:
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < CalendarAPI.getCalendarInstance().getNumberOfMonths(); i++) {
               list.add(String.valueOf(i));
            }
            list.addAll(Arrays.asList(CalendarAPI.getCalendarInstance().getListOfMonthsString()));
            return list;
      }
      return null;
   }
}

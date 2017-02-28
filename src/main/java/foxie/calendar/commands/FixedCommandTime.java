package foxie.calendar.commands;

import foxie.calendar.Config;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.versionhelpers.AbstractCommand;
import foxie.calendar.versionhelpers.TextComponentString;
import foxie.calendar.versionhelpers.TextComponentTranslation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.List;

public class FixedCommandTime extends AbstractCommand {

   @Override
   public String getName() {
      return "time";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getUsage(ICommandSender sender) {
      return "commands.fixedtime.usage";
   }

   @Override
   public void doCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      if (!Config.enableFixedTimeCommand)
         return;

      if (args.length == 0) {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(sender.getEntityWorld());
         sender.sendMessage(new TextComponentString("It is " + calendar.getHour() + ":" + calendar.getMinute()));
         return;
      }

      if (args.length < 2) {
         sender.sendMessage(new TextComponentTranslation("commands.fixedtime.usage"));
      }

      World world = null;

      world = sender.getEntityWorld();

      try {
         if (args.length == 3 && args[0].equals("set")) {
            processCommandSet(world, args[1], args[2]);
            return;
         }

         if (args[0].equals("add")) processCommandAdd(world, args[1]);
         else if (args[0].equals("set")) processCommandSet(world, args[1]);
         else throw new WrongUsageException("commands.fixedtime.usage");
      } catch (Exception e) {
         sender.sendMessage(new TextComponentTranslation("commands.fixedtime.usage"));
      }
   }

   private void processCommandAdd(World world, String time) throws WrongUsageException {
      try {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(world);
         calendar.addSeconds(Integer.parseInt(time));
         calendar.apply(world);
      } catch (Exception exception) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   private void processCommandSet(World world, String hours, String minutes) throws WrongUsageException {
      try {
         ICalendarProvider calendarProvider = CalendarAPI.getCalendarInstance(world);
         ICalendarProvider newTime = CalendarAPI.getCalendarInstance().setHour(Integer.parseInt(hours)).setMinute(Integer.parseInt(minutes));
         calendarProvider.setHour(newTime.getHour());
         calendarProvider.setMinute(newTime.getMinute());
         calendarProvider.apply(world);
      } catch (Exception e) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   private void processCommandSet(World world, String time) throws WrongUsageException {
      try {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(world);

         if (time.equals("morning")) calendar.setHour(6).setMinute(0);
         else if (time.equals("midday")) calendar.setHour(12).setMinute(0);
         else if (time.equals("evening")) calendar.setHour(18).setMinute(0);
         else if (time.equals("midnight")) calendar.setHour(0).setMinute(0);
         else {
            long iTime = Long.parseLong(time);
            ICalendarProvider c2 = CalendarAPI.getCalendarInstance(iTime);
            calendar.setHour(c2.getHour());
            calendar.setMinute(c2.getMinute());
            calendar.setSecond(c2.getSecond());
         }
         calendar.apply(world);

      } catch (Exception e) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   @Override
   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, int x, int y, int z) {
      return args.length == 1 ?
              getListOfStringsMatchingLastWord(args, "set", "add") :
              args.length == 2 && args[0].equals("set") ?
                      getListOfStringsMatchingLastWord(args, "morning", "midday", "evening", "midnight") :
                      null;
   }
}

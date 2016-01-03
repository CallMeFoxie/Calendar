package foxie.calendar.commands;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import net.minecraft.command.CommandTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class FixedCommandTime extends CommandTime {

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.fixedtime.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] params) {
      if (params.length == 0) {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(sender.getEntityWorld());
         sender.addChatMessage(new ChatComponentText("It is " + calendar.getHour() + ":" + calendar.getMinute()));
         return;
      }

      if (params.length < 2) {
         sender.addChatMessage(new ChatComponentTranslation("commands.fixedtime.usage"));
      }

      World world = null;

      if (sender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer) sender;
         world = player.worldObj;
      } else if (sender instanceof CommandBlockLogic) {
         CommandBlockLogic logic = (CommandBlockLogic) sender;
         world = logic.getEntityWorld();
      }

      try {
         if (params.length == 3 && params[0].equals("set")) {
            processCommandSet(world, params[1], params[2]);
            return;
         }

         if (params[0].equals("add")) processCommandAdd(world, params[1]);
         else if (params[0].equals("set")) processCommandSet(world, params[1]);
         else throw new WrongUsageException("commands.fixedtime.usage");
      } catch (Exception e) {
         sender.addChatMessage(new ChatComponentTranslation("commands.fixedtime.usage"));
      }
   }

   private void processCommandAdd(World world, String time) throws WrongUsageException {
      try {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(world);
         calendar.addSeconds(Integer.parseInt(time));
      } catch (Exception exception) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   private void processCommandSet(World world, String hours, String minutes) throws WrongUsageException {
      try {
         ICalendarProvider calendarProvider = CalendarAPI.getCalendarInstance(world);
         ICalendarProvider newTime = CalendarAPI.getCalendarInstance().setScaledHour(Integer.parseInt(hours)).setScaledMinute(Integer.parseInt(minutes));
         calendarProvider.setHour(newTime.getHour());
         calendarProvider.setMinute(newTime.getMinute());
      } catch (Exception e) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   private void processCommandSet(World world, String time) throws WrongUsageException {
      try {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(world);

         if (time.equals("morning")) calendar.setScaledHour(6);
         else if (time.equals("midday")) calendar.setScaledHour(12);
         else if (time.equals("evening")) calendar.setScaledHour(18);
         else if (time.equals("midnight")) calendar.setScaledHour(0);
         else {
            long iTime = Long.parseLong(time);
            ICalendarProvider c2 = CalendarAPI.getCalendarInstance(iTime);
            calendar.setHour(c2.getHour());
            calendar.setMinute(c2.getMinute());
            calendar.setSecond(c2.getSecond());
         }

      } catch (Exception e) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   @Override
   public List addTabCompletionOptions(ICommandSender sender, String[] params, BlockPos pos) {
      return params.length == 1 ?
              getListOfStringsMatchingLastWord(params, "set", "add") :
              params.length == 2 && params[0].equals("set") ?
                      getListOfStringsMatchingLastWord(params, "morning", "midday", "evening", "midnight") :
                      null;
   }
}

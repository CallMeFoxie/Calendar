package foxie.calendar.commands;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import foxie.calendar.implementation.CalendarImpl;
import net.minecraft.command.CommandTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class FixedCommandTime extends CommandTime {

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.fixedtime.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] params) {
      if (params.length < 2) {
         throw new WrongUsageException("commands.fixedtime.usage");
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
         if (params[0].equals("add")) processCommandAdd(world, params[1]);
         else if (params[0].equals("set")) processCommandSet(world, params[1]);
         else throw new WrongUsageException("commands.fixedtime.usage");
      } catch (Exception e) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   private void processCommandAdd(World world, String time) {
      try {
         CalendarImpl calendar = new CalendarImpl(world);
         calendar.addSeconds(Integer.parseInt(time));
      } catch (Exception exception) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   private void processCommandSet(World world, String time) {
      try {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(world);

         if (time.equals("morning")) calendar.setHour(0);
         else if (time.equals("midday")) calendar.setHour(6);
         else if (time.equals("evening")) calendar.setHour(12);
         else if (time.equals("midnight")) calendar.setHour(18);
         else {
            int iTime = Integer.parseInt(time);
            CalendarImpl c2 = new CalendarImpl(iTime);
            calendar.setHour(c2.getHour());
            calendar.setMinute(c2.getMinute());
            calendar.setSecond(c2.getSecond());
         }

      } catch (Exception e) {
         throw new WrongUsageException("commands.fixedtime.usage");
      }
   }

   @Override
   public List addTabCompletionOptions(ICommandSender sender, String[] params) {
      return params.length == 1 ?
              getListOfStringsMatchingLastWord(params, "set", "add") :
              params.length == 2 && params[0].equals("set") ?
                      getListOfStringsMatchingLastWord(params, "morning", "midday", "evening", "midnight") :
                      null;
   }
}

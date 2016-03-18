package foxie.calendar.commands;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ICalendarProvider;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandTime;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class FixedCommandTime extends CommandTime {

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.fixedtime.usage";
   }

   @Override
   public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
      if (params.length == 0) {
         ICalendarProvider calendar = CalendarAPI.getCalendarInstance(sender.getEntityWorld());
         sender.addChatMessage(new TextComponentString("It is " + calendar.getHour() + ":" + calendar.getMinute()));
         return;
      }

      if (params.length < 2) {
         sender.addChatMessage(new TextComponentTranslation("commands.fixedtime.usage"));
      }

      World world = null;

      if (sender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer) sender;
         world = player.worldObj;
      } else if (sender instanceof CommandBlockBaseLogic) {
         CommandBlockBaseLogic logic = (CommandBlockBaseLogic) sender;
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
         sender.addChatMessage(new TextComponentTranslation("commands.fixedtime.usage"));
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
   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
      return args.length == 1 ?
              getListOfStringsMatchingLastWord(args, "set", "add") :
              args.length == 2 && args[0].equals("set") ?
                      getListOfStringsMatchingLastWord(args, "morning", "midday", "evening", "midnight") :
                      null;
   }
}

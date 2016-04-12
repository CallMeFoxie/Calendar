package foxie.calendar.commands;

import foxie.calendar.Config;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.MCVersionHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandTemperature extends CommandBase {
   @Override
   public String getCommandName() {
      return "gettemp";
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.season.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (!Config.enableGetTempCommand)
         return;

      sender.addChatMessage(new ChatComponentText("Average temperature: " +
              CalendarAPI.getSeasonProvider(MCVersionHelper.getDimensionId(sender.getEntityWorld()))
                      .getAverageTemperature(CalendarAPI.getCalendarInstance(sender.getEntityWorld()), false)));

      sender.addChatMessage(new ChatComponentText("Actual temperature: " +
              CalendarAPI.getSeasonProvider(MCVersionHelper.getDimensionId(sender.getEntityWorld()))
                      .getTemperature(CalendarAPI.getCalendarInstance(sender.getEntityWorld()),
                              sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ())));
   }
}

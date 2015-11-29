package foxie.calendar.commands;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ISeason;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandSeason extends CommandBase {
   @Override
   public boolean canCommandSenderUseCommand(ICommandSender sender) {
      return true;
   }

   @Override
   public String getCommandName() {
      return "season";
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.season.usage";
   }

   @Override
   public void processCommand(ICommandSender sender, String[] args) {
      if (args.length == 0) {
         ISeason season = CalendarAPI.getSeasonProvider().getSeason(CalendarAPI.getCalendarInstance(sender.getEntityWorld()));
         sender.addChatMessage(new ChatComponentTranslation("commands.season.current", season.getName()));
         return;
      }

      if (args.length == 1) {
         if (args[0].equals("list")) {
            ISeason[] seasons = CalendarAPI.getSeasonProvider().getAllSeasons();
            sender.addChatMessage(new ChatComponentTranslation("commands.season.listing"));
            for (ISeason season : seasons) {
               sender.addChatMessage(new ChatComponentText("  " + season.getName() + ", " + season.getBeginningDate().getScaledDay() + ". " + season.getBeginningDate().getScaledMonth()));
            }
         }
      }
   }
}

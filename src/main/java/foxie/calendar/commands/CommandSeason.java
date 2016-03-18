package foxie.calendar.commands;

import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ISeason;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandSeason extends CommandBase {

   @Override
   public String getCommandName() {
      return "season";
   }

   @Override
   public String getCommandUsage(ICommandSender sender) {
      return "commands.season.usage";
   }

   @Override
   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      if (args.length == 0) {
         ISeason season = CalendarAPI.getSeasonProvider().getSeason(CalendarAPI.getCalendarInstance(sender.getEntityWorld()));
         sender.addChatMessage(new TextComponentTranslation("commands.season.current", season.getName()));
         return;
      }

      if (args.length == 1) {
         if (args[0].equals("list")) {
            ISeason[] seasons = CalendarAPI.getSeasonProvider().getAllSeasons();
            sender.addChatMessage(new TextComponentTranslation("commands.season.listing"));
            for (ISeason season : seasons) {
               sender.addChatMessage(new TextComponentString("  " + season.getName() + ", " + season.getBeginningDate().getDay() + ". " + season.getBeginningDate().getMonth()));
            }
         }
      }
   }
}

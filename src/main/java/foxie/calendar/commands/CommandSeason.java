package foxie.calendar.commands;

import foxie.calendar.Config;
import foxie.calendar.api.CalendarAPI;
import foxie.calendar.api.ISeason;
import foxie.calendar.versionhelpers.AbstractCommand;
import foxie.calendar.versionhelpers.TextComponentString;
import foxie.calendar.versionhelpers.TextComponentTranslation;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandSeason extends AbstractCommand {

   @Override
   public String getName() {
      return "season";
   }

   @Override
   public String getUsage(ICommandSender sender) {
      return "commands.season.usage";
   }

   @Override
   public void doCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      if(!Config.enableSeasonCommand)
         return;

      if (args.length == 0) {
         ISeason season = CalendarAPI.getSeasonProvider().getSeason(CalendarAPI.getCalendarInstance(sender.getEntityWorld()));
         sender.sendMessage(new TextComponentTranslation("commands.season.current", season.getName()));
         return;
      }

      if (args.length == 1) {
         if (args[0].equals("list")) {
            ISeason[] seasons = CalendarAPI.getSeasonProvider().getAllSeasons();
            sender.sendMessage(new TextComponentTranslation("commands.season.listing"));
            for (ISeason season : seasons) {
               sender.sendMessage(new TextComponentString("  " + season.getName() + ", " + season.getBeginningDate().getDay() + ". " + season.getBeginningDate().getMonth()));
            }
         }
      }
   }
}

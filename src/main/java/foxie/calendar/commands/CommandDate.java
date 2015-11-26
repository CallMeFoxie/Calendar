package foxie.calendar.commands;

import foxie.calendar.Tools;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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
            return Tools.getListOfMonths();
      }
      return null;
   }
}

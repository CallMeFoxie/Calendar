package foxie.calendar.versionhelpers;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import javax.vecmath.Point3d;
import java.util.List;

public abstract class AbstractCommand extends CommandBase {
   @Override
   public final void processCommand(ICommandSender sender, String[] args) throws CommandException {
      doCommand(MinecraftServer.getServer(), sender, args);
   }

   @Override
   public List addTabCompletionOptions(ICommandSender sender, String[] args) {
      Point3d location = getPosition(sender);
      return getTabCompletionOptions(MinecraftServer.getServer(), sender, args, (int) location.x, (int) location.y, (int) location.z);
   }

   public Point3d getPosition(ICommandSender sender) {
      if(sender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)sender;
         return new Point3d(player.posX, player.posY, player.posZ);
      }

      return new Point3d(0, 0, 0);
   }

   // never EVER change anything past this line without consulting every single version this is updated for!
   public abstract void doCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;

   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, int x, int y, int z) {
      return null;
   }
}

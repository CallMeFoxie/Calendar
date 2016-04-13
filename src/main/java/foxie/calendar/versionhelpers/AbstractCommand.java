package foxie.calendar.versionhelpers;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.vecmath.Point3d;
import java.util.List;

public abstract class AbstractCommand extends CommandBase {
   @Override
   public final void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      doCommand(server, sender, args);
   }

   @Override
   public final List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
      return getTabCompletionOptions(server, sender, args, pos.getX(), pos.getY(), pos.getZ());
   }

   public Point3d getPosition(ICommandSender sender) {
      return new Point3d(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ());
   }

   // never EVER change anything past this line without consulting every single version this is updated for!
   public abstract void doCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;

   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, int x, int y, int z) {
      return null;
   }
}

package foxie.calendar.api;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class MCVersionHelper {
   public static int getDimensionId(WorldProvider provider) {
      return provider.getDimension();
   }

   public static int getDimensionId(World world) {
      return getDimensionId(world.provider);
   }

   public static void log(String msg) {
      FMLLog.info(msg);
   }

   public static String getCurrentModId() {
      return Loader.instance().activeModContainer().getModId();
   }
}

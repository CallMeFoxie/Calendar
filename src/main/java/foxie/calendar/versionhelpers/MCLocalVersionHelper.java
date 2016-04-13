package foxie.calendar.versionhelpers;

import net.minecraft.util.StatCollector;

public class MCLocalVersionHelper {
   public static String translateToLocal(String key) {
      return StatCollector.translateToLocal(key);
   }

   public static String translateToLocalFormatted(String key, Object... format) {
      return StatCollector.translateToLocalFormatted(key, format);
   }

   public static String translateToFallback(String key) {
      return StatCollector.translateToFallback(key);
   }

   public static boolean canTranslate(String key) {
      return StatCollector.canTranslate(key);
   }

   public static long getLastTranslationUpdateTimeInMilliseconds() {
      return StatCollector.getLastTranslationUpdateTimeInMilliseconds();
   }
}

package foxie.calendar;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

   public static int[] days = new int[]{12, 9, 12, 10, 12, 10, 12, 12, 10, 12, 10, 12};

   public static boolean enableFixedTimeCommand = true;
   public static boolean enableDateCommand      = true;

   private Configuration cfg;

   public Config(File file) {
      cfg = new Configuration(file);

      // fetch months
      days = cfg.get("calendar", "days", days, "Days in months").getIntList();

      enableFixedTimeCommand = cfg.getBoolean("enableFixedTimeCommand", "config", enableFixedTimeCommand, "Enable fixed time command");
      enableDateCommand = cfg.getBoolean("enable   DateCommand", "config", enableDateCommand, "Enable date command");
   }

   public void preinit() {
   }

   public void init() {
   }

   public void postinit() {
   }
}

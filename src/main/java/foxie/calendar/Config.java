package foxie.calendar;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

   public static int[] days = new int[]{12, 9, 12, 10, 12, 10, 12, 12, 10, 12, 10, 12};

   private Configuration cfg;

   public void Config(File file) {
      cfg = new Configuration(file);

      // fetch months
      days = cfg.get("calendar", "days", days, "Days in months").getIntList();
   }

   public void preinit() {
   }

   public void init() {
   }

   public void postinit() {
   }
}

package foxie.calendar;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Arrays;

public class Config {

   public static int[]    days   = new int[]{12, 9, 12, 10, 12, 10, 12, 12, 10, 12, 10, 12};
   public static String[] months = new String[]{"January", "February", "March", "April",
           "May", "June", "July", "August", "September", "October", "November", "December"};

   public static boolean enableFixedTimeCommand = true;
   public static boolean enableDateCommand      = true;

   private Configuration cfg;

   public Config(File file) {
      cfg = new Configuration(file);

      // fetch months
      days = cfg.get("calendar", "days", days, "Days in months").getIntList();
      months = cfg.getStringList("monthNames", "calendar", months, "Named months");

      if (days.length > months.length) {
         FMLLog.bigWarning("Your month names list is shorter than year months days! Cutting the year short!");
         int[] days2 = Arrays.copyOfRange(days, 0, months.length);
         days = days2;
      }

      enableFixedTimeCommand = cfg.getBoolean("enableFixedTimeCommand", "config", enableFixedTimeCommand, "Enable fixed time command");
      enableDateCommand = cfg.getBoolean("enableDateCommand", "config", enableDateCommand, "Enable date command");

   }

   public void preinit() {
   }

   public void init() {
   }

   public void postinit() {
   }
}

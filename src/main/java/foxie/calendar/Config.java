package foxie.calendar;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;

import java.io.File;
import java.util.Arrays;

public class Config {

   public static int[]    days   = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
   public static String[] months = new String[]{"January", "February", "March", "April",
           "May", "June", "July", "August", "September", "October", "November", "December"};

   public static float tempLostPerZ = 0.005f; // 200 blocks = 1K = 1C drop, reasonable by default?
   public static float tempLostPerY = -0.15625f; // +10deg near the earth core

   public static boolean enableFixedTimeCommand = true;
   public static boolean enableDateCommand      = true;
   public static boolean enableSeasonCommand    = true;
   public static boolean enableGetTempCommand   = true;


   private Configuration cfg;

   public Config(File file) {
      cfg = new Configuration(file);

      // fetch months
      days = cfg.get("calendar", "days", days, "Days in months\nChanging for existing worlds will recalculate it").getIntList();
      months = cfg.getStringList("monthNames", "calendar", months, "Named months");

      if (days.length > months.length) {
         FMLLog.bigWarning("Your month names list is shorter than year months days! Cutting the year short!");
         int[] days2 = Arrays.copyOfRange(days, 0, months.length);
         days = days2;
      }

      enableFixedTimeCommand = cfg.getBoolean("enableFixedTimeCommand", "config", enableFixedTimeCommand, "Enable fixed time command");
      enableDateCommand = cfg.getBoolean("enableDateCommand", "config", enableDateCommand, "Enable date command");
      enableSeasonCommand = cfg.getBoolean("enableSeasonCommand", "config", enableSeasonCommand, "Enable season command");
      enableGetTempCommand = cfg.getBoolean("enableGetTempCommand", "config", enableGetTempCommand, "Enable gettemp command");
      tempLostPerZ = cfg.getFloat("tempLostPerZ", "config", tempLostPerZ, -999f, 999f, "How much temperature [K] is lost per Z block distance");
      tempLostPerY = cfg.getFloat("tempLostPerY", "config", tempLostPerY, -999f, 999f, "How much temperature [K] is lost per Y block distance (ref y 64)");

      if (cfg.hasChanged())
         cfg.save();

   }

   public void preinit() {
   }

   public void init() {
   }

   public void postinit() {
   }

   public Configuration getConfig() {
      return cfg;
   }
}

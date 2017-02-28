package foxie.calendar.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ProxyClient extends ProxyCommon {
   @Override
   public EntityPlayer getPlayer() {
      return Minecraft.getMinecraft().player;
   }
}

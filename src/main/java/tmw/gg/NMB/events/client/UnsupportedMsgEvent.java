package tmw.gg.NMB.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class UnsupportedMsgEvent {
    @SubscribeEvent
    public void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.sendSystemMessage(Component.literal("§f<§dN§fM§9B§f> NMB is currently §cunsupported §fon the client side due us willing develop this fully as a server utility mod first & fix all bugs that encounter, please install it on the server side for now. If someone is interested bringing this to the client-side now and help us out a bit hit me up on Discord §9§o2lay#0001"));
        }
    }
}

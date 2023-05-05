package tmw.gg.NMB.events.server;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Iterator;

public class ServerCastMessageEvent {
    public static void sendMessage(Player player, MutableComponent message) {
        sendMessage(player, message, false);
    }
    public static void sendMessage(Player player, MutableComponent message, boolean emptyline) {
        if (emptyline) {
            player.sendSystemMessage(Component.literal(""));
        }
        player.sendSystemMessage(message);
    }
    public static void broadcastMessage(Level world, MutableComponent message) {
        MinecraftServer server = world.getServer();
        if (server != null) {
            Iterator var3 = server.getPlayerList().getPlayers().iterator();

            while (var3.hasNext()) {
                Player player = (Player) var3.next();
                sendMessage(player, message);
            }

        }
    }
}
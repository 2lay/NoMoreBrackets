package tmw.gg.NMB.events.server;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tmw.gg.NMB.events.common.LoggerEvent;

public class ServerChatEvent {
    public static Pair<Boolean, Component> onServerChat(ServerPlayer serverPlayer, Component messageComponent) {
        String user = serverPlayer.getName().getString();
        String message = messageComponent.getString();

        if (!message.contains(user)) {
            return null;
        }
        if (message.contains("> ")) {
            message = message.substring(message.split("> ")[0].length() + 2);
        }

        Component output = serverPlayer.getDisplayName();

        MutableComponent mutableOutput = (MutableComponent) output;
        mutableOutput.append(message);

        return Pair.of(true, mutableOutput);
    }
    @SubscribeEvent
    public void onServerChat(net.minecraftforge.event.ServerChatEvent e) {
        ServerPlayer serverPlayer = e.getPlayer();
        Component originalMessage = e.getMessage();
        MutableComponent fullMessage = Component.literal("<" + serverPlayer.getName().getString() + "> ").append(originalMessage);

        Pair<Boolean, Component> pair = ServerChatEvent.onServerChat(serverPlayer, fullMessage);
        if (pair != null) {
            if (pair.getFirst()) {
                MutableComponent newMessage = pair.getSecond().copy();
                if (fullMessage != newMessage) {
                    e.setCanceled(true);

                    serverPlayer.server.execute(() -> {
                        LoggerEvent.logger.info(newMessage.getString());
                        ServerCastMessageEvent.broadcastMessage(serverPlayer.level, newMessage);
                    });
                }
            }
        }
    }
}


package tmw.gg.NMB.events;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class ChatEvent {
    public static Pair<Boolean, Component> onServerChat(ServerPlayer serverPlayer, Component messageComponent, UUID uuid) {
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
    public void onServerChat(ServerChatEvent e) {
        ServerPlayer serverPlayer = e.getPlayer();
        Component originalMessage = e.getMessage();
        MutableComponent fullMessage = Component.literal("<" + serverPlayer.getName().getString() + "> ").append(originalMessage);

        Pair<Boolean, Component> pair = ChatEvent.onServerChat(serverPlayer, fullMessage, serverPlayer.getUUID());
        if (pair != null) {
            if (pair.getFirst()) {
                MutableComponent newMessage = pair.getSecond().copy();
                if (fullMessage != newMessage) {
                    e.setCanceled(true);

                    serverPlayer.server.execute(() -> {
                        LoggerEvent.logger.info(newMessage.getString());
                        CastMessageEvent.broadcastMessage(serverPlayer.level, newMessage);
                    });
                }
            }
        }
    }
}


package tmw.gg.NMB;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tmw.gg.NMB.events.server.ServerChatEvent;
import tmw.gg.NMB.events.client.UnsupportedMsgEvent;

@Mod("nmb")
public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    public Main() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
            LOGGER.error("NMB is currently unsupported on the client side due us willing develop this fully as a server utility mod first & fix all bugs that encounter, please install it on the server side for now.");
            LOGGER.error("If someone is interested bringing this to the client-side now and help us out a bit hit me up on Discord 2lay#0001");
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            modEventBus.addListener(this::onLoadComplete);
            // MinecraftForge.EVENT_BUS.register(new ServerChatEvent()); <- Remove this comment to enable the non-working client side implementation
            MinecraftForge.EVENT_BUS.register(new UnsupportedMsgEvent());
        });

        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            LOGGER.info("Loading NMB Server (No More Brackets) version 1.19.2-Forge-1.0.0");
            MinecraftForge.EVENT_BUS.register(new ServerChatEvent());
        });
    }
    private void onLoadComplete(final FMLLoadCompleteEvent loadCompleteEvent) {
        LOGGER.info("NMB (No More Brackets) Loaded successfully! Enjoy a cleaner chat // 2lay");
    }
}

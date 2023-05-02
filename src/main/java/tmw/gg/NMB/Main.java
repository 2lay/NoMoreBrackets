package tmw.gg.NMB;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tmw.gg.NMB.events.ChatEvent;
import tmw.gg.NMB.events.LoggerEvent;

@Mod("nmb")
public class Main {
    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::loadComplete);
        LoggerEvent.logger.info("Loading NMB (No More Brackets) version 1.19.2-Forge-1.0.0" );
    }
    private void loadComplete(final FMLLoadCompleteEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChatEvent());
        LoggerEvent.logger.info("NMB (No More Brackets) Loaded successfully! Enjoy a cleaner chat // 2lay" );
    }

}
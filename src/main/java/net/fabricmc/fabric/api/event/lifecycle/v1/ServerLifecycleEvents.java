package net.fabricmc.fabric.api.event.lifecycle.v1;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;

public final class ServerLifecycleEvents {

	private ServerLifecycleEvents() {
	}

	public interface ServerStarting {
		void onServerStarting(MinecraftServer server);
	}

	public static final Event<ServerStarting> SERVER_STARTING = EventFactory.createArrayBacked(ServerStarting.class,
			listeners -> server -> {
				for (ServerStarting listener : listeners) {
					listener.onServerStarting(server);
				}
			});

	public static void fireServerStarting(MinecraftServer server) {
		SERVER_STARTING.invoker().onServerStarting(server);
	}

}

package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import java.util.function.Supplier;
import java.util.concurrent.CompletableFuture;
import com.luneruniverse.minecraft.mod.nbteditor.server.NBTEditorServer;
import com.luneruniverse.minecraft.mod.nbteditor.util.MainUtil;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceReload;
import net.minecraft.server.MinecraftServer;

public class DynamicRegistryManagerHolder {
	
	private static volatile DynamicRegistryManager clientManager;
	private static volatile DynamicRegistryManager serverManager;
	
	public static ResourceReload loadDefaultManager() {
		CompletableFuture<DynamicRegistryManager> future = CompletableFuture.completedFuture(getManager());
		
		return new ResourceReload() {
			@Override
			public CompletableFuture<?> whenComplete() {
				return future;
			}
			@Override
			public float getProgress() {
				return future.isDone() ? 1 : 0;
			}
		};
	}
	public static void onDefaultManagerLoad(Runnable callback) {
		callback.run();
	}
	
	public static DynamicRegistryManager getManager() {
		if (NBTEditorServer.isOnServerThread()) {
			if (serverManager == null)
				throw new IllegalStateException("The server manager hasn't been set yet!");
			return serverManager;
		}
		
		if (clientManager != null)
			return clientManager;
		if (MainUtil.client.world != null)
			return MainUtil.client.world.getRegistryManager();
		if (MainUtil.client.getNetworkHandler() != null)
			return MainUtil.client.getNetworkHandler().getRegistryManager();
		throw new IllegalStateException("No registry manager is available");
	}
	public static RegistryWrapper.WrapperLookup get() {
		return getManager();
	}
	
	public static void setClientManager(PacketListener listener) {
		clientManager = (listener == null ? null : ((ClientPlayNetworkHandler) listener).getRegistryManager());
	}
	public static void setServerManager(MinecraftServer server) {
		serverManager = server.getRegistryManager();
	}
	
	public static boolean hasClientManager() {
		return clientManager != null;
	}
	
	public static <T> T withDefaultManager(Supplier<T> callback) {
		return callback.get();
	}
	public static void withDefaultManager(Runnable callback) {
		callback.run();
	}
	public static <T> boolean isOwnedByDefaultManager(RegistryEntry.Reference<T> entry) {
		return false;
	}
	
}

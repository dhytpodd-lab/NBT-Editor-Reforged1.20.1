package com.luneruniverse.minecraft.mod.nbteditor;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod("nbteditor")
public class NBTEditorForge {

	public NBTEditorForge() {
		new NBTEditor().onInitialize();
		MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
		DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT,
				() -> () -> new NBTEditorClient().onInitializeClient());
	}

	private void onServerStarting(ServerStartingEvent event) {
		ServerLifecycleEvents.fireServerStarting(event.getServer());
	}

}

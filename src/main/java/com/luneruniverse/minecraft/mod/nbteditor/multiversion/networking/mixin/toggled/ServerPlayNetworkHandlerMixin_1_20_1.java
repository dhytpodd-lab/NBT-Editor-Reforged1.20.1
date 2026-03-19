package com.luneruniverse.minecraft.mod.nbteditor.multiversion.networking.mixin.toggled;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.networking.MVServerNetworking;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin_1_20_1 {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(MinecraftServer server, ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
		player.networkHandler = (ServerPlayNetworkHandler) (Object) this;
		MVServerNetworking.onPlayStart(player);
	}
}

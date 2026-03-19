package com.luneruniverse.minecraft.mod.nbteditor.multiversion.networking.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.networking.MVClientNetworking;
import com.luneruniverse.minecraft.mod.nbteditor.multiversion.networking.MVNetworking;
import com.luneruniverse.minecraft.mod.nbteditor.multiversion.networking.MVPacket;
import com.luneruniverse.minecraft.mod.nbteditor.multiversion.networking.MVServerNetworking;
import com.luneruniverse.minecraft.mod.nbteditor.server.NBTEditorServer;
import com.luneruniverse.minecraft.mod.nbteditor.server.ServerMixinLink;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

@SuppressWarnings("deprecation")
@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {
	@Shadow
	private PacketListener packetListener;
	@Shadow
	public abstract boolean isOpen();
	
	@Inject(method = "disconnect", at = @At("HEAD"))
	private void disconnect(Text reason, CallbackInfo info) {
		if (isOpen()) {
			if (!NBTEditorServer.IS_DEDICATED && ServerMixinLink.isInstanceOfClientPlayNetworkHandlerSafely(packetListener))
				MVClientNetworking.onPlayStop();
			if (packetListener instanceof ServerPlayNetworkHandler handler)
				MVServerNetworking.onPlayStop(handler.player);
		}
	}
	
	@Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
	private static void handlePacket(Packet<?> packet, PacketListener listener, CallbackInfo info) {
		if (!NBTEditorServer.IS_DEDICATED && ServerMixinLink.isInstanceOfClientPlayNetworkHandlerSafely(listener) && packet instanceof CustomPayloadS2CPacket customPacket) {
			MVPacket mvPacket = MVNetworking.readPacket(customPacket.getChannel(), customPacket.getData());
			if (mvPacket != null) {
				MVClientNetworking.callListeners(mvPacket);
				info.cancel();
			}
		}
		if (listener instanceof ServerPlayNetworkHandler handler && packet instanceof CustomPayloadC2SPacket customPacket) {
			MVPacket mvPacket = MVNetworking.readPacket(customPacket.getChannel(), customPacket.getData());
			if (mvPacket != null) {
				MVServerNetworking.callListeners(mvPacket, handler.player);
				info.cancel();
			}
		}
	}
}

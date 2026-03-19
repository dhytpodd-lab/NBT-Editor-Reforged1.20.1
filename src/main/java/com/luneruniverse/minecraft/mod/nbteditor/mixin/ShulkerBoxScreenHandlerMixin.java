package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.luneruniverse.minecraft.mod.nbteditor.server.ServerMixinLink;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.ShulkerBoxSlot;
import net.minecraft.screen.slot.Slot;

@Mixin(ShulkerBoxScreenHandler.class)
public class ShulkerBoxScreenHandlerMixin {
	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V", at = @At("RETURN"))
	private void initReturn(int syncId, PlayerInventory playerInventory, Inventory inventory, CallbackInfo info) {
		for (Slot slot : ((ScreenHandler) (Object) this).slots) {
			if (slot instanceof ShulkerBoxSlot)
				ServerMixinLink.SLOT_OWNER.put(slot, playerInventory.player);
		}
	}
}

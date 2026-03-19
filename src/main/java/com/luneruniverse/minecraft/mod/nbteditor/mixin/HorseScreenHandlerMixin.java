package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.luneruniverse.minecraft.mod.nbteditor.server.ServerMixinLink;

import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

@Mixin(HorseScreenHandler.class)
public class HorseScreenHandlerMixin {
	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/passive/AbstractHorseEntity;)V", at = @At("RETURN"), require = 0)
	private void initReturn(int syncId, PlayerInventory playerInventory, Inventory inventory, AbstractHorseEntity horse, CallbackInfo info) {
		for (Slot slot : ((ScreenHandler) (Object) this).slots)
			ServerMixinLink.SLOT_OWNER.put(slot, playerInventory.player);
	}
}

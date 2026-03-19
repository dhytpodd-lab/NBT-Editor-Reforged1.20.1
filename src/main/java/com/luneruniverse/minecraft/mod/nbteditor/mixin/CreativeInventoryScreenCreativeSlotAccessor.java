package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.screen.slot.Slot;

@Mixin(targets = "net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen$CreativeSlot")
public interface CreativeInventoryScreenCreativeSlotAccessor {
	
	@Accessor("slot")
	Slot getSlot();
	
}

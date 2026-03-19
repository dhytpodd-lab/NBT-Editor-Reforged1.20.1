package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
	@Accessor
	public Slot getFocusedSlot();
	
	@Accessor("cancelNextRelease")
	public void setCancelNextRelease(boolean cancelNextRelease);
	
	@Accessor("x")
	public int getX();
	
	@Accessor("x")
	public void setX(int x);
	
	@Accessor("y")
	public int getY();
	
	@Accessor("backgroundWidth")
	public int getBackgroundWidth();
	
	@Accessor("titleX")
	public int getTitleX();
	
	@Accessor("titleY")
	public int getTitleY();
	
	@Accessor("playerInventoryTitle")
	public Text getPlayerInventoryTitle();
	
	@Accessor("playerInventoryTitleX")
	public int getPlayerInventoryTitleX();
	
	@Accessor("playerInventoryTitleY")
	public int getPlayerInventoryTitleY();
}

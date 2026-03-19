package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.ClickableWidget;

@Mixin(ClickableWidget.class)
public interface ClickableWidgetAccessor {
	@Accessor("x")
	int nbteditor$getX();
	@Accessor("x")
	void nbteditor$setX(int x);
	
	@Accessor("y")
	int nbteditor$getY();
	@Accessor("y")
	void nbteditor$setY(int y);
	
	@Accessor("width")
	int nbteditor$getWidth();
	@Accessor("width")
	void nbteditor$setWidth(int width);
	
	@Accessor("height")
	int nbteditor$getHeight();
	@Accessor("height")
	void nbteditor$setHeight(int height);
	
	@Accessor("active")
	boolean nbteditor$isActive();
	@Accessor("active")
	void nbteditor$setActive(boolean active);
	
	@Accessor("visible")
	boolean nbteditor$isVisible();
	@Accessor("visible")
	void nbteditor$setVisible(boolean visible);
	
	@Accessor("hovered")
	boolean nbteditor$isHovered();
}

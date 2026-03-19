package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.SliderWidget;

@Mixin(SliderWidget.class)
public interface SliderWidgetAccessor {
	@Accessor("value")
	double nbteditor$getValue();
	@Accessor("value")
	void nbteditor$setValue(double value);
}

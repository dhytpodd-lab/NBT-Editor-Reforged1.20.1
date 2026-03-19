package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.ChatInputSuggestor.SuggestionWindow;
import net.minecraft.client.util.math.Rect2i;

@Mixin(SuggestionWindow.class)
public interface ChatInputSuggestorSuggestionWindowAccessor {
	@Accessor("area")
	Rect2i nbteditor$getArea();
}

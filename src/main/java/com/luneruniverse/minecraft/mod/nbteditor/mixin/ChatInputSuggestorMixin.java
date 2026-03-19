package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import java.awt.Point;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.MVMisc;
import com.luneruniverse.minecraft.mod.nbteditor.screens.widgets.SuggestingTextFieldWidget;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.Formatting;

@Mixin(ChatInputSuggestor.class)
public class ChatInputSuggestorMixin {
	@Shadow
	TextFieldWidget textField;
	
	@ModifyArg(method = "show", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatInputSuggestor$SuggestionWindow;<init>(Lnet/minecraft/client/gui/screen/ChatInputSuggestor;IIILjava/util/List;Z)V"), index = 1)
	private int SuggestionWindow_x(int x) {
		if (!(textField instanceof SuggestingTextFieldWidget suggestor))
			return x;
		
		if (suggestor.isDropdownOnly()) {
			Point pos = suggestor.getSpecialDropdownPos();
			return pos.x;
		}
		return x;
	}
	@ModifyArg(method = "show", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatInputSuggestor$SuggestionWindow;<init>(Lnet/minecraft/client/gui/screen/ChatInputSuggestor;IIILjava/util/List;Z)V"), index = 2)
	private int SuggestionWindow_y(int y) {
		if (!(textField instanceof SuggestingTextFieldWidget suggestor))
			return y;
		
		if (suggestor.isDropdownOnly()) {
			Point pos = suggestor.getSpecialDropdownPos();
			return pos.y;
		}
		return MVMisc.getWidgetY(textField) + MVMisc.getWidgetHeight(textField) + 2;
	}
	
	@Inject(method = "showUsages", at = @At("HEAD"), cancellable = true)
	@Group(name = "showUsages", min = 1)
	private void showUsages(Formatting formatting, CallbackInfoReturnable<Boolean> info) {
		if (!(textField instanceof SuggestingTextFieldWidget))
			return;
		
		info.setReturnValue(true);
	}
	@Inject(method = "method_23929(Lnet/minecraft/class_124;)V", at = @At("HEAD"), cancellable = true)
	@Group(name = "showUsages", min = 1)
	@SuppressWarnings("target")
	private void showUsages(Formatting formatting, CallbackInfo info) {
		if (!(textField instanceof SuggestingTextFieldWidget))
			return;
		
		info.cancel();
	}
}

package com.luneruniverse.minecraft.mod.nbteditor.mixin;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestions;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatInputSuggestor.SuggestionWindow;
import net.minecraft.text.OrderedText;

@Mixin(ChatInputSuggestor.class)
public interface ChatInputSuggestorAccessor {
	@Accessor("messages")
	List<OrderedText> nbteditor$getMessages();
	
	@Accessor("parse")
	void nbteditor$setParse(ParseResults<?> parse);
	
	@Accessor("pendingSuggestions")
	CompletableFuture<Suggestions> nbteditor$getPendingSuggestions();
	@Accessor("pendingSuggestions")
	void nbteditor$setPendingSuggestions(CompletableFuture<Suggestions> pendingSuggestions);
	
	@Accessor("window")
	SuggestionWindow nbteditor$getWindow();
	
	@Accessor("completingSuggestions")
	boolean nbteditor$isCompletingSuggestions();
	
	@Invoker("showCommandSuggestions")
	void nbteditor$showCommandSuggestions();
}

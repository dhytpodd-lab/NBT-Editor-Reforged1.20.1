package com.luneruniverse.minecraft.mod.nbteditor.integrations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalBlock;
import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalEntity;
import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalItem;
import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalNBT;
import com.luneruniverse.minecraft.mod.nbteditor.multiversion.nbt.NBTManagers;
import com.luneruniverse.minecraft.mod.nbteditor.util.MainUtil;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.visitor.StringNbtWriter;
import net.minecraft.util.Identifier;

public class NBTAutocompleteIntegration extends Integration {
	
	public static final Optional<NBTAutocompleteIntegration> INSTANCE = Integration.getOptional(NBTAutocompleteIntegration::new);
	private static final Class<?> SUGGESTION_MANAGER_CLASS = loadSuggestionManagerClass();
	private static final Field SUBTEXT_MAP_FIELD = loadSubtextMapField();
	private static final Method LOAD_FROM_NAME_METHOD = loadFromNameMethod();
	
	private static StringRange shiftRange(StringRange range, int shift) {
		return new StringRange(range.getStart() + shift, range.getEnd() + shift);
	}
	private static Suggestion shiftSuggestion(Suggestion suggestion, int shift) {
		Suggestion shiftedSuggestion = new Suggestion(shiftRange(suggestion.getRange(), shift), suggestion.getText(), suggestion.getTooltip());
		moveSubtext(suggestion, shiftedSuggestion);
		return shiftedSuggestion;
	}
	
	private NBTAutocompleteIntegration() {}
	
	@Override
	public String getModId() {
		return "nbt_ac";
	}

	private static Class<?> loadSuggestionManagerClass() {
		try {
			return Class.forName("com.mt1006.nbt_ac.autocomplete.NbtSuggestionManager");
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	private static Field loadSubtextMapField() {
		if (SUGGESTION_MANAGER_CLASS == null)
			return null;
		try {
			Field field = SUGGESTION_MANAGER_CLASS.getDeclaredField("subtextMap");
			field.setAccessible(true);
			return field;
		} catch (ReflectiveOperationException e) {
			return null;
		}
	}
	private static Method loadFromNameMethod() {
		if (SUGGESTION_MANAGER_CLASS == null)
			return null;
		try {
			Method method = SUGGESTION_MANAGER_CLASS.getDeclaredMethod("loadFromName", String.class, String.class, SuggestionsBuilder.class, boolean.class);
			method.setAccessible(true);
			return method;
		} catch (ReflectiveOperationException e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	private static void moveSubtext(Suggestion oldSuggestion, Suggestion newSuggestion) {
		if (SUBTEXT_MAP_FIELD == null)
			return;
		try {
			Object value = ((java.util.Map<Suggestion, Object>) SUBTEXT_MAP_FIELD.get(null)).remove(oldSuggestion);
			if (value != null)
				((java.util.Map<Suggestion, Object>) SUBTEXT_MAP_FIELD.get(null)).put(newSuggestion, value);
		} catch (ReflectiveOperationException e) {
			// Ignore optional integration metadata failures
		}
	}
	@SuppressWarnings("unchecked")
	public static CompletableFuture<Suggestions> loadSuggestionsByName(String name, String tag, SuggestionsBuilder builder) {
		if (LOAD_FROM_NAME_METHOD == null)
			return Suggestions.empty();
		try {
			return (CompletableFuture<Suggestions>) LOAD_FROM_NAME_METHOD.invoke(null, name, tag, builder, false);
		} catch (ReflectiveOperationException e) {
			return Suggestions.empty();
		}
	}
	
	private CompletableFuture<Suggestions> getSuggestions(String type, Identifier id, NbtElement nbt, List<String> path, String key, String value, int cursor, Collection<String> otherTags) {
		if (value != null && otherTags != null)
			throw new IllegalArgumentException("Both value and otherTags can't be non-null at the same time!");
		if (key == null && value == null)
			throw new IllegalArgumentException("Both key and value can't be null at the same time!");
		
		boolean components = NBTManagers.COMPONENTS_EXIST && type.equals("item");
		
		boolean nextTagAllowed;
		if (value == null) {
			key = key.substring(0, cursor);
			nextTagAllowed = false;
		} else {
			nextTagAllowed = (cursor < value.length());
			value = value.substring(0, cursor);
		}
		
		if (key != null && (key.contains("{") || key.contains("[")))
			return new SuggestionsBuilder("", 0).buildFuture();
		
		StringBuilder pathBuilder = new StringBuilder();
		boolean firstKey = true;
		if (nbt != null) {
			for (String piece : path) {
				if (nbt instanceof NbtCompound compound) {
					if (firstKey && components) {
						pathBuilder.append('[');
						pathBuilder.append(piece);
						pathBuilder.append('=');
					} else {
						pathBuilder.append('{');
						pathBuilder.append(escapeKey(piece));
						pathBuilder.append(':');
					}
					nbt = compound.get(piece);
				} else if (nbt instanceof NbtList list) {
					pathBuilder.append('[');
					nbt = list.get(Integer.parseInt(piece));
				} else
					return new SuggestionsBuilder("", 0).buildFuture();
				firstKey = false;
			}
		}
		int fieldStart = pathBuilder.length();
		if (key != null) {
			if (nbt instanceof NbtCompound) {
				if (firstKey && components)
					pathBuilder.append('[');
				else
					pathBuilder.append('{');
			} else if (nbt instanceof NbtList)
				pathBuilder.append('[');
			else
				return new SuggestionsBuilder("", 0).buildFuture();
			fieldStart = pathBuilder.length();
			
			if (nbt instanceof NbtCompound) {
				if (firstKey && components)
					pathBuilder.append(key);
				else {
					String escapedKey = escapeKey(key);
					pathBuilder.append(key.equals(escapedKey) ? key : escapedKey.substring(0, escapedKey.length() - 1));
				}
			}
			
			if (value != null) {
				if (nbt instanceof NbtCompound) {
					if (firstKey && components)
						pathBuilder.append('=');
					else
						pathBuilder.append(':');
					fieldStart = pathBuilder.length();
				}
				pathBuilder.append(value);
			}
		} else {
			if (firstKey && components) {
				pathBuilder.append("[container=[{item:{id:\"" + id + "\",components:");
				fieldStart = pathBuilder.length();
			}
			pathBuilder.append(value);
		}
		String pathStr = pathBuilder.toString();
		
		String suggestionId = type + "/" + id;
		final int fieldStartFinal = fieldStart;
		final String valueFinal = value;
		final boolean firstKeyFinal = firstKey;
		return loadFromName(suggestionId, pathStr, components).thenApply(suggestions -> {
			List<Suggestion> shiftedSuggestions = suggestions.getList().stream()
					.filter(suggestion ->
							!(suggestion.getText().isEmpty()) &&
							!(valueFinal == null && suggestion.getText().contains(":")) &&
							!(valueFinal == null && firstKeyFinal && components && suggestion.getText().contains("{")) &&
							!(!nextTagAllowed && (suggestion.getText().contains(",") || suggestion.getText().contains("}"))) &&
							!(otherTags != null && otherTags.contains(suggestion.getText())))
					.map(suggestion -> {
						suggestion = shiftSuggestion(suggestion, -fieldStartFinal);
						if (firstKeyFinal && components) {
							if (suggestion.getText().endsWith("=")) {
								String newText = suggestion.getText().substring(0, suggestion.getText().length() - 1);
								if (otherTags.contains(newText) || otherTags.contains(MainUtil.addNamespace(newText)))
									return null;
								suggestion = new Suggestion(suggestion.getRange(), newText, suggestion.getTooltip());
							}
						}
						return suggestion;
					})
					.filter(suggestion -> suggestion != null)
					.collect(Collectors.toList());
			return new Suggestions(shiftRange(suggestions.getRange(), -fieldStartFinal), shiftedSuggestions);
		});
	}
	private String escapeKey(String key) {
		if (key.isEmpty() || StringNbtWriter.SIMPLE_NAME.matcher(key).matches())
			return key;
		return NbtString.escape(key);
	}
	private CompletableFuture<Suggestions> loadFromName(String name, String tag, boolean components) {
		return loadSuggestionsByName(name, tag, new SuggestionsBuilder(tag, 0));
	}
	
	public CompletableFuture<Suggestions> getSuggestions(LocalNBT nbt, List<String> path, String key, String value, int cursor, Collection<String> otherTags) {
		if (nbt instanceof LocalItem)
			return getSuggestions("item", nbt.getId(), nbt.getNBT(), path, key, value, cursor, otherTags);
		if (nbt instanceof LocalBlock)
			return getSuggestions("block", nbt.getId(), nbt.getNBT(), path, key, value, cursor, otherTags);
		if (nbt instanceof LocalEntity)
			return getSuggestions("entity", nbt.getId(), nbt.getNBT(), path, key, value, cursor, otherTags);
		return new SuggestionsBuilder("", 0).buildFuture();
	}
	public CompletableFuture<Suggestions> getSuggestions(LocalNBT nbt, List<String> path, String key, String value, int cursor) {
		return getSuggestions(nbt, path, key, value, cursor, null);
	}
	
}

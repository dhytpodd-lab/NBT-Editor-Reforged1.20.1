package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import java.lang.invoke.MethodType;
import java.util.function.Supplier;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.luneruniverse.minecraft.mod.nbteditor.util.TextUtil;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextInst {
	
	public static Text of(String msg) {
		return Text.of(msg);
	}
	public static EditableText literal(String msg) {
		return new EditableText(Version.<MutableText>newSwitch()
				.range("1.19.0", null, () -> Text.literal(msg))
				.range(null, "1.18.2", () -> Reflection.newInstance("net.minecraft.class_2585", new Class[] {String.class}, msg)) // new LiteralText(msg)
				.get());
	}
	public static EditableText translatable(String key, Object... args) {
		return new EditableText(Version.<MutableText>newSwitch()
				.range("1.19.0", null, () -> Text.translatable(key, args))
				.range(null, "1.18.2", () -> Reflection.newInstance("net.minecraft.class_2588", new Class[] {String.class, Object[].class}, key, args)) // new TranslatableText(key, args)
				.get());
	}
	
	public static EditableText copy(Text text) {
		return new EditableText(text.copy());
	}
	public static EditableText copyContentOnly(Text text) {
		return new EditableText(text.copyContentOnly());
	}
	
	public static EditableText bracketed(Text text) {
		return translatable("chat.square_brackets", text);
	}
	
	
	private static final Supplier<Class<?>> Text$Serialization =
			Reflection.getOptionalClass("net.minecraft.class_2561$class_2562");
	private static final Supplier<Reflection.MethodInvoker> Text$Serialization_fromJson =
			Reflection.getOptionalMethod(Text$Serialization, () -> "method_10877", () -> MethodType.methodType(MutableText.class, String.class));
	/**
	 * <strong>CONSIDER USING {@link TextUtil#fromJsonSafely(String)}</strong>
	 */
	public static Text fromJson(String json) {
		return Version.<Text>newSwitch()
				.range("1.19.0", null, () -> Text.Serializer.fromJson(json))
				.range(null, "1.18.2", () -> Text$Serialization_fromJson.get().invokeThrowable(JsonParseException.class, null, json))
				.get();
	}
	private static final Supplier<Reflection.MethodInvoker> Text$Serialization_toJsonString =
			Reflection.getOptionalMethod(Text$Serialization, () -> "method_10867", () -> MethodType.methodType(String.class, Text.class));
	public static String toJsonString(Text text) {
		return Version.<String>newSwitch()
				.range("1.19.0", null, () -> Text.Serializer.toJson(text))
				.range(null, "1.18.2", () -> Text$Serialization_toJsonString.get().invoke(null, text))
				.get();
	}
	private static final Supplier<Reflection.MethodInvoker> Text$Serialization_toJsonTree =
			Reflection.getOptionalMethod(Text$Serialization, () -> "method_10868", () -> MethodType.methodType(JsonElement.class, Text.class));
	public static JsonElement toJsonTree(Text text) {
		return Version.<JsonElement>newSwitch()
				.range("1.19.0", null, () -> Text.Serializer.toJsonTree(text))
				.range(null, "1.18.2", () -> Text$Serialization_toJsonTree.get().invoke(null, text))
				.get();
	}
	
}

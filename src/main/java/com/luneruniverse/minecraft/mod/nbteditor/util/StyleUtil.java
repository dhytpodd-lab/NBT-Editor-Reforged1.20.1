package com.luneruniverse.minecraft.mod.nbteditor.util;

import java.util.Objects;

import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalBlock;
import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalEntity;
import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalItem;
import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalNBT;

import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Formatting;

public class StyleUtil {
	
	public static final boolean SHADOW_COLOR_EXISTS = false;
	
	public static final Style RESET_STYLE = Style.EMPTY.withColor(Formatting.WHITE)
			.withBold(false).withItalic(false).withUnderline(false).withStrikethrough(false).withObfuscated(false);
	
	public static Style getBaseNameStyle(LocalNBT localNBT, boolean itemName) {
		Style baseNameStyle = Style.EMPTY;
		if (localNBT instanceof LocalItem item) {
			if (!itemName)
				baseNameStyle = baseNameStyle.withFormatting(Formatting.ITALIC);
			baseNameStyle = baseNameStyle.withFormatting(item.getEditableItem().getRarity().formatting);
		} else if (localNBT instanceof LocalBlock)
			;
		else if (localNBT instanceof LocalEntity)
			baseNameStyle = baseNameStyle.withFormatting(Formatting.WHITE);
		else
			throw new IllegalStateException("Cannot get base name style for " + localNBT.getClass().getName());
		
		return baseNameStyle;
	}
	
	public static final Style BASE_LORE_STYLE = Style.EMPTY.withFormatting(Formatting.ITALIC, Formatting.DARK_PURPLE);
	
	public static final Style BOOK_STYLE = Style.EMPTY.withFormatting(Formatting.BLACK);
	
	public static Boolean getBoldRaw(Style style) {
		return style.isBold();
	}
	public static Boolean getItalicRaw(Style style) {
		return style.isItalic();
	}
	public static Boolean getUnderlinedRaw(Style style) {
		return style.isUnderlined();
	}
	public static Boolean getStrikethroughRaw(Style style) {
		return style.isStrikethrough();
	}
	public static Boolean getObfuscatedRaw(Style style) {
		return style.isObfuscated();
	}
	public static Identifier getFontRaw(Style style) {
		return style.getFont();
	}
	
	public static boolean identical(Style a, Style b) {
		boolean output = Objects.equals(a.getColor(), b.getColor()) &&
				a.isBold() == b.isBold() &&
				a.isItalic() == b.isItalic() &&
				a.isUnderlined() == b.isUnderlined() &&
				a.isStrikethrough() == b.isStrikethrough() &&
				a.isObfuscated() == b.isObfuscated() &&
				Objects.equals(a.getClickEvent(), b.getClickEvent()) &&
				Objects.equals(a.getHoverEvent(), b.getHoverEvent()) &&
				Objects.equals(a.getInsertion(), b.getInsertion()) &&
				Objects.equals(getFontRaw(a), getFontRaw(b));
		
		return output;
	}
	
	public static boolean hasFormatting(Style style, Formatting formatting) {
		return identical(style, style.withFormatting(formatting));
	}
	
	public static boolean hasFormatting(Style style, Style base) {
		return !identical(style.withParent(base), base);
	}
	
	public static Style minus(Style style, Style base) {
		Style output = Style.EMPTY;
		Identifier font = getFontRaw(style);
		Identifier baseFont = getFontRaw(base);
		
		if (!Objects.equals(style.getColor(), base.getColor()))
			output = output.withColor(style.getColor());
		if (style.isBold() != base.isBold())
			output = output.withBold(style.isBold());
		if (style.isItalic() != base.isItalic())
			output = output.withItalic(style.isItalic());
		if (style.isUnderlined() != base.isUnderlined())
			output = output.withUnderline(style.isUnderlined());
		if (style.isStrikethrough() != base.isStrikethrough())
			output = output.withStrikethrough(style.isStrikethrough());
		if (style.isObfuscated() != base.isObfuscated())
			output = output.withObfuscated(style.isObfuscated());
		if (!Objects.equals(style.getClickEvent(), base.getClickEvent()))
			output = output.withClickEvent(style.getClickEvent());
		if (!Objects.equals(style.getHoverEvent(), base.getHoverEvent()))
			output = output.withHoverEvent(style.getHoverEvent());
		if (!Objects.equals(style.getInsertion(), base.getInsertion()))
			output = output.withInsertion(style.getInsertion());
		if (!Objects.equals(font, baseFont))
			output = output.withFont(font);
		
		return output;
	}
	
	public static Style minusFormatting(Style style, Style base, Formatting formatting) {
		if (formatting == Formatting.RESET)
			return base;
		if (formatting.isColor())
			return style.withColor(base.getColor());
		return switch (formatting) {
			case BOLD -> style.withBold(base.isBold());
			case ITALIC -> style.withItalic(base.isItalic());
			case UNDERLINE -> style.withUnderline(base.isUnderlined());
			case STRIKETHROUGH -> style.withStrikethrough(base.isStrikethrough());
			case OBFUSCATED -> style.withObfuscated(base.isObfuscated());
			default -> throw new IllegalArgumentException("Unknown formatting: " + formatting);
		};
	}
	
}

package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;

/**
 * A wrapper for MutableText, since it is changed from an interface (1.18) to a class (1.19)
 */
public class EditableText implements Text {
	
	private MutableText value;
	
	public EditableText(MutableText value) {
		this.value = value;
	}
	
	public MutableText getInternalValue() {
		return value;
	}
	
	private EditableText wrapMutable(MutableText output) {
		return output == value ? this : new EditableText(output);
	}
	
	// Text
	@Override
	public OrderedText asOrderedText() {
		return value.asOrderedText();
	}
	
	@Override
	public TextContent getContent() {
		return value.getContent();
	}
	
	@Override
	public List<Text> getSiblings() {
		return value.getSiblings();
	}
	
	@Override
	public Style getStyle() {
		return value.getStyle();
	}
	
	// 1.18 Text
	public String method_10851() { // asString
		return value.getString();
	}
	
	public MutableText method_27662() { // copy
		return value.copy();
	}
	
	public MutableText method_27661() { // shallowCopy
		return value.copyContentOnly();
	}
	
	public <T> Optional<T> method_27660(StringVisitable.StyledVisitor<T> visitor, Style style) { // visitSelf
		return value.visit(visitor, style);
	}
	
	public <T> Optional<T> method_27659(StringVisitable.Visitor<T> visitor) { // visitSelf
		return value.visit(visitor);
	}
	
	// Mutable Text
	public EditableText setStyle(Style style) {
		return wrapMutable(value.setStyle(style));
	}
	
	public EditableText append(String text) {
		return wrapMutable(value.append(text));
	}
	
	public EditableText append(Text text) {
		return wrapMutable(value.append(text));
	}
	
	public EditableText styled(UnaryOperator<Style> styleUpdater) {
		return wrapMutable(value.styled(styleUpdater));
	}
	
	public EditableText fillStyle(Style styleOverride) {
		return wrapMutable(value.fillStyle(styleOverride));
	}
	
	public EditableText formatted(Formatting... formattings) {
		return wrapMutable(value.formatted(formattings));
	}
	
	// Other
	@Override
	public boolean equals(Object obj) {
		return value.equals(obj instanceof EditableText text ? text.value : obj);
	}
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
}

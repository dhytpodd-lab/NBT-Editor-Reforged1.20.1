package com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.TextInst;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.TagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.HideFlagsNBTTagReference;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public enum HideFlag implements TagReference<Boolean, ItemStack> {
	ENCHANTMENTS(TextInst.translatable("nbteditor.hide_flags.enchantments"), 1),
	ATTRIBUTE_MODIFIERS(TextInst.translatable("nbteditor.hide_flags.attribute_modifiers"), 2),
	UNBREAKABLE(TextInst.translatable("nbteditor.hide_flags.unbreakable"), 4),
	CAN_BREAK(TextInst.translatable("nbteditor.hide_flags.can_destroy"), 8),
	CAN_PLACE_ON(TextInst.translatable("nbteditor.hide_flags.can_place_on"), 16),
	MISC(TextInst.translatable("nbteditor.hide_flags.misc"), 32),
	DYED_COLOR(TextInst.translatable("nbteditor.hide_flags.dyed_color"), 64),
	
	TOOLTIP(TextInst.translatable("nbteditor.hide_flags.tooltip"), -1),
	STORED_ENCHANTMENTS(TextInst.translatable("nbteditor.hide_flags.stored_enchantments"), -1),
	TRIM(TextInst.translatable("nbteditor.hide_flags.trim"), -1),
	JUKEBOX_PLAYABLE(TextInst.translatable("nbteditor.hide_flags.jukebox_playable"), -1);
	
	private static final TagReference<Boolean, ItemStack> NO_OP = new TagReference<>() {
		@Override
		public Boolean get(ItemStack object) {
			return false;
		}
		@Override
		public void set(ItemStack object, Boolean value) {
		}
	};
	
	private final Text text;
	private final int code;
	private final TagReference<Boolean, ItemStack> tagRef;
	
	private HideFlag(Text text, int code) {
		this.text = text;
		this.code = code;
		this.tagRef = (code > 0 ? new HideFlagsNBTTagReference(this) : createNoOp());
	}
	private static TagReference<Boolean, ItemStack> createNoOp() {
		return NO_OP;
	}
	
	public Text getText() {
		return text;
	}
	public boolean isInThisVersion() {
		return code > 0;
	}
	
	public boolean isEnabled(int code) {
		return (code & this.code) != 0;
	}
	public int set(int code, boolean enabled) {
		return enabled ? (code | this.code) : (code & ~this.code);
	}
	public int toggle(int code) {
		return (code & ~this.code) | (~code & this.code);
	}
	
	@Override
	public Boolean get(ItemStack object) {
		return tagRef.get(object);
	}
	@Override
	public void set(ItemStack object, Boolean value) {
		tagRef.set(object, value);
	}
}

package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.ItemTagReferences;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data.Enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MVEnchantments {
	
	public static final boolean DATA_PACK_ENCHANTMENTS = false;
	
	public static final Enchantment LOYALTY = Enchantments.LOYALTY;
	public static final Enchantment FIRE_ASPECT = Enchantments.FIRE_ASPECT;

	public static boolean isCursed(Enchantment enchant) {
		return enchant.isCursed();
	}
	
	public static void addEnchantment(ItemStack item, Enchantment enchant, int level) {
		Enchants enchants = ItemTagReferences.ENCHANTMENTS.get(item);
		enchants.addEnchant(enchant, level);
		ItemTagReferences.ENCHANTMENTS.set(item, enchants);
	}
	
	public static Text getEnchantmentName(Enchantment enchant) {
		Formatting color = (isCursed(enchant) ? Formatting.RED : Formatting.GRAY);
		EditableText output = TextInst.translatable(enchant.getTranslationKey());
		output.formatted(color);
		return output;
	}
	
}

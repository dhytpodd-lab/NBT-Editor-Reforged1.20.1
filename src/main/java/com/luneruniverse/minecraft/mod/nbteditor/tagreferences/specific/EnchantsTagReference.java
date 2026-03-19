package com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific;

import java.util.ArrayList;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.IdentifierInst;
import com.luneruniverse.minecraft.mod.nbteditor.multiversion.MVRegistry;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.NBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.TagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data.Enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class EnchantsTagReference implements TagReference<Enchants, ItemStack> {
	
	private static TagReference<Enchants, ItemStack> getEnchantsTagRef(String tag) {
		return TagReference.mapValue(Enchants::new, Enchants::getEnchants,
				TagReference.forItems(ArrayList::new, TagReference.forLists(element -> {
					if (!(element instanceof NbtCompound compound))
						return null;
					if (!compound.contains("id", NbtElement.STRING_TYPE))
						return null;
					Enchantment enchant = MVRegistry.getEnchantmentRegistry().get(IdentifierInst.of(compound.getString("id")));
					if (enchant == null)
						return null;
					int level = compound.getShort("lvl");
					if (level < 1)
						return null;
					return new Enchants.EnchantWithLevel(enchant, level);
				}, enchant -> {
					NbtCompound output = new NbtCompound();
					output.putString("id", MVRegistry.getEnchantmentRegistry().getId(enchant.enchant()).toString());
					output.putShort("lvl", (short) enchant.level());
					return output;
				}, new NBTTagReference<>(NbtList.class, tag))));
	}
	
	private static final TagReference<Enchants, ItemStack> ENCHANTMENTS = getEnchantsTagRef("Enchantments");
	private static final TagReference<Enchants, ItemStack> STORED_ENCHANTMENTS = getEnchantsTagRef("StoredEnchantments");
	
	public EnchantsTagReference() {
		
	}
	
	@Override
	public Enchants get(ItemStack object) {
		if (object.isOf(Items.ENCHANTED_BOOK))
			return STORED_ENCHANTMENTS.get(object);
		return ENCHANTMENTS.get(object);
	}
	
	@Override
	public void set(ItemStack object, Enchants value) {
		if (object.isOf(Items.ENCHANTED_BOOK))
			STORED_ENCHANTMENTS.set(object, value);
		else
			ENCHANTMENTS.set(object, value);
	}
	
}

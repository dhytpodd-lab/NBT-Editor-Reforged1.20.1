package com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.TagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data.CustomPotionContents;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.potion.PotionUtil;

public class CustomPotionContentsNBTTagReference implements TagReference<CustomPotionContents, ItemStack> {

	@Override
	public CustomPotionContents get(ItemStack object) {
		Integer color = null;
		if (object.manager$hasNbt()) {
			NbtCompound nbt = object.manager$getNbt();
			if (nbt.contains("CustomPotionColor", NbtElement.NUMBER_TYPE))
				color = nbt.getInt("CustomPotionColor");
		}
		List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(object);
		return new CustomPotionContents(Optional.ofNullable(color), effects);
	}

	@Override
	public void set(ItemStack object, CustomPotionContents value) {
		if (value.color().isEmpty()) {
			if (object.manager$hasNbt())
				object.manager$modifyNbt(nbt -> nbt.remove("CustomPotionColor"));
		} else
			object.manager$modifyNbt(nbt -> nbt.putInt("CustomPotionColor", value.color().get()));
		PotionUtil.setCustomPotionEffects(object, value.effects());
	}
	
}

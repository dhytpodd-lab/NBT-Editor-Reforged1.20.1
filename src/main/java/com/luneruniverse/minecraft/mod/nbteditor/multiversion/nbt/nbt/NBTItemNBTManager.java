package com.luneruniverse.minecraft.mod.nbteditor.multiversion.nbt.nbt;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.nbt.Attempt;
import com.luneruniverse.minecraft.mod.nbteditor.multiversion.nbt.DeserializableNBTManager;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NBTItemNBTManager implements DeserializableNBTManager<ItemStack> {
	
	@Override
	public Attempt<NbtCompound> trySerialize(ItemStack subject) {
		return new Attempt<>(subject.writeNbt(new NbtCompound()));
	}
	
	@Override
	public Attempt<ItemStack> tryDeserialize(NbtCompound nbt) {
		return new Attempt<>(ItemStack.fromNbt(nbt.copy()));
	}
	
	@Override
	public boolean hasNbt(ItemStack subject) {
		return subject.hasNbt();
	}
	
	@Override
	public NbtCompound getNbt(ItemStack subject) {
		NbtCompound nbt = subject.getNbt();
		return nbt == null ? null : nbt.copy();
	}
	
	@Override
	public NbtCompound getOrCreateNbt(ItemStack subject) {
		return subject.getOrCreateNbt().copy();
	}
	
	@Override
	public void setNbt(ItemStack subject, NbtCompound nbt) {
		subject.setNbt(nbt == null ? null : nbt.copy());
	}
	
}

package com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific;

import java.util.Optional;
import java.util.UUID;

import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.TagReference;
import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;

public class GameProfileNBTTagReference implements TagReference<Optional<GameProfile>, NbtCompound> {
	@Override
	public Optional<GameProfile> get(NbtCompound object) {
		if (object.contains("SkullOwner", NbtElement.STRING_TYPE))
			return Optional.of(new GameProfile(new UUID(0L, 0L), object.getString("SkullOwner")));
		if (object.contains("SkullOwner", NbtElement.COMPOUND_TYPE))
			return Optional.ofNullable(NbtHelper.toGameProfile(object.getCompound("SkullOwner")));
		return Optional.empty();
	}

	@Override
	public void set(NbtCompound object, Optional<GameProfile> value) {
		value.ifPresentOrElse(
				profile -> object.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), profile)),
				() -> object.remove("SkullOwner"));
	}
	
}

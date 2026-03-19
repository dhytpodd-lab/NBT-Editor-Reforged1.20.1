package com.luneruniverse.minecraft.mod.nbteditor.tagreferences;

import java.util.Optional;

import com.luneruniverse.minecraft.mod.nbteditor.localnbt.LocalBlock;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.TagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.GameProfileNBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.GameProfileNameNBTTagReference;
import com.mojang.authlib.GameProfile;

public class BlockTagReferences {
	
	public static final TagReference<Optional<String>, LocalBlock> PROFILE_NAME =
			TagReference.forLocalNBT(Optional::empty, new GameProfileNameNBTTagReference());
	public static final TagReference<Optional<GameProfile>, LocalBlock> PROFILE =
			TagReference.forLocalNBT(Optional::empty, new GameProfileNBTTagReference());
	
}

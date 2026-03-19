package com.luneruniverse.minecraft.mod.nbteditor.tagreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.NBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.TagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.AttributesNBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.CustomDataNBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.CustomPotionContentsNBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.EnchantsTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.GameProfileNBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.GameProfileNameNBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data.AttributeData;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data.CustomPotionContents;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data.Enchants;
import com.mojang.authlib.GameProfile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

public class ItemTagReferences {
	
	public static final TagReference<CustomPotionContents, ItemStack> CUSTOM_POTION_CONTENTS =
			new CustomPotionContentsNBTTagReference();
	
	public static final TagReference<Optional<String>, ItemStack> PROFILE_NAME =
			TagReference.forItems(Optional::empty, new GameProfileNameNBTTagReference());
	public static final TagReference<Optional<GameProfile>, ItemStack> PROFILE =
			TagReference.forItems(Optional::empty, new GameProfileNBTTagReference());
	
	public static final TagReference<List<AttributeData>, ItemStack> ATTRIBUTES =
			TagReference.forItems(ArrayList::new, new AttributesNBTTagReference(AttributesNBTTagReference.NBTLayout.ITEM_OLD));
	
	public static final TagReference<List<String>, ItemStack> WRITABLE_BOOK_PAGES =
			TagReference.forItems(ArrayList::new, TagReference.forLists(String.class, new NBTTagReference<>(String[].class, "pages")));
	
	public static final TagReference<Boolean, ItemStack> UNBREAKABLE =
			TagReference.forItems(() -> false, new NBTTagReference<>(Boolean.class, "Unbreakable"));
	
	public static final TagReference<NbtCompound, ItemStack> CUSTOM_DATA = new CustomDataNBTTagReference();
	
	public static final TagReference<Map<String, String>, ItemStack> BLOCK_STATE =
			TagReference.forItems(HashMap::new, TagReference.forMaps(
					element -> element instanceof NbtString str ? str.asString() : null,
					NbtString::of,
					new NBTTagReference<>(NbtCompound.class, "BlockStateTag")));
	
	public static final TagReference<NbtCompound, ItemStack> BLOCK_ENTITY_DATA =
			TagReference.forItems(NbtCompound::new, new NBTTagReference<>(NbtCompound.class, "BlockEntityTag"));
	
	public static final TagReference<NbtCompound, ItemStack> ENTITY_DATA =
			TagReference.forItems(NbtCompound::new, new NBTTagReference<>(NbtCompound.class, "EntityTag"));
	
	public static final TagReference<Enchants, ItemStack> ENCHANTMENTS = new EnchantsTagReference();
	
	public static final TagReference<List<Text>, ItemStack> LORE =
			TagReference.forItems(ArrayList::new, TagReference.forLists(Text.class, new NBTTagReference<>(Text[].class, "display/Lore")));
	
}

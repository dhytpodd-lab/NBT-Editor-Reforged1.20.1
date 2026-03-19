package com.luneruniverse.minecraft.mod.nbteditor.tagreferences;

import java.util.ArrayList;
import java.util.List;

import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.NBTTagReference;
import com.luneruniverse.minecraft.mod.nbteditor.tagreferences.general.TagReference;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class WrittenBookTagReferences {
	
	public static final TagReference<String, ItemStack> TITLE =
			TagReference.forItems(() -> "", TagReference.alsoRemove("filtered_title",
					new NBTTagReference<>(String.class, "title")));
	
	public static final TagReference<String, ItemStack> AUTHOR =
			TagReference.forItems(() -> "", new NBTTagReference<>(String.class, "author"));
	
	public static final TagReference<Integer, ItemStack> GENERATION =
			TagReference.forItems(() -> 0, new NBTTagReference<>(Integer.class, "generation"));
	
	public static final TagReference<List<Text>, ItemStack> PAGES =
			TagReference.forItems(ArrayList::new, TagReference.alsoRemove("filtered_pages",
					TagReference.forLists(Text.class, new NBTTagReference<>(Text[].class, "pages"))));
	
}

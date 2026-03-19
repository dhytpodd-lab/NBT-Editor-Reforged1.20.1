package com.luneruniverse.minecraft.mod.nbteditor.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public abstract class BasicMixinPlugin implements IMixinConfigPlugin {
	
	private String mixinPackage;
	
	@Override
	public void onLoad(String mixinPackage) {
		this.mixinPackage = mixinPackage;
	}
	
	@Override
	public String getRefMapperConfig() {
		return null;
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return true;
	}
	
	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
		
	}
	
	@Override
	public List<String> getMixins() {
		List<String> output = new ArrayList<>();
		addMixins(output);
		output.removeIf(mixin -> !mixinExists(mixin));
		if (output.isEmpty())
			return null;
		return output;
	}
	private boolean mixinExists(String mixin) {
		if (mixinPackage == null)
			return true;
		String path = mixinPackage.replace('.', '/') + "/" + mixin.replace('.', '/') + ".class";
		return BasicMixinPlugin.class.getClassLoader().getResource(path) != null;
	}
	public abstract void addMixins(List<String> output);
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		
	}
	
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		
	}
	
}

package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class MVRegistry<T> implements Iterable<T> {

	public static final MVRegistry<? extends Registry<?>> REGISTRIES = new MVRegistry<>(Registries.REGISTRIES);
	public static final MVRegistry<ScreenHandlerType<?>> SCREEN_HANDLER = new MVRegistry<>(Registries.SCREEN_HANDLER);
	public static final MVRegistry<Item> ITEM = new MVRegistry<>(Registries.ITEM);
	public static final MVRegistry<Block> BLOCK = new MVRegistry<>(Registries.BLOCK);
	public static final MVRegistry<EntityType<?>> ENTITY_TYPE = new MVRegistry<>(Registries.ENTITY_TYPE);
	public static final MVRegistry<EntityAttribute> ATTRIBUTE = new MVRegistry<>(Registries.ATTRIBUTE);
	public static final MVRegistry<Potion> POTION = new MVRegistry<>(Registries.POTION);
	public static final MVRegistry<StatusEffect> STATUS_EFFECT = new MVRegistry<>(Registries.STATUS_EFFECT);
	private static final MVRegistry<Enchantment> ENCHANTMENT = new MVRegistry<>(Registries.ENCHANTMENT);

	public static MVRegistry<Enchantment> getEnchantmentRegistry() {
		return ENCHANTMENT;
	}

	public static <V, T extends V> T register(MVRegistry<V> registry, Identifier id, T entry) {
		return Registry.register(registry.getInternalValue(), id, entry);
	}


	private final Registry<T> value;

	private MVRegistry(Registry<T> value) {
		this.value = value;
	}

	public Registry<T> getInternalValue() {
		return value;
	}

	@Override
	public Iterator<T> iterator() {
		return value.iterator();
	}

	public Optional<T> getOrEmpty(Identifier id) {
		return value.getOrEmpty(id);
	}

	public Identifier getId(T entry) {
		return value.getId(entry);
	}

	public T get(Identifier id) {
		return value.get(id);
	}

	public Set<Identifier> getIds() {
		return value.getIds();
	}

	public Set<Map.Entry<Identifier, T>> getEntrySet() {
		return value.getEntrySet().stream()
				.map(entry -> Map.entry(((RegistryKey<T>) entry.getKey()).getValue(), entry.getValue()))
				.collect(Collectors.toUnmodifiableSet());
	}

	public boolean containsId(Identifier id) {
		return value.containsId(id);
	}

}

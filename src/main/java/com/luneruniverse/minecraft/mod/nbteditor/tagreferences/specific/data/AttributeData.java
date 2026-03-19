package com.luneruniverse.minecraft.mod.nbteditor.tagreferences.specific.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.TextInst;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.text.Text;

public record AttributeData(EntityAttribute attribute, double value, Optional<AttributeModifierData> modifierData) {
	
	public static record AttributeModifierData(Operation operation, Slot slot, AttributeModifierId id) {
		
		public enum Operation {
			ADD("nbteditor.attributes.operation.add"),
			ADD_MULTIPLIED_BASE("nbteditor.attributes.operation.add_multiplied_base"),
			ADD_MULTIPLIED_TOTAL("nbteditor.attributes.operation.add_multiplied_total");
			
			private final Text name;
			private Operation(String key) {
				this.name = TextInst.translatable(key);
			}
			public static Operation fromMinecraft(EntityAttributeModifier.Operation operation) {
				return values()[operation.ordinal()];
			}
			public EntityAttributeModifier.Operation toMinecraft() {
				return EntityAttributeModifier.Operation.values()[ordinal()];
			}
			@Override
			public String toString() {
				return name.getString();
			}
		}
		
		public enum Slot {
			ANY("nbteditor.attributes.slot.any", false),
			HAND("nbteditor.attributes.slot.hand", true),
			MAINHAND("nbteditor.attributes.slot.mainhand", false),
			OFFHAND("nbteditor.attributes.slot.offhand", false),
			ARMOR("nbteditor.attributes.slot.armor", true),
			HEAD("nbteditor.attributes.slot.head", false),
			CHEST("nbteditor.attributes.slot.chest", false),
			LEGS("nbteditor.attributes.slot.legs", false),
			FEET("nbteditor.attributes.slot.feet", false),
			BODY("nbteditor.attributes.slot.body", true);
			
			public static List<Slot> getNotOnlyForComponents() {
				return Arrays.stream(values()).filter(slot -> !slot.isOnlyForComponents()).toList();
			}
			
			private final Text name;
			private final boolean onlyForComponents;
			private Slot(String key, boolean onlyForComponents) {
				this.name = TextInst.translatable(key);
				this.onlyForComponents = onlyForComponents;
			}
			public boolean isOnlyForComponents() {
				return onlyForComponents;
			}
			public boolean isInThisVersion() {
				return !onlyForComponents;
			}
			@Override
			public String toString() {
				return name.getString();
			}
		}
		
		public static class AttributeModifierId {
			
			public static final boolean ID_IS_IDENTIFIER = false;
			
			public static AttributeModifierId randomUUID() {
				return new AttributeModifierId(UUID.randomUUID());
			}
			
			private final UUID id;
			
			public AttributeModifierId(UUID id) {
				this.id = id;
			}
			
			public UUID getUUID() {
				return id;
			}
			
			public EntityAttributeModifier toMinecraft(String name, double value, EntityAttributeModifier.Operation operation) {
				return new EntityAttributeModifier(id, name, value, operation);
			}
			
		}
		
	}
	
	public AttributeData(EntityAttribute attribute, double value) {
		this(attribute, value, Optional.empty());
	}
	public AttributeData(EntityAttribute attribute, double value,
			AttributeModifierData.Operation operation,
			AttributeModifierData.Slot slot,
			AttributeModifierData.AttributeModifierId id) {
		this(attribute, value, Optional.of(new AttributeModifierData(operation, slot, id)));
	}
	
}

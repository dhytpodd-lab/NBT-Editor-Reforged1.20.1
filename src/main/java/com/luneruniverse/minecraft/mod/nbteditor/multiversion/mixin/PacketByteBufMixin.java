package com.luneruniverse.minecraft.mod.nbteditor.multiversion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.luneruniverse.minecraft.mod.nbteditor.multiversion.MVPacketByteBufParent;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;

@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin implements MVPacketByteBufParent {
	
	@Shadow
	private ByteBuf parent;
	
	@Override
	public PacketByteBuf writeBoolean(boolean value) {
		parent.writeBoolean(value);
		return (PacketByteBuf) (Object) this;
	}
	
	@Override
	public PacketByteBuf writeDouble(double value) {
		parent.writeDouble(value);
		return (PacketByteBuf) (Object) this;
	}
	
	@Override
	public <T> RegistryKey<T> readRegistryKey(RegistryKey<? extends Registry<T>> registryRef) {
		return RegistryKey.of(registryRef, ((PacketByteBuf) (Object) this).readIdentifier());
	}
	@Override
	public void writeRegistryKey(RegistryKey<?> key) {
		((PacketByteBuf) (Object) this).writeIdentifier(key.getValue());
	}
	
	@Override
	public PacketByteBuf writeNbtCompound(NbtCompound element) {
		return ((PacketByteBuf) (Object) this).writeNbt(element);
	}
	
	@Override
	public Vec3d readVec3d() {
		PacketByteBuf payload = (PacketByteBuf) (Object) this;
		return new Vec3d(payload.readDouble(), payload.readDouble(), payload.readDouble());
	}
	@Override
	public void writeVec3d(Vec3d vector) {
		PacketByteBuf payload = (PacketByteBuf) (Object) this;
		payload.writeDouble(vector.getX());
		payload.writeDouble(vector.getY());
		payload.writeDouble(vector.getZ());
	}
	
}

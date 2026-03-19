package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.client.render.VertexFormats;

public class MVShaders {
	
	private static VertexFormatElement getElement(String oldElement) {
		return Reflection.getField(VertexFormats.class, oldElement, "Lnet/minecraft/class_296;").get(null);
	}
	
	public static record MVShaderProgramKey(String name, VertexFormat vertexFormat, Object mcKey) {
		public MVShaderProgramKey(String name, VertexFormat vertexFormat) {
			this(name, vertexFormat, null);
		}
	}
	public static class MVShaderProgram {
		public final MVShaderProgramKey key;
		public ShaderProgram shader;
		public MVShaderProgram(MVShaderProgramKey key) {
			this.key = key;
		}
	}
	public static record MVShaderAndLayer(MVShaderProgram shader, RenderLayer layer) {}
	
	public static final VertexFormatElement POSITION_ELEMENT = getElement("field_1587");
	public static final VertexFormatElement TEXTURE_ELEMENT = getElement("field_1591");
	public static final VertexFormatElement LIGHT_ELEMENT = getElement("field_20886");
	
	public static VertexFormat createFormat(Consumer<ImmutableMap.Builder<String, VertexFormatElement>> builderConsumer) {
		ImmutableMap.Builder<String, VertexFormatElement> mapBuilder = ImmutableMap.builder();
		builderConsumer.accept(mapBuilder);
		ImmutableMap<String, VertexFormatElement> map = mapBuilder.build();
		
		return Reflection.newInstance(VertexFormat.class, new Class<?>[] {ImmutableMap.class}, map);
	}
	
	public static RenderPhase.ShaderProgram newRenderPhaseShaderProgram(MVShaderProgram shader) {
		return Reflection.newInstance(RenderPhase.ShaderProgram.class, new Class<?>[] {Supplier.class}, (Supplier<ShaderProgram>) () -> shader.shader);
	}
	
}

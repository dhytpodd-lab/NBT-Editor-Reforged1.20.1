package com.luneruniverse.minecraft.mod.nbteditor.multiversion;

import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;

public class IdentifierInst {
	
	public static Identifier of(String id) throws InvalidIdentifierException {
		return new Identifier(id);
	}
	public static Identifier of(String namespace, String path) throws InvalidIdentifierException {
		return new Identifier(namespace, path);
	}
	
	public static boolean isValid(String id) {
		try {
			of(id);
			return true;
		} catch (InvalidIdentifierException e) {
			return false;
		}
	}
	public static boolean isValid(String namespace, String path) {
		try {
			of(namespace, path);
			return true;
		} catch (InvalidIdentifierException e) {
			return false;
		}
	}
	
}

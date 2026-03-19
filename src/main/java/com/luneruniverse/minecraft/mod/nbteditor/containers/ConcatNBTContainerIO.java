package com.luneruniverse.minecraft.mod.nbteditor.containers;

import java.util.Objects;

public class ConcatNBTContainerIO extends ConcatNonItemNBTContainerIO implements NBTContainerIO {
	
	private final NBTContainerIO[] nbtIOs;
	
	public ConcatNBTContainerIO(NBTContainerIO... nbtIOs) {
		super(nbtIOs);
		this.nbtIOs = nbtIOs;
	}
	
	@Override
	public String getDefaultEntityId() {
		if (nbtIOs.length == 0)
			return null;
		String output = nbtIOs[0].getDefaultEntityId();
		for (int i = 1; i < nbtIOs.length; i++) {
			if (!Objects.equals(output, nbtIOs[i].getDefaultEntityId()))
				throw new IllegalStateException("Concated containers disagree on the default entity id!");
		}
		return output;
	}
	
}

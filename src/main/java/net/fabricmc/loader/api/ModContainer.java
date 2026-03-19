package net.fabricmc.loader.api;

public interface ModContainer {

	Metadata getMetadata();

	interface Metadata {
		String getId();
		Version getVersion();
	}

}

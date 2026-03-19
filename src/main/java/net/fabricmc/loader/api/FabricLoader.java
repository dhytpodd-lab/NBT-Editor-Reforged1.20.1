package net.fabricmc.loader.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.IModInfo;

public final class FabricLoader {

	private static final FabricLoader INSTANCE = new FabricLoader();
	private static final MappingResolver MAPPINGS = new IdentityMappingResolver();

	private FabricLoader() {
	}

	public static FabricLoader getInstance() {
		return INSTANCE;
	}

	public EnvType getEnvironmentType() {
		return FMLEnvironment.dist.isClient() ? EnvType.CLIENT : EnvType.SERVER;
	}

	public MappingResolver getMappingResolver() {
		return MAPPINGS;
	}

	public List<ModContainer> getAllMods() {
		return ModList.get().getMods().stream().map(ForgeModContainer::new).collect(Collectors.toList());
	}

	public Optional<ModContainer> getModContainer(String modId) {
		return ModList.get().getMods().stream()
				.filter(mod -> mod.getModId().equals(modId))
				.findFirst()
				.map(ForgeModContainer::new);
	}

	public <T> List<EntrypointContainer<T>> getEntrypointContainers(String key, Class<T> type) {
		return List.of();
	}

	private static final class IdentityMappingResolver implements MappingResolver {
		@Override
		public String mapClassName(String from, String className) {
			return className;
		}

		@Override
		public String unmapClassName(String from, String className) {
			return className;
		}

		@Override
		public String mapFieldName(String from, String owner, String name, String descriptor) {
			return name;
		}

		@Override
		public String mapMethodName(String from, String owner, String name, String descriptor) {
			return name;
		}
	}

	private static final class ForgeModContainer implements ModContainer {
		private final IModInfo modInfo;

		private ForgeModContainer(IModInfo modInfo) {
			this.modInfo = modInfo;
		}

		@Override
		public Metadata getMetadata() {
			return new ForgeMetadata(modInfo);
		}
	}

	private static final class ForgeMetadata implements ModContainer.Metadata {
		private final IModInfo modInfo;

		private ForgeMetadata(IModInfo modInfo) {
			this.modInfo = modInfo;
		}

		@Override
		public String getId() {
			return modInfo.getModId();
		}

		@Override
		public Version getVersion() {
			return () -> modInfo.getVersion().toString();
		}
	}

}

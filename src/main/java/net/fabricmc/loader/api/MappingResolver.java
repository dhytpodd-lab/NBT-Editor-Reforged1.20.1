package net.fabricmc.loader.api;

public interface MappingResolver {

	String mapClassName(String from, String className);

	String unmapClassName(String from, String className);

	String mapFieldName(String from, String owner, String name, String descriptor);

	String mapMethodName(String from, String owner, String name, String descriptor);

}

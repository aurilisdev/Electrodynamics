package physica.core.common.block.property;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public class PropertySingle<T extends Enum<T> & IStringSerializable> extends PropertyEnum<T> {
	protected PropertySingle(String name, Collection<T> values, Class<T> enumClass) {
		super(name, enumClass, values);
	}

	/**
	 * Create a new PropertySingle with the given name
	 */
	public static <T extends Enum<T> & IStringSerializable> PropertySingle<T> createProperty(String name,
			Class<T> clasz) {
		return createProperty(name, Predicates.alwaysTrue(), clasz);
	}

	/**
	 * Create a new PropertySingle with all enums that match the given Predicate
	 */
	public static <T extends Enum<T> & IStringSerializable> PropertySingle<T> createProperty(String name,
			Predicate<T> filter, Class<T> clasz) {
		return createProperty(name, Collections2.filter(Lists.newArrayList(clasz.getEnumConstants()), filter), clasz);
	}

	/**
	 * Create a new PropertyDirection for the given direction values
	 */
	public static <T extends Enum<T> & IStringSerializable> PropertySingle<T> createProperty(String name,
			Collection<T> values, Class<T> clasz) {
		return new PropertySingle<>(name, values, clasz);
	}
}
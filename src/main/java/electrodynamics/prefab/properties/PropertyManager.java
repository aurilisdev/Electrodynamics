package electrodynamics.prefab.properties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * A wrapper class designed to react when properties change and take according action
 * 
 * @author AurilisDev
 * @author skip999
 *
 */
public class PropertyManager {
	
	public static final ConcurrentHashMap<ResourceLocation, IPropertyType> REGISTERED_PROPERTIES = new ConcurrentHashMap<>();
	
	public static void registerProperties(Map<ResourceLocation, IPropertyType> propeties) {
		REGISTERED_PROPERTIES.clear();
		REGISTERED_PROPERTIES.putAll(propeties);
	}

	private final BlockEntity owner;

	private ArrayList<Property<?>> properties = new ArrayList<>();

	private HashSet<PropertyWrapper> dirtyProperties = new HashSet<>();

	private boolean isDirty = false;

	public PropertyManager(BlockEntity owner) {
		this.owner = owner;
	}

	public <T> Property<T> addProperty(Property<T> prop) {
		properties.add(prop);
		prop.setManager(this);
		prop.setIndex(properties.size() - 1);
		return prop;
	}

	public ArrayList<Property<?>> getProperties() {
		return properties;
	}

	public HashSet<PropertyWrapper> getClientUpdateProperties() {

		return dirtyProperties;

	}

	public void clean() {
		isDirty = false;
		dirtyProperties.forEach(wrapper -> {
			wrapper.property.clean();
		});
		dirtyProperties.clear();
	}

	public void update(int indexOf, Object value) {
		properties.get(indexOf).set(value);
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(Property<?> dirtyProp) {
		isDirty = true;
		dirtyProperties.add(new PropertyWrapper(dirtyProp.getIndex(), dirtyProp.getType(), dirtyProp.get(), dirtyProp));
	}

	@Override
	public String toString() {
		String string = "";
		for (int i = 0; i < properties.size(); i++) {
			string = string + i + ": " + properties.get(i).toString() + "\n";
		}
		return string;
	}

	public BlockEntity getOwner() {
		return owner;
	}

	public static record PropertyWrapper(int index, IPropertyType type, Object value, @Nullable Property<?> property) {

	}
}

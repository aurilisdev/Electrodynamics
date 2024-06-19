package electrodynamics.common.eventbus;

import java.util.HashMap;

import electrodynamics.prefab.properties.IPropertyType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

public class RegisterPropertiesEvent extends Event implements IModBusEvent {

	private final HashMap<ResourceLocation, IPropertyType> propertiesMap = new HashMap<>();

	public void registerProperty(IPropertyType type) {
		if (propertiesMap.containsKey(type.getId())) {
			throw new UnsupportedOperationException("A property with the ID " + type.getId().toString() + " already exists");
		}
		propertiesMap.put(type.getId(), type);
	}

	public HashMap<ResourceLocation, IPropertyType> getRegisteredProperties() {
		return propertiesMap;
	}

}

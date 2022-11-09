package electrodynamics.prefab.tile.components.type;

import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;

public class ComponentName implements Component {
	protected boolean translation;
	protected String name = "";

	public ComponentName(String name) {
		this.name = name;
	}

	public ComponentName translation(boolean value) {
		translation = value;
		return this;
	}

	public net.minecraft.network.chat.Component getName() {
		return translation ? net.minecraft.network.chat.Component.translatable(name) : net.minecraft.network.chat.Component.literal(name);
	}

	@Override
	public ComponentType getType() {
		return ComponentType.Name;
	}
}

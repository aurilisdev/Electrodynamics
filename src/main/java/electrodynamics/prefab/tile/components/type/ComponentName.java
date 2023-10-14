package electrodynamics.prefab.tile.components.type;

import electrodynamics.prefab.tile.components.IComponent;
import electrodynamics.prefab.tile.components.IComponentType;

public class ComponentName implements IComponent {
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
	public IComponentType getType() {
		return IComponentType.Name;
	}
}

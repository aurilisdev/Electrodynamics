package electrodynamics.prefab.tile.components.type;

import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

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
		return translation ? new TranslatableComponent(name) : new TextComponent(name);
	}

	@Override
	public ComponentType getType() {
		return ComponentType.Name;
	}
}

package electrodynamics.prefab.tile.components.type;

import electrodynamics.prefab.tile.components.IComponent;
import electrodynamics.prefab.tile.components.IComponentType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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

	public ITextComponent getName() {
		return translation ? new TranslationTextComponent(name) : new StringTextComponent(name);
	}

	@Override
	public IComponentType getType() {
		return IComponentType.Name;
	}
}

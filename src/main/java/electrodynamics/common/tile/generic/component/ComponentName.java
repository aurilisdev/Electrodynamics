package electrodynamics.common.tile.generic.component;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ComponentName implements Component {
    private boolean translation;
    private String name = "";

    public ComponentName(String name) {
	this.name = name;
    }

    public ComponentName setTranslation(boolean value) {
	translation = value;
	return this;
    }

    public ITextComponent getName() {
	return translation ? new TranslationTextComponent(name) : new StringTextComponent(name);
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Name;
    }
}

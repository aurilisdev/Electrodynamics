package electrodynamics.api.screen.component;

import net.minecraft.util.text.IFormattableTextComponent;

@FunctionalInterface
public interface TextSupplier {

	IFormattableTextComponent getText();
}
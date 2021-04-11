package electrodynamics.api.screen.component;

import net.minecraft.util.text.ITextComponent;

@FunctionalInterface
public interface TextSupplier {
    ITextComponent getText();
}
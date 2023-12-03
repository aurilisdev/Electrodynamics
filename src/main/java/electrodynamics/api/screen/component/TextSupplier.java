package electrodynamics.api.screen.component;

import net.minecraft.network.chat.MutableComponent;

@FunctionalInterface
public interface TextSupplier {
	MutableComponent getText();
}
package electrodynamics.api.screen.component;

import net.minecraft.network.chat.Component;

@FunctionalInterface
public interface TextSupplier {
	Component getText();
}
package electrodynamics.api.screen.component;

import java.util.List;

import net.minecraft.util.IReorderingProcessor;

@FunctionalInterface
public interface TextPropertySupplier {
	List<? extends IReorderingProcessor> getInfo();
}
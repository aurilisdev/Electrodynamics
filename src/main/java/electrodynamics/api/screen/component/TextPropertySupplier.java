package electrodynamics.api.screen.component;

import java.util.List;

import net.minecraft.util.FormattedCharSequence;

@FunctionalInterface
public interface TextPropertySupplier {
	List<? extends FormattedCharSequence> getInfo();
}
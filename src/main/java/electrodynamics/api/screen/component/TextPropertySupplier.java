package electrodynamics.api.screen.component;

import java.util.List;

import net.minecraft.network.chat.FormattedText;

@FunctionalInterface
public interface TextPropertySupplier {
    List<? extends FormattedText> getInfo();
}
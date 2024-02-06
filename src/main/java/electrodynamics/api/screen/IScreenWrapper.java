package electrodynamics.api.screen;

import net.minecraft.client.gui.Font;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IScreenWrapper {

	double getGuiWidth();

	double getGuiHeight();

	Font getFontRenderer();

}
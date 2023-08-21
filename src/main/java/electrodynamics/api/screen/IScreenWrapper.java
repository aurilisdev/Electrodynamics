package electrodynamics.api.screen;

import net.minecraft.client.gui.Font;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IScreenWrapper {

	double getGuiWidth();

	double getGuiHeight();

	Font getFontRenderer();

}
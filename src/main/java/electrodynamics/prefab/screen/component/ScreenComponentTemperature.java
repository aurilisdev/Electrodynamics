package electrodynamics.prefab.screen.component;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentTemperature extends ScreenComponentGuiTab {
	
	//this could be condensed to a ScreenComponentGuiTab class, but is left for future expansion purposes
	public ScreenComponentTemperature(final TextPropertySupplier infoHandler, final IScreenWrapper gui, final int x, final int y) {
		super(GuiInfoTabTextures.TEMPERATURE, infoHandler, gui, x, y);
	}

}
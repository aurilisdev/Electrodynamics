package electrodynamics.prefab.screen.component;

import java.util.List;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ScreenComponentWater extends ScreenComponentInfo {

	public ScreenComponentWater(TextPropertySupplier infoHandler, IScreenWrapper gui, int x, int y) {
		super(infoHandler, new ResourceLocation(References.ID, "textures/screen/component/water.png"), gui, x, y);
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		return infoHandler.getInfo();
	}

}

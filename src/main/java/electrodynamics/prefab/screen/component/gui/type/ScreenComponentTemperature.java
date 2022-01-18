package electrodynamics.prefab.screen.component.gui.type;

import java.util.List;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.screen.component.gui.ScreenComponentInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentTemperature extends ScreenComponentInfo {
	public ScreenComponentTemperature(final TextPropertySupplier infoHandler, final IScreenWrapper gui, final int x, final int y) {
		super(infoHandler, new ResourceLocation(References.ID + ":textures/screen/component/temperature.png"), gui, x, y);
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		return list;
	}
}
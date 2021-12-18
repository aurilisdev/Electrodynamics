package electrodynamics.prefab.screen.component;

import java.util.List;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentElectricInfo extends ScreenComponentInfo {
	public ScreenComponentElectricInfo(final TextPropertySupplier infoHandler, final IScreenWrapper gui, final int x, final int y) {
		super(infoHandler, new ResourceLocation(References.ID + ":textures/screen/component/electric.png"), gui, x, y);
	}

	@Override
	protected List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list) {
		return list;
	}
}
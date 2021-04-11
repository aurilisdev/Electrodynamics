package electrodynamics.prefab.screen.component;

import java.util.List;

import electrodynamics.api.References;
import electrodynamics.api.screen.IGuiWrapper;
import electrodynamics.api.screen.component.TextPropertySupplier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentTemperature extends ScreenComponentInfo {
    public ScreenComponentTemperature(final TextPropertySupplier infoHandler, final IGuiWrapper gui, final int x, final int y) {
	super(infoHandler, new ResourceLocation(References.ID + ":textures/gui/component/temperature.png"), gui, x, y);
    }

    @Override
    protected List<? extends ITextProperties> getInfo(List<? extends ITextProperties> list) {
	return list;
    }
}
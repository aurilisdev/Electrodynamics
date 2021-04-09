package electrodynamics.api.gui.component;

import java.util.List;

import electrodynamics.api.References;
import electrodynamics.api.gui.IGuiWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiComponentElectricInfo extends GuiComponentInfo {
    public GuiComponentElectricInfo(final TextPropertySupplier infoHandler, final IGuiWrapper gui, final int x, final int y) {
	super(infoHandler, new ResourceLocation(References.ID + ":textures/gui/component/electric.png"), gui, x, y);
    }

    @Override
    protected List<? extends ITextProperties> getInfo(List<? extends ITextProperties> list) {
	return list;
    }
}
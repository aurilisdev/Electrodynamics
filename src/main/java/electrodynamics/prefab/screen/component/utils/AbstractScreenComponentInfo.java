package electrodynamics.prefab.screen.component.utils;

import java.util.Collections;
import java.util.List;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractScreenComponentInfo extends ScreenComponentGeneric {
	public static final int SIZE = 26;
	protected TextPropertySupplier infoHandler;

	public static final TextPropertySupplier EMPTY = Collections::emptyList;

	public AbstractScreenComponentInfo(ITexture texture, TextPropertySupplier infoHandler, int x, int y) {
		super(texture, x, y);
		this.infoHandler = infoHandler;
	}

	@Override
	public void renderForeground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (isHovered()) {
			graphics.renderTooltip(gui.getFontRenderer(), getInfo(infoHandler.getInfo()), xAxis, yAxis);
		}
	}

	protected abstract List<? extends FormattedCharSequence> getInfo(List<? extends FormattedCharSequence> list);

}
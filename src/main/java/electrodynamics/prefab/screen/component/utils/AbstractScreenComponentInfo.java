package electrodynamics.prefab.screen.component.utils;

import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.TextPropertySupplier;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import net.minecraft.util.IReorderingProcessor;
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
	public void renderForeground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (isHovered()) {
			
			gui.displayTooltips(stack, getInfo(infoHandler.getInfo()), xAxis, yAxis);
		
		}
	}

	protected abstract List<? extends IReorderingProcessor> getInfo(List<? extends IReorderingProcessor> list);

}
package electrodynamics.api.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IScreenWrapper {

	double getGuiWidth();

	double getGuiHeight();

	FontRenderer getFontRenderer();

	public void displayTooltips(MatrixStack stack, List<? extends IReorderingProcessor> tooltips, int mouseX, int mouseY);

	public void displayTooltips(MatrixStack stack, List<? extends IReorderingProcessor> lines, int x, int y, FontRenderer font);
	
	default public void displayTooltip(MatrixStack stack, IReorderingProcessor tooltip, int mouseX, int mouseY) {
		displayTooltips(stack, Lists.newArrayList(tooltip), mouseX, mouseY);
	}

	default public void displayTooltip(MatrixStack stack, IReorderingProcessor line, int x, int y, FontRenderer font) {
		displayTooltips(stack, Lists.newArrayList(line), x, y, font);
	}

}
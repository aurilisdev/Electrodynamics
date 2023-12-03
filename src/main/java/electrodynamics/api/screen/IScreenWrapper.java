package electrodynamics.api.screen;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IScreenWrapper {

	double getGuiWidth();

	double getGuiHeight();

	Font getFontRenderer();

	public void displayTooltips(PoseStack stack, List<? extends FormattedCharSequence> tooltips, int mouseX, int mouseY);

	public void displayTooltips(PoseStack stack, List<? extends FormattedCharSequence> lines, int x, int y, Font font);
	
	default public void displayTooltip(PoseStack stack, FormattedCharSequence tooltip, int mouseX, int mouseY) {
		displayTooltips(stack, List.of(tooltip), mouseX, mouseY);
	}

	default public void displayTooltip(PoseStack stack, FormattedCharSequence line, int x, int y, Font font) {
		displayTooltips(stack, List.of(line), x, y, font);
	}

}
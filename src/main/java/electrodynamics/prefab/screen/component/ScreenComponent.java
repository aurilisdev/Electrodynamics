package electrodynamics.prefab.screen.component;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.IGuiComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ScreenComponent implements IGuiComponent {
    protected ResourceLocation resource;
    protected IScreenWrapper gui;
    protected int xLocation;
    protected int yLocation;

    protected ScreenComponent(ResourceLocation resource, IScreenWrapper gui, int x, int y) {
	this.resource = resource;
	this.gui = gui;

	xLocation = x;
	yLocation = y;
    }

    public void displayTooltip(MatrixStack stack, ITextComponent tooltip, int xAxis, int yAxis) {
	gui.displayTooltip(stack, tooltip, xAxis, yAxis);
    }

    public void displayTooltips(MatrixStack stack, List<? extends ITextProperties> tooltips, int xAxis, int yAxis) {
	gui.displayTooltips(stack, tooltips, xAxis, yAxis);
    }

    public void renderScaledText(MatrixStack stack, String text, int x, int y, int color, int maxX) {
	int length = gui.getFontRenderer().getStringWidth(text);

	if (length <= maxX) {
	    gui.getFontRenderer().drawString(stack, text, x, y, color);
	} else {
	    float scale = (float) maxX / length;
	    float reverse = 1 / scale;
	    float yAdd = 4 - scale * 8 / 2F;

	    stack.push();

	    stack.scale(scale, scale, scale);
	    gui.getFontRenderer().drawString(stack, text, (int) (x * reverse), (int) (y * reverse + yAdd), color);

	    stack.pop();
	}
    }

    protected boolean isPointInRegion(int x, int y, double xAxis, double yAxis, int width, int height) {
	return xAxis >= x && xAxis <= x + width - 1 && yAxis >= y && yAxis <= y + height - 1;
    }
}
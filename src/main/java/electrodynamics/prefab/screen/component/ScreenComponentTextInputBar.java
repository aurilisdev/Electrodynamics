package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentTextInputBar extends ScreenComponent {

	private boolean small = false;

	public ScreenComponentTextInputBar(final IScreenWrapper gui, final int x, final int y) {
		super(new ResourceLocation(References.ID + ":textures/screen/component/textinputbar.png"), gui, x, y);
	}

	public ScreenComponentTextInputBar small() {
		small = true;
		return this;
	}

	@Override
	public Rectangle getBounds(final int guiWidth, final int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, 56, 16);
	}

	@Override
	public void renderBackground(PoseStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
		RenderingUtils.bindTexture(resource);
		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, 0, small ? 16 : 0, 56, 16);
	}
}
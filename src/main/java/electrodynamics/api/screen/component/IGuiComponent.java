package electrodynamics.api.screen.component;

import java.awt.Rectangle;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IGuiComponent {
	Rectangle getBounds(int guiWidth, int guiHeight);

	default void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
	}

	default void renderForeground(PoseStack stack, int xAxis, int yAxis) {
	}

	default void preMouseClicked(double xAxis, double yAxis, int button) {
	}

	default void mouseClicked(double xAxis, double yAxis, int button) {
	}

	default void mouseClickMove(int mouseX, int mouseY, int button, long ticks) {
	}

	default void mouseReleased(double xAxis, double yAxis, int type) {
	}

	default void mouseWheel(double mouseX, double mouseY, double delta) {
	}
	
	default boolean isVisible() {
		return true;
	}

}
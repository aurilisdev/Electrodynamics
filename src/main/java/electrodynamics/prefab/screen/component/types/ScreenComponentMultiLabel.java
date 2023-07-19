package electrodynamics.prefab.screen.component.types;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.screen.component.AbstractScreenComponent;

public class ScreenComponentMultiLabel extends AbstractScreenComponent {

	private final Consumer<PoseStack> fontConsumer;
	
	public ScreenComponentMultiLabel(int x, int y, Consumer<PoseStack> fontConsumer) {
		super(x, y, 0, 0);
		this.fontConsumer = fontConsumer;
	}
	
	@Override
	public void renderForeground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		fontConsumer.accept(stack);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return false;
	}

}

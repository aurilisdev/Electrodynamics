package electrodynamics.client.render.event.guipost;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public abstract class AbstractPostGuiOverlayHandler {

	public abstract void renderToScreen(ElementType elementType, MatrixStack stack, MainWindow window, Minecraft minecraft, float partialTicks);

}
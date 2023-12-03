package electrodynamics.client.render.event.guipost;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public abstract class AbstractPostGuiOverlayHandler {

	public abstract void renderToScreen(ElementType elementType, PoseStack stack, Window window, Minecraft minecraft, float partialTicks);

}
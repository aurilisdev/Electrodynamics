package electrodynamics.client.render.event.guipost;

import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.gui.overlay.NamedGuiOverlay;

public abstract class AbstractPostGuiOverlayHandler {

	public abstract void renderToScreen(NamedGuiOverlay overlay, GuiGraphics graphics, Window window, Minecraft minecraft, float partialTicks);

}

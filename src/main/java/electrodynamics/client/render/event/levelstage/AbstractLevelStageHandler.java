package electrodynamics.client.render.event.levelstage;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;

/**
 * A note about using this event:
 * It is IMPERATIVE that the event be as efficient as possible. The more work the event must do in a single tick, the more tiny inefficient solutions start to add up. This really doens't play out much in the marker lines renderer for example, but it definitely has an impact in the quarry renderer
 * 
 * @author skip999
 *
 */
public abstract class AbstractLevelStageHandler {

	public abstract void render(ActiveRenderInfo camera, ViewFrustum frustum, WorldRenderer renderer, MatrixStack stack, Matrix4f projectionMatrix, Minecraft minecraft, int renderTick, float partialTick);

	public void clear() {

	}

}
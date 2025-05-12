package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.machines.quarry.TileMotorComplex;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderMotorComplex extends AbstractTileRenderer<TileMotorComplex> {

	public RenderMotorComplex(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileMotorComplex tile, float ticks, MatrixStack stack, @Nonnull IRenderTypeBuffer source, int light, int overlay) {

		stack.pushPose();

		Direction facing = tile.getFacing();
		float clientTicks = tile.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks();
		float progressDegrees = 0.0F;

		if (tile.isPowered.getValue()) {
			progressDegrees = 360.0f * (float) Math.sin(clientTicks / tile.speed.getValue());
		}

		IBakedModel shaft = getModel(ElectrodynamicsClientRegister.MODEL_MOTORCOMPLEXROTOR);

		switch (facing) {
		case EAST:
			stack.translate(0.5, 0.5, 0.5);
			stack.mulPose(new Quaternion(new Vector3f(-1.0F, 0.0F, 0.0F), progressDegrees, true));
			break;
		case WEST:
			stack.translate(0.9375, 0.5, 0.5);
			stack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), progressDegrees, true));
			break;
		case SOUTH:
			stack.translate(0.5, 0.5, 0.0625);
			stack.mulPose(new Quaternion(new Vector3f(0, 1F, 0), 90, true));
			stack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), progressDegrees, true));
			break;
		case NORTH:
			stack.translate(0.5, 0.5, 0.5);
			stack.mulPose(new Quaternion(new Vector3f(0, 1F, 0), 90, true));
			stack.mulPose(new Quaternion(new Vector3f(-1.0F, 0.0F, 0.0F), progressDegrees, true));
			break;
		default:
			break;
		}

		RenderingUtils.renderModel(shaft, tile, RenderType.solid(), stack, source, light, overlay);

		stack.popPose();
	}

}

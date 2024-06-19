package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.electricitygrid.generators.TileHydroelectricGenerator;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class RenderHydroelectricGenerator extends AbstractTileRenderer<TileHydroelectricGenerator> {

	public RenderHydroelectricGenerator(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(@NotNull TileHydroelectricGenerator tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BakedModel ibakedmodel = getModel(ClientRegister.MODEL_HYDROELECTRICGENERATORBLADES);
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		float partial = (float) (partialTicks * tileEntityIn.rotationSpeed * (tileEntityIn.directionFlag.get() ? 1 : -1));
		matrixStackIn.mulPose(MathUtils.rotQuaternionDeg((float) (-(tileEntityIn.savedTickRotation + partial) * 5f), 0, 0));
		// matrixStackIn.mulPose(new Quaternion((float) (-(tileEntityIn.savedTickRotation + partial) * 5f), 0, 0, true));
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
	
	@Override
	public AABB getRenderBoundingBox(TileHydroelectricGenerator blockEntity) {
	    Direction facing = blockEntity.getFacing();
	    return super.getRenderBoundingBox(blockEntity).expandTowards(facing.getStepX(), facing.getStepY(), facing.getStepZ());
	}
}

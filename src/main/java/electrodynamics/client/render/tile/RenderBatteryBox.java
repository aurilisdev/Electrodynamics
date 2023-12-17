package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderBatteryBox extends AbstractTileRenderer<TileBatteryBox> {

	public RenderBatteryBox(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileBatteryBox tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IBakedModel ibakedmodel;
		ComponentElectrodynamic el = tileEntityIn.getComponent(IComponentType.Electrodynamic);
		int stored = (int) (el.getJoulesStored() / el.getMaxJoulesStored() * 6);
		ibakedmodel = null;
		switch (stored) {
		case 1:
			ibakedmodel = getModel(ClientRegister.MODEL_BATTERYBOX2);
			break;
		case 2:
			ibakedmodel = getModel(ClientRegister.MODEL_BATTERYBOX3);
			break;
		case 3:
			ibakedmodel = getModel(ClientRegister.MODEL_BATTERYBOX4);
			break;
		case 4:
			ibakedmodel = getModel(ClientRegister.MODEL_BATTERYBOX5);
			break;
		case 5:
			ibakedmodel = getModel(ClientRegister.MODEL_BATTERYBOX6);
			break;
		case 6:
			ibakedmodel = getModel(ClientRegister.MODEL_BATTERYBOX7);
			break;
		default:
			ibakedmodel = getModel(ClientRegister.MODEL_BATTERYBOX);
			break;
		}

		switch (tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING)) {
		case NORTH:
			matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
			matrixStackIn.translate(-1, 0, 0);
			break;
		case SOUTH:
			matrixStackIn.mulPose(new Quaternion(0, 270, 0, true));
			matrixStackIn.translate(0, 0, -1);
			break;
		case WEST:
			matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
			matrixStackIn.translate(-1, 0, -1);
			break;
		default:
		}
		matrixStackIn.translate(0.5, 0.5, 0.5);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}
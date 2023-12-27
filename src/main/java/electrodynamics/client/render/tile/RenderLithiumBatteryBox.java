package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.tile.electricitygrid.batteries.TileLithiumBatteryBox;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;

public class RenderLithiumBatteryBox extends AbstractTileRenderer<TileLithiumBatteryBox> {

	public RenderLithiumBatteryBox(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileLithiumBatteryBox tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IBakedModel ibakedmodel;
		ComponentElectrodynamic el = tileEntityIn.getComponent(IComponentType.Electrodynamic);
		int stored = (int) (el.getJoulesStored() / el.getMaxJoulesStored() * 6);
		switch (stored) {
		case 0:
			ibakedmodel = getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX);
			break;
		case 1:
			ibakedmodel = getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX2);
			break;
		case 2:
			ibakedmodel = getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX3);
			break;
		case 3:
			ibakedmodel = getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX4);
			break;
		case 4:
			ibakedmodel = getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX5);
			break;
		case 5:
			ibakedmodel = getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX6);
			break;
		case 6:
			ibakedmodel = getModel(ClientRegister.MODEL_LITHIUMBATTERYBOX7);
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
			break;
		}
		matrixStackIn.translate(0.5, 0.5, 0.5);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}
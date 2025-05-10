package electrodynamics.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.electricitygrid.batteries.TileCarbyneBatteryBox;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderCarbyneBatteryBox extends AbstractTileRenderer<TileCarbyneBatteryBox> {

	public RenderCarbyneBatteryBox(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileCarbyneBatteryBox tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IBakedModel ibakedmodel;
		ComponentElectrodynamic el = tileEntityIn.getComponent(IComponentType.Electrodynamic);
		int stored = (int) (el.getJoulesStored() / el.getMaxJoulesStored() * 6);
		switch (stored) {
		case 0:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_CARBYNEBATTERYBOX);
			break;
		case 1:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_CARBYNEBATTERYBOX2);
			break;
		case 2:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_CARBYNEBATTERYBOX3);
			break;
		case 3:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_CARBYNEBATTERYBOX4);
			break;
		case 4:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_CARBYNEBATTERYBOX5);
			break;
		case 5:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_CARBYNEBATTERYBOX6);
			break;
		case 6:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_CARBYNEBATTERYBOX7);
			break;
		default:
			ibakedmodel = getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX);
			break;
		}

		switch (tileEntityIn.getFacing()) {
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

package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.electricitygrid.batteries.TileLithiumBatteryBox;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.MathUtils;

public class RenderLithiumBatteryBox extends AbstractTileRenderer<TileLithiumBatteryBox> {

	public RenderLithiumBatteryBox(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileLithiumBatteryBox tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BakedModel ibakedmodel;
		ComponentElectrodynamic el = tileEntityIn.getComponent(IComponentType.Electrodynamic);
		int stored = (int) (el.getJoulesStored() / el.getMaxJoulesStored() * 6);
		ibakedmodel = switch (stored) {
		case 0 -> getModel(ElectrodynamicsClientRegister.MODEL_LITHIUMBATTERYBOX);
		case 1 -> getModel(ElectrodynamicsClientRegister.MODEL_LITHIUMBATTERYBOX2);
		case 2 -> getModel(ElectrodynamicsClientRegister.MODEL_LITHIUMBATTERYBOX3);
		case 3 -> getModel(ElectrodynamicsClientRegister.MODEL_LITHIUMBATTERYBOX4);
		case 4 -> getModel(ElectrodynamicsClientRegister.MODEL_LITHIUMBATTERYBOX5);
		case 5 -> getModel(ElectrodynamicsClientRegister.MODEL_LITHIUMBATTERYBOX6);
		case 6 -> getModel(ElectrodynamicsClientRegister.MODEL_LITHIUMBATTERYBOX7);
		default -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX);
		};

		switch (tileEntityIn.getFacing()) {
		case NORTH -> {
			matrixStackIn.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));
			// matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
			matrixStackIn.translate(-1, 0, 0);
		}
		case SOUTH -> {
			matrixStackIn.mulPose(MathUtils.rotQuaternionDeg(0, 270, 0));
			// matrixStackIn.mulPose(new Quaternion(0, 270, 0, true));
			matrixStackIn.translate(0, 0, -1);
		}
		case WEST -> {
			matrixStackIn.mulPose(MathUtils.rotQuaternionDeg(0, 180, 0));
			// matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
			matrixStackIn.translate(-1, 0, -1);
		}
		default -> {
		}
		}
		matrixStackIn.translate(0.5, 0.5, 0.5);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}

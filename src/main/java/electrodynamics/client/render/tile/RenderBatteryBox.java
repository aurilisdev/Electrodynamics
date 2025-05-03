package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderBatteryBox extends AbstractTileRenderer<TileBatteryBox> {

	public RenderBatteryBox(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileBatteryBox tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BakedModel ibakedmodel;
		ComponentElectrodynamic el = tileEntityIn.getComponent(IComponentType.Electrodynamic);
		int stored = (int) (el.getJoulesStored() / el.getMaxJoulesStored() * 6);
		ibakedmodel = switch (stored) {
		case 1 -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX2);
		case 2 -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX3);
		case 3 -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX4);
		case 4 -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX5);
		case 5 -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX6);
		case 6 -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX7);
		default -> getModel(ElectrodynamicsClientRegister.MODEL_BATTERYBOX);
		};

		switch (tileEntityIn.getFacing()) {
		case NORTH -> {
			matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
			matrixStackIn.translate(-1, 0, 0);
		}
		case SOUTH -> {
			matrixStackIn.mulPose(new Quaternion(0, 270, 0, true));
			matrixStackIn.translate(0, 0, -1);
		}
		case WEST -> {
			matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
			matrixStackIn.translate(-1, 0, -1);
		}
		default -> {
		}
		}
		matrixStackIn.translate(0.5, 0.5, 0.5);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
	
}

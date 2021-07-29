package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.tile.generic.TileGenericCharger;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderCharger extends TileEntityRenderer<TileGenericCharger>{

	public RenderCharger(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileGenericCharger tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		
		ComponentElectrodynamic electro = tileEntityIn.getComponent(ComponentType.Electrodynamic);
		ComponentInventory inv = electro.getHolder().getComponent(ComponentType.Inventory);
		int voltage = (int) electro.getVoltage();
		double offset = 0;
		
		switch(voltage) {
			case 120:
				offset = 0.1f;
				break;
			case 240:
				offset = 0.1f;
				break;
			case 480:
				offset = 0.1f;
				break;
			default:
		}
		
		ItemStack chargingItem = inv.getStackInSlot(0);
		if(chargingItem.isEmpty()) {
			chargingItem = inv.getStackInSlot(1);
		}
		
		if(chargingItem != null && !chargingItem.isEmpty()) {
			matrixStackIn.translate(0.5f, 1f + offset, 0.53f);
			matrixStackIn.scale(0.28f, 0.28f, 0.28f);
			matrixStackIn.rotate(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90, true));
			Minecraft.getInstance().getItemRenderer().renderItem(chargingItem, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn,
				    bufferIn);
		}
		
	}

}

package electrodynamics.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.tile.machines.charger.GenericTileCharger;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RenderChargerGeneric extends AbstractTileRenderer<GenericTileCharger> {

	public RenderChargerGeneric(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(GenericTileCharger tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		Direction dir = tileEntityIn.getFacing();

		ComponentInventory inv = tileEntityIn.getComponent(IComponentType.Inventory);

		ItemStack chargingItem = inv.getItem(0);

		if (chargingItem.isEmpty()) {

			chargingItem = inv.getItem(1);

		}

		if (chargingItem != null && !chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {

			matrixStackIn.pushPose();

			if (chargingItem.getItem() instanceof ToolItem) {
				switch (dir) {
				case NORTH:
					matrixStackIn.translate(0.5f, 1.25f, 0.47f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
					matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180, true));
					break;
				case EAST:
					matrixStackIn.translate(0.53f, 1.25f, 0.5f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
					matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90, true));
					break;
				case SOUTH:
					matrixStackIn.translate(0.5f, 1.25f, 0.53f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
					break;
				case WEST:
					matrixStackIn.translate(0.47f, 1.25f, 0.5f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
					matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 270, true));
					break;
				default:
					break;
				}
			} else {
				switch (dir) {
				case NORTH:
					matrixStackIn.translate(0.5f, 1.15f, 0.47f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
					matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 270, true));
					break;
				case EAST:
					matrixStackIn.translate(0.53f, 1.15f, 0.5f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
					matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180, true));
					break;
				case SOUTH:
					matrixStackIn.translate(0.5f, 1.15f, 0.53f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
					matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90, true));
					break;
				case WEST:
					matrixStackIn.translate(0.47f, 1.15f, 0.5f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
					break;
				default:
					break;
				}
			}
			renderItem(chargingItem, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tileEntityIn.getLevel(), 0);
			matrixStackIn.popPose();
		}

	}

}
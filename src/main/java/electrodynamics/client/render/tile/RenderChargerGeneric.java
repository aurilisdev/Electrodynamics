package electrodynamics.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.tile.machines.charger.GenericTileCharger;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderChargerGeneric extends AbstractTileRenderer<GenericTileCharger> {

	public RenderChargerGeneric(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(GenericTileCharger tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

		Direction dir = tileEntityIn.getFacing();

		ComponentInventory inv = tileEntityIn.getComponent(IComponentType.Inventory);

		ItemStack chargingItem = inv.getItem(0);

		if (chargingItem.isEmpty()) {

			chargingItem = inv.getItem(1);

		}

		if (chargingItem != null && !chargingItem.isEmpty() && chargingItem.getItem() instanceof IItemElectric) {

			matrixStackIn.pushPose();

			if (chargingItem.getItem() instanceof DiggerItem) {
				switch (dir) {
				case NORTH -> {
					matrixStackIn.translate(0.5f, 1.25f, 0.47f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
					matrixStackIn.mulPose(MathUtils.rotVectorQuaternionDeg(180, MathUtils.YP));
					// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180, true));
				}
				case EAST -> {
					matrixStackIn.translate(0.53f, 1.25f, 0.5f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
					matrixStackIn.mulPose(MathUtils.rotVectorQuaternionDeg(90, MathUtils.YP));
					// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90, true));
				}
				case SOUTH -> {
					matrixStackIn.translate(0.5f, 1.25f, 0.53f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
				}
				case WEST -> {
					matrixStackIn.translate(0.47f, 1.25f, 0.5f);
					matrixStackIn.scale(0.5f, 0.5f, 0.5f);
					matrixStackIn.mulPose(MathUtils.rotVectorQuaternionDeg(270, MathUtils.YP));
					// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 270, true));
				}
				default -> {
				}
				}
			} else {
				switch (dir) {
				case NORTH -> {
					matrixStackIn.translate(0.5f, 1.15f, 0.47f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
					matrixStackIn.mulPose(MathUtils.rotVectorQuaternionDeg(270, MathUtils.YP));
					// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 270, true));
				}
				case EAST -> {
					matrixStackIn.translate(0.53f, 1.15f, 0.5f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
					matrixStackIn.mulPose(MathUtils.rotVectorQuaternionDeg(180, MathUtils.YP));
					// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180, true));
				}
				case SOUTH -> {
					matrixStackIn.translate(0.5f, 1.15f, 0.53f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
					matrixStackIn.mulPose(MathUtils.rotVectorQuaternionDeg(90, MathUtils.YP));
					// matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90, true));
				}
				case WEST -> {
					matrixStackIn.translate(0.47f, 1.15f, 0.5f);
					matrixStackIn.scale(0.28f, 0.28f, 0.28f);
				}
				default -> {
				}
				}
			}
			renderItem(chargingItem, ItemDisplayContext.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tileEntityIn.getLevel(), 0);
			matrixStackIn.popPose();
		}

	}

}

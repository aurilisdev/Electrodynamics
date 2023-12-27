package electrodynamics.client.render.tile;

import java.util.Random;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * A basic abstract class for BlockEntityRenderer that allows for the storage of utility methods amongst other things
 * 
 * @author skip999
 *
 * @param <T>
 */
public abstract class AbstractTileRenderer<T extends GenericTile> extends TileEntityRenderer<T> {

	protected final Random random;

	public AbstractTileRenderer(TileEntityRendererDispatcher context) {
		super(context);
		random = new Random();
	}

	public long getGameTime() {
		return minecraft().level.getGameTime();
	}

	public Minecraft minecraft() {
		return Minecraft.getInstance();
	}

	public ClientWorld level() {
		return minecraft().level;
	}

	public IBakedModel getModel(ResourceLocation model) {
		return Minecraft.getInstance().getModelManager().getModel(model);
	}

	public void renderItem(ItemStack stack, TransformType type, int light, int overlay, MatrixStack poseStack, IRenderTypeBuffer bufferSource, @Nullable World world, int seed) {
		Minecraft.getInstance().getItemRenderer().renderStatic(stack, type, light, overlay, poseStack, bufferSource);
	}

	@Override
	public abstract void render(T tile, float partialTick, MatrixStack poseStack, IRenderTypeBuffer bufferSource, int packedLight, int packedOverlay);

	public AxisAlignedBB aabb(double x0, double y0, double z0, double x1, double y1, double z1) {
		return new AxisAlignedBB(x0 / 16.0F, y0 / 16.0F, z0 / 16.0F, x1 / 16.0F, y1 / 16.0F, z1 / 16.0F);
	}

}
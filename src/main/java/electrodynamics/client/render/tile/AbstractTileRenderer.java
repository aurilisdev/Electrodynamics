package electrodynamics.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

/**
 * A basic abstract class for BlockEntityRenderer that allows for the storage of utility methods 
 * amongst other things
 * 
 * @author skip999
 *
 * @param <T>
 */
public abstract class AbstractTileRenderer<T extends GenericTile> implements BlockEntityRenderer<T> {

	protected BlockEntityRendererProvider.Context context;
	protected final Random random;

	public AbstractTileRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
		random = new Random();
	}
	
	public long getGameTime() {
		return minecraft().level.getGameTime();
	}
	
	public Minecraft minecraft() {
		return Minecraft.getInstance();
	}
	
	public ClientLevel level() {
		return minecraft().level;
	}
	
	public BakedModel getModel(ResourceLocation model) {
		return Minecraft.getInstance().getModelManager().getModel(model);
	}

	@Override
	public abstract void render(T tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
			int packedLight, int ppackedOverlay);

}

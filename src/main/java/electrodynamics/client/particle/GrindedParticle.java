package electrodynamics.client.particle;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrindedParticle extends TextureSheetParticle {

	private final BlockState sourceState;
	private BlockPos sourcePos;
	private final float uCoord;
	private final float vCoord;

	public GrindedParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, BlockState state) {
		super(world, x, y, z, motionX, motionY, motionZ);
		sourceState = state;
		setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(state));
		gravity = 1.0F;
		rCol = 0.6F;
		gCol = 0.6F;
		bCol = 0.6F;
		quadSize /= 2.0F;
		uCoord = random.nextFloat() * 3.0F;
		vCoord = random.nextFloat() * 3.0F;
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.TERRAIN_SHEET;
	}

	public GrindedParticle setBlockPos(BlockPos pos) {
		updateSprite(pos);
		sourcePos = pos;
		if (sourceState.is(Blocks.GRASS_BLOCK)) {
			return this;
		}
		multiplyColor(pos);
		return this;
	}

	public GrindedParticle init() {
		sourcePos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
		if (sourceState.is(Blocks.GRASS_BLOCK)) {
			return this;
		}
		multiplyColor(sourcePos);
		return this;
	}

	protected void multiplyColor(@Nullable BlockPos pos) {
		int i = Minecraft.getInstance().getBlockColors().getColor(sourceState, level, pos, 0);
		rCol *= (i >> 16 & 255) / 255.0F;
		gCol *= (i >> 8 & 255) / 255.0F;
		bCol *= (i & 255) / 255.0F;
	}

	@Override
	protected float getU0() {
		return sprite.getU((uCoord + 1.0F) / 4.0F * 16.0F);
	}

	@Override
	protected float getU1() {
		return sprite.getU(uCoord / 4.0F * 16.0F);
	}

	@Override
	protected float getV0() {
		return sprite.getV(vCoord / 4.0F * 16.0F);
	}

	@Override
	protected float getV1() {
		return sprite.getV((vCoord + 1.0F) / 4.0F * 16.0F);
	}

	@Override
	public int getLightColor(float partialTick) {
		int i = super.getLightColor(partialTick);
		int j = LevelRenderer.getLightColor(level, new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z)));
		return i == 0 ? j : i;
	}

	private void updateSprite(BlockPos pos) {
		if (pos != null) {
			setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getTexture(sourceState, level, pos));
		}
	}
}

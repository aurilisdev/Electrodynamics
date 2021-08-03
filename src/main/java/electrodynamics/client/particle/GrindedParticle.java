package electrodynamics.client.particle;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrindedParticle extends SpriteTexturedParticle {
    
	private final BlockState sourceState;
    private BlockPos sourcePos;
    private final float uCoord;
    private final float vCoord;

    public GrindedParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, BlockState state) {
		super(world, x, y, z, motionX, motionY, motionZ);
		sourceState = state;
		setSprite(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
		particleGravity = 1.0F;
		particleRed = 0.6F;
		particleGreen = 0.6F;
		particleBlue = 0.6F;
		particleScale /= 2.0F;
		uCoord = rand.nextFloat() * 3.0F;
		vCoord = rand.nextFloat() * 3.0F;
    }

    @Override
    public IParticleRenderType getRenderType() {
    	return IParticleRenderType.TERRAIN_SHEET;
    }

    public GrindedParticle setBlockPos(BlockPos pos) {
		updateSprite(pos);
		sourcePos = pos;
		if (sourceState.isIn(Blocks.GRASS_BLOCK)) {
		    return this;
		}
		multiplyColor(pos);
		return this;
    }

    public GrindedParticle init() {
		sourcePos = new BlockPos(posX, posY, posZ);
		if (sourceState.isIn(Blocks.GRASS_BLOCK)) {
		    return this;
		}
		multiplyColor(sourcePos);
		return this;
    }

    protected void multiplyColor(@Nullable BlockPos pos) {
		int i = Minecraft.getInstance().getBlockColors().getColor(sourceState, world, pos, 0);
		particleRed *= (i >> 16 & 255) / 255.0F;
		particleGreen *= (i >> 8 & 255) / 255.0F;
		particleBlue *= (i & 255) / 255.0F;
    }

    @Override
    protected float getMinU() {
    	return sprite.getInterpolatedU((uCoord + 1.0F) / 4.0F * 16.0F);
    }

    @Override
    protected float getMaxU() {
    	return sprite.getInterpolatedU(uCoord / 4.0F * 16.0F);
    }

    @Override
    protected float getMinV() {
    	return sprite.getInterpolatedV(vCoord / 4.0F * 16.0F);
    }

    @Override
    protected float getMaxV() {
    	return sprite.getInterpolatedV((vCoord + 1.0F) / 4.0F * 16.0F);
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
		int i = super.getBrightnessForRender(partialTick);
		int j = WorldRenderer.getCombinedLight(world, new BlockPos(posX, posY, posZ));
		return i == 0 ? j : i;
    }

    private Particle updateSprite(BlockPos pos) {
		if (pos != null) {
		    setSprite(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(sourceState, world, pos));
		}
		return this;
    }
}

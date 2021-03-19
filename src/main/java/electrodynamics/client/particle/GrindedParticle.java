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
	this.sourceState = state;
	this.setSprite(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
	this.particleGravity = 1.0F;
	this.particleRed = 0.6F;
	this.particleGreen = 0.6F;
	this.particleBlue = 0.6F;
	this.particleScale /= 2.0F;
	this.uCoord = this.rand.nextFloat() * 3.0F;
	this.vCoord = this.rand.nextFloat() * 3.0F;
    }

    @Override
    public IParticleRenderType getRenderType() {
	return IParticleRenderType.TERRAIN_SHEET;
    }

    public GrindedParticle setBlockPos(BlockPos pos) {
	updateSprite(pos);
	this.sourcePos = pos;
	if (this.sourceState.isIn(Blocks.GRASS_BLOCK)) {
	    return this;
	}
	this.multiplyColor(pos);
	return this;
    }

    public GrindedParticle init() {
	this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
	if (this.sourceState.isIn(Blocks.GRASS_BLOCK)) {
	    return this;
	}
	this.multiplyColor(this.sourcePos);
	return this;
    }

    protected void multiplyColor(@Nullable BlockPos pos) {
	int i = Minecraft.getInstance().getBlockColors().getColor(this.sourceState, this.world, pos, 0);
	this.particleRed *= (i >> 16 & 255) / 255.0F;
	this.particleGreen *= (i >> 8 & 255) / 255.0F;
	this.particleBlue *= (i & 255) / 255.0F;
    }

    @Override
    protected float getMinU() {
	return this.sprite.getInterpolatedU((this.uCoord + 1.0F) / 4.0F * 16.0F);
    }

    @Override
    protected float getMaxU() {
	return this.sprite.getInterpolatedU(this.uCoord / 4.0F * 16.0F);
    }

    @Override
    protected float getMinV() {
	return this.sprite.getInterpolatedV(this.vCoord / 4.0F * 16.0F);
    }

    @Override
    protected float getMaxV() {
	return this.sprite.getInterpolatedV((this.vCoord + 1.0F) / 4.0F * 16.0F);
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
	int i = super.getBrightnessForRender(partialTick);
	int j = WorldRenderer.getCombinedLight(this.world, new BlockPos(posX, posY, posZ));
	return i == 0 ? j : i;
    }

    private Particle updateSprite(BlockPos pos) {
	if (pos != null)
	    this.setSprite(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(sourceState, world, pos));
	return this;
    }
}

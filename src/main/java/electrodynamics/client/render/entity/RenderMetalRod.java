package electrodynamics.client.render.entity;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMetalRod extends EntityRenderer<EntityMetalRod> {

    public RenderMetalRod(EntityRendererManager renderManager) {
	super(renderManager);
    }

    @Override
    public void render(EntityMetalRod entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int packedLightIn) {

	matrixStackIn.push();

	// not gonna split hairs immerisve engineering gets credit for this
	double yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F;
	double pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;

	matrixStackIn.rotate(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), (float) yaw, true));
	matrixStackIn.rotate(new Quaternion(new Vector3f(0.0F, 0.0F, 1.0F), (float) pitch, true));

	matrixStackIn.translate(-0.5, -0.5, -0.5);

	switch (entity.getNumber()) {
	case 0:
	    IBakedModel steelrod = Minecraft.getInstance().getModelManager().getModel(electrodynamics.client.ClientRegister.MODEL_RODSTEEL);
	    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(entity.world, steelrod,
		    Blocks.AIR.getDefaultState(), entity.getPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
		    entity.world.rand, new Random().nextLong(), 0);
	    break;
	case 1:
	    IBakedModel stainlessSteelrod = Minecraft.getInstance().getModelManager()
		    .getModel(electrodynamics.client.ClientRegister.MODEL_RODSTAINLESSSTEEL);
	    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(entity.world, stainlessSteelrod,
		    Blocks.AIR.getDefaultState(), entity.getPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
		    entity.world.rand, new Random().nextLong(), 0);
	    break;
	case 2:
	    IBakedModel hslaSteelrod = Minecraft.getInstance().getModelManager().getModel(electrodynamics.client.ClientRegister.MODEL_RODHSLASTEEL);
	    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(entity.world, hslaSteelrod,
		    Blocks.AIR.getDefaultState(), entity.getPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
		    entity.world.rand, new Random().nextLong(), 0);

	}

	matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(EntityMetalRod entity) {

	switch (entity.getNumber()) {
	case 0:
	    return ClientRegister.TEXTURE_RODSTEEL;
	case 1:
	    return ClientRegister.TEXTURE_RODSTAINLESSSTEEL;
	case 2:
	    return ClientRegister.TEXTURE_RODHSLASTEEL;
	default:
	    return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}

    }
}

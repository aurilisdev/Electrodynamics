package electrodynamics.client.render.entity;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMetalRod extends EntityRenderer<EntityMetalRod> {

    public RenderMetalRod(Context renderManager) {
	super(renderManager);
    }

    @Override
    public void render(EntityMetalRod entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int packedLightIn) {

	matrixStackIn.pushPose();

	// not gonna split hairs immerisve engineering gets credit for this
	double yaw = entity.yRotO + (entity.getYRot() - entity.yRotO) * partialTicks - 90.0F;
	double pitch = entity.xRotO + (entity.getXRot() - entity.xRotO) * partialTicks;

	matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), (float) yaw, true));
	matrixStackIn.mulPose(new Quaternion(new Vector3f(0.0F, 0.0F, 1.0F), (float) pitch, true));

	matrixStackIn.translate(-0.5, -0.5, -0.5);

	switch (entity.getNumber()) {

	case 0:
	    BakedModel steelrod = Minecraft.getInstance().getModelManager().getModel(electrodynamics.client.ClientRegister.MODEL_RODSTEEL);
	    Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithoutAO(entity.level, steelrod, Blocks.AIR.defaultBlockState(),
		    entity.blockPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.solid()), false, entity.level.random,
		    new Random().nextLong(), 0);
	    break;
	case 1:
	    BakedModel stainlessSteelrod = Minecraft.getInstance().getModelManager()
		    .getModel(electrodynamics.client.ClientRegister.MODEL_RODSTAINLESSSTEEL);
	    Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithoutAO(entity.level, stainlessSteelrod,
		    Blocks.AIR.defaultBlockState(), entity.blockPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.solid()), false,
		    entity.level.random, new Random().nextLong(), 0);
	    break;
	case 2:
	    BakedModel hslaSteelrod = Minecraft.getInstance().getModelManager().getModel(electrodynamics.client.ClientRegister.MODEL_RODHSLASTEEL);
	    Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithoutAO(entity.level, hslaSteelrod,
		    Blocks.AIR.defaultBlockState(), entity.blockPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.solid()), false,
		    entity.level.random, new Random().nextLong(), 0);
	    break;
	default:
	    break;
	}

	matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMetalRod entity) {

	switch (entity.getNumber()) {
	case 0:
	    return ClientRegister.TEXTURE_RODSTEEL;
	case 1:
	    return ClientRegister.TEXTURE_RODSTAINLESSSTEEL;
	case 2:
	    return ClientRegister.TEXTURE_RODHSLASTEEL;
	default:
	    return InventoryMenu.BLOCK_ATLAS;
	}

    }
}

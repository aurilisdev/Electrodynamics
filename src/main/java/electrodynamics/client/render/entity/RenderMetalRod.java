package electrodynamics.client.render.entity;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.DeferredRegisters;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.entity.projectile.types.metalrod.MetalRod;
import electrodynamics.common.item.subtype.SubtypeRod;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class RenderMetalRod extends EntityRenderer<MetalRod> {

	private static ItemStack STEEL_ROD = new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.steel));
	private static ItemStack STAINLESS_STEEL_ROD = new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.stainlesssteel));
	private static ItemStack HSLA_STEEL_ROD = new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeRod.hslasteel));
	
	public RenderMetalRod(EntityRendererManager renderManager) {
		super(renderManager);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MetalRod entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
		IRenderTypeBuffer bufferIn, int packedLightIn) {
		
		matrixStackIn.push();
		matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1, 0), entity.rotationYaw + 90, true));
		matrixStackIn.rotate(new Quaternion(new Vector3f(0, 0, 1), 90 - entity.rotationPitch, true));
		
		ItemStack item = entity.getItem();
		
		if(ItemStack.areItemsEqual(item, STEEL_ROD)) {
			
			IBakedModel steelrod = Minecraft.getInstance().getModelManager().getModel(electrodynamics.client.ClientRegister.MODEL_STEELROD);
		    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(entity.world, steelrod,
			    Blocks.AIR.getDefaultState(), entity.getPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
			    entity.world.rand, new Random().nextLong(), 0);
		
		}else if(ItemStack.areItemsEqual(item, STAINLESS_STEEL_ROD)) {
			
			IBakedModel stainlessSteelrod = Minecraft.getInstance().getModelManager().getModel(electrodynamics.client.ClientRegister.MODEL_STAINLESSSTEELROD);
		    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(entity.world, stainlessSteelrod,
			    Blocks.AIR.getDefaultState(), entity.getPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
			    entity.world.rand, new Random().nextLong(), 0);
		
		}else if(ItemStack.areItemsEqual(item, HSLA_STEEL_ROD)) {
			
			IBakedModel hslaSteelrod = Minecraft.getInstance().getModelManager().getModel(electrodynamics.client.ClientRegister.MODEL_HSLASTEELROD);
		    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(entity.world, hslaSteelrod,
			    Blocks.AIR.getDefaultState(), entity.getPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()), false,
			    entity.world.rand, new Random().nextLong(), 0);
		
		}
	    matrixStackIn.pop();
	}
	
	
	@Override
	public boolean shouldRender(MetalRod livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResourceLocation getEntityTexture(MetalRod entity) {
		ItemStack item = entity.getItem();
		if(ItemStack.areItemsEqual(item, STEEL_ROD)) {
			return ClientRegister.TEXTURE_STEELROD;
		}else if(ItemStack.areItemsEqual(item, STAINLESS_STEEL_ROD)) {
			return ClientRegister.TEXTURE_STAINLESSSTEELROD;
		}else if(ItemStack.areItemsEqual(item, HSLA_STEEL_ROD)) {
			return ClientRegister.TEXTURE_HSLASTEELROD;
		}else {
			return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
		}
	}
}

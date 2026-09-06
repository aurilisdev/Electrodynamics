package electrodynamics.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.tile.machines.chemicalreactor.TileChemicalReactor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import voltaic.Voltaic;
import voltaic.api.fluid.PropertyFluidTank;
import voltaic.client.particle.fluiddrop.ParticleOptionFluidDrop;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;
import voltaic.prefab.utilities.math.MathUtils;

public class RenderChemicalReactor extends AbstractTileRenderer<TileChemicalReactor> {
    public RenderChemicalReactor(BlockEntityRendererProvider.Context context) {
	super(context);
    }

    @Override
    public void render(TileChemicalReactor tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
	    int packedLight, int packedOverlay) {
	Level level = tile.getLevel();
	if (level == null)
	    return;

	BlockPos pos = tile.getBlockPos();

	poseStack.pushPose();

	switch (tile.getFacing()) {
	case NORTH -> {
	    poseStack.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));
	    poseStack.translate(-1, 0, 0);
	}
	case SOUTH -> {
	    poseStack.mulPose(MathUtils.rotQuaternionDeg(0, 270, 0));
	    poseStack.translate(0, 0, -1);
	}
	case WEST -> {
	    poseStack.mulPose(MathUtils.rotQuaternionDeg(0, 180, 0));
	    poseStack.translate(-1, 0, -1);
	}
	default -> {
	}
	}

	ComponentProcessor processor = tile.requireComponent(IComponentType.Processor);
	ComponentInventory inv = tile.requireComponent(IComponentType.Inventory);
	boolean active = processor.isActive(0);

	poseStack.pushPose();
	poseStack.translate(0.5, 1.5, 0.5);

	float progress = (float) (processor.operatingTicks.getValue()[0] / processor.requiredTicks.getValue()[0]);

	poseStack.mulPose(MathUtils.rotVectorQuaternionDeg(progress * 90.0F, MathUtils.YP));

	RenderingUtils.renderModel(getModel(ElectrodynamicsClientRegister.MODEL_CHEMICALREACTOR_ROTOR),
		RenderType.solid(), poseStack, bufferSource, packedLight, packedOverlay);

	poseStack.popPose();
	poseStack.pushPose();

	if (tile.hasItemInputs.getValue()) {
	    poseStack.translate(0.5, 1.25, 0.5);

	    ItemStack input1 = inv.getItem(0);
	    ItemStack input2 = inv.getItem(1);

	    poseStack.pushPose();

	    if (!input1.isEmpty()) {
		if (!input2.isEmpty()) {
		    poseStack.translate(0.1875, 0, 0.1875);
		}

		renderItem(input1, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource,
			level, 0);
	    }

	    poseStack.popPose();
	    poseStack.pushPose();

	    if (!input2.isEmpty()) {
		if (!input1.isEmpty()) {
		    poseStack.translate(-0.1875, 0, -0.1875);
		}

		renderItem(input2, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource,
			level, 0);
	    }

	    poseStack.popPose();
	}

	poseStack.popPose();

	if (tile.hasFluidInputs.getValue()) {
	    ComponentFluidHandlerMulti multi = tile.requireComponent(IComponentType.FluidHandler);
	    PropertyFluidTank[] tanks = multi.getInputTanks();

	    FluidStack stack1 = tanks[0].getFluid();
	    FluidStack stack2 = tanks[1].getFluid();

	    poseStack.pushPose();

	    if (tile.hasItemInputs.getValue() && active && level.getRandom().nextDouble() < 0.4) {
		Color color = null;

		if (stack1.isEmpty() && !stack2.isEmpty()) {
		    IClientFluidTypeExtensions attributes = IClientFluidTypeExtensions.of(stack2.getFluid());
		    TextureAtlasSprite sprite = minecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
			    .apply(attributes.getStillTexture());

		    color = new Color(sprite.getPixelRGBA(0, 5, 5)).multiply(new Color(attributes.getTintColor()));
		} else if (stack2.isEmpty() && !stack1.isEmpty() || Voltaic.RANDOM.nextBoolean() && !stack1.isEmpty()) {
		    IClientFluidTypeExtensions attributes = IClientFluidTypeExtensions.of(stack1.getFluid());
		    TextureAtlasSprite sprite = minecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
			    .apply(attributes.getStillTexture());

		    color = new Color(sprite.getPixelRGBA(0, 5, 5)).multiply(new Color(attributes.getTintColor()));
		} else if (!stack2.isEmpty()) {
		    IClientFluidTypeExtensions attributes = IClientFluidTypeExtensions.of(stack2.getFluid());
		    TextureAtlasSprite sprite = minecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
			    .apply(attributes.getStillTexture());

		    color = new Color(sprite.getPixelRGBA(0, 5, 5)).multiply(new Color(attributes.getTintColor()));
		}

		double x = pos.getX() + Voltaic.RANDOM.nextDouble(0.5) + 0.25;
		double y = pos.getY() + 1.6875;
		double z = pos.getZ() + Voltaic.RANDOM.nextDouble(0.5) + 0.25;

		if (color != null) {
		    minecraft().particleEngine.createParticle(new ParticleOptionFluidDrop()
			    .setParameters(color.rFloat(), color.gFloat(), color.bFloat(), 0.5F), x, y, z, 0, -0.1, 0);
		}
	    } else if (!tile.hasItemInputs.getValue()) {
		if (stack1.isEmpty() && !stack2.isEmpty()) {
		    poseStack.translate(0, 1, 0);
		    RenderingUtils.renderFluidBox(poseStack, minecraft(),
			    bufferSource.getBuffer(RenderType.translucentMovingBlock()),
			    new AABB(0.0625, 0.25, 0.0625, 0.9375, 1, 0.9375), stack2, packedLight, packedOverlay,
			    RenderingUtils.ALL_FACES);
		} else if (stack2.isEmpty() && !stack1.isEmpty()) {
		    poseStack.translate(0, 1, 0);
		    RenderingUtils.renderFluidBox(poseStack, minecraft(),
			    bufferSource.getBuffer(RenderType.translucentMovingBlock()),
			    new AABB(0.0625, 0.25, 0.0625, 0.9375, 1, 0.9375), stack1, packedLight, packedOverlay,
			    RenderingUtils.ALL_FACES);
		} else if (!stack1.isEmpty() && !stack2.isEmpty()) {
		    poseStack.pushPose();
		    poseStack.translate(0, 1, 0);

		    RenderingUtils.renderFluidBox(poseStack, minecraft(),
			    bufferSource.getBuffer(RenderType.translucentMovingBlock()),
			    new AABB(0.0625, 0.25, 0.0625, 0.9375, 1, 0.9375), stack1, packedLight, packedOverlay,
			    RenderingUtils.ALL_FACES);

		    poseStack.popPose();

		    if (!stack2.isEmpty() && level.getRandom().nextDouble() < 0.1) {
			double x = pos.getX() + Voltaic.RANDOM.nextDouble(0.5) + 0.25;
			double y = pos.getY() + Voltaic.RANDOM.nextDouble(0.375) + 1.3125;
			double z = pos.getZ() + Voltaic.RANDOM.nextDouble(0.5) + 0.25;

			minecraft().particleEngine.createParticle(ParticleTypes.BUBBLE, x, y, z, 0, 0, 0);
		    }
		}
	    }
	    poseStack.popPose();
	}
	poseStack.pushPose();

	if (tile.hasGasInputs.getValue() && active && level.getRandom().nextDouble() < 0.8) {
	    double x = pos.getX();
	    double y = pos.getY() + 1.25;
	    double z = pos.getZ();

	    if (Voltaic.RANDOM.nextBoolean()) {
		x += Voltaic.RANDOM.nextDouble(0.875) + 0.0625;
		z += Voltaic.RANDOM.nextBoolean() ? Voltaic.RANDOM.nextDouble(0.125) + 0.0625
			: Voltaic.RANDOM.nextDouble(0.125) + 0.8125;
	    } else {
		z += Voltaic.RANDOM.nextDouble(0.875) + 0.0625;
		x += Voltaic.RANDOM.nextBoolean() ? Voltaic.RANDOM.nextDouble(0.125) + 0.0625
			: Voltaic.RANDOM.nextDouble(0.125) + 0.8125;
	    }

	    minecraft().particleEngine.createParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
	}

	poseStack.popPose();
	poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(TileChemicalReactor blockEntity) {
	return super.getRenderBoundingBox(blockEntity).expandTowards(0, 2, 0);
    }
}

//package electrodynamics.client.render.item;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Function;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableMap;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonObject;
//import com.mojang.datafixers.util.Pair;
//import com.mojang.math.Transformation;
//
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.client.renderer.block.model.ItemOverrides;
//import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.client.resources.model.BakedModel;
//import net.minecraft.client.resources.model.BlockModelRotation;
//import net.minecraft.client.resources.model.Material;
//import net.minecraft.client.resources.model.ModelBakery;
//import net.minecraft.client.resources.model.ModelState;
//import net.minecraft.client.resources.model.UnbakedModel;
//import net.minecraft.core.Direction;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.ResourceManager;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.material.Fluid;
//import net.minecraft.world.level.material.Fluids;
//import net.minecraftforge.client.ForgeHooksClient;
//import net.minecraftforge.client.model.CompositeModelState;
//import net.minecraftforge.client.model.ForgeModelBakery;
//import net.minecraftforge.client.model.IModelConfiguration;
//import net.minecraftforge.client.model.IModelLoader;
//import net.minecraftforge.client.model.ItemLayerModel;
//import net.minecraftforge.client.model.ItemMultiLayerBakedModel;
//import net.minecraftforge.client.model.ItemTextureQuadConverter;
//import net.minecraftforge.client.model.PerspectiveMapWrapper;
//import net.minecraftforge.client.model.geometry.IModelGeometry;
//import net.minecraftforge.fluids.FluidUtil;
//import net.minecraftforge.registries.ForgeRegistries;
//
// TODO: Fix canister render???
////we don't need this, however it could be useful for a custom item renderer at some
////later point
//public class ItemCanisterModel implements IModelGeometry<ItemCanisterModel> {
//
//	private static final float NORTH_Z_COVER = 7.496f / 16f;
//	private static final float SOUTH_Z_COVER = 8.504f / 16f;
//
//	private static final float NORTH_Z_FLUID = 7.498f / 16f;
//	private static final float SOUTH_Z_FLUID = 8.502f / 16f;
//
//	@Nonnull
//	private Fluid fluid;
//
//	private final boolean tint;
//	private final boolean coverIsMask;
//	private final boolean applyFluidLuminosity;
//
//	public ItemCanisterModel(Fluid fluid, boolean hasTint, boolean coverIsMask, boolean applyFluidLuminosity) {
//		this.fluid = fluid;
//		tint = hasTint;
//		this.coverIsMask = coverIsMask;
//		this.applyFluidLuminosity = applyFluidLuminosity;
//	}
//
//	public ItemCanisterModel withFluid(Fluid fluid) {
//		return new ItemCanisterModel(fluid, tint, coverIsMask, applyFluidLuminosity);
//	}
//
//	@Override
//	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> sprite, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelloc) {
//
//		Material particleLocation = owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : null;
//		Material baseLocation = owner.isTexturePresent("base") ? owner.resolveTexture("base") : null;
//		Material fluidMaskLocation = owner.isTexturePresent("fluid") ? owner.resolveTexture("fluid") : null;
//		Material coverLocation = owner.isTexturePresent("cover") ? owner.resolveTexture("cover") : null;
//
//		ModelState transformsFromModel = owner.getCombinedTransform();
//
//		TextureAtlasSprite fluidSprite = fluid != Fluids.EMPTY ? sprite.apply(ForgeHooksClient.getBlockMaterial(fluid.getAttributes().getStillTexture())) : null;
//		TextureAtlasSprite coverSprite = coverLocation != null && (!coverIsMask || baseLocation != null) ? sprite.apply(coverLocation) : null;
//
//		ImmutableMap<TransformType, Transformation> transformMap = PerspectiveMapWrapper.getTransforms(new CompositeModelState(transformsFromModel, modelTransform));
//
//		TextureAtlasSprite particleSprite = particleLocation != null ? sprite.apply(particleLocation) : null;
//
//		if (particleSprite == null) {
//			particleSprite = fluidSprite;
//		}
//		if (particleSprite == null && !coverIsMask) {
//			particleSprite = coverSprite;
//		}
//
//		Transformation transform = modelTransform.getRotation();
//
//		ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particleSprite, new CanisterFluidOverrideHandler(overrides, bakery, owner, this), transformMap);
//
//		if (baseLocation != null) {
//			builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemLayerModel.getQuadsForSprites(ImmutableList.of(baseLocation), transform, sprite));
//		}
//
//		if (fluidMaskLocation != null && fluidSprite != null) {
//			TextureAtlasSprite templateSprite = sprite.apply(fluidMaskLocation);
//			if (templateSprite != null) {
//				int luminosity = applyFluidLuminosity ? fluid.getAttributes().getLuminosity() : 0;
//				int color = tint ? fluid.getAttributes().getColor() : 0xFFFFFFFF;
//				builder.addQuads(ItemLayerModel.getLayerRenderType(luminosity > 0), ItemTextureQuadConverter.convertTexture(transform, templateSprite, fluidSprite, NORTH_Z_FLUID, Direction.NORTH, color, 1, luminosity));
//				builder.addQuads(ItemLayerModel.getLayerRenderType(luminosity > 0), ItemTextureQuadConverter.convertTexture(transform, templateSprite, fluidSprite, SOUTH_Z_FLUID, Direction.SOUTH, color, 1, luminosity));
//			}
//		}
//
//		if (coverIsMask) {
//			if (coverSprite != null && baseLocation != null) {
//				TextureAtlasSprite baseSprite = sprite.apply(baseLocation);
//				builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, coverSprite, baseSprite, NORTH_Z_COVER, Direction.NORTH, 0xFFFFFFFF, 2));
//				builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, coverSprite, baseSprite, SOUTH_Z_COVER, Direction.SOUTH, 0xFFFFFFFF, 2));
//			}
//		} else if (coverSprite != null) {
//			builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, NORTH_Z_COVER, coverSprite, Direction.NORTH, 0xFFFFFFFF, 2));
//			builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, SOUTH_Z_COVER, coverSprite, Direction.SOUTH, 0xFFFFFFFF, 2));
//		}
//		builder.setParticle(particleSprite);
//
//		return builder.build();
//	}
//
//	@Override
//	public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
//
//		Set<Material> textures = Sets.newHashSet();
//
//		if (owner.isTexturePresent("particle")) {
//			textures.add(owner.resolveTexture("particle"));
//		}
//		if (owner.isTexturePresent("base")) {
//			textures.add(owner.resolveTexture("base"));
//		}
//		if (owner.isTexturePresent("fluid")) {
//			textures.add(owner.resolveTexture("fluid"));
//		}
//		if (owner.isTexturePresent("cover")) {
//			textures.add(owner.resolveTexture("cover"));
//		}
//		return textures;
//	}
//
//	public enum Loader implements IModelLoader<ItemCanisterModel> {
//		INSTANCE;
//
//		@Override
//		public void onResourceManagerReload(ResourceManager resourceManager) {
//			// no need to clear cache since we create a new model instance
//		}
//
//		@Override
//		public ItemCanisterModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
//			if (!modelContents.has("fluid")) {
//				throw new RuntimeException("Bucket model requires 'fluid' value.");
//			}
//
//			ResourceLocation fluidName = new ResourceLocation(modelContents.get("fluid").getAsString());
//
//			Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidName);
//
//			boolean flip = false;
//			if (modelContents.has("flipGas")) {
//				flip = modelContents.get("flipGas").getAsBoolean();
//			}
//
//			boolean tint = true;
//			if (modelContents.has("applyTint")) {
//				tint = modelContents.get("applyTint").getAsBoolean();
//			}
//
//			boolean coverIsMask = true;
//			if (modelContents.has("coverIsMask")) {
//				coverIsMask = modelContents.get("coverIsMask").getAsBoolean();
//			}
//
//			// create new model with correct liquid
//			return new ItemCanisterModel(fluid, flip, tint, coverIsMask);
//		}
//	}
//
//	private static final class CanisterFluidOverrideHandler extends ItemOverrides {
//		private final Map<String, BakedModel> cache = Maps.newHashMap(); // contains all the baked models since they'll never change
//		private final ItemOverrides nested;
//		private final ModelBakery bakery;
//		private final IModelConfiguration owner;
//		private final ItemCanisterModel parent;
//
//		private CanisterFluidOverrideHandler(ItemOverrides nested, ModelBakery bakery, IModelConfiguration owner, ItemCanisterModel itemCanisterModel) {
//			this.nested = nested;
//			this.bakery = bakery;
//			this.owner = owner;
//			parent = itemCanisterModel;
//		}
//
//		@Override
//		public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
//			BakedModel overriden = nested.resolve(originalModel, stack, world, entity, seed);
//			if (overriden != originalModel) {
//				return overriden;
//			}
//			return FluidUtil.getFluidContained(stack).map(fluidStack -> {
//				Fluid fluid = fluidStack.getFluid();
//				String name = fluid.getRegistryName().toString();
//
//				if (!cache.containsKey(name)) {
//					ItemCanisterModel unbaked = parent.withFluid(fluid);
//					BakedModel bakedModel = unbaked.bake(owner, bakery, ForgeModelBakery.defaultTextureGetter(), BlockModelRotation.X0_Y0, this, new ResourceLocation("forge:bucket_override"));
//					cache.put(name, bakedModel);
//					return bakedModel;
//				}
//
//				return cache.get(name);
//			})
//					// not a fluid item apparently
//					.orElse(originalModel); // empty bucket
//		}
//	}
//}

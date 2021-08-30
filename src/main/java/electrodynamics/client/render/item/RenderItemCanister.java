package electrodynamics.client.render.item;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ItemMultiLayerBakedModel;
import net.minecraftforge.client.model.ItemTextureQuadConverter;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;

public class RenderItemCanister implements IModelGeometry<RenderItemCanister>{

	//TODO possible rework?
	private static final float NORTH_Z_COVER = 7.496f / 16f;
    private static final float SOUTH_Z_COVER = 8.504f / 16f;
    
    //TODO rework for texture
    private static final float NORTH_Z_FLUID = 7.498f / 16f;
    private static final float SOUTH_Z_FLUID = 8.502f / 16f;
	
	@Nonnull
	private Fluid fluid;
	
	private final boolean tint;
    private final boolean coverIsMask;
    private final boolean applyFluidLuminosity;
	
	public RenderItemCanister(Fluid fluid, boolean hasTint, boolean coverIsMask, boolean applyFluidLuminosity) {
		this.fluid = fluid;
		this.tint = hasTint;
		this.coverIsMask = coverIsMask;
		this.applyFluidLuminosity = applyFluidLuminosity;
	}
	
	public RenderItemCanister addFluid(Fluid fluid) {
		return new RenderItemCanister(fluid, tint, coverIsMask, applyFluidLuminosity);
	}
	
	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter,
		IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		
		RenderMaterial particleLocation = owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : null;
		RenderMaterial baseLocation = owner.isTexturePresent("base") ? owner.resolveTexture("base") : null;
        RenderMaterial fluidMaskLocation = owner.isTexturePresent("fluid") ? owner.resolveTexture("fluid") : null;
        RenderMaterial coverLocation = owner.isTexturePresent("cover") ? owner.resolveTexture("cover") : null;
		
		IModelTransform transformsFromModel = owner.getCombinedTransform();
		
		TextureAtlasSprite fluidSprite = fluid != Fluids.EMPTY ? spriteGetter.apply(ForgeHooksClient.getBlockMaterial(fluid.getAttributes().getStillTexture())) : null;
        TextureAtlasSprite coverSprite = (coverLocation != null && (!coverIsMask || baseLocation != null)) ? spriteGetter.apply(coverLocation) : null;
        
        ImmutableMap<TransformType, TransformationMatrix> transformMap =
                PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform));
        
        TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;

        if (particleSprite == null) {
        	particleSprite = fluidSprite;
        }
        if (particleSprite == null && !coverIsMask) {
        	particleSprite = coverSprite;
        }
        
        TransformationMatrix transform = modelTransform.getRotation();
        
        ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particleSprite, new CanisterFluidOverrideHandler(overrides, bakery, owner, this), transformMap);
        
        if(baseLocation != null){
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemLayerModel.getQuadsForSprites(ImmutableList.of(baseLocation), transform, spriteGetter));
        }

        if(fluidMaskLocation != null && fluidSprite != null){
            TextureAtlasSprite templateSprite = spriteGetter.apply(fluidMaskLocation);
            if (templateSprite != null){
                int luminosity = applyFluidLuminosity ? fluid.getAttributes().getLuminosity() : 0;
                int color = tint ? fluid.getAttributes().getColor() : 0xFFFFFFFF;
                builder.addQuads(ItemLayerModel.getLayerRenderType(luminosity > 0), ItemTextureQuadConverter.convertTexture(transform, templateSprite, fluidSprite, NORTH_Z_FLUID, Direction.NORTH, color, 1, luminosity));
                builder.addQuads(ItemLayerModel.getLayerRenderType(luminosity > 0), ItemTextureQuadConverter.convertTexture(transform, templateSprite, fluidSprite, SOUTH_Z_FLUID, Direction.SOUTH, color, 1, luminosity));
            }
        }

        if(coverIsMask){
            if (coverSprite != null && baseLocation != null){
                TextureAtlasSprite baseSprite = spriteGetter.apply(baseLocation);
                builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, coverSprite, baseSprite, NORTH_Z_COVER, Direction.NORTH, 0xFFFFFFFF, 2));
                builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, coverSprite, baseSprite, SOUTH_Z_COVER, Direction.SOUTH, 0xFFFFFFFF, 2));
            }
        } else {
            if(coverSprite != null){
                builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, NORTH_Z_COVER, coverSprite, Direction.NORTH, 0xFFFFFFFF, 2));
                builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, SOUTH_Z_COVER, coverSprite, Direction.SOUTH, 0xFFFFFFFF, 2));
            }
        }
        builder.setParticle(particleSprite);

        return builder.build();
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter,
		Set<Pair<String, String>> missingTextureErrors) {
		
		Set<RenderMaterial> textures = Sets.newHashSet();
		
		if (owner.isTexturePresent("particle")) {
			textures.add(owner.resolveTexture("particle"));
		}
		if (owner.isTexturePresent("base")) {
			textures.add(owner.resolveTexture("base"));
		}
        if (owner.isTexturePresent("fluid")) {
        	textures.add(owner.resolveTexture("fluid"));
        }
        if (owner.isTexturePresent("cover")) {
        	textures.add(owner.resolveTexture("cover"));
        }
		return textures;
	}
	
	public enum Loader implements IModelLoader<RenderItemCanister>{
		INSTANCE;

		@Override
		public IResourceType getResourceType() {
			return VanillaResourceType.MODELS;
		}
		
		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {}
		
		
		@Override
		public RenderItemCanister read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			
			if(!modelContents.has("fluid")) {
				throw new RuntimeException("Bucket model requires 'fluid' value.");
			}
			
			ResourceLocation fluidName = new ResourceLocation(modelContents.get("fluid").getAsString());
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidName);
            
            boolean tint = true;
            if(modelContents.has("applyTint")){
                tint = modelContents.get("applyTint").getAsBoolean();
            }
            boolean coverIsMask = true;
            if(modelContents.has("coverIsMask")){
                coverIsMask = modelContents.get("coverIsMask").getAsBoolean();
            }
            boolean applyFluidLuminosity = true;
            if(modelContents.has("applyFluidLuminosity")){
                applyFluidLuminosity = modelContents.get("applyFluidLuminosity").getAsBoolean();
            }
			return new RenderItemCanister(fluid, tint, coverIsMask, applyFluidLuminosity);
		}
	}
	
	private static final class CanisterFluidOverrideHandler extends ItemOverrideList{
		
		private final Map<String, IBakedModel> cache = Maps.newHashMap(); 
        private final ItemOverrideList nested;
        private final ModelBakery bakery;
        private final IModelConfiguration owner;
        private final RenderItemCanister parent;
		
		public CanisterFluidOverrideHandler(ItemOverrideList nested, ModelBakery bakery, IModelConfiguration owner,
			RenderItemCanister parent) {
			
			this.nested = nested;
			this.bakery = bakery;
			this.owner = owner;
			this.parent = parent;
		}
		
		@Override
		public IBakedModel getOverrideModel(IBakedModel originalModel, ItemStack stack, @Nullable ClientWorld world, 
			@Nullable LivingEntity entity) {
			
			IBakedModel overriden = nested.getOverrideModel(originalModel, stack, world, entity);
            if(overriden != originalModel) {
            	return overriden;
            }
            return FluidUtil.getFluidContained(stack)
                    .map(fluidStack -> {
                        Fluid fluid = fluidStack.getFluid();
                        String name = fluid.getRegistryName().toString();

                        if (!cache.containsKey(name))
                        {
                            RenderItemCanister unbaked = this.parent.addFluid(fluid);
                            IBakedModel bakedModel = unbaked.bake(
                            	owner, bakery, ModelLoader.defaultTextureGetter(),
                            	ModelRotation.X0_Y0, this, new ResourceLocation("electrodynamics:canister_override")
                            );
                            cache.put(name, bakedModel);
                            return bakedModel;
                        }
                        return cache.get(name);
                    }).orElse(originalModel); 
		}
	}
}

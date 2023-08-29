package electrodynamics.client.texture.atlas;

import electrodynamics.api.References;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;

public class AtlasHolderElectrodynamicsCustom extends TextureAtlasHolder {

	public static AtlasHolderElectrodynamicsCustom INSTANCE;

	// Custom Textures
	public static final ResourceLocation TEXTURE_QUARRYARM = new ResourceLocation(References.ID, "quarryarm");
	public static final ResourceLocation TEXTURE_QUARRYARM_DARK = new ResourceLocation(References.ID, "quarrydark");
	public static final ResourceLocation TEXTURE_MERCURY = new ResourceLocation(References.ID, "mercury");
	public static final ResourceLocation TEXTURE_GAS = new ResourceLocation(References.ID, "gastexture"); 

	public AtlasHolderElectrodynamicsCustom(TextureManager textureManager) {
		super(textureManager, new ResourceLocation(References.ID, "textures/" + References.ID + "/" + ElectrodynamicsTextureAtlases.ELECTRODYNAMICS_CUSTOM_NAME + ".png"), ElectrodynamicsTextureAtlases.ELECTRODYNAMICS_CUSTOM);
	}

	@Override
	public TextureAtlasSprite getSprite(ResourceLocation location) {
		return super.getSprite(location);
	}

	public static TextureAtlasSprite get(ResourceLocation loc) {
		return INSTANCE.getSprite(loc);
	}

}

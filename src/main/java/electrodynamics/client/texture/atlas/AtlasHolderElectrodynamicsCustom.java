package electrodynamics.client.texture.atlas;

import electrodynamics.api.References;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;

public class AtlasHolderElectrodynamicsCustom extends TextureAtlasHolder {

	public AtlasHolderElectrodynamicsCustom(TextureManager textureManager) {
		super(textureManager, new ResourceLocation(References.ID, "textures/" + References.ID + "/" + ElectrodynamicsTextureAtlases.ELECTRODYNAMICS_CUSTOM_NAME + ".png"), ElectrodynamicsTextureAtlases.ELECTRODYNAMICS_CUSTOM);
	}
	
	@Override
	public TextureAtlasSprite getSprite(ResourceLocation location) {
		return super.getSprite(location);
	}

}

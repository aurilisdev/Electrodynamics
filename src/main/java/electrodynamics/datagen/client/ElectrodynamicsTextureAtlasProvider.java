package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.client.texture.atlas.ElectrodynamicsTextureAtlases;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

public class ElectrodynamicsTextureAtlasProvider extends SpriteSourceProvider {

	public ElectrodynamicsTextureAtlasProvider(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, fileHelper, References.ID);
	}

	@Override
	protected void addSources() {
		atlas(ElectrodynamicsTextureAtlases.ELECTRODYNAMICS_CUSTOM).addSource(new DirectoryLister("custom", "custom/"));
	}

}

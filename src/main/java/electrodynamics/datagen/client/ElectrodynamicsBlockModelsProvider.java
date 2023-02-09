package electrodynamics.datagen.client;

import electrodynamics.api.References;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Unused class for now; do not remove please
 * @author skip999
 *
 */
public class ElectrodynamicsBlockModelsProvider extends BlockModelProvider {

	public ElectrodynamicsBlockModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, References.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

	}

	private void blockTopBottom(RegistryObject<Block> block, String top, String bottom, String side) {
		cubeBottomTop(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), new ResourceLocation(References.ID, side),
				new ResourceLocation(References.ID, bottom), new ResourceLocation(References.ID, top));
	}

}

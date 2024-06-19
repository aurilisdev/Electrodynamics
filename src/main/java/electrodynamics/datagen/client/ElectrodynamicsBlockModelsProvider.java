package electrodynamics.datagen.client;

import electrodynamics.api.References;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Unused class for now; do not remove please
 * 
 * @author skip999
 *
 */
public class ElectrodynamicsBlockModelsProvider extends BlockModelProvider {

	public ElectrodynamicsBlockModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, References.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

	}

}

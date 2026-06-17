package electrodynamics.datagen.client;

import electrodynamics.Electrodynamics;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Unused class for now; do not remove please
 * 
 * @author skip999
 *
 */
public class ElectrodynamicsBlockModelsProvider extends BlockModelProvider {

    public ElectrodynamicsBlockModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
	super(output, Electrodynamics.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

}

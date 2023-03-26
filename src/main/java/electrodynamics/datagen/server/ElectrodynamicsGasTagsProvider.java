package electrodynamics.datagen.server;

import org.jetbrains.annotations.Nullable;

import electrodynamics.api.References;
import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;

public class ElectrodynamicsGasTagsProvider extends ForgeRegistryTagsProvider<Gas> {

	public ElectrodynamicsGasTagsProvider(DataGenerator generator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(generator, ElectrodynamicsRegistries.gasRegistry(), modId, existingFileHelper);
	}
	
	public ElectrodynamicsGasTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		this(generator, References.ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		
	}

}

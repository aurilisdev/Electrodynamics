package electrodynamics.datagen;

import electrodynamics.api.References;
import electrodynamics.datagen.client.ElectrodynamicsBlockModelsProvider;
import electrodynamics.datagen.client.ElectrodynamicsBlockStateProvider;
import electrodynamics.datagen.client.ElectrodynamicsItemModelsProvider;
import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider;
import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider.Locale;
import electrodynamics.datagen.client.ElectrodynamicsSoundProvider;
import electrodynamics.datagen.server.CoalGeneratorFuelSourceProvider;
import electrodynamics.datagen.server.CombustionChamberFuelSourceProvider;
import electrodynamics.datagen.server.ElectrodynamicsAdvancementProvider;
import electrodynamics.datagen.server.ElectrodynamicsBiomeFeaturesProvider;
import electrodynamics.datagen.server.ElectrodynamicsBlockTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsFluidTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsGasTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsItemTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import electrodynamics.datagen.server.ThermoelectricGenHeatSourceProvider;
import electrodynamics.datagen.server.recipe.ElectrodynamicsRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();
		
		PackOutput output = generator.getPackOutput();
		
		if (event.includeServer()) {
			ElectrodynamicsBlockTagsProvider blockProvider = new ElectrodynamicsBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(true, blockProvider);
			generator.addProvider(true, new ElectrodynamicsItemTagsProvider(generator, blockProvider, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsFluidTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsGasTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsLootTablesProvider(generator));
			generator.addProvider(true, new ElectrodynamicsRecipeProvider(output));
			generator.addProvider(true, new ElectrodynamicsBiomeFeaturesProvider(output));
			generator.addProvider(true, new CombustionChamberFuelSourceProvider(output));
			generator.addProvider(true, new CoalGeneratorFuelSourceProvider(output));
			generator.addProvider(true, new ThermoelectricGenHeatSourceProvider(generator));
			generator.addProvider(true, new ElectrodynamicsAdvancementProvider(output));
		}
		if (event.includeClient()) {
			generator.addProvider(true, new ElectrodynamicsBlockStateProvider(output, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsBlockModelsProvider(output, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsItemModelsProvider(output, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsLangKeyProvider(output, Locale.EN_US));
			generator.addProvider(true, new ElectrodynamicsSoundProvider(output, event.getExistingFileHelper()));
		}
	}

}

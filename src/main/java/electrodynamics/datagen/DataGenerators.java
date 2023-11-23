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
import electrodynamics.datagen.server.ElectrodynamicsBiomeModifierProvider;
import electrodynamics.datagen.server.ElectrodynamicsBlockTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsFluidTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsGasTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsItemTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import electrodynamics.datagen.server.ThermoelectricGenHeatSourceProvider;
import electrodynamics.datagen.server.recipe.ElectrodynamicsRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			ElectrodynamicsBlockTagsProvider blockProvider = new ElectrodynamicsBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(true, blockProvider);
			generator.addProvider(true, new ElectrodynamicsItemTagsProvider(generator, blockProvider, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsFluidTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsGasTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsLootTablesProvider(generator));
			generator.addProvider(true, new ElectrodynamicsRecipeProvider(generator));
			generator.addProvider(true, new ElectrodynamicsBiomeModifierProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new CombustionChamberFuelSourceProvider(generator));
			generator.addProvider(true, new CoalGeneratorFuelSourceProvider(generator));
			generator.addProvider(true, new ThermoelectricGenHeatSourceProvider(generator));
			generator.addProvider(true, new ElectrodynamicsAdvancementProvider(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(true, new ElectrodynamicsBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsBlockModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsItemModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsLangKeyProvider(generator, Locale.EN_US));
			generator.addProvider(true, new ElectrodynamicsSoundProvider(generator, event.getExistingFileHelper()));
		}
	}

}

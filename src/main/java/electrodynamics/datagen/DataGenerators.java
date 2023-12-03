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
import electrodynamics.datagen.server.ElectrodynamicsBlockTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsFluidTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsItemTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import electrodynamics.datagen.server.ThermoelectricGenHeatSourceProvider;
import electrodynamics.datagen.server.recipe.ElectrodynamicsRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			ElectrodynamicsBlockTagsProvider blockProvider = new ElectrodynamicsBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(blockProvider);
			generator.addProvider(new ElectrodynamicsItemTagsProvider(generator, blockProvider, event.getExistingFileHelper()));
			generator.addProvider(new ElectrodynamicsFluidTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new ElectrodynamicsLootTablesProvider(generator));
			generator.addProvider(new ElectrodynamicsRecipeProvider(generator));
			generator.addProvider(new CombustionChamberFuelSourceProvider(generator));
			generator.addProvider(new CoalGeneratorFuelSourceProvider(generator));
			generator.addProvider(new ThermoelectricGenHeatSourceProvider(generator));
			generator.addProvider(new ElectrodynamicsAdvancementProvider(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(new ElectrodynamicsBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new ElectrodynamicsBlockModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new ElectrodynamicsItemModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new ElectrodynamicsLangKeyProvider(generator, Locale.EN_US));
			generator.addProvider(new ElectrodynamicsSoundProvider(generator, event.getExistingFileHelper()));
		}
	}

}

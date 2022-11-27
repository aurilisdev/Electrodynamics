package electrodynamics.datagen;

import electrodynamics.api.References;
import electrodynamics.datagen.client.OverdriveBlockModelsProvider;
import electrodynamics.datagen.client.OverdriveBlockStateProvider;
import electrodynamics.datagen.client.OverdriveItemModelsProvider;
import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider;
import electrodynamics.datagen.server.ElectrodynamicsBiomeFeaturesProvider;
import electrodynamics.datagen.server.ElectrodynamicsBlockTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsFluidTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsItemTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import electrodynamics.datagen.server.recipe.ElectrodynamicsRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

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
			generator.addProvider(true, new ElectrodynamicsLootTablesProvider(generator));
			generator.addProvider(true, new ElectrodynamicsRecipeProvider(generator));
			generator.addProvider(true, new ElectrodynamicsBiomeFeaturesProvider(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(true, new OverdriveBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new OverdriveBlockModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new OverdriveItemModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsLangKeyProvider(generator, "en_us"));
		}
	}

}

package electrodynamics.datagen.server;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.datagen.utils.SimpleOreFeatureProvider;
import electrodynamics.datagen.utils.SimpleOreFeatureProvider.BiomeModifierType;
import electrodynamics.registers.ElectrodynamicsFeatures;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsBiomeFeaturesProvider implements DataProvider {

	public static final String FORGE_BIOME_MODIFIERS_BASE = "data/" + References.ID + "/forge/biome_modifier/";

	private final DataGenerator dataGenerator;

	private final Map<String, JsonObject> jsons = new HashMap<>();

	public ElectrodynamicsBiomeFeaturesProvider(DataGenerator generator) {
		dataGenerator = generator;
	}

	@Override
	public void run(CachedOutput cache) throws IOException {

		addOres();

		Path parent = dataGenerator.getOutputFolder().resolve(FORGE_BIOME_MODIFIERS_BASE);
		try {
			for (Entry<String, JsonObject> json : jsons.entrySet()) {
				DataProvider.saveStable(cache, json.getValue(), parent.resolve(json.getKey() + ".json"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addOres() {
		for (RegistryObject<PlacedFeature> ore : ElectrodynamicsFeatures.PLACED_FEATURES.getEntries()) {
			jsons.put(
					//
					"add_ore_" + ore.getId().getPath().replace("ore", ""),
					//
					SimpleOreFeatureProvider.of(ore, BiomeModifierType.ADD_FEATURES)
							//
							.biomeTag(BiomeTags.IS_OVERWORLD).decoration(Decoration.UNDERGROUND_ORES).toJson()
			//
			);
		}

	}

	@Override
	public String getName() {
		return "Electrodynamics Biome Features Provider";
	}

}

package electrodynamics.datagen.server;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonObject;

import electrodynamics.api.References;
import electrodynamics.datagen.utils.SimpleOreFeatureProvider;
import electrodynamics.datagen.utils.SimpleOreFeatureProvider.BiomeModifierType;
import electrodynamics.registers.ElectrodynamicsFeatures;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsBiomeFeaturesProvider implements DataProvider {

	public static final String FORGE_BIOME_MODIFIERS_BASE = "data/" + References.ID + "/forge/biome_modifier/";

	private final PackOutput output;

	private final Map<String, JsonObject> jsons = new HashMap<>();

	public ElectrodynamicsBiomeFeaturesProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {

		addOres();

		Path parent = output.getOutputFolder().resolve(FORGE_BIOME_MODIFIERS_BASE);

		List<CompletableFuture<?>> completed = new ArrayList<>();

		for (Entry<String, JsonObject> json : jsons.entrySet()) {
			completed.add(DataProvider.saveStable(cache, json.getValue(), parent.resolve(json.getKey() + ".json")));
		}

		return CompletableFuture.allOf(completed.toArray((size) -> {
			return new CompletableFuture[size];
		}));

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

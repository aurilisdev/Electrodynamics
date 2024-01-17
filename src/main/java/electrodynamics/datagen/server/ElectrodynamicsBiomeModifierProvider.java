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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsBiomeModifierProvider implements DataProvider {

	public static final String FORGE_BIOME_MODIFIERS_BASE = "data/" + References.ID + "/forge/biome_modifier/";

	private final DataGenerator dataGenerator;

	private final Map<String, JsonObject> jsons = new HashMap<>();

	public ElectrodynamicsBiomeModifierProvider(DataGenerator generator) {
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

		addOre(ElectrodynamicsFeatures.ORE_ALUMINUM_MODIFIER, ElectrodynamicsFeatures.ORE_ALUMINUM_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_CHROMIUM_MODIFIER, ElectrodynamicsFeatures.ORE_CHROMIUM_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_FLUORITE_MODIFIER, ElectrodynamicsFeatures.ORE_FLUORITE_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_LEAD_MODIFIER, ElectrodynamicsFeatures.ORE_LEAD_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_LITHIUM_MODIFIER, ElectrodynamicsFeatures.ORE_LITHIUM_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_MOLYBDENUM_MODIFIER, ElectrodynamicsFeatures.ORE_MOLYBDENUM_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_MONAZITE_MODIFIER, ElectrodynamicsFeatures.ORE_MONAZITE_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_NITER_MODIFIER, ElectrodynamicsFeatures.ORE_NITER_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_SALT_MODIFIER, ElectrodynamicsFeatures.ORE_SALT_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_SILVER_MODIFIER, ElectrodynamicsFeatures.ORE_SILVER_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_SULFUR_MODIFIER, ElectrodynamicsFeatures.ORE_SULFUR_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_SYLVITE_MODIFIER, ElectrodynamicsFeatures.ORE_SYLVITE_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_TIN_MODIFIER, ElectrodynamicsFeatures.ORE_TIN_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_TITANIUM_MODIFIER, ElectrodynamicsFeatures.ORE_TITANIUM_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_THORIUM_MODIFIER, ElectrodynamicsFeatures.ORE_THORIUM_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_URANIUM_MODIFIER, ElectrodynamicsFeatures.ORE_URANIUM_PLACED);
		addOre(ElectrodynamicsFeatures.ORE_VANADIUM_MODIFIER, ElectrodynamicsFeatures.ORE_VANADIUM_PLACED);

	}

	private void addOre(ResourceLocation name, RegistryObject<PlacedFeature> feature) {
		jsons.put(name.getPath(), SimpleOreFeatureProvider.of(feature, BiomeModifierType.ADD_FEATURES).biomeTag(BiomeTags.IS_OVERWORLD).decoration(Decoration.UNDERGROUND_ORES).toJson());
	}

	@Override
	public String getName() {
		return "Electrodynamics Biome Features Provider";
	}

}

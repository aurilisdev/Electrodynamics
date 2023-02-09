package electrodynamics.datagen.utils;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SimpleOreFeatureProvider {

	// Chose one or the other
	@Nullable
	private TagKey<Biome> tag;
	@Nullable
	private Biome biome;
	private RegistryObject<PlacedFeature> feature;
	private Decoration decoration;
	private BiomeModifierType modifierType;

	private SimpleOreFeatureProvider(RegistryObject<PlacedFeature> feature, BiomeModifierType type) {
		this.feature = feature;
		modifierType = type;
	}

	public SimpleOreFeatureProvider biomeTag(TagKey<Biome> tag) {
		if (biome != null) {
			throw new UnsupportedOperationException("Specific biome already specified; cannot use tag!");
		}
		this.tag = tag;
		return this;
	}

	public SimpleOreFeatureProvider specificBiome(Biome biome) {
		if (tag != null) {
			throw new UnsupportedOperationException("Biome tag already specified; cannot use specific biome!");
		}
		this.biome = biome;
		return this;
	}

	public SimpleOreFeatureProvider decoration(Decoration decoration) {
		this.decoration = decoration;
		return this;
	}

	public JsonObject toJson() {

		JsonObject json = new JsonObject();

		String modifierName;
		switch (modifierType) {
		case NONE_BIOME:
			modifierName = ForgeMod.NONE_BIOME_MODIFIER_TYPE.getId().toString();
			break;
		case ADD_FEATURES:
			modifierName = ForgeMod.ADD_FEATURES_BIOME_MODIFIER_TYPE.getId().toString();
			break;
		case REMOVE_FEATURES:
			modifierName = ForgeMod.REMOVE_FEATURES_BIOME_MODIFIER_TYPE.getId().toString();
			break;
		case ADD_SPAWNS:
			modifierName = ForgeMod.ADD_SPAWNS_BIOME_MODIFIER_TYPE.getId().toString();
			break;
		case REMOVE_SPAWNS:
			modifierName = ForgeMod.REMOVE_SPAWNS_BIOME_MODIFIER_TYPE.getId().toString();
			break;
		case NONE_STRUCTURE:
			modifierName = ForgeMod.NONE_STRUCTURE_MODIFIER_TYPE.getId().toString();
			break;
		default:
			modifierName = ForgeMod.NONE_BIOME_MODIFIER_TYPE.getId().toString();
		}

		json.addProperty("type", modifierName);

		String biomeName;

		if (tag == null) {
			biomeName = ForgeRegistries.BIOMES.getKey(biome).toString();
		} else {
			biomeName = "#" + tag.location().toString();
		}

		json.addProperty("biomes", biomeName);

		json.addProperty("features", feature.getId().toString());
		
		json.addProperty("step", decoration.getName());

		return json;

	}

	public static SimpleOreFeatureProvider of(RegistryObject<PlacedFeature> feature, BiomeModifierType modifier) {
		return new SimpleOreFeatureProvider(feature, modifier);
	}

	public static enum BiomeModifierType {
		NONE_BIOME, ADD_FEATURES, REMOVE_FEATURES, ADD_SPAWNS, REMOVE_SPAWNS, NONE_STRUCTURE;
	}

}

package electrodynamics.registers;

import java.util.List;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.common.world.ruletest.RuleTestOre;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsFeatures {
	
	/* CONFIGURED FEATURES */
	
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, References.ID);

	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_ALUMINUM_CONFIGURED = configured("ore_aluminum", SubtypeOre.aluminum, SubtypeOreDeepslate.aluminum);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_CHROMIUM_CONFIGURED = configured("ore_chromium", SubtypeOre.chromite, SubtypeOreDeepslate.chromite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_FLUORITE_CONFIGURED = configured("ore_fluorite", SubtypeOre.fluorite, SubtypeOreDeepslate.fluorite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_LEAD_CONFIGURED = configured("ore_lead", SubtypeOre.lead, SubtypeOreDeepslate.lead);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_LITHIUM_CONFIGURED = configured("ore_lithium", SubtypeOre.lepidolite, SubtypeOreDeepslate.lepidolite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_MOLYBDENUM_CONFIGURED = configured("ore_molybdenum", SubtypeOre.molybdenum, SubtypeOreDeepslate.molybdenum);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_MONAZITE_CONFIGURED = configured("ore_monazite", SubtypeOre.monazite, SubtypeOreDeepslate.monazite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_NITER_CONFIGURED = configured("ore_niter", SubtypeOre.niter, SubtypeOreDeepslate.niter);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_SALT_CONFIGURED = configured("ore_salt", SubtypeOre.halite, SubtypeOreDeepslate.halite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_SILVER_CONFIGURED = configured("ore_silver", SubtypeOre.silver, SubtypeOreDeepslate.silver);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_SULFUR_CONFIGURED = configured("ore_sulfur", SubtypeOre.sulfur, SubtypeOreDeepslate.sulfur);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_SYLVITE_CONFIGURED = configured("ore_sylvite", SubtypeOre.sylvite, SubtypeOreDeepslate.sylvite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_TIN_CONFIGURED = configured("ore_tin", SubtypeOre.tin, SubtypeOreDeepslate.tin);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_TITANIUM_CONFIGURED = configured("ore_titanium", SubtypeOre.rutile, SubtypeOreDeepslate.rutile);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_THORIUM_CONFIGURED = configured("ore_thorium", SubtypeOre.thorianite, SubtypeOreDeepslate.thorianite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_URANIUM_CONFIGURED = configured("ore_uranium", SubtypeOre.uraninite, SubtypeOreDeepslate.uraninite);
	public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_VANADIUM_CONFIGURED = configured("ore_vanadium", SubtypeOre.vanadinite, SubtypeOreDeepslate.vanadinite);

	private static RegistryObject<ConfiguredFeature<?, ?>> configured(String name, SubtypeOre ore, SubtypeOreDeepslate deepOre) {
		return CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(Feature.ORE, dualOre(ore, deepOre)));
	}

	private static OreConfiguration dualOre(SubtypeOre ore, SubtypeOreDeepslate deepOre) {
		return new OreConfiguration(List.of(OreConfiguration.target(new RuleTestOre(ore, null, BlockTags.STONE_ORE_REPLACEABLES), ElectrodynamicsBlocks.getBlock(ore).defaultBlockState()), OreConfiguration.target(new RuleTestOre(null, deepOre, BlockTags.DEEPSLATE_ORE_REPLACEABLES), ElectrodynamicsBlocks.getBlock(deepOre).defaultBlockState())), ore.veinSize);
	}

	/* PLACED FEATURES */
	
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, References.ID);

	public static final RegistryObject<PlacedFeature> ORE_ALUMINUM_PLACED = placed("ore_aluminum", ORE_ALUMINUM_CONFIGURED, (int) (SubtypeOre.aluminum.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.aluminum.minY, SubtypeOre.aluminum.maxY);
	public static final RegistryObject<PlacedFeature> ORE_CHROMIUM_PLACED = placed("ore_chromium", ORE_CHROMIUM_CONFIGURED, (int) (SubtypeOre.chromite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.chromite.minY, SubtypeOre.chromite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_FLUORITE_PLACED = placed("ore_fluorite", ORE_FLUORITE_CONFIGURED, (int) (SubtypeOre.fluorite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.fluorite.minY, SubtypeOre.fluorite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_LEAD_PLACED = placed("ore_lead", ORE_LEAD_CONFIGURED, (int) (SubtypeOre.lead.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.lead.minY, SubtypeOre.lead.maxY);
	public static final RegistryObject<PlacedFeature> ORE_LITHIUM_PLACED = placed("ore_lithium", ORE_LITHIUM_CONFIGURED, (int) (SubtypeOre.lepidolite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.lepidolite.minY, SubtypeOre.lepidolite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_MOLYBDENUM_PLACED = placed("ore_molybdenum", ORE_MOLYBDENUM_CONFIGURED, (int) (SubtypeOre.molybdenum.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.molybdenum.minY, SubtypeOre.molybdenum.maxY);
	public static final RegistryObject<PlacedFeature> ORE_MONAZITE_PLACED = placed("ore_monazite", ORE_MONAZITE_CONFIGURED, (int) (SubtypeOre.monazite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.monazite.minY, SubtypeOre.monazite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_NITER_PLACED = placed("ore_niter", ORE_NITER_CONFIGURED, (int) (SubtypeOre.niter.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.niter.minY, SubtypeOre.niter.maxY);
	public static final RegistryObject<PlacedFeature> ORE_SALT_PLACED = placed("ore_salt", ORE_SALT_CONFIGURED, (int) (SubtypeOre.halite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.halite.minY, SubtypeOre.halite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_SILVER_PLACED = placed("ore_silver", ORE_SILVER_CONFIGURED, (int) (SubtypeOre.silver.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.silver.minY, SubtypeOre.silver.maxY);
	public static final RegistryObject<PlacedFeature> ORE_SULFUR_PLACED = placed("ore_sulfur", ORE_SULFUR_CONFIGURED, (int) (SubtypeOre.sulfur.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.sulfur.minY, SubtypeOre.sulfur.maxY);
	public static final RegistryObject<PlacedFeature> ORE_SYLVITE_PLACED = placed("ore_sylvite", ORE_SYLVITE_CONFIGURED, (int) (SubtypeOre.sylvite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.sylvite.minY, SubtypeOre.sylvite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_TIN_PLACED = placed("ore_tin", ORE_TIN_CONFIGURED, (int) (SubtypeOre.tin.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.tin.minY, SubtypeOre.tin.maxY);
	public static final RegistryObject<PlacedFeature> ORE_TITANIUM_PLACED = placed("ore_titanium", ORE_TITANIUM_CONFIGURED, (int) (SubtypeOre.rutile.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.rutile.minY, SubtypeOre.rutile.maxY);
	public static final RegistryObject<PlacedFeature> ORE_THORIUM_PLACED = placed("ore_thorium", ORE_THORIUM_CONFIGURED, (int) (SubtypeOre.thorianite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.thorianite.minY, SubtypeOre.thorianite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_URANIUM_PLACED = placed("ore_uranium", ORE_URANIUM_CONFIGURED, (int) (SubtypeOre.uraninite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.uraninite.minY, SubtypeOre.uraninite.maxY);
	public static final RegistryObject<PlacedFeature> ORE_VANADIUM_PLACED = placed("ore_vanadium", ORE_VANADIUM_CONFIGURED, (int) (SubtypeOre.vanadinite.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER), SubtypeOre.vanadinite.minY, SubtypeOre.vanadinite.maxY);

	private static RegistryObject<PlacedFeature> placed(String name, RegistryObject<ConfiguredFeature<?,?>> registryObject, int count, int minY, int maxY) {
		return PLACED_FEATURES.register(name, () -> new PlacedFeature(new Holder.Direct<>(registryObject.get()), orePlacement(count, minY, maxY)));
	}

	private static List<PlacementModifier> orePlacement(int count, int minY, int maxY) {
		return List.of(CountPlacement.of(count), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), BiomeFilter.biome());
	}

	/* BIOME MODIFIER */

	public static final ResourceLocation ORE_ALUMINUM_MODIFIER = modifier("ore_aluminum");
	public static final ResourceLocation ORE_CHROMIUM_MODIFIER = modifier("ore_chromium");
	public static final ResourceLocation ORE_FLUORITE_MODIFIER = modifier("ore_fluorite");
	public static final ResourceLocation ORE_LEAD_MODIFIER = modifier("ore_lead");
	public static final ResourceLocation ORE_LITHIUM_MODIFIER = modifier("ore_lithium");
	public static final ResourceLocation ORE_MOLYBDENUM_MODIFIER = modifier("ore_molybdenum");
	public static final ResourceLocation ORE_MONAZITE_MODIFIER = modifier("ore_monazite");
	public static final ResourceLocation ORE_NITER_MODIFIER = modifier("ore_niter");
	public static final ResourceLocation ORE_SALT_MODIFIER = modifier("ore_salt");
	public static final ResourceLocation ORE_SILVER_MODIFIER = modifier("ore_silver");
	public static final ResourceLocation ORE_SULFUR_MODIFIER = modifier("ore_sulfur");
	public static final ResourceLocation ORE_SYLVITE_MODIFIER = modifier("ore_sylvite");
	public static final ResourceLocation ORE_TIN_MODIFIER = modifier("ore_tin");
	public static final ResourceLocation ORE_TITANIUM_MODIFIER = modifier("ore_titanium");
	public static final ResourceLocation ORE_THORIUM_MODIFIER = modifier("ore_thorium");
	public static final ResourceLocation ORE_URANIUM_MODIFIER = modifier("ore_uranium");
	public static final ResourceLocation ORE_VANADIUM_MODIFIER = modifier("ore_vanadium");

	private static ResourceLocation modifier(String name) {
		return new ResourceLocation(References.ID, name);
	}
}

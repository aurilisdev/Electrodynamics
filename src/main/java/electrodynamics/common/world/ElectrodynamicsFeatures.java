package electrodynamics.common.world;

import java.util.concurrent.ConcurrentHashMap;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.common.world.ruletest.RuleTestOre;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.ID)
public class ElectrodynamicsFeatures {

	public static final IRuleTestType<RuleTestOre> RULE_TEST_ORE = IRuleTestType.register("rule_test_ore", RuleTestOre.CODEC);
	
	public static final ConcurrentHashMap<SubtypeOre, ConfiguredFeature<?, ?>> SUBTYPEORE_CONFIGUREDFEATURE_MAP = new ConcurrentHashMap<>();

	@SubscribeEvent
	public static void generateOres(final BiomeLoadingEvent event) {
		for (SubtypeOre ore : SubtypeOre.values()) {

			OreFeatureConfig feature = new OreFeatureConfig(new RuleTestOre(ore, BlockTags.BASE_STONE_OVERWORLD), ElectrodynamicsBlocks.getBlock(ore).defaultBlockState(), ore.veinSize);

			ConfiguredPlacement<TopSolidRangeConfig> configuredPlacement = Placement.RANGE.configured(new TopSolidRangeConfig(ore.minY, ore.minY, ore.maxY));

			ConfiguredFeature<?, ?> configuredFeature = registerFeature(ore, feature, configuredPlacement);

			event.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, configuredFeature);

			SUBTYPEORE_CONFIGUREDFEATURE_MAP.put(ore, configuredFeature);
		}
	}

	private static ConfiguredFeature<?, ?> registerFeature(SubtypeOre ore, OreFeatureConfig feature, ConfiguredPlacement<TopSolidRangeConfig> placement) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ore_" + ore.name(), Feature.ORE.configured(feature).count((int) (ore.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER)).decorated(placement).squared());
	}

}

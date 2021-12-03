package electrodynamics.common.world;

import electrodynamics.api.References;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class OreGeneration {
//    public static void registerOres() {
//	for (SubtypeOre ore : SubtypeOre.values()) {
//	    if (OreConfig.oresToSpawn.contains(ore.name())) {
//		OreConfiguration feature = new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE,
//			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(ore).defaultBlockState(), ore.veinSize);
//		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId(),
//			Feature.ORE.configured(feature)
//				.range(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(ore.maxY))))
//				.count((int) (ore.veinsPerChunk * OreConfig.OREGENERATIONMULTIPLIER)).squared());
//	    }
//	}
//	for (SubtypeOreDeepslate ore : SubtypeOreDeepslate.values()) {
//	    if (OreConfig.oresToSpawn.contains(ore.name().replace("deepslate", ""))) {
//		OreConfiguration feature = new OreConfiguration(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES,
//			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(ore).defaultBlockState(), ore.veinSize);
//		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId(),
//			Feature.ORE.configured(feature)
//				.range(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(ore.maxY))))
//				.count((int) (ore.veinsPerChunk * OreConfig.OREGENERATIONMULTIPLIER)).squared());
//	    }
//	}
//    }
//
//    @SubscribeEvent
//    public static void gen(BiomeLoadingEvent event) {
//	for (SubtypeOre ore : SubtypeOre.values()) {
//	    BiomeGenerationSettingsBuilder gen = event.getGeneration();
//	    if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND) {
//		gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
//			BuiltinRegistries.CONFIGURED_FEATURE.get(DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId()));
//	    }
//	}
//	for (SubtypeOreDeepslate ore : SubtypeOreDeepslate.values()) {
//	    BiomeGenerationSettingsBuilder gen = event.getGeneration();
//	    if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND) {
//		gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
//			BuiltinRegistries.CONFIGURED_FEATURE.get(DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId()));
//	    }
//	}
//    }
}

package electrodynamics.common.world;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.settings.OreConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class OreGeneration {
    public static void registerOres() {
	for (SubtypeOre ore : SubtypeOre.values()) {
	    if (OreConfig.oresToSpawn.contains(ore.name())) {
		OreConfiguration feature = new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE,
			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(ore).defaultBlockState(), ore.veinSize);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId(),
			Feature.ORE.configured(feature)
				.range(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(ore.maxY))))
				.count((int) (ore.veinsPerChunk * OreConfig.OREGENERATIONMULTIPLIER)).squared());
	    }
	}
    }

    @SubscribeEvent
    public static void gen(BiomeLoadingEvent event) {
	for (SubtypeOre ore : SubtypeOre.values()) {
	    BiomeGenerationSettingsBuilder gen = event.getGeneration();
	    if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND) {
		gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
			BuiltinRegistries.CONFIGURED_FEATURE.get(DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId()));
	    }
	}
    }
}

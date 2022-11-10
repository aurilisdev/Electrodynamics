package electrodynamics.common.world;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.settings.OreConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class OreGeneration {

	public static void registerOres() { // called in Electrodynamics constructor
		for (SubtypeOre ore : SubtypeOre.values()) {
			if (OreConfig.oresToSpawn.contains(ore.name())) {
				Supplier<List<OreConfiguration.TargetBlockState>> targetSupplier = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, DeferredRegisters.getSafeBlock(ore).defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, DeferredRegisters.getSafeBlock(SubtypeOreDeepslate.values()[ore.ordinal()]).defaultBlockState())));
				Supplier<ConfiguredFeature<?, ?>> featureSupplier = () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetSupplier.get(), ore.veinSize));
				RegistryObject<ConfiguredFeature<?, ?>> registered = DeferredRegisters.CONFIGURED_FEATURES.register(ore.tag(), featureSupplier);
				Supplier<PlacedFeature> placedSupplier = () -> new PlacedFeature(registered.getHolder().get(), List.of(CountPlacement.of((int) (ore.veinsPerChunk * OreConfig.OREGENERATIONMULTIPLIER)), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(ore.minY), VerticalAnchor.absolute(ore.maxY)), BiomeFilter.biome()));
				DeferredRegisters.PLACED_FEATURES.register(ore.tag(), placedSupplier);
			}
		}
	}

	public static void generateSulfurAround(RandomSource random, BlockPos pos, WorldGenLevel level) {
		if (OreConfig.oresToSpawn.contains(SubtypeOre.sulfur.name())) {
			for (Direction direction : Direction.values()) {
				BlockPos offset = pos.offset(direction.getNormal());
				if (random.nextFloat() < 0.3) {
					if (level.getBlockState(offset).getBlock() == Blocks.STONE) {
						level.setBlock(offset, DeferredRegisters.getSafeBlock(SubtypeOre.sulfur).defaultBlockState(), 3);
					}
					if (level.getBlockState(offset).getBlock() == Blocks.DEEPSLATE) {
						level.setBlock(offset, DeferredRegisters.getSafeBlock(SubtypeOreDeepslate.sulfur).defaultBlockState(), 3);
					}
				}
			}
		}
	}
}

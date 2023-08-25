package electrodynamics.registers;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.settings.OreConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsFeatures {
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registries.CONFIGURED_FEATURE, References.ID);
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registries.PLACED_FEATURE, References.ID);

	static {
		// Ore Generation
		for (SubtypeOre ore : SubtypeOre.values()) {
			if (OreConfig.oresToSpawn.contains(ore.name())) {
				Supplier<List<OreConfiguration.TargetBlockState>> targetSupplier = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ElectrodynamicsBlocks.getBlock(ore).defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ElectrodynamicsBlocks.getBlock(SubtypeOreDeepslate.values()[ore.ordinal()]).defaultBlockState())));
				Supplier<ConfiguredFeature<?, ?>> featureSupplier = () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetSupplier.get(), ore.veinSize));
				RegistryObject<ConfiguredFeature<?, ?>> registered = CONFIGURED_FEATURES.register(ore.tag(), featureSupplier);
				Supplier<PlacedFeature> placedSupplier = () -> new PlacedFeature(registered.getHolder().get(), List.of(CountPlacement.of((int) (ore.veinsPerChunk * OreConfig.OREGENERATIONMULTIPLIER)), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(ore.minY), VerticalAnchor.absolute(ore.maxY)), BiomeFilter.biome()));
				PLACED_FEATURES.register(ore.tag(), placedSupplier);
			}
		}
	}
}

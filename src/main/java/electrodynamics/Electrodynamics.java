package electrodynamics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import electrodynamics.api.References;
import electrodynamics.api.configuration.ConfigurationHandler;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.settings.Constants;
import electrodynamics.packet.NetworkHandler;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class Electrodynamics {

    public Electrodynamics() {
	ConfigurationHandler.registerConfig(Constants.class);
	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
	DeferredRegisters.BLOCKS.register(bus);
	DeferredRegisters.ITEMS.register(bus);
	DeferredRegisters.TILES.register(bus);
	DeferredRegisters.CONTAINERS.register(bus);

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
	CapabilityElectrodynamic.register();
	NetworkHandler.init();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
	ClientRegister.setup();
    }

    @SubscribeEvent
    public static void onLoadEvent(FMLLoadCompleteEvent event) {
	for (SubtypeOre ore : SubtypeOre.values()) {
	    OreFeatureConfig feature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
		    DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(ore).getDefaultState(), ore.veinSize);
	    Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
		    DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId(),
		    Feature.ORE.withConfiguration(feature).range(ore.maxY).func_242731_b(ore.veinsPerChunk).square());
	}
	setupGen();
    }

    @Deprecated
    public static void setupGen() {
	for (SubtypeOre ore : SubtypeOre.values()) {
	    for (Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.BIOME.getEntries()) {
		if (!biome.getValue().getCategory().equals(Biome.Category.NETHER)
			&& !biome.getValue().getCategory().equals(Biome.Category.THEEND)) {
		    addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.UNDERGROUND_ORES,
			    WorldGenRegistries.CONFIGURED_FEATURE
				    .getOrDefault(DeferredRegisters.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId()));
		}
	    }
	}
    }

    public static void addFeatureToBiome(Biome biome, GenerationStage.Decoration decoration,
	    ConfiguredFeature<?, ?> configuredFeature) {
	List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(
		biome.getGenerationSettings().getFeatures());

	while (biomeFeatures.size() <= decoration.ordinal()) {
	    biomeFeatures.add(Lists.newArrayList());
	}

	List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
	features.add(() -> configuredFeature);
	biomeFeatures.set(decoration.ordinal(), features);

	ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(),
		biomeFeatures, "field_242484_f");
    }
}

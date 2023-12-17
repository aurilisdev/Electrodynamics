package electrodynamics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.voxelshapes.ElectrodynamicsVoxelShapeRegistry;
import electrodynamics.common.condition.ConfigCondition;
import electrodynamics.common.eventbus.RegisterPropertiesEvent;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.client.PacketResetGuidebookPages;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.configuration.ConfigurationHandler;
import electrodynamics.prefab.properties.PropertyManager;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.UnifiedElectrodynamicsRegister;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class Electrodynamics {

	public static Logger LOGGER = LogManager.getLogger(electrodynamics.api.References.ID);

	public static final Random RANDOM = new Random();

	public Electrodynamics() {
		ConfigurationHandler.registerConfig(Constants.class);
		ConfigurationHandler.registerConfig(OreConfig.class);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		UnifiedElectrodynamicsRegister.register(bus);
		Electrodynamics.LOGGER.info("Starting Electrodynamics recipe engine");
		ElectrodynamicsRecipeInit.RECIPE_SERIALIZER.register(bus);
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		NetworkHandler.init();
		CombustionFuelRegister.INSTANCE = new CombustionFuelRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		CoalGeneratorFuelRegister.INSTANCE = new CoalGeneratorFuelRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		ThermoelectricGeneratorHeatRegister.INSTANCE = new ThermoelectricGeneratorHeatRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		MinecraftForge.EVENT_BUS.addListener(getGuidebookListener());
		ElectrodynamicsTags.init();

		event.enqueueWork(() -> {
			ElectrodynamicsCapabilities.register();

			RegisterPropertiesEvent properties = new RegisterPropertiesEvent();

			ModLoader.get().postEvent(properties);

			PropertyManager.registerProperties(properties.getRegisteredProperties());
		});
		ElectrodynamicsVoxelShapeRegistry.init();
	}

	@SubscribeEvent
	public static void registerProperties(RegisterPropertiesEvent event) {
		for (PropertyType type : PropertyType.values()) {
			event.registerProperty(type);
		}
	}

	@SubscribeEvent
	public static void registerRecipeSerialziers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
	}

	// I wonder how long this bug has been there
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegister.setup();
		});
	}

	@SubscribeEvent
	@Deprecated
	public static void onLoadEvent(FMLLoadCompleteEvent event) {

		for (SubtypeOre ore : SubtypeOre.values()) {

			boolean spawnOre = false;

			switch (ore) {
			case aluminum:
				spawnOre = OreConfig.SPAWN_ALUMINUM_ORE;
				break;
			case chromite:
				spawnOre = OreConfig.SPAWN_CHROMIUM_ORE;
				break;
			case fluorite:
				spawnOre = OreConfig.SPAWN_FLUORITE_ORE;
				break;
			case lead:
				spawnOre = OreConfig.SPAWN_LEAD_ORE;
				break;
			case lepidolite:
				spawnOre = OreConfig.SPAWN_LITHIUM_ORE;
				break;
			case molybdenum:
				spawnOre = OreConfig.SPAWN_MOLYBDENUM_ORE;
				break;
			case monazite:
				spawnOre = OreConfig.SPAWN_MONAZITE_ORE;
				break;
			case niter:
				spawnOre = OreConfig.SPAWN_NITER_ORE;
				break;
			case halite:
				spawnOre = OreConfig.SPAWN_SALT_ORE;
				break;
			case silver:
				spawnOre = OreConfig.SPAWN_SILVER_ORE;
				break;
			case sulfur:
				spawnOre = OreConfig.SPAWN_SULFUR_ORE;
				break;
			case sylvite:
				spawnOre = OreConfig.SPAWN_SYLVITE_ORE;
				break;
			case thorianite:
				spawnOre = OreConfig.SPAWN_THORIUM_ORE;
				break;
			case tin:
				spawnOre = OreConfig.SPAWN_TIN_ORE;
				break;
			case rutile:
				spawnOre = OreConfig.SPAWN_TITANIUM_ORE;
				break;
			case uraninite:
				spawnOre = OreConfig.SPAWN_URANIUM_ORE;
				break;
			case vanadinite:
				spawnOre = OreConfig.SPAWN_VANADIUM_ORE;
				break;
			default:
				spawnOre = false;
				break;
			}

			if (!spawnOre) {
				continue;
			}

			OreFeatureConfig feature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ElectrodynamicsBlocks.getBlock(ore).defaultBlockState(), ore.veinSize);

			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId(), Feature.ORE.configured(feature).range(ore.maxY).count((int) (ore.veinsPerChunk * OreConfig.ORE_GENERATION_MULTIPLIER)).squared());

			ConfiguredFeature<?, ?> configuredFeature = WorldGenRegistries.CONFIGURED_FEATURE.get(ElectrodynamicsBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS.get(ore).getId());

			int decorationOrdinal = GenerationStage.Decoration.UNDERGROUND_ORES.ordinal();

			for (Entry<RegistryKey<Biome>, Biome> entry : WorldGenRegistries.BIOME.entrySet()) {

				Biome biome = entry.getValue();

				if (biome.getBiomeCategory() == Category.NETHER || biome.getBiomeCategory() == Category.NETHER) {
					continue;
				}

				List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(biome.getGenerationSettings().features());

				while (biomeFeatures.size() <= decorationOrdinal) {
					biomeFeatures.add(Lists.newArrayList());
				}

				List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decorationOrdinal));

				features.add(() -> configuredFeature);
				biomeFeatures.set(decorationOrdinal, features);

				ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(), biomeFeatures, "features");

			}

		}

	}

	// Don't really have a better place to put this for now
	private static Consumer<OnDatapackSyncEvent> getGuidebookListener() {

		return event -> {
			ServerPlayerEntity player = event.getPlayer();
			PacketTarget target = player == null ? PacketDistributor.ALL.noArg() : PacketDistributor.PLAYER.with(() -> player);
			NetworkHandler.CHANNEL.send(target, new PacketResetGuidebookPages());
		};

	}

}

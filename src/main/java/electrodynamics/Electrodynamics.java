package electrodynamics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.api.References;
import electrodynamics.api.capability.ceramicplate.CapabilityCeramicPlate;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.BlockCustomGlass;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.prefab.configuration.ConfigurationHandler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class Electrodynamics {

    public static Logger LOGGER = LogManager.getLogger(electrodynamics.api.References.ID);

    public Electrodynamics() {
	ConfigurationHandler.registerConfig(Constants.class);
	ConfigurationHandler.registerConfig(OreConfig.class);
	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
	SoundRegister.SOUNDS.register(bus);
	DeferredRegisters.BLOCKS.register(bus);
	DeferredRegisters.ITEMS.register(bus);
	ElectrodynamicsRecipeInit.RECIPE_SERIALIZER.register(bus);
	DeferredRegisters.TILES.register(bus);
	DeferredRegisters.CONTAINERS.register(bus);
	DeferredRegisters.FLUIDS.register(bus);
	DeferredRegisters.ENTITIES.register(bus);
    }

    //TODO: ERROR  Parsing error loading custom advancement electrodynamics:adv9: Advancement criteria cannot be empty
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
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
	NetworkHandler.init();
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
	CapabilityElectrodynamic.register(event);
	CapabilityCeramicPlate.register(event);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
	for (RegistryObject<Block> block : DeferredRegisters.BLOCKS.getEntries()) {
	    if (block.get() instanceof BlockCustomGlass) {
		ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.cutout());
	    }
	}
	ClientRegister.setup();
    }
}

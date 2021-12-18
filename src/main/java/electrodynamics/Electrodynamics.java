package electrodynamics;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.api.References;
import electrodynamics.api.capability.ceramicplate.CapabilityCeramicPlateStorage;
import electrodynamics.api.capability.dirstorage.CapabilityDirectionalStorage;
import electrodynamics.api.capability.electrodynamic.CapabilityElectrodynamic;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.BlockCustomGlass;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.common.world.OreGeneration;
import electrodynamics.prefab.configuration.ConfigurationHandler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
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
import net.minecraftforge.registries.RegistryObject;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class Electrodynamics {

	public static Logger LOGGER = LogManager.getLogger(electrodynamics.api.References.ID);

	public static final Random RANDOM = new Random();

	public Electrodynamics() {
		ConfigurationHandler.registerConfig(Constants.class);
		ConfigurationHandler.registerConfig(OreConfig.class);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		SoundRegister.SOUNDS.register(bus);
		DeferredRegisters.BLOCKS.register(bus);
		DeferredRegisters.ITEMS.register(bus);
		DeferredRegisters.TILES.register(bus);
		DeferredRegisters.CONTAINERS.register(bus);
		DeferredRegisters.FLUIDS.register(bus);
		DeferredRegisters.ENTITIES.register(bus);
		Electrodynamics.LOGGER.info("Starting Electrodynamics recipe engine");
		ElectrodynamicsRecipeInit.RECIPE_SERIALIZER.register(bus);
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		OreGeneration.registerOres();
		NetworkHandler.init();
		ElectrodynamicsTags.init();
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		CapabilityElectrodynamic.register(event);
		CapabilityCeramicPlateStorage.register(event);
		CapabilityDirectionalStorage.register(event);
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

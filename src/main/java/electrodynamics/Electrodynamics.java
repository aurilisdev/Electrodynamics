package electrodynamics;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.condition.ConfigCondition;
import electrodynamics.common.entity.ElectrodynamicsAttributeModifiers;
import electrodynamics.common.event.ServerEventHandler;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.configuration.ConfigurationHandler;
import electrodynamics.registers.UnifiedElectrodynamicsRegister;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class Electrodynamics {

	public static Logger LOGGER = LogManager.getLogger(electrodynamics.api.References.ID);

	public static final Random RANDOM = new Random();

	public Electrodynamics() {
		ConfigurationHandler.registerConfig(Constants.class);
		ConfigurationHandler.registerConfig(OreConfig.class);
		// MUST GO BEFORE BLOCKS!!!!
		ElectrodynamicsBlockStates.init();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		UnifiedElectrodynamicsRegister.register(bus);
		Electrodynamics.LOGGER.info("Starting Electrodynamics recipe engine");
		ElectrodynamicsRecipeInit.RECIPE_TYPES.register(bus);
		ElectrodynamicsRecipeInit.RECIPE_SERIALIZER.register(bus);
		ElectrodynamicsAttributeModifiers.init();
		
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		ServerEventHandler.init();
		NetworkHandler.init();
		CombustionFuelRegister.INSTANCE = new CombustionFuelRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		CoalGeneratorFuelRegister.INSTANCE = new CoalGeneratorFuelRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		ElectrodynamicsTags.init();
		CraftingHelper.register(ConfigCondition.Serializer.INSTANCE); // Probably wrong location after update from 1.18.2 to 1.19.2
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		ElectrodynamicsCapabilities.register(event);

	}

//	@SubscribeEvent
//	public static void registerRecipeSerialziers(Register<RecipeSerializer<?>> event) {
//		CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
//	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		ClientRegister.setup();
	}
}

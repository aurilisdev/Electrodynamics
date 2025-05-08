package electrodynamics;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.block.voxelshapes.ElectrodynamicsVoxelShapes;
import electrodynamics.common.entity.ElectrodynamicsAttributeModifiers;
import electrodynamics.common.event.ServerEventHandler;
import electrodynamics.common.eventbus.RegisterWiresEvent;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.settings.OreConfig;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.UnifiedElectrodynamicsRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import voltaic.prefab.configuration.ConfigurationHandler;

@Mod(Electrodynamics.ID)
@EventBusSubscriber(modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
public class Electrodynamics {

	public static final String ID = "electrodynamics";
	public static final String NAME = "Electrodynamics";

	public Electrodynamics() {
		ConfigurationHandler.registerConfig(ElectroConstants.class);
		ConfigurationHandler.registerConfig(OreConfig.class);
		// MUST GO BEFORE BLOCKS!!!!
		ElectrodynamicsBlockStates.init();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		UnifiedElectrodynamicsRegister.register(bus);
		ElectrodynamicsAttributeModifiers.init();
		bus.register(RegisterWiresEvent.class);

	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		ServerEventHandler.init();
		NetworkHandler.init();
		CombustionFuelRegister.INSTANCE = new CombustionFuelRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		CoalGeneratorFuelRegister.INSTANCE = new CoalGeneratorFuelRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		ThermoelectricGeneratorHeatRegister.INSTANCE = new ThermoelectricGeneratorHeatRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		//CraftingHelper.register(ConfigCondition.Serializer.INSTANCE); // Probably wrong location after update from 1.18.2 to 1.19.2

		event.enqueueWork(() -> {

			RegisterWiresEvent wiresEvent = new RegisterWiresEvent();

			MinecraftForge.EVENT_BUS.post(wiresEvent);

			wiresEvent.process();
		});
		ElectrodynamicsVoxelShapes.init();
	}
	
	@SubscribeEvent
    public static void registerWires(RegisterWiresEvent event) {
        for (BlockWire wire : ElectrodynamicsBlocks.BLOCKS_WIRE.getAllValues()) {
            event.registerWire(wire);
        }
    }

	// I wonder how long this bug has been there
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ElectrodynamicsClientRegister.setup();
		});
	}

	public static final ResourceLocation rl(String path) {
		return new ResourceLocation(ID, path);
	}

}

package electrodynamics;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.block.voxelshapes.ElectrodynamicsVoxelShapes;
import electrodynamics.common.entity.ElectrodynamicsAttributeModifiers;
import electrodynamics.common.event.ServerEventHandler;
import electrodynamics.common.eventbus.RegisterWiresEvent;
import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.reloadlistener.GasCollectorChromoCardsRegister;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.UnifiedElectrodynamicsRegister;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Electrodynamics.ID)
@EventBusSubscriber(modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
public class Electrodynamics {

    public static final String ID = "electrodynamics";
    public static final String NAME = "Electrodynamics";

    public Electrodynamics(IEventBus bus) {
        // MUST GO BEFORE BLOCKS!!!!
        ElectrodynamicsBlockStates.init();
        ElectrodynamicsVoxelShapes.init();
        UnifiedElectrodynamicsRegister.register(bus);

        ElectrodynamicsAttributeModifiers.init();

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ServerEventHandler.init();
        CombustionFuelRegister.INSTANCE = new CombustionFuelRegister().subscribeAsSyncable();
        CoalGeneratorFuelRegister.INSTANCE = new CoalGeneratorFuelRegister().subscribeAsSyncable();
        GasCollectorChromoCardsRegister.INSTANCE = new GasCollectorChromoCardsRegister().subscribeAsSyncable();
        ThermoelectricGeneratorHeatRegister.INSTANCE = new ThermoelectricGeneratorHeatRegister().subscribeAsSyncable();
        // CraftingHelper.register(ConfigCondition.Serializer.INSTANCE); // Probably wrong location after update from 1.18.2 to
        // 1.19.2

        // RegisterFluidToGasMapEvent map = new RegisterFluidToGasMapEvent();
        // MinecraftForge.EVENT_BUS.post(map);
        // ElectrodynamicsGases.MAPPED_GASSES.putAll(map.fluidToGasMap);

        event.enqueueWork(() -> {

            RegisterWiresEvent wiresEvent = new RegisterWiresEvent();

            ModLoader.postEvent(wiresEvent);

            wiresEvent.process();
        });

    }

    // I wonder how long this bug has been there
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ElectrodynamicsClientRegister.setup();
        });
    }


    @SubscribeEvent
    public static void registerWires(RegisterWiresEvent event) {
        for (BlockWire wire : ElectrodynamicsBlocks.BLOCKS_WIRE.getAllValues()) {
            event.registerWire(wire);
        }
    }

    public static final ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

}

package electrodynamics.datagen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.datagen.client.ElectrodynamicsBlockModelsProvider;
import electrodynamics.datagen.client.ElectrodynamicsBlockStateProvider;
import electrodynamics.datagen.client.ElectrodynamicsItemModelsProvider;
import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider;
import electrodynamics.datagen.client.ElectrodynamicsSoundProvider;
import electrodynamics.datagen.server.CoalGeneratorFuelSourceProvider;
import electrodynamics.datagen.server.CombustionChamberFuelSourceProvider;
import electrodynamics.datagen.server.ElectrodynamicsAdvancementProvider;
import electrodynamics.datagen.server.ElectrodynamicsBiomeModifierProvider;
import electrodynamics.datagen.server.ElectrodynamicsBlockTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsFluidTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsItemTagsProvider;
import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import electrodynamics.datagen.server.ThermoelectricGenHeatSourceProvider;
import electrodynamics.datagen.server.radiation.ElectrodynamicsRadiationShieldingProvider;
import electrodynamics.datagen.server.radiation.ElectrodynamicsRadioactiveBlocksProvider;
import electrodynamics.datagen.server.radiation.ElectrodynamicsRadioactiveItemsProvider;
import electrodynamics.datagen.server.recipe.ElectrodynamicsRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import voltaic.api.network.cable.type.IWire;
import voltaic.datagen.utils.client.BaseLangKeyProvider.Locale;

@Mod.EventBusSubscriber(modid = Electrodynamics.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	
	public static final HashMap<IWire.IWireClass, HashSet<SubtypeWire>> WIRES = new HashMap<>();

    static {
        for (SubtypeWire wire : SubtypeWire.values()) {
            HashSet<SubtypeWire> wireSet = WIRES.getOrDefault(wire.getWireClass(), new HashSet<>());
            wireSet.add(wire);
            WIRES.put(wire.getWireClass(), wireSet);
        }
    }

    @Nullable
    public static SubtypeWire getWire(IWire.IWireMaterial conductor, SubtypeWire.InsulationMaterial insulation, SubtypeWire.WireClass wireClass, SubtypeWire.WireColor color) {

        for (SubtypeWire wire : WIRES.getOrDefault(wireClass, new HashSet<>())) {
            if (wire.getWireMaterial() == conductor && wire.getInsulation() == insulation && wire.getWireClass() == wireClass && wire.getWireColor() == color) {
                return wire;
            }
        }
        return null;
    }

    public static SubtypeWire[] getWires(IWire.IWireMaterial[] conductors, SubtypeWire.InsulationMaterial insulation, SubtypeWire.WireClass wireClass, SubtypeWire.WireColor... colors) {

        List<SubtypeWire> list = new ArrayList<>();

        SubtypeWire wire;
        for (IWire.IWireMaterial conductor : conductors) {
            for (SubtypeWire.WireColor color : colors) {
                wire = getWire(conductor, insulation, wireClass, color);
                if (wire != null) {
                    list.add(wire);
                }
            }
        }

        return list.toArray(new SubtypeWire[0]);
    }


	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			ElectrodynamicsBlockTagsProvider blockProvider = new ElectrodynamicsBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(true, blockProvider);
			generator.addProvider(true, new ElectrodynamicsItemTagsProvider(generator, blockProvider, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsFluidTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsLootTablesProvider(generator));
			generator.addProvider(true, new ElectrodynamicsRecipeProvider(generator));
			generator.addProvider(true, new ElectrodynamicsBiomeModifierProvider(generator));
			generator.addProvider(true, new CombustionChamberFuelSourceProvider(generator));
			generator.addProvider(true, new CoalGeneratorFuelSourceProvider(generator));
			generator.addProvider(true, new ThermoelectricGenHeatSourceProvider(generator));
			generator.addProvider(true, new ElectrodynamicsAdvancementProvider(generator));
			generator.addProvider(true, new ElectrodynamicsRadioactiveBlocksProvider(generator));
            generator.addProvider(true, new ElectrodynamicsRadioactiveItemsProvider(generator));
            generator.addProvider(true, new ElectrodynamicsRadiationShieldingProvider(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(true, new ElectrodynamicsBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsBlockModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsItemModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new ElectrodynamicsLangKeyProvider(generator, Locale.EN_US));
			generator.addProvider(true, new ElectrodynamicsSoundProvider(generator, event.getExistingFileHelper()));
		}
	}

}

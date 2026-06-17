package electrodynamics.registers;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister
	    .create(Registries.CREATIVE_MODE_TAB, Electrodynamics.ID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main",
	    () -> CreativeModeTab.builder().title(ElectroTextUtils.creativeTab("main")).icon(
		    () -> new ItemStack(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnace)))
		    .build());
    public static final RegistryObject<CreativeModeTab> GRID = CREATIVE_TABS.register("grid",
	    () -> CreativeModeTab.builder().title(ElectroTextUtils.creativeTab("grid"))
		    .icon(() -> new ItemStack(
			    ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.downgradetransformer)))
		    .build());

}

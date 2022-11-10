package electrodynamics.common.tab;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsRegisters;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupElectrodynamics extends CreativeModeTab {

	public ItemGroupElectrodynamics(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ElectrodynamicsRegisters.getSafeBlock(SubtypeMachine.downgradetransformer));
	}
}

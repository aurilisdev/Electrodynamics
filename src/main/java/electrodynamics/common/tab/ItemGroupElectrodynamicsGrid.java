package electrodynamics.common.tab;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupElectrodynamicsGrid extends CreativeModeTab {

	public ItemGroupElectrodynamicsGrid(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.downgradetransformer));
	}
}

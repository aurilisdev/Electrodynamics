package electrodynamics.common.tab;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupElectrodynamics extends ItemGroup {

	public ItemGroupElectrodynamics(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ElectrodynamicsItems.getItem(SubtypeMachine.downgradetransformer));
	}
}

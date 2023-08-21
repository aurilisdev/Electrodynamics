package electrodynamics.common.tab;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupElectroGrid extends CreativeModeTab {

	public ItemGroupElectroGrid(String label) {
		super(label);
	}

	@Override
	public ItemStack getIconItem() {
		return new ItemStack(ElectrodynamicsBlocks.getBlock(SubtypeMachine.downgradetransformer));
	}

}

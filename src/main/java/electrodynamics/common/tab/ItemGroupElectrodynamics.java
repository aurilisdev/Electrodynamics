package electrodynamics.common.tab;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.subtype.SubtypeMachine;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupElectrodynamics extends ItemGroup {

    public ItemGroupElectrodynamics(String label) {
	super(label);
    }

    @Override
    public ItemStack createIcon() {
	return new ItemStack(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.downgradetransformer));
    }
}

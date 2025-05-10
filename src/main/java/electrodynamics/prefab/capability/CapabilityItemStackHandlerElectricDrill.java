package electrodynamics.prefab.capability;

import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.item.ItemStack;
import voltaic.api.item.CapabilityItemStackHandler;

public class CapabilityItemStackHandlerElectricDrill extends CapabilityItemStackHandler {

	public CapabilityItemStackHandlerElectricDrill(int size, ItemStack owner) {
		super(size, owner);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		if(getStackInSlot(0).isEmpty()) {
			insertItem(0, new ItemStack(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(SubtypeDrillHead.steel)), false);
		}
	}

}

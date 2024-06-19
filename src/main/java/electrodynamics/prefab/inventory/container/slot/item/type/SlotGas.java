package electrodynamics.prefab.inventory.container.slot.item.type;

import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.registers.ElectrodynamicsCapabilities;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SlotGas extends SlotGeneric {

    public SlotGas(Container inventory, int index, int x, int y) {
        super(SlotType.NORMAL, IconType.GAS_DARK, inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return super.mayPlace(stack) && stack.getCapability(ElectrodynamicsCapabilities.CAPABILITY_GASHANDLER_ITEM) != null;
    }

}

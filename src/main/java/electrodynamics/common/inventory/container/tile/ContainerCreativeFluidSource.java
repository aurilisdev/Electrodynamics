package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.fluid.TileCreativeFluidSource;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.prefab.inventory.container.slot.item.type.SlotFluid;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerCreativeFluidSource extends GenericContainerBlockEntity<TileCreativeFluidSource> {

    public ContainerCreativeFluidSource(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(2), new SimpleContainerData(5));
    }

    public ContainerCreativeFluidSource(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEFLUIDSOURCE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new SlotFluid(inv, nextIndex(), 58, 34));
	addSlot(new SlotFluid(inv, nextIndex(), 133, 34));
    }

}

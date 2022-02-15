package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileQuarry;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.BlockItem;

public class ContainerQuarry extends GenericContainerBlockEntity<TileQuarry> {

	public ContainerQuarry(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_QUARRY.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerQuarry(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(19), new SimpleContainerData(3));
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		playerInvOffset = 58;
		addSlot(new SlotRestricted(inv, nextIndex(), 153, 26, ItemDrillHead.class));
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlot(new SlotRestricted(inv, nextIndex(), 85 + j * 18, 26 + i * 18, BlockItem.class));
			}
		}
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlot(new SlotRestricted(inv, nextIndex(), 85 + j * 18, 75 + i * 18));
			}
		}
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 71, SubtypeItemUpgrade.fortune, SubtypeItemUpgrade.silktouch, SubtypeItemUpgrade.unbreaking, SubtypeItemUpgrade.itemvoid));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 91, SubtypeItemUpgrade.fortune, SubtypeItemUpgrade.silktouch, SubtypeItemUpgrade.unbreaking, SubtypeItemUpgrade.itemvoid));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 111, SubtypeItemUpgrade.fortune, SubtypeItemUpgrade.silktouch, SubtypeItemUpgrade.unbreaking, SubtypeItemUpgrade.itemvoid));
	}

}

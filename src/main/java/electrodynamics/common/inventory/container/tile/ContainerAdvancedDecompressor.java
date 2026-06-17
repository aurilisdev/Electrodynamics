package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.tile.pipelines.gas.gastransformer.compressor.GenericTileAdvancedCompressor;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.item.type.SlotGas;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerAdvancedDecompressor
	extends GenericContainerBlockEntity<GenericTileAdvancedCompressor.TileAdvancedDecompressor> {

    public static final SubtypeItemUpgrade[] VALID_UPGRADES = {
	    SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed };

    public ContainerAdvancedDecompressor(int id, Inventory playerinv, Container inventory,
	    ContainerData inventorydata) {
	super(ElectrodynamicsMenuTypes.CONTAINER_ADVANCEDDECOMPRESSOR.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerAdvancedDecompressor(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(5), new SimpleContainerData(3));
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	setPlayerInvOffset(47);
	addSlot(new SlotGas(inv, nextIndex(), 20, 50));
	addSlot(new SlotGas(inv, nextIndex(), 109, 50));
	addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
	addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
	addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
    }
}

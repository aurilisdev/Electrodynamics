package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.MachineRecipes;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileTicking;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentContainerProvider;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentInventory;
import electrodynamics.common.tile.generic.component.type.ComponentPacketHandler;
import electrodynamics.common.tile.generic.component.type.ComponentProcessor;
import electrodynamics.common.tile.generic.component.type.ComponentProcessorType;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.util.Direction;

public class TileWireMill extends GenericTileTicking {
    public TileWireMill() {
	super(DeferredRegisters.TILE_WIREMILL.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable());
	addComponent(new ComponentElectrodynamic(this).addRelativeInputDirection(Direction.NORTH));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotsOnFace(Direction.UP, 0)
		.addSlotsOnFace(Direction.DOWN, 1).addRelativeSlotsOnFace(Direction.EAST, 0)
		.addRelativeSlotsOnFace(Direction.WEST, 1).setItemValidPredicate(
			(slot, stack) -> slot == 0 || slot != 1 && stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentContainerProvider("container.wiremill")
		.setCreateMenuFunction((id, player) -> new ContainerO2OProcessor(id, player,
			getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(2, 3, 4)
		.setCanProcess(component -> MachineRecipes.canProcess(this))
		.setProcess(component -> MachineRecipes.process(this))
		.setRequiredTicks(Constants.WIREMILL_REQUIRED_TICKS).setJoulesPerTick(Constants.WIREMILL_USAGE_PER_TICK)
		.setType(ComponentProcessorType.ObjectToObject));
    }
}

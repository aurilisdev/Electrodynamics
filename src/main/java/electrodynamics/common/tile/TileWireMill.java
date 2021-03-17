package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentContainerProvider;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentInventory;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.tile.components.type.ComponentProcessorType;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.MachineRecipes;
import electrodynamics.common.settings.Constants;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;

public class TileWireMill extends GenericTileTicking {
    public TileWireMill() {
	super(DeferredRegisters.TILE_WIREMILL.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().addTickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).addRelativeInputDirection(Direction.NORTH));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotsOnFace(Direction.UP, 0).addSlotsOnFace(Direction.DOWN, 1)
		.addRelativeSlotsOnFace(Direction.EAST, 0).addRelativeSlotsOnFace(Direction.WEST, 1)
		.setItemValidPredicate((slot, stack) -> slot == 0 || slot != 1 && stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentContainerProvider("container.wiremill").setCreateMenuFunction(
		(id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(2, 3, 4).setCanProcess(component -> MachineRecipes.canProcess(this))
		.setProcess(component -> MachineRecipes.process(this)).setRequiredTicks(Constants.WIREMILL_REQUIRED_TICKS)
		.setJoulesPerTick(Constants.WIREMILL_USAGE_PER_TICK).setType(ComponentProcessorType.ObjectToObject));
    }

    protected void tickClient(ComponentTickable tickable) {
	if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.5 + 0.5,
		    pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
    }
}

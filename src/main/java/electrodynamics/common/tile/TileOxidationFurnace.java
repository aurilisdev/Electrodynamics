package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
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

public class TileOxidationFurnace extends GenericTileTicking {
    public static final int[] SLOTS_OUTPUT = new int[] { 2 };

    public TileOxidationFurnace() {
	super(DeferredRegisters.TILE_OXIDATIONFURNACE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable());
	addComponent(new ComponentElectrodynamic(this).addRelativeInputDirection(Direction.NORTH)
		.setVoltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotsOnFace(Direction.UP, 0, 1)
		.addSlotsOnFace(Direction.DOWN, 2));
	addComponent(new ComponentContainerProvider("container.mineralcrusher")
		.setCreateMenuFunction((id, player) -> new ContainerO2OProcessor(id, player,
			getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(3, 4, 5).setCanProcess(this::canProcess)
		.setProcess(component -> MachineRecipes.process(this))
		.setRequiredTicks(Constants.OXIDATIONFURNACE_REQUIRED_TICKS)
		.setJoulesPerTick(Constants.OXIDATIONFURNACE_USAGE_PER_TICK)
		.setType(ComponentProcessorType.ObjectToObject));
    }

    protected boolean canProcess(ComponentProcessor component) {
	if (MachineRecipes.canProcess(this)) {
	    if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
		    .get(SubtypeMachine.oxidationfurnace)) {
		world.setBlockState(pos,
			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning)
				.getDefaultState()
				.with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)),
			3);
	    }
	    return true;
	} else if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
		.get(SubtypeMachine.oxidationfurnacerunning)) {
	    world.setBlockState(pos,
		    DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace).getDefaultState()
			    .with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)),
		    3);
	}
	return false;
    }

}

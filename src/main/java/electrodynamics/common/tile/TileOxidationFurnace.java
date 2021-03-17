package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
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
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.MachineRecipes;
import electrodynamics.common.settings.Constants;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;

public class TileOxidationFurnace extends GenericTileTicking {
    public static final int[] SLOTS_OUTPUT = new int[] { 2 };

    public TileOxidationFurnace() {
	super(DeferredRegisters.TILE_OXIDATIONFURNACE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().addTickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).addRelativeInputDirection(Direction.NORTH)
		.setVoltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentInventory().setInventorySize(6).addSlotsOnFace(Direction.UP, 0, 1).addSlotsOnFace(Direction.DOWN, 2)
		.addRelativeSlotsOnFace(Direction.EAST, 1).addRelativeSlotsOnFace(Direction.WEST, 2)
		.setItemValidPredicate((slot, stack) -> slot == 0 || slot > 2 && stack.getItem() instanceof ItemProcessorUpgrade));
	addComponent(new ComponentContainerProvider("container.mineralcrusher").setCreateMenuFunction(
		(id, player) -> new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(3, 4, 5).setCanProcess(this::canProcess)
		.setProcess(component -> MachineRecipes.process(this)).setRequiredTicks(Constants.OXIDATIONFURNACE_REQUIRED_TICKS)
		.setJoulesPerTick(Constants.OXIDATIONFURNACE_USAGE_PER_TICK).setType(ComponentProcessorType.ObjectToObject));
    }

    protected boolean canProcess(ComponentProcessor component) {
	if (MachineRecipes.canProcess(this)) {
	    if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace)) {
		world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning).getDefaultState()
			.with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 2 | 16 | 32);
	    }
	    return true;
	} else if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning)) {
	    world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace).getDefaultState()
		    .with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 2 | 16 | 32);
	}
	return false;
    }

    protected void tickClient(ComponentTickable tickable) {
	if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    double d4 = world.rand.nextDouble();
	    double d5 = direction.getAxis() == Direction.Axis.X ? direction.getXOffset() * (direction.getXOffset() == -1 ? 0 : 1) : d4;
	    double d6 = world.rand.nextDouble();
	    double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getZOffset() * (direction.getZOffset() == -1 ? 0 : 1) : d4;
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + d5, pos.getY() + d6, pos.getZ() + d7, 0.0D, 0.0D, 0.0D);
	}
    }

}

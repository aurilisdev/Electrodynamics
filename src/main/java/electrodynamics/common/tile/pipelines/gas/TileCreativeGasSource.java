package electrodynamics.common.tile.pipelines.gas;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCreativeGasSource;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.GasTank;
import voltaic.api.gas.IGasHandler;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentGasHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileCreativeGasSource extends GenericTile {
    public TileCreativeGasSource(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_CREATIVEGASSOURCE.get(), worldPos, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));

	addComponent(new ComponentGasHandlerSimple(this, "", 128000, 1000000, 1000000)
		.setOutputDirections(BlockEntityUtils.MachineDirection.values()));
	addComponent(
		new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().gasInputs(1).gasOutputs(1))
			.valid((slot, stack,
				i) -> stack.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM) != null));
	addComponent(new ComponentContainerProvider(SubtypeMachine.creativegassource.tag(), this)
		.createMenu((id, player) -> new ContainerCreativeGasSource(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    private void tickServer(Level level, ComponentTickable tick) {
	ComponentGasHandlerSimple simple = requireComponent(IComponentType.GasHandler);
	ComponentInventory inv = requireComponent(IComponentType.Inventory);

	ItemStack input = inv.getItem(0);
	ItemStack output = inv.getItem(1);

	GasStack gas = simple.getGas();

	simple.setGas(new GasStack(gas.getGas(), simple.getCapacity(), gas.getTemperature(), gas.getPressure()));

	if (!input.isEmpty()) {
	    IGasHandlerItem handler = input.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);

	    if (handler != null) {
		GasStack contained = handler.drain(Integer.MAX_VALUE, GasAction.SIMULATE);

		simple.setGas(new GasStack(contained.getGas(), simple.getCapacity(), contained.getTemperature(),
			contained.getPressure()));
	    }
	}

	if (!output.isEmpty()) {
	    IGasHandlerItem handler = output.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM);

	    if (handler != null) {
		handler.fill(simple.getGas().copy(), GasAction.EXECUTE);
		inv.setItem(1, handler.getContainer());
	    }
	}

	Direction facing = getFacing();

	for (Direction relative : simple.outputDirections) {
	    Direction direction = BlockEntityUtils.getRelativeSide(facing, relative.getOpposite());
	    BlockPos face = getBlockPos().relative(direction.getOpposite());
	    BlockEntity faceTile = level.getBlockEntity(face);

	    if (faceTile == null) {
		continue;
	    }

	    IGasHandler handler = level.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK,
		    faceTile.getBlockPos(), faceTile.getBlockState(), faceTile, direction);

	    if (handler == null) {
		continue;
	    }

	    for (GasTank gasTank : simple.asArray()) {
		handler.fill(gasTank.getGas(), GasAction.EXECUTE);
	    }
	}
    }
}

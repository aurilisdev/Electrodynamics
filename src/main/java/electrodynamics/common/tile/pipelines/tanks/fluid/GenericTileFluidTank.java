package electrodynamics.common.tile.pipelines.tanks.fluid;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFluidTankGeneric;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericMaterialTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

public class GenericTileFluidTank extends GenericMaterialTile {

	public GenericTileFluidTank(BlockEntityType<?> tile, int capacity, SubtypeMachine machine, BlockPos pos, BlockState state) {
		super(tile, pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerSimple(capacity, this, "").setInputDirections(Direction.UP).setOutputDirections(Direction.DOWN));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1).bucketOutputs(1)).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(machine, this).createMenu((id, player) -> new ContainerFluidTankGeneric(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {
		ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) getComponent(IComponentType.FluidHandler);
		FluidUtilities.drainItem(this, handler.toArray());
		FluidUtilities.fillItem(this, handler.toArray());
		FluidUtilities.outputToPipe(this, handler.toArray(), handler.outputDirections);

		// Output to tank below
		BlockPos pos = getBlockPos();
		BlockPos below = pos.below();

		if (level.getBlockState(below).hasBlockEntity()) {
			BlockEntity tile = level.getBlockEntity(below);
			if (tile instanceof GenericTileFluidTank tankBelow) {
				ComponentFluidHandlerSimple belowHandler = tankBelow.getComponent(IComponentType.FluidHandler);
				ComponentFluidHandlerSimple thisHandler = getComponent(IComponentType.FluidHandler);

				if (belowHandler.isFluidValid(0, thisHandler.getFluid())) {
					int room = belowHandler.getSpace();
					if (room > 0) {
						int amtTaken = room >= thisHandler.getFluidAmount() ? thisHandler.getFluidAmount() : room;
						FluidStack stack = new FluidStack(thisHandler.getFluid().getFluid(), amtTaken);
						belowHandler.fill(stack, FluidAction.EXECUTE);
						thisHandler.drain(stack, FluidAction.EXECUTE);
					}
				}
			}
		}
	}

	@Override
	public int getComparatorSignal() {
		ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) getComponent(IComponentType.FluidHandler);
		return (int) ((double) handler.getFluidAmount() / (double) Math.max(1, handler.getCapacity()) * 15.0);
	}
}

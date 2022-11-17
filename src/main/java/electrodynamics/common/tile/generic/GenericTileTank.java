package electrodynamics.common.tile.generic;

import electrodynamics.common.inventory.container.tile.ContainerTankGeneric;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.generic.AbstractFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class GenericTileTank extends GenericTile {

	public GenericTileTank(BlockEntityType<?> tile, int capacity, String name, BlockPos pos, BlockState state) {
		super(tile, pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentFluidHandlerSimple(this).relativeInput(Direction.UP).relativeOutput(Direction.DOWN).setManualFluids(1, true, capacity, FluidUtilities.getAllRegistryFluids()));
		addComponent(new ComponentInventory(this).size(2).bucketInputs(1).bucketOutputs(1).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.tank" + name).createMenu((id, player) -> new ContainerTankGeneric(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {
		ComponentFluidHandlerSimple handler = (ComponentFluidHandlerSimple) getComponent(ComponentType.FluidHandler);
		FluidUtilities.drainItem(this);
		FluidUtilities.fillItem(this);
		FluidUtilities.outputToPipe(this, handler.getOutputTanks(), AbstractFluidHandler.getDirectionalArray(handler.relativeOutputDirections));
		FluidUtilities.outputToPipe(this, handler.getOutputTanks(), AbstractFluidHandler.getDirectionalArray(handler.outputDirections));

		// Output to tank below
		BlockPos pos = getBlockPos();
		BlockPos below = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());

		if (level.getBlockState(below).hasBlockEntity()) {
			BlockEntity tile = level.getBlockEntity(below);
			if (tile instanceof GenericTileTank tankBelow) {
				ComponentFluidHandlerSimple belowHandler = tankBelow.getComponent(ComponentType.FluidHandler);
				ComponentFluidHandlerSimple thisHandler = getComponent(ComponentType.FluidHandler);
				FluidTank belowTank = belowHandler.getTankFromFluid(FluidStack.EMPTY.getFluid(), true);
				FluidStack thisTankFluid = thisHandler.getFluidInTank(0);

				if (belowHandler.isFluidValid(0, thisTankFluid)) {
					int room = belowTank.getCapacity() - belowTank.getFluidAmount();
					if (room > 0) {
						int amtTaken = room >= thisTankFluid.getAmount() ? thisTankFluid.getAmount() : room;
						FluidStack stack = new FluidStack(thisTankFluid.getFluid(), amtTaken);
						belowHandler.addFluidToTank(stack, true);
						thisHandler.drainFluidFromTank(stack, true);
					}
				}
			}
		}
	}
}

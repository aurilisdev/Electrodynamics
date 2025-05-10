package electrodynamics.common.tile.pipelines.fluid.tank;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFluidTankGeneric;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.tile.types.GenericMaterialTile;
import voltaic.prefab.utilities.BlockEntityUtils;

public class GenericTileFluidTank extends GenericMaterialTile {

	public GenericTileFluidTank(TileEntityType<?> tile, int capacity, SubtypeMachine machine) {
		super(tile);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentFluidHandlerSimple(capacity, this, "").setInputDirections(BlockEntityUtils.MachineDirection.TOP).setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().bucketInputs(1).bucketOutputs(1)).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(machine.tag(), this).createMenu((id, player) -> new ContainerFluidTankGeneric(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {
		ComponentFluidHandlerSimple handler = getComponent(IComponentType.FluidHandler);
		FluidUtilities.drainItem(this, handler.toArray());
		FluidUtilities.fillItem(this, handler.toArray());
		FluidUtilities.outputToPipe(this, handler.toArray(), handler.outputDirections);

		TileEntity blockentity = level.getBlockEntity(getBlockPos().below()); 
		if (blockentity instanceof GenericTileFluidTank) {
			GenericTileFluidTank tankBelow = (GenericTileFluidTank) blockentity;
			ComponentFluidHandlerSimple belowHandler = tankBelow.getComponent(IComponentType.FluidHandler);

			handler.drain(belowHandler.fill(handler.getFluid(), FluidAction.EXECUTE), FluidAction.EXECUTE);
		}
	}

	@Override
	public int getComparatorSignal() {
		ComponentFluidHandlerSimple handler = getComponent(IComponentType.FluidHandler);
		return (int) ((double) handler.getFluidAmount() / (double) Math.max(1, handler.getCapacity()) * 15.0);
	}
}

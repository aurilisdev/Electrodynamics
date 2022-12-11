package electrodynamics.common.tile;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCoolantResavoir;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericFluidTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TileCoolantResavoir extends GenericFluidTile {

	public TileCoolantResavoir(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_COOLANTRESAVOIR.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentFluidHandlerSimple(Constants.QUARRY_WATERUSAGE_PER_BLOCK * 1000, this, "").setInputDirections(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST).setValidFluids(Fluids.WATER));
		addComponent(new ComponentInventory(this).size(1).bucketInputs(1).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.coolantresavoir).createMenu((id, player) -> new ContainerCoolantResavoir(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		FluidUtilities.drainItem(this, this.<ComponentFluidHandlerSimple>getComponent(ComponentType.FluidHandler).toArray());
	}

	public boolean hasEnoughFluid(int fluidAmnt) {
		ComponentFluidHandlerSimple simple = getComponent(ComponentType.FluidHandler);
		return !simple.isEmpty() && simple.getFluidAmount() >= fluidAmnt;
	}

	public void drainFluid(int fluidAmnt) {
		ComponentFluidHandlerSimple simple = getComponent(ComponentType.FluidHandler);
		simple.drain(fluidAmnt, FluidAction.EXECUTE);
	}

}

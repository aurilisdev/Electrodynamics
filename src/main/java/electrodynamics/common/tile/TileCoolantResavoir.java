package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.tile.ContainerCoolantResavoir;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class TileCoolantResavoir extends GenericTile {

	public TileCoolantResavoir(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_COOLANTRESAVOIR.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentFluidHandlerSimple(this).relativeInput(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST).setManualFluids(1, true, 10000, Fluids.WATER));
		addComponent(new ComponentInventory(this).size(1).bucketInputs(1).valid(machineValidator()).shouldSendInfo());
		addComponent(new ComponentContainerProvider("container.coolantresavoir").createMenu((id, player) -> new ContainerCoolantResavoir(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}
	
	private void tickServer(ComponentTickable tick) {
		if(tick.getTicks() % 10 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		FluidUtilities.drainItem(this);
	}

}

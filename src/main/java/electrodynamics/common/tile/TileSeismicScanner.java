//package electrodynamics.common.tile;
//
//import electrodynamics.DeferredRegisters;
//import electrodynamics.prefab.tile.GenericTile;
//import electrodynamics.prefab.tile.components.type.ComponentDirection;
//import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
//import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
//import electrodynamics.prefab.tile.components.type.ComponentTickable;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.level.block.state.BlockState;
//
//public class TileSeismicScanner extends GenericTile {
//	public TileSeismicScanner(BlockPos worldPosition, BlockState blockState) {
//		super(DeferredRegisters.TILE_SEISMICSCANNER.get(), worldPosition, blockState);
//		addComponent(new ComponentDirection());
//		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
//		addComponent(new ComponentPacketHandler());
//		addComponent(new ComponentElectrodynamic(this).output(Direction.NORTH).output(Direction.SOUTH).output(Direction.EAST).output(Direction.WEST));
//	}
//
//	private void tickServer(ComponentTickable tickable) {
//	}
//
//	private void tickCommon(ComponentTickable tickable) {
//	}
//
//	private void tickClient(ComponentTickable tickable) {
//	}
//}

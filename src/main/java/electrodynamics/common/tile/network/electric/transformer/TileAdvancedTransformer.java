package electrodynamics.common.tile.network.electric.transformer;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedDowngradeTransformer;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedUpgradeTransformer;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class TileAdvancedTransformer extends TileGenericTransformer {

	public final Property<Double> coilRatio;
	
	public final double defaultCoilRatio;
	
	public TileAdvancedTransformer(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState, double defaultCoilRatio) {
		super(type, worldPosition, blockState);
		coilRatio =  property(new Property<>(PropertyType.Double, "coilratio", defaultCoilRatio)).onChange((prop, old) -> {
			if(level.isClientSide || hasComponent(ComponentType.Tickable)) {
				return;
			}
			setChanged();
		});
		this.defaultCoilRatio = defaultCoilRatio;
	}
	
	//This version is enclosed and won't shock but more expensive
	@Override
	public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		
	}
	
	@Override
	public double getCoilRatio() {
		return coilRatio.get() <= 0 ? defaultCoilRatio : coilRatio.get();
	}
	
	public static final class TileAdvancedDowngradeTransformer extends TileAdvancedTransformer {

		public TileAdvancedDowngradeTransformer(BlockPos worldPosition, BlockState blockState) {
			super(ElectrodynamicsBlockTypes.TILE_ADVANCEDDOWNGRADETRANSFORMER.get(), worldPosition, blockState, 0.5);
			addComponent(new ComponentContainerProvider(SubtypeMachine.advanceddowngradetransformer, this).createMenu((id, playerinv) -> new ContainerAdvancedDowngradeTransformer(id, playerinv, getCoordsArray())));
		}
		
	}
	
	public static final class TileAdvancedUpgradeTransformer extends TileAdvancedTransformer {

		public TileAdvancedUpgradeTransformer(BlockPos worldPosition, BlockState blockState) {
			super(ElectrodynamicsBlockTypes.TILE_ADVANCEDUPGRADETRANSFORMER.get(), worldPosition, blockState, 2.0);
			addComponent(new ComponentContainerProvider(SubtypeMachine.advancedupgradetransformer, this).createMenu((id, playerinv) -> new ContainerAdvancedUpgradeTransformer(id, playerinv, getCoordsArray())));
		}
		
	}
	
	static {
		
		VoxelShape shape = Block.box(0, 0, 0, 16, 1, 16);
		shape = Shapes.join(shape, Block.box(1, 1, 1, 15, 15, 15), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 4, 4, 1, 12, 12), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(15, 4, 4, 16, 12, 12), BooleanOp.OR);
		
		VoxelShapes.registerShape(SubtypeMachine.advanceddowngradetransformer, shape, Direction.EAST);
		VoxelShapes.registerShape(SubtypeMachine.advancedupgradetransformer, shape, Direction.EAST);
	}

}

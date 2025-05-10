package electrodynamics.common.tile.electricitygrid.transformer;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedDowngradeTransformer;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedUpgradeTransformer;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public abstract class TileAdvancedTransformer extends TileGenericTransformer {

	public final SingleProperty<Double> coilRatio;

	public final double defaultCoilRatio;

	public TileAdvancedTransformer(TileEntityType<?> type, double defaultCoilRatio) {
		super(type);
		coilRatio = property(new SingleProperty<>(PropertyTypes.DOUBLE, "coilratio", defaultCoilRatio));
		this.defaultCoilRatio = defaultCoilRatio;
	}

	// This version is enclosed and won't shock but more expensive
	@Override
	public void onEntityInside(BlockState state, World level, BlockPos pos, Entity entity) {

	}

	@Override
	public double getCoilRatio() {
		return coilRatio.getValue() <= 0 ? defaultCoilRatio : coilRatio.getValue();
	}

	public static final class TileAdvancedDowngradeTransformer extends TileAdvancedTransformer {

		public TileAdvancedDowngradeTransformer() {
			super(ElectrodynamicsTiles.TILE_ADVANCEDDOWNGRADETRANSFORMER.get(), 0.5);
			addComponent(new ComponentContainerProvider(SubtypeMachine.advanceddowngradetransformer.tag(), this).createMenu((id, playerinv) -> new ContainerAdvancedDowngradeTransformer(id, playerinv, getCoordsArray())));
		}

	}

	public static final class TileAdvancedUpgradeTransformer extends TileAdvancedTransformer {

		public TileAdvancedUpgradeTransformer() {
			super(ElectrodynamicsTiles.TILE_ADVANCEDUPGRADETRANSFORMER.get(), 2.0);
			addComponent(new ComponentContainerProvider(SubtypeMachine.advancedupgradetransformer.tag(), this).createMenu((id, playerinv) -> new ContainerAdvancedUpgradeTransformer(id, playerinv, getCoordsArray())));
		}

	}

}

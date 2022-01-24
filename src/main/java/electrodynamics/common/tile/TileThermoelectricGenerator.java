package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class TileThermoelectricGenerator extends GenericTile {
	protected CachedTileOutput output;
	protected boolean hasHeat = false;

	public TileThermoelectricGenerator(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_THERMOELECTRICGENERATOR.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.UP));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		ComponentDirection direction = getComponent(ComponentType.Direction);
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (tickable.getTicks() % 60 == 0) {
			Fluid fluid = level.getFluidState(worldPosition.relative(direction.getDirection().getOpposite())).getType();
			hasHeat = fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA;
			output.update();
		}
		if (hasHeat && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.UP, TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE * level.getFluidState(worldPosition.relative(direction.getDirection().getOpposite())).getAmount() / 16.0, electro.getVoltage()), false);
		}
	}
}

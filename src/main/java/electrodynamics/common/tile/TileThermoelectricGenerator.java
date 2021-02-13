package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class TileThermoelectricGenerator extends GenericTileBase implements ITickableTileBase, IPowerProvider, IElectricTile {
	protected CachedTileOutput output;
	protected boolean hasHeat = false;

	public TileThermoelectricGenerator() {
		super(DeferredRegisters.TILE_THERMOELECTRICGENERATOR.get());
	}

	@Override
	public void tickServer() {
		if (output == null) {
			output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
		}
		if (world.getWorldInfo().getDayTime() % 20 == 0) {
			hasHeat = world.getBlockState(new BlockPos(pos).offset(getFacing().getOpposite())).getFluidState().getFluid() == Fluids.LAVA;
		}
		if (hasHeat) {
			if (output.get() instanceof IPowerReceiver) {
				output.<IPowerReceiver>get().receivePower(TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE, getVoltage(Direction.UP)), Direction.UP, false);
			}
		}
	}

	@Override
	public double getVoltage(Direction from) {
		return 120.0;
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.UP;
	}

}

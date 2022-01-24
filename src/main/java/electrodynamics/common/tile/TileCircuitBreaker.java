package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileWire;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

public class TileCircuitBreaker extends GenericTile {
	public CachedTileOutput output;
	public CachedTileOutput output2;
	public double lastTransfer = 0;
	public boolean locked = false;

	public TileCircuitBreaker(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_CIRCUITBREAKER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).relativeOutput(Direction.SOUTH).relativeInput(Direction.NORTH));
	}

	protected TransferPack receivePower(TransferPack transfer, boolean debug) {
		Direction facing = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing));
		}
		if (output2 == null) {
			output2 = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (locked || checkDirection(true, transfer, debug)) {
			return TransferPack.EMPTY;
		}
		locked = true;
		TransferPack returner = ElectricityUtils.receivePower(output.getSafe(), facing.getOpposite(), TransferPack.joulesVoltage(transfer.getJoules() * Constants.CIRCUITBREAKER_EFFICIENCY, transfer.getVoltage()), debug);
		locked = false;
		if (returner.getJoules() > 0) {
			returner = TransferPack.joulesVoltage(returner.getJoules() + transfer.getJoules() * (1.0 - Constants.CIRCUITBREAKER_EFFICIENCY), transfer.getVoltage());
		}
		lastTransfer = returner.getJoules();
		return returner;
	}

	protected boolean checkDirection(boolean facing, TransferPack transfer, boolean debug) {
		BlockEntity tile = facing ? output.getSafe() : output2.getSafe();
		if (tile instanceof GenericTileWire wire) {
			if (wire.electricNetwork != null) {
				if (wire.electricNetwork.getNetworkMaxTransfer() <= transfer.getAmps() && !debug) {
					return true;
				}
				for (BlockEntity acceptor : wire.electricNetwork.getEnergyAcceptors()) {
					LazyOptional<ICapabilityElectrodynamic> el = acceptor.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC);
					if (el.isPresent() && !(acceptor instanceof TileCircuitBreaker) && el.resolve().get().getVoltage() < transfer.getVoltage()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

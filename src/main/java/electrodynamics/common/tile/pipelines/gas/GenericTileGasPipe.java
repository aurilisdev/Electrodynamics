package electrodynamics.common.tile.pipelines.gas;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import electrodynamics.common.network.type.GasNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.IGasHandler;
import voltaic.api.network.cable.type.IGasPipe;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;
import voltaic.registers.VoltaicCapabilities;

public abstract class GenericTileGasPipe
	extends GenericRefreshingConnectTile<IGasPipe, GenericTileGasPipe, GasNetwork> {
    private final IGasHandler[] capability = new IGasHandler[6];

    public GenericTileGasPipe(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
	super(tileEntityTypeIn, worldPos, blockState);
	for (Direction dir : Direction.values()) {
	    capability[dir.ordinal()] = new IGasHandler() {

		@Override
		public boolean isGasValid(int tank, GasStack gas) {
		    return gas != null;
		}

		@Override
		public int heat(int tank, int deltaTemperature, GasAction action) {
		    return -1;
		}

		@Override
		public int getTanks() {
		    return 1;
		}

		@Override
		public int getTankMaxTemperature(int tank) {
		    return -1;
		}

		@Override
		public int getTankMaxPressure(int tank) {
		    return getNetwork() == null ? 0 : getNetwork().maxPressure;
		}

		@Override
		public int getTankCapacity(int tank) {
		    return 0;
		}

		@Override
		public GasStack getGasInTank(int tank) {
		    return GasStack.EMPTY;
		}

		@Override
		public int fill(GasStack gas, GasAction action) {
		    if (action == GasAction.SIMULATE || getNetwork() == null || gas.isEmpty()) {
			return 0;
		    }
		    return getNetwork().emit(gas, Lists.newArrayList(level.getBlockEntity(worldPos.relative(dir))),
			    action == GasAction.SIMULATE).getAmount();
		}

		@Override
		public GasStack drain(int maxFill, GasAction action) {
		    return GasStack.EMPTY;
		}

		@Override
		public GasStack drain(GasStack gas, GasAction action) {
		    return GasStack.EMPTY;
		}

		@Override
		public int bringPressureTo(int tank, int atm, GasAction action) {
		    return -1;
		}
	    };
	}
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
	if (cap == VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK && side != null) {
	    return LazyOptional.of(() -> capability[side.ordinal()]).cast();
	}
	return LazyOptional.empty();
    }

    @Override
    public double getMaxTransfer() {
	return getCableType().getMaxTransfer();
    }

    @Override
    public GasNetwork createInstance(Set<GasNetwork> gasNetworks) {
	return new GasNetwork(gasNetworks);
    }

    @Override
    public GasNetwork createInstanceConductor(Set<GenericTileGasPipe> genericTileGasPipes) {
	return new GasNetwork(genericTileGasPipes);
    }

    @Override
    public void destroyViolently() {
	if (level.isClientSide) {
	    return;
	}
	level.playSound(null, getBlockPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0F, 1.0F);
	level.destroyBlock(getBlockPos(), false);
    }
}

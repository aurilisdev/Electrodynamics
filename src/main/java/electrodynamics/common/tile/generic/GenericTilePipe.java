package electrodynamics.common.tile.generic;

import java.util.ArrayList;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import electrodynamics.api.network.pipe.IPipe;
import electrodynamics.api.networks.AbstractNetwork;
import electrodynamics.common.network.FluidNetwork;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class GenericTilePipe extends GenericTileBase implements IPipe {

	public FluidNetwork fluidNetwork;
	private ArrayList<IFluidHandler> handler = new ArrayList<>();

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> handler.get(facing.ordinal())).cast();
		}
		return super.getCapability(capability, facing);
	}

	public GenericTilePipe(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		for (Direction dir : Direction.values()) {
			handler.add(new IFluidHandler() {

				@Override
				public int getTanks() {
					return 1;
				}

				@Override
				public FluidStack getFluidInTank(int tank) {
					return new FluidStack(Fluids.WATER, 0);
				}

				@Override
				public int getTankCapacity(int tank) {
					return 0;
				}

				@Override
				public boolean isFluidValid(int tank, FluidStack stack) {
					return stack != null && stack.getFluid() == Fluids.WATER;
				}

				@Override
				public int fill(FluidStack resource, FluidAction action) {
					if (action == FluidAction.SIMULATE || getNetwork() == null) {
						return 0;
					}
					ArrayList<TileEntity> ignored = new ArrayList<>();
					ignored.add(world.getTileEntity(new BlockPos(pos).offset(dir)));
					return fluidNetwork.emit(resource, ignored).getAmount();
				}

				@Override
				public FluidStack drain(FluidStack resource, FluidAction action) {
					return new FluidStack(Fluids.WATER, 0);
				}

				@Override
				public FluidStack drain(int maxDrain, FluidAction action) {
					return new FluidStack(Fluids.WATER, 0);
				}
			});
		}
	}

	private HashSet<IPipe> getConnectedConductors() {
		HashSet<IPipe> set = new HashSet<>();
		for (Direction dir : Direction.values()) {
			TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
			if (facing instanceof IPipe) {
				set.add((IPipe) facing);
			}
		}
		return set;
	}

	@Override
	public FluidNetwork getNetwork() {
		return getNetwork(true);
	}

	@Override
	public FluidNetwork getNetwork(boolean createIfNull) {
		if (fluidNetwork == null && createIfNull) {
			HashSet<IPipe> adjacentCables = getConnectedConductors();
			HashSet<FluidNetwork> connectedNets = new HashSet<>();
			for (IPipe wire : adjacentCables) {
				if (wire.getNetwork(false) != null && wire.getNetwork() instanceof FluidNetwork) {
					connectedNets.add((FluidNetwork) wire.getNetwork());
				}
			}
			if (connectedNets.size() == 0) {
				fluidNetwork = new FluidNetwork(Sets.newHashSet(this));
			} else if (connectedNets.size() == 1) {
				fluidNetwork = (FluidNetwork) connectedNets.toArray()[0];
				fluidNetwork.conductorSet.add(this);
			} else {
				fluidNetwork = new FluidNetwork(connectedNets, false);
				fluidNetwork.conductorSet.add(this);
			}
		}
		return fluidNetwork;
	}

	@Override
	public void setNetwork(AbstractNetwork<?, ?, ?, ?> network) {
		if (fluidNetwork != network && network instanceof FluidNetwork) {
			removeFromNetwork();
			fluidNetwork = (FluidNetwork) network;
		}
	}

	@Override
	public void refreshNetwork() {
		if (!world.isRemote) {
			ArrayList<FluidNetwork> foundNetworks = new ArrayList<>();
			for (Direction dir : Direction.values()) {
				TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
				if (facing instanceof IPipe && ((IPipe) facing).getNetwork() instanceof FluidNetwork) {
					foundNetworks.add((FluidNetwork) ((IPipe) facing).getNetwork());
				}
			}
			if (foundNetworks.size() > 0) {
				foundNetworks.get(0).conductorSet.add(this);
				fluidNetwork = foundNetworks.get(0);
				if (foundNetworks.size() > 1) {
					foundNetworks.remove(0);
					for (FluidNetwork network : foundNetworks) {
						getNetwork().merge(network);
					}
				}
			}
			getNetwork().refresh();
		}
	}

	@Override
	public void removeFromNetwork() {
		if (fluidNetwork != null) {
			fluidNetwork.removeFromNetwork(this);
		}
	}

	@Override
	public void fixNetwork() {
		getNetwork().fixMessedUpNetwork(this);
	}

	@Override
	public void destroyViolently() {
		world.setBlockState(pos, Blocks.AIR.getDefaultState());
	}

	@Override
	public void remove() {
		if (!world.isRemote) {
			if (fluidNetwork != null) {
				getNetwork().split(this);
			}
		}
		super.remove();
	}

	@Override
	public void onChunkUnloaded() {
		remove();
		NetworkRegistry.pruneEmptyNetworks();
	}

}

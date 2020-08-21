package electrodynamics.common.tile.generic;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Sets;

import electrodynamics.api.network.INetwork;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.electricity.network.ElectricNetwork;
import electrodynamics.common.network.NetworkRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public abstract class GenericTileWire extends GenericTileBase implements IConductor {

	public ElectricNetwork electricNetwork;

	public GenericTileWire(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	private HashSet<IConductor> getConnectedConductors() {
		HashSet<IConductor> set = new HashSet<>();
		for (Direction dir : Direction.values()) {
			TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
			if (facing instanceof IConductor) {
				set.add((IConductor) facing);
			}
		}
		return set;
	}

	@Override
	public ElectricNetwork getNetwork() {
		return getNetwork(true);
	}

	@Override
	public ElectricNetwork getNetwork(boolean createIfNull) {
		if (electricNetwork == null && createIfNull) {
			HashSet<IConductor> adjacentCables = getConnectedConductors();
			HashSet<ElectricNetwork> connectedNets = new HashSet<>();
			for (IConductor wire : adjacentCables) {
				if (wire.getNetwork(false) != null && wire.getNetwork() instanceof ElectricNetwork) {
					connectedNets.add((ElectricNetwork) wire.getNetwork());
				}
			}
			if (connectedNets.size() == 0) {
				electricNetwork = new ElectricNetwork(new IConductor[] { this });
			} else if (connectedNets.size() == 1) {
				electricNetwork = (ElectricNetwork) connectedNets.toArray()[0];
				electricNetwork.conductorSet.add(this);
			} else {
				electricNetwork = new ElectricNetwork(connectedNets);
				electricNetwork.conductorSet.add(this);
			}
		}
		return electricNetwork;
	}

	@Override
	public void setNetwork(INetwork network) {
		if (electricNetwork != network && network instanceof ElectricNetwork) {
			removeFromNetwork();
			electricNetwork = (ElectricNetwork) network;
		}
	}

	@Override
	public void refreshNetwork() {
		if (!world.isRemote) {
			ArrayList<ElectricNetwork> foundNetworks = new ArrayList<>();
			for (Direction dir : Direction.values()) {
				TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
				if (facing instanceof IConductor && ((IConductor) facing).getNetwork() instanceof ElectricNetwork) {
					foundNetworks.add((ElectricNetwork) ((IConductor) facing).getNetwork());
				}
			}
			if (foundNetworks.size() > 0) {
				foundNetworks.get(0).addAllCables(Sets.newHashSet(this));
				electricNetwork = foundNetworks.get(0);
				if (foundNetworks.size() > 1) {
					foundNetworks.remove(0);
					for (ElectricNetwork network : foundNetworks) {
						getNetwork().merge(network);
					}
				}
			}
			getNetwork().refresh();
		}
	}

	@Override
	public void removeFromNetwork() {
		if (electricNetwork != null) {
			electricNetwork.removeWire(this);
		}
	}

	@Override
	public void fixNetwork() {
		getNetwork().fixMessedUpNetwork(this);
	}

	@Override
	public void destroyViolently() {
		world.setBlockState(pos, Blocks.FIRE.getDefaultState());
	}

	@Override
	public void remove() {
		if (!world.isRemote) {
			getNetwork().split(this);
		}
		super.remove();
	}

	@Override
	public void onChunkUnloaded() {
		remove();
		NetworkRegistry.pruneEmptyNetworks();
	}

	@Override
	public TransferPack receivePower(TransferPack transfer, Direction dir, boolean debug) {
		if (debug) {
			return TransferPack.EMPTY;
		}
		ArrayList<TileEntity> ignored = new ArrayList<>();
		ignored.add(world.getTileEntity(new BlockPos(pos).add(dir.getXOffset(), dir.getYOffset(), dir.getZOffset())));
		return getNetwork().emit(transfer, ignored);
	}

}

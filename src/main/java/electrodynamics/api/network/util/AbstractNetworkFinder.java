package electrodynamics.api.network.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import electrodynamics.api.network.cable.IAbstractCable;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AbstractNetworkFinder<C extends IAbstractCable> {
	public World worldObj;
	public BlockPos start;
	public AbstractNetwork<C, ?, ?, ?> net;
	public List<TileEntity> iteratedTiles = new ArrayList<>();
	public List<BlockPos> toIgnore = new ArrayList<>();

	public AbstractNetworkFinder(World world, BlockPos location, AbstractNetwork<C, ?, ?, ?> net, BlockPos... ignore) {
		worldObj = world;
		start = location;
		this.net = net;
		if (ignore.length > 0) {
			toIgnore = Arrays.asList(ignore);
		}
	}

	public void loopAll(BlockPos location) {
		TileEntity curr = worldObj.getBlockEntity(location);
		if (net.isConductor(curr, (C) curr)) {
			iteratedTiles.add(curr);
		}
		for (Direction direction : Direction.values()) {
			BlockPos obj = new BlockPos(location).offset(direction.getNormal());
			if (!(toIgnore.size() == 1 ? toIgnore.get(0) == obj : toIgnore.contains(obj))) {
				if (worldObj.hasChunkAt(obj)) {
					TileEntity tileEntity = worldObj.getBlockEntity(obj);
					if (!iteratedTiles.contains(tileEntity) && net.isConductor(tileEntity, (C) curr)) {
						loopAll((IAbstractCable) tileEntity);
					}
				}
			}
		}

	}

	public void loopAll(IAbstractCable conductor) {
		iteratedTiles.add((TileEntity) conductor);
		for (TileEntity connections : conductor.getAdjacentConnections()) {
			if (connections != null) {
				BlockPos pos = connections.getBlockPos();
				if (!iteratedTiles.contains(connections) && !(toIgnore.size() == 1 ? toIgnore.get(0) == pos : toIgnore.contains(pos))) {
					if (net.isConductor(connections, (C) conductor)) {
						loopAll((IAbstractCable) connections);
					}
				}
			}
		}
	}

	public List<TileEntity> exploreNetwork() {
		loopAll(start);
		return iteratedTiles;
	}
}

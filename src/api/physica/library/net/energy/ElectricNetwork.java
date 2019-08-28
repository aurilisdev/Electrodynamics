package physica.library.net.energy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkEvent;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;
import physica.api.core.conductor.EnumConductorType;
import physica.api.core.conductor.IConductor;
import physica.library.location.GridLocation;
import physica.library.net.ElectricNetworkRegistry;

public class ElectricNetwork {
	private static final int								DEFAULT_VOLTAGE					= 120;
	public HashSet<IConductor>								conductorSet					= new HashSet<>();
	public HashSet<TileEntity>								acceptorSet						= new HashSet<>();
	public HashMap<TileEntity, HashSet<Face>>				acceptorInputMap				= new HashMap<>();
	private HashMap<EnumConductorType, HashSet<IConductor>>	conductorTypeMap				= new HashMap<>();
	private int												energyTransmittedBuffer			= 0;
	private int												energyTransmittedLastTick		= 0;
	private int												fixTimerTicksSinceNetworkCreate	= 0;
	private int												ticksSinceNetworkRefresh		= 0;
	private boolean											fixed							= false;
	private int												maxPowerTransfer				= 0;

	public int getEnergyTransmittedLastTick()
	{
		return energyTransmittedLastTick;
	}

	public int getMaxPowerTransfer()
	{
		return maxPowerTransfer;
	}

	public ElectricNetwork(IConductor... varCables) {
		conductorSet.addAll(Arrays.asList(varCables));
		ElectricNetworkRegistry.getInstance().registerNetwork(this);
	}

	public ElectricNetwork(Set<ElectricNetwork> networks) {
		for (ElectricNetwork net : networks)
		{
			if (net != null)
			{
				addAllCables(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		ElectricNetworkRegistry.getInstance().registerNetwork(this);
	}

	public int emit(int energyToSend, ArrayList<TileEntity> ignored)
	{
		energyToSend = Math.min(maxPowerTransfer - energyTransmittedBuffer, energyToSend);
		if (energyToSend > 0)
		{
			Set<TileEntity> availableAcceptors = getEnergyAcceptors();

			int energySent = 0;
			if (!availableAcceptors.isEmpty())
			{
				checkForVoltage(energyToSend);
				HashSet<TileEntity> validAcceptors = new HashSet<>();
				for (TileEntity acceptor : availableAcceptors)
				{
					if (!ignored.contains(acceptor))
					{
						if (AbstractionLayer.Electricity.isElectricReceiver(acceptor))
						{
							validAcceptors.add(acceptor);
						}
					}
				}
				if (validAcceptors.size() > 0)
				{
					int energyPerReceiver = energyToSend / validAcceptors.size();
					for (TileEntity receiver : validAcceptors)
					{
						for (Face connection : acceptorInputMap.get(receiver))
						{
							energySent += AbstractionLayer.Electricity.receiveElectricity(receiver, connection.getOpposite(), energyPerReceiver, false);
						}
					}
				}
			}
			energyTransmittedBuffer += energySent;
			return energySent;
		}
		return 0;
	}

	public int getSafeVoltageLevel()
	{
		for (EnumConductorType index : EnumConductorType.values())
		{
			if (conductorTypeMap.containsKey(index) && !conductorTypeMap.get(index).isEmpty())
			{
				return index.getVoltage();
			}
		}
		return 0;
	}

	private void checkForVoltage(int energyToSend)
	{
		HashSet<EnumConductorType> checkList = new HashSet<>();
		for (EnumConductorType type : EnumConductorType.values())
		{
			if (type.getVoltage() > 0)
			{
				int calculatedVoltage = energyToSend / (type.getTransferRate() / DEFAULT_VOLTAGE);
				if (calculatedVoltage > type.getVoltage())
				{
					checkList.add(type);
				}
			}
		}
		for (EnumConductorType index : checkList)
		{
			for (IConductor conductor : conductorTypeMap.get(index))
			{
				conductor.destroyNodeViolently();
			}
		}
	}

	public Set<TileEntity> getEnergyAcceptors()
	{
		Set<TileEntity> toReturn = new HashSet<>();
		for (TileEntity acceptor : acceptorSet)
		{
			if (AbstractionLayer.Electricity.isElectricReceiver(acceptor))
			{
				for (Face connection : acceptorInputMap.get(acceptor))
				{
					Face direction = connection.getOpposite();
					if (AbstractionLayer.Electricity.canInputElectricityNow(acceptor, direction))
					{
						toReturn.add(acceptor);
					}
				}
			}
		}
		return toReturn;
	}

	public TileEntity[] getConnectedEnergyAcceptors(TileEntity tileEntity)
	{
		TileEntity[] acceptors = { null, null, null, null, null, null };
		for (Face orientation : Face.VALID)
		{
			TileEntity acceptor = new GridLocation(tileEntity).OffsetFace(orientation).getTile(tileEntity.getWorldObj());
			if (AbstractionLayer.Electricity.isElectricReceiver(acceptor) && !(acceptor instanceof IConductor))
			{
				acceptors[orientation.ordinal()] = acceptor;
			}
		}
		return acceptors;
	}

	public void refresh()
	{
		@SuppressWarnings("unchecked")
		Set<IConductor> iterCables = (Set<IConductor>) conductorSet.clone();
		Iterator<IConductor> it = iterCables.iterator();

		acceptorSet.clear();
		acceptorInputMap.clear();
		while (it.hasNext())
		{
			IConductor conductor = it.next();
			if (conductor == null || ((TileEntity) conductor).isInvalid())
			{
				it.remove();
				conductorSet.remove(conductor);
			} else
			{
				conductor.setNetwork(this);
			}
		}
		for (IConductor cable : iterCables)
		{
			TileEntity[] acceptors = getConnectedEnergyAcceptors((TileEntity) cable);
			for (TileEntity acceptor : acceptors)
			{
				if (acceptor != null && !(acceptor instanceof IConductor))
				{
					acceptorSet.add(acceptor);
					HashSet<Face> directions = acceptorInputMap.containsKey(acceptor) ? acceptorInputMap.get(acceptor) : new HashSet<>();
					directions.add(Face.getOrientation(Arrays.asList(acceptors).indexOf(acceptor)));
					acceptorInputMap.put(acceptor, directions);
				}
			}
		}
		updatePowerRate();
	}

	private void updatePowerRate()
	{
		maxPowerTransfer = 0;
		conductorTypeMap.clear();
		for (EnumConductorType type : EnumConductorType.values())
		{
			conductorTypeMap.put(type, new HashSet<>());
		}
		for (IConductor cable : conductorSet)
		{
			conductorTypeMap.get(cable.getCableType()).add(cable);
			maxPowerTransfer += cable.getCableType().getTransferRate();
		}
	}

	public void merge(ElectricNetwork network)
	{
		if (network != null && network != this)
		{
			Set<ElectricNetwork> networks = new HashSet<>();
			networks.add(this);
			networks.add(network);
			ElectricNetwork newNetwork = new ElectricNetwork(networks);
			newNetwork.refresh();
		}
	}

	public void addAllCables(Set<IConductor> newCables)
	{
		conductorSet.addAll(newCables);
	}

	public void split(IConductor splitPoint)
	{
		if (splitPoint instanceof TileEntity)
		{
			removeCable(splitPoint);

			TileEntity[] connectedBlocks = new TileEntity[6];
			boolean[] dealtWith = { false, false, false, false, false, false };
			for (Face direction : Face.VALID)
			{
				TileEntity sideTile = new GridLocation((TileEntity) splitPoint).OffsetFace(direction).getTile(((TileEntity) splitPoint).getWorldObj());
				if (sideTile != null)
				{
					connectedBlocks[Arrays.asList(Face.values()).indexOf(direction)] = sideTile;
				}
			}
			for (int countOne = 0; countOne < connectedBlocks.length; countOne++)
			{
				TileEntity connectedBlockA = connectedBlocks[countOne];
				if (connectedBlockA instanceof IConductor && dealtWith[countOne] == false)
				{
					NetworkFinder finder = new NetworkFinder(((TileEntity) splitPoint).getWorldObj(), new GridLocation(connectedBlockA), new GridLocation[] { new GridLocation((TileEntity) splitPoint) });
					List<GridLocation> partNetwork = finder.exploreNetwork();
					for (int countTwo = countOne + 1; countTwo < connectedBlocks.length; countTwo++)
					{
						TileEntity connectedBlockB = connectedBlocks[countTwo];
						if (connectedBlockB instanceof IConductor && dealtWith[countTwo] == false)
						{
							if (partNetwork.contains(new GridLocation(connectedBlockB)))
							{
								dealtWith[countTwo] = true;
							}
						}
					}
					ElectricNetwork newNetwork = new ElectricNetwork(new IConductor[0]);
					for (GridLocation node : finder.iterated)
					{
						TileEntity nodeTile = node.getTile(((TileEntity) splitPoint).getWorldObj());
						if (nodeTile instanceof IConductor)
						{
							if (nodeTile != splitPoint)
							{
								newNetwork.conductorSet.add((IConductor) nodeTile);
							}
						}
					}
					newNetwork.refresh();
				}
			}
			deregister();
		}
	}

	public void fixMessedUpNetwork(IConductor cable)
	{
		if (cable instanceof TileEntity)
		{
			NetworkFinder finder = new NetworkFinder(((TileEntity) cable).getWorldObj(), new GridLocation((TileEntity) cable));
			List<GridLocation> partNetwork = finder.exploreNetwork();
			Set<IConductor> newCables = new HashSet<>();
			for (GridLocation node : partNetwork)
			{
				TileEntity nodeTile = node.getTile(((TileEntity) cable).getWorldObj());
				if (nodeTile instanceof IConductor)
				{
					((IConductor) nodeTile).removeFromNetwork();
					newCables.add((IConductor) nodeTile);
				}
			}
			ElectricNetwork newNetwork = new ElectricNetwork(newCables.toArray(new IConductor[0]));
			newNetwork.refresh();
			newNetwork.fixed = true;
			deregister();
		}
	}

	public void removeCable(IConductor cable)
	{
		conductorSet.remove(cable);
		if (conductorSet.size() == 0)
		{
			deregister();
		}
	}

	public void deregister()
	{
		conductorSet.clear();
		ElectricNetworkRegistry.getInstance().removeNetwork(this);
	}

	public static class NetworkFinder {
		public World				worldObj;
		public GridLocation			start;
		public List<GridLocation>	iterated	= new ArrayList<>();
		public List<GridLocation>	toIgnore	= new ArrayList<>();

		public NetworkFinder(World world, GridLocation location, GridLocation... ignore) {
			worldObj = world;
			start = location;
			if (ignore.length > 0)
			{
				toIgnore = Arrays.asList(ignore);
			}
		}

		public void loopAll(GridLocation location)
		{
			if (location.getTile(worldObj) instanceof IConductor)
			{
				iterated.add(location);
			}
			for (Face direction : Face.VALID)
			{
				GridLocation obj = location.OffsetFace(direction);
				if (!iterated.contains(obj) && !toIgnore.contains(obj))
				{
					TileEntity tileEntity = obj.getTile(worldObj);
					if (tileEntity instanceof IConductor)
					{
						loopAll(obj);
					}
				}
			}
		}

		public List<GridLocation> exploreNetwork()
		{
			loopAll(start);

			return iterated;
		}
	}

	public static class NetworkLoader {
		@SubscribeEvent
		public void onChunkLoad(ChunkEvent.Load event)
		{
			if (event.getChunk() != null)
			{
				for (Object obj : event.getChunk().chunkTileEntityMap.values())
				{
					if (obj instanceof TileEntity)
					{
						TileEntity tileEntity = (TileEntity) obj;
						if (tileEntity instanceof IConductor)
						{
							((IConductor) tileEntity).refreshNetwork();
						}
					}
				}
			}
		}
	}

	public void tick()
	{
		clearJoulesTransmitted();
		if (!fixed)
		{
			fixTimerTicksSinceNetworkCreate += 1;
			if (fixTimerTicksSinceNetworkCreate > 1200)
			{
				fixTimerTicksSinceNetworkCreate = 0;
				fixMessedUpNetwork(conductorSet.toArray(new IConductor[0])[0]);
			}
		}
		ticksSinceNetworkRefresh++;
		if (ticksSinceNetworkRefresh > 1600)
		{
			ticksSinceNetworkRefresh = 0;
			refresh();
		}
	}

	public void clearJoulesTransmitted()
	{
		energyTransmittedLastTick = energyTransmittedBuffer;
		energyTransmittedBuffer = 0;
	}

}

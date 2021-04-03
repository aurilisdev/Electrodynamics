package improvedapi.core.electricity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import improvedapi.core.capability.CapabilityConductor;
import improvedapi.core.capability.CapabilityConnectionProvider;
import improvedapi.core.capability.CapabilityElectrical;
import improvedapi.core.capability.CapabilityNetworkProvider;
import improvedapi.core.electricity.ElectricalEvent.ElectricProductionEvent;
import improvedapi.core.path.Pathfinder;
import improvedapi.core.path.PathfinderChecker;
import improvedapi.core.tile.IConductor;
import improvedapi.core.tile.IConnectionProvider;
import improvedapi.core.tile.IElectrical;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;

public class ElectricityNetwork implements IElectricityNetwork {
    public Set<TileEntity> electricalTiles = new HashSet<>();
    public Map<TileEntity, Direction> acceptorDirections = new HashMap<>();

    private final Set<IConductor> conductors = new HashSet<>();

    public ElectricityNetwork() {

    }

    public ElectricityNetwork(IConductor... conductors) {
	this.conductors.addAll(Arrays.asList(conductors));
    }

    @Override
    public double produce(ElectricityPack electricity, TileEntity... ignoreTiles) {
	ElectricProductionEvent evt = new ElectricProductionEvent(electricity, ignoreTiles);
	MinecraftForge.EVENT_BUS.post(evt);

	double energy = electricity.getWatts();
	double voltage = electricity.voltage;

	if (!evt.isCanceled()) {
	    Set<TileEntity> avaliableEnergyTiles = this.getElectrical();

	    if (!avaliableEnergyTiles.isEmpty()) {
		double totalEnergyRequest = this.getRequest(ignoreTiles);

		loop: for (TileEntity tileEntity : avaliableEnergyTiles) {
		    if (ignoreTiles != null) {
			for (TileEntity ignoreTile : ignoreTiles) {
			    if (tileEntity == ignoreTile) {
				continue loop;
			    }
			}
		    }
		    LazyOptional<IElectrical> capability = tileEntity.getCapability(CapabilityElectrical.INSTANCE);
		    if (capability.isPresent()) {
			double energyToSend = energy * (capability.resolve().get().getRequest() / totalEnergyRequest);
			ElectricityPack electricityToSend = ElectricityPack.getFromWatts(energyToSend, voltage);

			// Calculate energy loss caused by resistance.
			double ampsReceived = electricityToSend.amperes
				- (electricityToSend.amperes * electricityToSend.amperes * this.getTotalResistance()) / electricityToSend.voltage;
			double voltsReceived = electricityToSend.voltage - (electricityToSend.amperes * this.getTotalResistance());

			electricityToSend = new ElectricityPack(ampsReceived, voltsReceived);

			energy -= (electricityToSend.getWatts() - ((IElectrical) tileEntity).receiveElectricity(electricityToSend, true));
		    }
		}

	    }
	}

	return energy;
    }

    /**
     * @return How much electricity this network needs.
     */
    @Override
    public double getRequest(TileEntity... ignoreTiles) {
	double requiredElectricity = 0;

	Iterator<TileEntity> it = this.getElectrical().iterator();

	loop: while (it.hasNext()) {
	    TileEntity tileEntity = it.next();

	    if (ignoreTiles != null) {
		for (TileEntity ignoreTile : ignoreTiles) {
		    if (tileEntity == ignoreTile) {
			continue loop;
		    }
		}
	    }

	    if (tileEntity instanceof IElectrical && !tileEntity.isRemoved()
		    && tileEntity.getWorld().getTileEntity(tileEntity.getPos()) == tileEntity) {
		requiredElectricity += ((IElectrical) tileEntity).getRequest();
		continue;
	    }

	    it.remove();

	}

	return requiredElectricity;
    }

    /**
     * @return Returns all producers in this electricity network.
     */
    @Override
    public Set<TileEntity> getElectrical() {
	return this.electricalTiles;
    }

    @Override
    public void cleanUpConductors() {
	Iterator<IConductor> it = this.conductors.iterator();

	while (it.hasNext()) {
	    IConductor conductor = it.next();

	    if (conductor == null) {
		it.remove();
	    } else if (((TileEntity) conductor).isRemoved()) {
		it.remove();
	    } else {
		conductor.setNetwork(this);
	    }
	}
    }

    /**
     * This function is called to refresh all conductors in this network
     */
    @Override
    public void refresh() {
	this.cleanUpConductors();

	this.electricalTiles.clear();

	try {
	    Iterator<IConductor> it = this.conductors.iterator();

	    while (it.hasNext()) {
		IConductor conductor = it.next();
		conductor.refresh();

		for (TileEntity acceptor : conductor.getAdjacentConnections()) {
		    this.electricalTiles.add(acceptor);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public double getTotalResistance() {
	float resistance = 0;

	for (IConductor conductor : this.conductors) {
	    resistance += conductor.getResistance();
	}

	return resistance;
    }

    @Override
    public double getLowestCurrentCapacity() {
	double lowestAmperage = 0;

	for (IConductor conductor : this.conductors) {
	    if (lowestAmperage == 0 || conductor.getCurrentCapacity() < lowestAmperage) {
		lowestAmperage = conductor.getCurrentCapacity();
	    }
	}

	return lowestAmperage;
    }

    @Override
    public Set<IConductor> getConductors() {
	return this.conductors;
    }

    @Override
    public void merge(IElectricityNetwork network) {
	if (network != null && network != this) {
	    ElectricityNetwork newNetwork = new ElectricityNetwork();
	    newNetwork.getConductors().addAll(this.getConductors());
	    newNetwork.getConductors().addAll(network.getConductors());
	    newNetwork.cleanUpConductors();
	}
    }

    @Override
    public void split(IConnectionProvider splitPoint) {
	if (splitPoint instanceof TileEntity) {
	    this.getConductors().remove(splitPoint);

	    /**
	     * Loop through the connected blocks and attempt to see if there are connections
	     * between the two points elsewhere.
	     */
	    TileEntity[] connectedBlocks = splitPoint.getAdjacentConnections();

	    for (int i = 0; i < connectedBlocks.length; i++) {
		TileEntity connectedBlockA = connectedBlocks[i];
		connectedBlockA.getCapability(CapabilityConnectionProvider.INSTANCE).ifPresent(capability -> {
		    for (int ii = 0; ii < connectedBlocks.length; ii++) {
			final TileEntity connectedBlockB = connectedBlocks[ii];

			if (connectedBlockA != connectedBlockB) {
			    connectedBlockB.getCapability(CapabilityConnectionProvider.INSTANCE).ifPresent(capability3 -> {
				Pathfinder finder = new PathfinderChecker(((TileEntity) splitPoint).getWorld(), capability3, splitPoint);
				finder.init(connectedBlockA.getPos());
				if (!finder.results.isEmpty()) {
				    /**
				     * The connections A and B are still intact elsewhere. Set all references of
				     * wire connection into one network.
				     */

				    for (BlockPos node : finder.closedSet) {
					TileEntity nodeTile = ((TileEntity) splitPoint).getWorld().getTileEntity(node);
					nodeTile.getCapability(CapabilityNetworkProvider.INSTANCE).ifPresent(capability2 -> {
					    if (nodeTile != splitPoint) {
						capability2.setNetwork(this);
					    }
					});
				    }
				} else {
				    /**
				     * The connections A and B are not connected anymore. Give both of them a new
				     * network.
				     */
				    IElectricityNetwork newNetwork = new ElectricityNetwork();

				    for (BlockPos node : finder.closedSet) {
					TileEntity nodeTile = ((TileEntity) splitPoint).getWorld().getTileEntity(node);
					nodeTile.getCapability(CapabilityConductor.INSTANCE).ifPresent(capability2 -> {
					    if (nodeTile != splitPoint) {
						newNetwork.getConductors().add(capability2);
					    }
					});
				    }
				    newNetwork.cleanUpConductors();
				}
			    });
			}
		    }
		});
	    }
	}
    }

    @Override
    public String toString() {
	return "ElectricityNetwork[" + this.hashCode() + "|Wires:" + this.conductors.size() + "]";
    }

}

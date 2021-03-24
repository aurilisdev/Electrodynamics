package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import electrodynamics.api.network.AbstractNetwork;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public class ElectricNetwork extends AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> {
    private double resistance;
    private double energyLoss;
    private double lastEnergyLoss;
    private double voltage = 0.0;
    private double lastVoltage = 0.0;

    public double getLastEnergyLoss() {
	return lastEnergyLoss;
    }

    public double getLastVoltage() {
	return lastVoltage;
    }

    public double getResistance() {
	return resistance;
    }

    public ElectricNetwork() {
	this(new HashSet<IConductor>());
    }

    public ElectricNetwork(Collection<? extends IConductor> varCables) {
	conductorSet.addAll(varCables);
	NetworkRegistry.register(this);
    }

    public ElectricNetwork(Set<AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack>> networks) {
	for (AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> net : networks) {
	    if (net != null) {
		conductorSet.addAll(net.conductorSet);
		net.deregister();
	    }
	}
	refresh();
	NetworkRegistry.register(this);
    }

    public ElectricNetwork(Set<ElectricNetwork> networks, boolean special) {
	for (ElectricNetwork net : networks) {
	    if (net != null) {
		conductorSet.addAll(net.conductorSet);
		net.deregister();
	    }
	}
	refresh();
	NetworkRegistry.register(this);
    }

    @Override
    public TransferPack emit(TransferPack maxTransfer, ArrayList<TileEntity> ignored, boolean debug) {
	if (voltage == 0.0) {
	    voltage = maxTransfer.getVoltage();
	}
	if (maxTransfer.getJoules() > 0) {
	    Set<TileEntity> availableAcceptors = new HashSet<>();
	    availableAcceptors.addAll(getEnergyAcceptors());
	    double joulesSent = 0;
	    availableAcceptors.removeAll(ignored);
	    if (!availableAcceptors.isEmpty()) {
		Iterator<TileEntity> it = availableAcceptors.iterator();
		double totalUsage = 0;
		HashMap<TileEntity, Double> usage = new HashMap<>();
		while (it.hasNext()) {
		    TileEntity receiver = it.next();
		    if (acceptorInputMap.containsKey(receiver)) {
			boolean shouldRemove = true;
			double localUsage = 0;
			for (Direction connection : acceptorInputMap.get(receiver)) {
			    TransferPack pack = ElectricityUtilities.receivePower(receiver, connection,
				    TransferPack.joulesVoltage(maxTransfer.getJoules(), maxTransfer.getVoltage()), true);
			    if (pack.getJoules() != 0) {
				shouldRemove = false;
				totalUsage += pack.getJoules();
				localUsage += pack.getJoules();
				break;
			    }
			}
			usage.put(receiver, localUsage);
			if (shouldRemove) {
			    it.remove();
			}
		    }
		}
		double totalYieldLoss = maxTransfer.getJoules() * maxTransfer.getJoules() * resistance / Math.pow(maxTransfer.getVoltage(), 2);
		TransferPack useableEnergy = TransferPack.joulesVoltage(Math.max(maxTransfer.getJoules() - totalYieldLoss, 0),
			maxTransfer.getVoltage());
		for (TileEntity receiver : availableAcceptors) {
		    TransferPack dedicated = TransferPack.joulesVoltage(useableEnergy.getJoules() * (usage.get(receiver) / totalUsage),
			    maxTransfer.getVoltage());
		    if (acceptorInputMap.containsKey(receiver)) {
			TransferPack perConnection = TransferPack.joulesVoltage(dedicated.getJoules() / acceptorInputMap.get(receiver).size(),
				maxTransfer.getVoltage());
			for (Direction connection : acceptorInputMap.get(receiver)) {
			    TransferPack pack = ElectricityUtilities.receivePower(receiver, connection, perConnection, debug);
			    joulesSent += pack.getJoules();
			    if (!debug) {
				transmittedThisTick += pack.getJoules();
			    }
			}
			checkForOverload(TransferPack.joulesVoltage(transmittedThisTick, dedicated.getVoltage()));
		    }
		}
		if (joulesSent > 0.0) {
		    double lost = totalYieldLoss;
		    joulesSent += lost;
		    if (!debug) {
			energyLoss += lost;
			transmittedThisTick += lost;
		    }
		}
	    }
	    return TransferPack.joulesVoltage(Math.min(maxTransfer.getJoules(), joulesSent), maxTransfer.getVoltage());
	}
	return TransferPack.EMPTY;
    }

    public Set<TileEntity> getEnergyAcceptors() {
	Set<TileEntity> toReturn = new HashSet<>();
	for (TileEntity acceptor : acceptorSet) {
	    if (ElectricityUtilities.isElectricReceiver(acceptor)) {
		for (Direction connection : acceptorInputMap.get(acceptor)) {
		    if (ElectricityUtilities.canInputPower(acceptor, connection)) {
			toReturn.add(acceptor);
		    }
		}
	    }
	}
	return toReturn;
    }

    private boolean checkForOverload(TransferPack attemptSend) {
	if (attemptSend.getAmps() >= networkMaxTransfer) {
	    HashSet<SubtypeWire> checkList = new HashSet<>();
	    for (SubtypeWire type : SubtypeWire.values()) {
		if (type.maxAmps <= attemptSend.getAmps()) {
		    checkList.add(type);
		}
	    }
	    for (SubtypeWire index : checkList) {
		for (IConductor conductor : conductorTypeMap.get(index)) {
		    conductor.destroyViolently();
		}
	    }
	    return true;
	}
	return false;
    }

    @Override
    public void updateStatistics(IConductor cable) {
	super.updateStatistics(cable);
	resistance += cable.getWireType().resistance * 5.0;
    }

    @Override
    public void updateStatistics() {
	resistance = 0;
	super.updateStatistics();
    }

    @Override
    public void tick() {
	super.tick();
	lastVoltage = voltage;
	voltage = 0;
	lastEnergyLoss = energyLoss;
	energyLoss = 0;
    }

    @Override
    public boolean isConductor(TileEntity tile) {
	return ElectricityUtilities.isConductor(tile);
    }

    @Override
    public boolean isAcceptor(TileEntity acceptor, Direction orientation) {
	return ElectricityUtilities.isElectricReceiver(acceptor);
    }

    @Override
    public AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> createInstance() {
	return new ElectricNetwork();
    }

    @Override
    public AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> createInstanceConductor(Set<IConductor> conductors) {
	return new ElectricNetwork(conductors);
    }

    @Override
    public AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> createInstance(
	    Set<AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack>> networks) {
	return new ElectricNetwork(networks);

    }

    @Override
    public SubtypeWire[] getConductorTypes() {
	return SubtypeWire.values();
    }

    @Override
    public boolean canConnect(TileEntity acceptor, Direction orientation) {
	return ElectricityUtilities.canInputPower(acceptor, orientation.getOpposite());
    }
}

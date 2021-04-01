package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import electrodynamics.api.electricity.IElectrodynamic;
import electrodynamics.api.network.AbstractNetwork;
import electrodynamics.api.network.conductor.IConductor;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public class ElectricNetwork extends AbstractNetwork<IConductor, SubtypeWire, TileEntity, TransferPack> implements IElectrodynamic {
    private double resistance;
    private double energyLoss;
    private double lastEnergyLoss;
    private double voltage = 0.0;
    private double lastVoltage = 0.0;
    private ArrayList<TileEntity> currentProducers = new ArrayList<>();
    private double transferBuffer = 0;

    public double getLastEnergyLoss() {
	return lastEnergyLoss;
    }

    @Override
    public double getVoltage() {
	return -1;
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

    private TransferPack sendToReceivers(TransferPack maxTransfer, ArrayList<TileEntity> ignored, boolean debug) {
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
		    double localUsage = 0;
		    if (acceptorInputMap.containsKey(receiver)) {
			boolean shouldRemove = true;
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
			if (shouldRemove) {
			    it.remove();
			}
		    }
		    usage.put(receiver, localUsage);
		}
		for (TileEntity receiver : availableAcceptors) {
		    TransferPack dedicated = TransferPack.joulesVoltage(maxTransfer.getJoules() * (usage.get(receiver) / totalUsage),
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

    private boolean checkForOverload() {
	if (networkMaxTransfer - transmittedThisTick <= 0) {
	    HashSet<SubtypeWire> checkList = new HashSet<>();
	    for (SubtypeWire type : SubtypeWire.values()) {
		if (type != SubtypeWire.superconductive && type != SubtypeWire.insulatedsuperconductive
			&& type != SubtypeWire.logisticssuperconductive && type.maxAmps <= transmittedLastTick / voltage * 20
			&& type.maxAmps <= transmittedThisTick / voltage * 20) {
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
	resistance += cable.getWireType().resistance;
    }

    @Override
    public void updateStatistics() {
	resistance = 0;
	super.updateStatistics();
    }

    public void addProducer(TileEntity tile, double d) {
	currentProducers.add(tile);
	voltage = Math.max(voltage, d);
    }

    @Override
    public void tick() {
	super.tick();
	if (voltage > 0) {
	    double loss = transferBuffer * transferBuffer * resistance / (voltage * voltage);
	    transferBuffer -= loss;
	    energyLoss += loss;
	    transmittedThisTick += loss;
	    transferBuffer -= sendToReceivers(TransferPack.joulesVoltage(transferBuffer, voltage), currentProducers, false).getJoules();
	    checkForOverload();
	}
	lastVoltage = voltage;
	voltage = 0;
	lastEnergyLoss = energyLoss;
	energyLoss = 0;
	currentProducers.clear();

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

    @Override
    public double getJoulesStored() {
	return transferBuffer;
    }

    @Override
    public void setJoulesStored(double joules) {
	transferBuffer = joules;
    }

    @Override
    public double getMaxJoulesStored() {
	Set<TileEntity> availableAcceptors = getEnergyAcceptors();
	Iterator<TileEntity> it = availableAcceptors.iterator();
	double totalUsage = 0;
	HashMap<TileEntity, Double> usage = new HashMap<>();
	while (it.hasNext()) {
	    TileEntity receiver = it.next();
	    double localUsage = 0;
	    if (acceptorInputMap.containsKey(receiver)) {
		for (Direction connection : acceptorInputMap.get(receiver)) {
		    TransferPack pack = ElectricityUtilities.receivePower(receiver, connection, TransferPack.joulesVoltage(Double.MAX_VALUE, voltage),
			    true);
		    if (pack.getJoules() != 0) {
			totalUsage += pack.getJoules();
			localUsage += pack.getJoules();
			break;
		    }
		}
	    }
	    usage.put(receiver, localUsage);
	}
	return totalUsage;
    }
}

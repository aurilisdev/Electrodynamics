package electrodynamics.common.network.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.network.NetworkRegistry;
import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ElectricNetwork extends AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack> implements ICapabilityElectrodynamic {

	public static final int MAXIMUM_OVERLOAD_PERIOD_TICKS = 20;

	private double resistance = 0;
	private double energyLoss = 0;
	private double voltage = 0.0;
	private double lastEnergyLoss = 0;
	private double lastVoltage = 0.0;
	private HashSet<BlockEntity> producersToIgnore = new HashSet<>();
	private double transferBuffer = 0;
	private double maxTransferBuffer = 0;

	private double minimumVoltage = -1.0D;

	private HashMap<BlockEntity, HashMap<Direction, TransferPack>> lastTransfer = new HashMap<>();
	private HashSet<BlockEntity> noUsage = new HashSet<>();

	private boolean locked = false;

	private int ticksOverloaded = 0;

	// private long numTicks = 0;

	public double getLastEnergyLoss() {
		return lastEnergyLoss;
	}

	@Override
	public double getVoltage() {
		return voltage;
	}

	public double getActiveVoltage() {
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

	public ElectricNetwork(Set<AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack>> networks) {
		for (AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack> net : networks) {
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
	public void refresh() {
		ticksOverloaded = 0;
		resistance = 0;
		energyLoss = 0;
		voltage = 0.0;
		lastEnergyLoss = 0;
		lastVoltage = 0.0;
		producersToIgnore.clear();
		transferBuffer = 0;
		maxTransferBuffer = 0;

		minimumVoltage = -1;

		lastTransfer.clear();
		noUsage.clear();

		super.refresh();
	}

	private TransferPack sendToReceivers(TransferPack maxTransfer, ArrayList<BlockEntity> ignored, boolean debug) {
		if (maxTransfer.getJoules() <= 0 || maxTransfer.getVoltage() <= 0) {
			return TransferPack.EMPTY;
		}
		Set<BlockEntity> availableAcceptors = getEnergyAcceptors();
		double joulesSent = 0;
		availableAcceptors.removeAll(ignored);
		availableAcceptors.removeAll(noUsage);

		if (availableAcceptors.isEmpty()) {
			return TransferPack.EMPTY;
		}

		Iterator<BlockEntity> it = availableAcceptors.iterator();
		double totalUsage = 0;
		HashMap<BlockEntity, Double> usage = new HashMap<>();
		while (it.hasNext()) {
			BlockEntity receiver = it.next();
			double localUsage = 0;
			if (acceptorInputMap.containsKey(receiver)) {
				boolean shouldRemove = true;
				for (Direction connection : acceptorInputMap.get(receiver)) {
					TransferPack pack = ElectricityUtils.receivePower(receiver, connection, TransferPack.joulesVoltage(maxTransfer.getJoules(), maxTransfer.getVoltage()), true);
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
		for (BlockEntity receiver : availableAcceptors) {
			TransferPack dedicated = TransferPack.joulesVoltage(maxTransfer.getJoules() * (usage.get(receiver) / totalUsage), maxTransfer.getVoltage());
			HashMap<Direction, TransferPack> perConnectionMap;
			if (acceptorInputMap.containsKey(receiver)) {
				TransferPack perConnection = TransferPack.joulesVoltage(dedicated.getJoules() / acceptorInputMap.get(receiver).size(), maxTransfer.getVoltage());
				perConnectionMap = new HashMap<>();
				for (Direction connection : acceptorInputMap.get(receiver)) {
					TransferPack pack = ElectricityUtils.receivePower(receiver, connection, perConnection, debug);
					perConnectionMap.put(connection, pack);
					joulesSent += pack.getJoules();
					if (!debug) {
						transmittedThisTick += pack.getJoules();
					}
				}
				lastTransfer.put(receiver, perConnectionMap);
			}
		}
		return TransferPack.joulesVoltage(Math.min(maxTransfer.getJoules(), joulesSent), maxTransfer.getVoltage());
	}

	public Set<BlockEntity> getEnergyAcceptors() {
		return new HashSet<>(acceptorSet);
	}

	private boolean checkForOverload() {

		if (voltage <= 0 || networkMaxTransfer * voltage - transmittedThisTick * 20 > 0) {
			ticksOverloaded = 0;
			return false;
		}

		HashSet<SubtypeWire> checkList = new HashSet<>();
		for (SubtypeWire type : SubtypeWire.values()) {
			if (type.conductor.ampacity <= transmittedLastTick / voltage * 20 && type.conductor.ampacity <= transmittedThisTick / voltage * 20) {
				checkList.add(type);
			}
		}

		if (checkList.isEmpty()) {

			ticksOverloaded = 0;
			return false;

		}

		ticksOverloaded++;

		if (ticksOverloaded < MAXIMUM_OVERLOAD_PERIOD_TICKS) {
			return true;
		}

		for (SubtypeWire index : checkList) {
			for (IConductor conductor : conductorTypeMap.get(index)) {
				Scheduler.schedule(1, conductor::destroyViolently);
			}
		}
		return true;

	}

	@Override
	public void updateConductorStatistics(IConductor cable) {
		super.updateConductorStatistics(cable);
		resistance += cable.getWireType().resistance;

	}

	@Override
	public void updateRecieverStatistics(BlockEntity reciever, Direction dir) {
		reciever.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, dir).ifPresent(cap -> {
			if (cap.getVoltage() < 0) {
				return;
			}

			if (minimumVoltage <= 0) {
				minimumVoltage = cap.getVoltage();
			} else if (cap.getVoltage() < minimumVoltage) {
				minimumVoltage = cap.getVoltage();
			}
		});
	}

	@Override
	public void updateStatistics() {
		resistance = 0;
		super.updateStatistics();
	}

	public void addProducer(BlockEntity tile, double d, boolean isEnergyReceiver) {
		if (!isEnergyReceiver) {
			producersToIgnore.add(tile);
		}
		voltage = Math.max(voltage, d);
	}

	@Override
	public void tick() {
		super.tick();
		/*
		 * if (conductorSet.size() > 10) { Electrodynamics.LOGGER.info("Beginning of tick"); Electrodynamics.LOGGER.info("ticks " + numTicks); Electrodynamics.LOGGER.info("length " + conductorSet.size()); Electrodynamics.LOGGER.info("voltage " + voltage); Electrodynamics.LOGGER.info("trans " + transferBuffer); Electrodynamics.LOGGER.info("max trans " + maxTransferBuffer);
		 * 
		 * }
		 */
		lastTransfer.clear();
		noUsage.clear();
		if (maxTransferBuffer > 0) {
			ArrayList<BlockEntity> producersList = new ArrayList<>(producersToIgnore);
			if ((int) voltage != 0 && voltage > 0 && transferBuffer > 0) {
				if (resistance > 0) {
					double bufferAsWatts = transferBuffer * 20.0; // buffer as watts
					double maxWatts = (-voltage * voltage + voltage * Math.sqrt(voltage * voltage + 4.0 * bufferAsWatts * resistance)) / (2.0 * resistance);
					double maxPerTick = maxWatts / 20.0;
					// above is power as watts when powerSend + powerLossToWires = m
					TransferPack send = TransferPack.joulesVoltage(maxPerTick, voltage);
					double sent = sendToReceivers(send, producersList, false).getJoules();
					double lossPerTick = send.getAmps() * send.getAmps() * resistance / 20.0;
					transferBuffer -= sent + lossPerTick;
					energyLoss += lossPerTick;
					transmittedThisTick += lossPerTick;
					checkForOverload();
				} else {
					transferBuffer -= sendToReceivers(TransferPack.joulesVoltage(transferBuffer, voltage), producersList, false).getJoules();
				}
			}
		} else {
			transferBuffer = 0;
		}
		lastVoltage = voltage;
		if (transferBuffer <= 0) {
			voltage = 0;
		}
		lastEnergyLoss = energyLoss;
		energyLoss = 0;

		maxTransferBuffer = 0;
		producersToIgnore.clear();

		maxTransferBuffer = getConnectedLoad(new LoadProfile(TransferPack.joulesVoltage(transmittedLastTick, lastVoltage), TransferPack.joulesVoltage(transmittedLastTick, lastVoltage)), Direction.UP).getJoules();

		Iterator<IConductor> it = conductorSet.iterator();
		boolean broken = false;
		while (it.hasNext()) {
			IConductor conductor = it.next();
			if (conductor instanceof BlockEntity entity && entity.isRemoved() || conductor.getNetwork() != this) {
				broken = true;
				break;
			}
		}
		if (broken) {
			refresh();
		}
		if (getSize() == 0) {
			deregister();
		}
		// Electrodynamics.LOGGER.info("");
		// Electrodynamics.LOGGER.info("End of tick");
		// Electrodynamics.LOGGER.info("length " + conductorSet.size());
		// Electrodynamics.LOGGER.info("voltage " + voltage);
		// Electrodynamics.LOGGER.info("trans " + transferBuffer);
		// Electrodynamics.LOGGER.info("max trans " + maxTransferBuffer);
		// Electrodynamics.LOGGER.info("");
		/*
		 * if (conductorSet.size() > 10) { Electrodynamics.LOGGER.info(""); numTicks++;
		 * 
		 * }
		 */

	}

	@Override
	public boolean isConductor(BlockEntity tile, IConductor requesterCable) {
		return ElectricityUtils.isConductor(tile, requesterCable);
	}

	@Override
	public boolean isConductorClass(BlockEntity tile) {
		return tile instanceof IConductor;
	}

	@Override
	public boolean isAcceptor(BlockEntity acceptor, Direction orientation) {
		return ElectricityUtils.isElectricReceiver(acceptor);
	}

	@Override
	public AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack> createInstance() {
		return new ElectricNetwork();
	}

	@Override
	public AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack> createInstanceConductor(Set<IConductor> conductors) {
		return new ElectricNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack> createInstance(Set<AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack>> networks) {
		return new ElectricNetwork(networks);

	}

	@Override
	public SubtypeWire[] getConductorTypes() {
		return SubtypeWire.values();
	}

	@Override
	public boolean canConnect(BlockEntity acceptor, Direction orientation) {
		return ElectricityUtils.canInputPower(acceptor, orientation.getOpposite());
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
		return maxTransferBuffer;
	}

	@Override
	public void onChange() {

	}

	@Override
	public double getMinimumVoltage() {
		return minimumVoltage;
	}

	@Override
	public double getAmpacity() {
		return networkMaxTransfer;
	}

	@Override
	public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction dir) {

		if (locked) {
			return TransferPack.EMPTY;
		}

		locked = true;

		TransferPack connectedLoad = TransferPack.joulesVoltage(0, 0);

		TransferPack capLoad;

		ArrayList<BlockEntity> load = new ArrayList<>(acceptorSet);

		load.removeAll(producersToIgnore);
		HashMap<Direction, TransferPack> lastPerTile;

		for (BlockEntity tile : load) {

			lastPerTile = lastTransfer.getOrDefault(load, new HashMap<>());

			boolean noUsage = true;

			for (Direction direction : acceptorInputMap.getOrDefault(tile, new HashSet<>())) {

				final LoadProfile profile = new LoadProfile(lastPerTile.getOrDefault(lastPerTile, TransferPack.EMPTY), loadProfile.maximumAvailable());

				capLoad = tile.getCapability(ElectrodynamicsCapabilities.ELECTRODYNAMIC, direction).map(cap -> cap.getConnectedLoad(profile, direction)).orElseGet(() -> tile.getCapability(ForgeCapabilities.ENERGY, dir).map(cap -> TransferPack.joulesVoltage(cap.receiveEnergy(Integer.MAX_VALUE, true), ElectrodynamicsCapabilities.DEFAULT_VOLTAGE)).orElse(TransferPack.EMPTY));
				if (capLoad.getJoules() != 0) {

					noUsage = false;

					connectedLoad = TransferPack.joulesVoltage(connectedLoad.getJoules() + capLoad.getJoules(), Math.max(connectedLoad.getVoltage(), capLoad.getVoltage()));
					break;

				}

			}

			if (noUsage) {
				this.noUsage.add(tile);
			}

		}

		locked = false;

		return connectedLoad;
	}

	@Override
	public boolean isEnergyReceiver() {
		return true;
	}

	@Override
	public boolean isEnergyProducer() {
		return true;
	}

}

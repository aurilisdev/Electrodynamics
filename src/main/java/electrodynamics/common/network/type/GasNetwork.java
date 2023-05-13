package electrodynamics.common.network.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.network.cable.type.IGasPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.network.NetworkRegistry;
import electrodynamics.common.network.utils.GasUtilities;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GasNetwork extends AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack> {

	public double heatLossPerBlock = 0;
	public int maxPressure = 0;

	public double pressureOfTransmitted = 0;
	public double temperatureOfTransmitted = 0;

	public GasNetwork() {
		this(new HashSet<IGasPipe>());
	}

	public GasNetwork(Collection<? extends IGasPipe> pipes) {
		conductorSet.addAll(pipes);
		NetworkRegistry.register(this);
	}

	public GasNetwork(Set<AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack>> networks) {
		for (AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack> network : networks) {
			if (network != null) {
				conductorSet.addAll(network.conductorSet);
				network.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	public GasNetwork(Set<GasNetwork> networks, boolean sep) {
		for (GasNetwork network : networks) {
			if (network != null) {
				conductorSet.addAll(network.conductorSet);
				network.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	@Override
	/**
	 * Returns a GasStack representing how much fluid was actually emitted through the network
	 * 
	 * @param transfer: The gas stack to be transfered
	 * @param ignored:  By convention, the first tile in the ignored list will be the transmitting tile, and will be the point used to
	 *                  determine the heat loss
	 * @param debug:    Whether or not this should be simulated
	 * 
	 * @return Empty if the transmitted pack is empty or if there is no transmitting tile (i.e. ignored is empty). All gas will be
	 *         used if the network exploded
	 */
	public GasStack emit(GasStack transfer, ArrayList<BlockEntity> ignored, boolean debug) {

		if (transfer.getAmount() <= 0 || ignored.isEmpty()) {
			return GasStack.EMPTY;
		}

		if (checkForOverloadAndHandle(transfer, !debug)) {
			return transfer;
		}

		Set<BlockEntity> recievingTiles = ConcurrentHashMap.newKeySet();
		
		recievingTiles.addAll(acceptorSet);

		recievingTiles.removeAll(ignored);

		if (recievingTiles.isEmpty()) {
			return GasStack.EMPTY;
		}

		GasStack copy = transfer.copy();

		BlockPos senderPos = ignored.get(0).getBlockPos();
		GasStack taken = new GasStack(transfer.getGas(), 0, transfer.getTemperature(), transfer.getPressure());

		GasStack gasPerTile;
		GasStack preGasPerTile;
		GasStack gasPerConnection;
		GasStack preGasPerConnection;

		double deltaT = copy.getTemperature() != Gas.ROOM_TEMPERATURE ? -Math.signum(copy.getTemperature() - Gas.ROOM_TEMPERATURE) : 0;

		for (BlockEntity tile : recievingTiles) {

			gasPerTile = new GasStack(copy.getGas(), copy.getAmount() / (double) recievingTiles.size(), copy.getTemperature(), copy.getPressure());
			preGasPerTile = gasPerTile.copy();

			double deltaDegreesKelvin = ((double) (Math.abs(tile.getBlockPos().getX() - senderPos.getX()) + Math.abs(tile.getBlockPos().getY() - senderPos.getY()) + Math.abs(tile.getBlockPos().getZ() - senderPos.getZ()))) * heatLossPerBlock * gasPerTile.getTemperature() * deltaT;

			double newTemperature = gasPerTile.getTemperature() + deltaDegreesKelvin;

			if (deltaT < 0 && newTemperature < Gas.ROOM_TEMPERATURE) {
				newTemperature = Gas.ROOM_TEMPERATURE;
			}

			deltaDegreesKelvin = newTemperature - gasPerTile.getTemperature();

			HashSet<Direction> connections = acceptorInputMap.get(tile);

			int connectionCount = connections.size();

			for (Direction dir : connections) {

				gasPerConnection = new GasStack(gasPerTile.getGas(), gasPerTile.getAmount() / (double) connectionCount, gasPerTile.getTemperature(), gasPerTile.getPressure());
				preGasPerConnection = gasPerConnection.copy();

				gasPerConnection.heat(deltaDegreesKelvin);

				double amtTaken = GasUtilities.recieveGas(tile, dir, gasPerConnection, GasAction.EXECUTE);

				gasPerConnection.shrink(amtTaken);

				if (gasPerConnection.getAmount() > 0) {
					gasPerConnection.heat(-deltaDegreesKelvin);
				}

				gasPerTile.shrink(preGasPerConnection.getAmount() - gasPerConnection.getAmount());

				connectionCount--;
			}

			double takenAmt = preGasPerTile.getAmount() - gasPerTile.getAmount();

			copy.shrink(takenAmt);

			taken.setAmount(taken.getAmount() + takenAmt);

			recievingTiles.remove(tile);
		}

		transmittedThisTick = taken.getAmount();
		temperatureOfTransmitted = taken.getTemperature();
		pressureOfTransmitted = taken.getPressure();

		return taken;
	}

	private boolean checkForOverloadAndHandle(GasStack stack, boolean live) {

		if (stack.getPressure() <= maxPressure) {
			return false;
		}

		boolean exploded = false;
		HashSet<SubtypeGasPipe> overloadedPipes = new HashSet<>();

		for (SubtypeGasPipe pipe : SubtypeGasPipe.values()) {

			if (pipe.pipeMaterial.maxPressure < stack.getPressure()) {

				overloadedPipes.add(pipe);

			}

		}
		for (SubtypeGasPipe pipe : overloadedPipes) {
			for (IGasPipe gasPipe : conductorTypeMap.get(pipe)) {
				if (live) {
					gasPipe.destroyViolently();
				}
				exploded = true;
			}
		}

		return exploded;

	}

	@Override
	public void updateStatistics(IGasPipe cable) {

		SubtypeGasPipe pipe = cable.getPipeType();

		if (heatLossPerBlock == 0) {
			heatLossPerBlock = pipe.effectivePipeHeatLoss;
		}

		heatLossPerBlock = (heatLossPerBlock + pipe.effectivePipeHeatLoss) / 2.0;

		if (maxPressure == 0) {
			maxPressure = pipe.pipeMaterial.maxPressure;
		}

		if (pipe.pipeMaterial.maxPressure < maxPressure) {
			maxPressure = pipe.pipeMaterial.maxPressure;
		}

	}

	@Override
	public void tick() {
		super.tick();
		pressureOfTransmitted = 0;
		temperatureOfTransmitted = 0;

		Iterator<IGasPipe> it = conductorSet.iterator();
		boolean broken = false;
		while (it.hasNext()) {
			IGasPipe conductor = it.next();
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

	}

	@Override
	public boolean isConductor(BlockEntity tile) {
		return tile instanceof IGasPipe;
	}

	@Override
	public boolean isAcceptor(BlockEntity acceptor, Direction orientation) {
		return GasUtilities.isGasReciever(acceptor, orientation.getOpposite());
	}

	@Override
	public boolean canConnect(BlockEntity acceptor, Direction orientation) {
		return GasUtilities.isGasReciever(acceptor, orientation.getOpposite());
	}

	@Override
	public AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack> createInstance() {
		return new GasNetwork();
	}

	@Override
	public AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack> createInstanceConductor(Set<IGasPipe> conductors) {
		return new GasNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack> createInstance(Set<AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack>> networks) {
		return new GasNetwork(networks);
	}

	@Override
	public SubtypeGasPipe[] getConductorTypes() {
		return SubtypeGasPipe.values();
	}

}

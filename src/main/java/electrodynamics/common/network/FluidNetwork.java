package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import electrodynamics.api.network.pipe.IPipe;
import electrodynamics.api.networks.AbstractNetwork;
import electrodynamics.common.block.subtype.SubtypePipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public class FluidNetwork extends AbstractNetwork<IPipe, SubtypePipe, TileEntity, FluidStack> {
	public FluidNetwork() {
		this(new HashSet<IPipe>());
	}

	public FluidNetwork(Collection<? extends IPipe> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<AbstractNetwork<IPipe, SubtypePipe, TileEntity, FluidStack>> networks) {
		for (AbstractNetwork<IPipe, SubtypePipe, TileEntity, FluidStack> net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<FluidNetwork> networks, boolean special) {
		for (FluidNetwork net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	@Override
	public FluidStack emit(FluidStack transfer, ArrayList<TileEntity> ignored) {
		if (transfer.getAmount() > 0) {
			Set<TileEntity> availableAcceptors = getFluidAcceptors(transfer);
			FluidStack joulesSent = new FluidStack(transfer.getFluid(), 0);
			availableAcceptors.removeAll(ignored);
			if (!availableAcceptors.isEmpty()) {
				FluidStack perReceiver = new FluidStack(transfer.getFluid(), transfer.getAmount() / availableAcceptors.size());
				for (TileEntity receiver : availableAcceptors) {
					if (acceptorInputMap.containsKey(receiver)) {
						for (Direction connection : acceptorInputMap.get(receiver)) {
							int rec = FluidUtilities.receiveFluid(receiver, connection, perReceiver, false);
							joulesSent.setAmount(joulesSent.getAmount() + rec);
							transmittedThisTick += rec;
						}
						checkForOverload((int) transmittedThisTick);
					}
				}
			}
			return joulesSent;
		}
		return FluidStack.EMPTY;
	}

	public Set<TileEntity> getFluidAcceptors(FluidStack compare) {
		Set<TileEntity> toReturn = new HashSet<>();
		for (TileEntity acceptor : acceptorSet) {
			if (FluidUtilities.isFluidReceiver(acceptor)) {
				for (Direction connection : acceptorInputMap.get(acceptor)) {
					if (FluidUtilities.canInputFluid(acceptor, connection, compare)) {
						toReturn.add(acceptor);
					}
				}
			}
		}
		return toReturn;
	}

	private boolean checkForOverload(int attemptSend) {
		if (attemptSend >= networkMaxTransfer) {
			HashSet<SubtypePipe> checkList = new HashSet<>();
			for (SubtypePipe type : SubtypePipe.values()) {
				if (type.maxTransfer <= attemptSend) {
					checkList.add(type);
				}
			}
			for (SubtypePipe index : checkList) {
				for (IPipe conductor : conductorTypeMap.get(index)) {
					conductor.destroyViolently();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isConductor(TileEntity tile) {
		return tile instanceof IPipe;
	}

	@Override
	public boolean isAcceptor(TileEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor);
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, TileEntity, FluidStack> createInstance() {
		return new FluidNetwork();
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, TileEntity, FluidStack> createInstanceConductor(Set<IPipe> conductors) {
		return new FluidNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, TileEntity, FluidStack> createInstance(Set<AbstractNetwork<IPipe, SubtypePipe, TileEntity, FluidStack>> networks) {
		return new FluidNetwork(networks);

	}

	@Override
	public SubtypePipe[] getConductorTypes() {
		return SubtypePipe.values();
	}

	@Override
	public boolean canConnect(TileEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor, orientation.getOpposite());
	}
}

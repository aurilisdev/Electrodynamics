package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public class FluidNetwork extends AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> {
	public FluidNetwork() {
		this(new HashSet<IFluidPipe>());
	}

	public FluidNetwork(Collection<? extends IFluidPipe> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack>> networks) {
		for (AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> net : networks) {
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
	public FluidStack emit(FluidStack transfer, ArrayList<BlockEntity> ignored, boolean debug) {
		if (transfer.getAmount() > 0) {
			Set<BlockEntity> availableAcceptors = Sets.newHashSet(acceptorSet);
			FluidStack joulesSent = new FluidStack(transfer.getFluid(), 0);
			availableAcceptors.removeAll(ignored);
			if (!availableAcceptors.isEmpty()) {
				FluidStack perReceiver = new FluidStack(transfer.getFluid(), transfer.getAmount() / availableAcceptors.size());
				for (BlockEntity receiver : availableAcceptors) {
					if (acceptorInputMap.containsKey(receiver)) {
						FluidStack perConnection = new FluidStack(transfer.getFluid(), perReceiver.getAmount() / acceptorInputMap.get(receiver).size());
						for (Direction connection : acceptorInputMap.get(receiver)) {
							int rec = FluidUtilities.receiveFluid(receiver, connection, perConnection, false);
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

	private boolean checkForOverload(int attemptSend) {
		if (attemptSend >= networkMaxTransfer) {
			HashSet<SubtypeFluidPipe> checkList = new HashSet<>();
			for (SubtypeFluidPipe type : SubtypeFluidPipe.values()) {
				if (type.maxTransfer <= attemptSend) {
					checkList.add(type);
				}
			}
			for (SubtypeFluidPipe index : checkList) {
				for (IFluidPipe conductor : conductorTypeMap.get(index)) {
					conductor.destroyViolently();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isAcceptor(BlockEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor);
	}

	@Override
	public AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> createInstance() {
		return new FluidNetwork();
	}

	@Override
	public AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> createInstanceConductor(Set<IFluidPipe> conductors) {
		return new FluidNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> createInstance(Set<AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack>> networks) {
		return new FluidNetwork(networks);

	}

	@Override
	public SubtypeFluidPipe[] getConductorTypes() {
		return SubtypeFluidPipe.values();
	}

	@Override
	public boolean canConnect(BlockEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor, orientation.getOpposite());
	}

	@Override
	public boolean isConductor(BlockEntity tile, IFluidPipe requsterCable) {
		return tile instanceof IFluidPipe;
	}

	@Override
	public boolean isConductorClass(BlockEntity tile) {
		return tile instanceof IFluidPipe;
	}
}

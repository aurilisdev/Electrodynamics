package electrodynamics.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import electrodynamics.api.network.pipe.IPipe;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public class FluidNetwork extends AbstractNetwork<IPipe, SubtypePipe, BlockEntity, FluidStack> {
	public FluidNetwork() {
		this(new HashSet<IPipe>());
	}

	public FluidNetwork(Collection<? extends IPipe> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<AbstractNetwork<IPipe, SubtypePipe, BlockEntity, FluidStack>> networks) {
		for (AbstractNetwork<IPipe, SubtypePipe, BlockEntity, FluidStack> net : networks) {
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
						/**
						 * TODO: Here it doesn't account for side amounts. Say a block with two water inputs. If you try to input 500 mb into the machine it would do 500 mb into both inputs. This is a dupe which isnt relevant yet but might be if we add multiple inputs to a block in the future.
						 */
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
	public boolean isConductor(BlockEntity tile) {
		return tile instanceof IPipe;
	}

	@Override
	public boolean isAcceptor(BlockEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor);
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, BlockEntity, FluidStack> createInstance() {
		return new FluidNetwork();
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, BlockEntity, FluidStack> createInstanceConductor(Set<IPipe> conductors) {
		return new FluidNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IPipe, SubtypePipe, BlockEntity, FluidStack> createInstance(
			Set<AbstractNetwork<IPipe, SubtypePipe, BlockEntity, FluidStack>> networks) {
		return new FluidNetwork(networks);

	}

	@Override
	public SubtypePipe[] getConductorTypes() {
		return SubtypePipe.values();
	}

	@Override
	public boolean canConnect(BlockEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor, orientation.getOpposite());
	}
}

package electrodynamics.common.tile.generic.component.type;

import java.util.HashSet;
import java.util.function.BiFunction;

import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class ComponentElectrodynamic implements Component, IElectrodynamic {
    protected HashSet<Direction> inputDirections = new HashSet<>();
    protected HashSet<Direction> outputDirections = new HashSet<>();
    protected BiFunction<TransferPack, Boolean, TransferPack> guiPacketWriter;
    protected double joules = 0;
    protected double maxJoules = 0;
    private Direction lastReturnedSide = Direction.UP;

    @Override
    public void loadFromNBT(BlockState state, CompoundNBT nbt) {
	joules = nbt.getDouble("joules");
	maxJoules = nbt.getDouble("maxJoules");
    }

    @Override
    public void saveToNBT(CompoundNBT nbt) {
	nbt.putDouble("joules", joules);
	nbt.putDouble("maxJoules", maxJoules);
    }

    @Override
    @Deprecated
    public void setJoulesStored(double joules) {
	setJoules(joules);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction side) {
	return (inputDirections.contains(side) || outputDirections.contains(side))
		&& capability == CapabilityElectrodynamic.ELECTRODYNAMIC;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
	lastReturnedSide = side;
	return (LazyOptional<T>) LazyOptional.of(() -> this);
    }

    @Override
    public TransferPack extractPower(TransferPack transfer, boolean debug) {
	if (outputDirections.contains(lastReturnedSide)) {
	    return IElectrodynamic.super.extractPower(transfer, debug);
	}
	return TransferPack.EMPTY;
    }

    @Override
    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	if (inputDirections.contains(lastReturnedSide)) {
	    return IElectrodynamic.super.receivePower(transfer, debug);
	}
	return TransferPack.EMPTY;
    }

    public ComponentElectrodynamic setJoules(double joules) {
	this.joules = Math.max(0, Math.min(maxJoules, joules));
	return this;
    }

    public ComponentElectrodynamic setMaxJoules(double maxJoules) {
	this.maxJoules = maxJoules;
	return this;
    }

    public ComponentElectrodynamic addInputDirection(Direction dir) {
	inputDirections.add(dir);
	return this;
    }

    public ComponentElectrodynamic addOutputDirection(Direction dir) {
	outputDirections.add(dir);
	return this;
    }

    @Override
    public double getJoulesStored() {
	return joules;
    }

    @Override
    public double getMaxJoulesStored() {
	return maxJoules;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Electrodynamic;
    }
}

package electrodynamics.common.tile.generic.component.type;

import java.util.HashSet;
import java.util.function.BiFunction;

import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.electric.IElectrodynamic;
import electrodynamics.api.utilities.TileUtilities;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class ComponentElectrodynamic implements Component, IElectrodynamic {
    private GenericTile holder;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    protected BiFunction<TransferPack, Boolean, TransferPack> receivePower = IElectrodynamic.super::receivePower;
    protected BiFunction<TransferPack, Boolean, TransferPack> extractPower = IElectrodynamic.super::extractPower;
    protected HashSet<Direction> relativeOutputDirections = new HashSet<>();
    protected HashSet<Direction> relativeInputDirections = new HashSet<>();
    protected HashSet<Direction> outputDirections = new HashSet<>();
    protected HashSet<Direction> inputDirections = new HashSet<>();
    protected double voltage = CapabilityElectrodynamic.DEFAULT_VOLTAGE;
    protected double maxJoules = 0;
    protected double joules = 0;
    private Direction lastReturnedSide = Direction.UP;

    @Override
    public void loadFromNBT(BlockState state, CompoundNBT nbt) {
	maxJoules = nbt.getDouble("maxJoules");
	voltage = nbt.getDouble("voltage");
	joules = nbt.getDouble("joules");
    }

    @Override
    public void saveToNBT(CompoundNBT nbt) {
	nbt.putDouble("maxJoules", maxJoules);
	nbt.putDouble("voltage", voltage);
	nbt.putDouble("joules", joules);
    }

    @Override
    public double getVoltage() {
	return voltage;
    }

    public void setVoltage(double voltage) {
	this.voltage = voltage;
    }

    @Override
    @Deprecated
    public void setJoulesStored(double joules) {
	setJoules(joules);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction side) {
	return (inputDirections.contains(side) || outputDirections.contains(side)
		|| (holder.hasComponent(ComponentType.Direction) && (relativeInputDirections
			.contains(TileUtilities.getRelativeSide(
				holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(),
				lastReturnedSide))
			|| relativeOutputDirections.contains(TileUtilities.getRelativeSide(
				holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(),
				lastReturnedSide)))))
		&& capability == CapabilityElectrodynamic.ELECTRODYNAMIC;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
	lastReturnedSide = side;
	return (LazyOptional<T>) LazyOptional.of(() -> this);
    }

    @Override
    public TransferPack extractPower(TransferPack transfer, boolean debug) {
	if (outputDirections.contains(lastReturnedSide) || (holder.hasComponent(ComponentType.Direction)
		&& relativeOutputDirections.contains(TileUtilities.getRelativeSide(
			holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(),
			lastReturnedSide)))) {
	    return extractPower.apply(transfer, debug);
	}
	return TransferPack.EMPTY;
    }

    @Override
    public TransferPack receivePower(TransferPack transfer, boolean debug) {
	if (inputDirections.contains(lastReturnedSide) || (holder.hasComponent(ComponentType.Direction)
		&& relativeInputDirections.contains(TileUtilities.getRelativeSide(
			holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(),
			lastReturnedSide)))) {
	    return receivePower.apply(transfer, debug);
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

    public ComponentElectrodynamic addRelativeInputDirection(Direction dir) {
	relativeInputDirections.add(dir);
	return this;
    }

    public ComponentElectrodynamic addRelativeOutputDirection(Direction dir) {
	relativeOutputDirections.add(dir);
	return this;
    }

    public ComponentElectrodynamic setReceivePower(BiFunction<TransferPack, Boolean, TransferPack> receivePower) {
	this.receivePower = receivePower;
	return this;
    }

    public ComponentElectrodynamic setExtractPower(BiFunction<TransferPack, Boolean, TransferPack> extractPower) {
	this.extractPower = extractPower;
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

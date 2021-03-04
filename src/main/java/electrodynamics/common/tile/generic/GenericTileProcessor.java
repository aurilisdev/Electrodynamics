package electrodynamics.common.tile.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.api.tile.processing.IElectricProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.MachineRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class GenericTileProcessor extends GenericTileInventory
	implements ITickableTileBase, IElectricProcessor {
    public static final double DEFAULT_BASIC_MACHINE_VOLTAGE = 120.0;
    protected HashSet<Integer> upgradeSlots = new HashSet<>();
    protected double currentOperatingTick = 0;
    protected double joules = 0;
    protected double currentSpeedMultiplier = 1;
    protected long ticks = 0;

    protected GenericTileProcessor(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    protected void addUpgradeSlots(Integer... i) {
	upgradeSlots.addAll(new ArrayList<>(Arrays.asList(i)));
    }

    @Override
    public void tick() {
	ticks++;
	ITickableTileBase.super.tick();
    }

    @Override
    public void tickServer() {
	trackInteger(0, (int) currentOperatingTick);
	trackInteger(1, (int) getVoltage());
	trackInteger(2, (int) Math.ceil(getJoulesPerTick()));
	trackInteger(3, getRequiredTicks() == 0 ? 1 : getRequiredTicks());
	boolean failed = false;
	if (canProcess()) {
	    joules -= getJoulesPerTick();
	    increaseOperatingTick();
	    if (currentOperatingTick >= getRequiredTicks()) {
		process();
		currentOperatingTick = 0;
	    }
	} else {
	    failed = true;
	}
	if (failed) {
	    if (currentOperatingTick > 0) {
		failedOperation();
	    }
	}
    }

    protected void increaseOperatingTick() {
	currentSpeedMultiplier = 1;
	for (Integer in : upgradeSlots) {
	    ItemStack stack = items.get(in);
	    if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		ItemProcessorUpgrade upgrade = (ItemProcessorUpgrade) stack.getItem();
		currentSpeedMultiplier *= upgrade.subtype.speedMultiplier;
	    }
	}
	currentOperatingTick += currentSpeedMultiplier;
    }

    protected void failedOperation() {
	currentOperatingTick = 0;
    }

    public boolean isProcessing() {
	return currentOperatingTick > 0;
    }

    @Override
    public double getVoltage() {
	return DEFAULT_BASIC_MACHINE_VOLTAGE;
    }

    public abstract int getRequiredTicks();

    public boolean canProcess() {
	return MachineRecipes.canProcess(this);
    }

    public void process() {
	MachineRecipes.process(this);
    }

    @Override
    public void setJoulesStored(double joules) {
	this.joules = Math.min(getMaxJoulesStored(), joules);
    }

    @Override
    public double getJoulesStored() {
	return joules;
    }

    @Override
    public double getMaxJoulesStored() {
	return getJoulesPerTick() * 10;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
	if (capability == CapabilityElectrodynamic.ELECTRODYNAMIC && compareCapabilityDirectionElectricity(facing)) {
	    return (LazyOptional<T>) LazyOptional.of(() -> this);
	}
	return super.getCapability(capability, facing);
    }

    public boolean compareCapabilityDirectionElectricity(Direction dir) {
	return getFacing().getOpposite() == dir;
    }
}

package electrodynamics.common.tile.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.MachineRecipes;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentInventory;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public abstract class GenericTileProcessor extends GenericTileTicking {
    protected HashSet<Integer> upgradeSlots = new HashSet<>();
    protected double currentOperatingTick = 0;
    protected double joules = 0;
    protected double currentSpeedMultiplier = 1;
    protected long ticks = 0;

    protected GenericTileProcessor(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
	addComponent(new ComponentElectrodynamic());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
	addComponent(new ComponentInventory());

    }

    protected void addUpgradeSlots(Integer... i) {
	upgradeSlots.addAll(new ArrayList<>(Arrays.asList(i)));
    }

    public void tickServer() {
	ticks++;
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
	if (failed && currentOperatingTick > 0) {
	    currentOperatingTick = 0;
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

    public boolean isProcessing() {
	return currentOperatingTick > 0;
    }

    public abstract int getRequiredTicks();

    public boolean canProcess() {
	return MachineRecipes.canProcess(this);
    }

    public void process() {
	MachineRecipes.process(this);
    }

    public abstract double getJoulesPerTick();

}

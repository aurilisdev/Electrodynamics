package electrodynamics.common.tile.generic.component.type;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ComponentProcessor implements Component {
    private GenericTile holder;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    protected double speedMultiplier;
    protected double operatingTicks;
    protected double joulesPerTick;
    protected long requiredTicks = 100;
    protected long ticks;
    private Predicate<ComponentProcessor> canProcess = component -> false;
    private Consumer<ComponentProcessor> process;
    private Consumer<ComponentProcessor> failed;
    private HashSet<Integer> upgradeSlots = new HashSet<>();

    public ComponentProcessor(GenericTile source) {
	setHolder(source);
	if (!holder.hasComponent(ComponentType.Inventory)) {
	    throw new UnsupportedOperationException(
		    "You need to implement an inventory component to use the processor component!");
	}
	if (holder.hasComponent(ComponentType.Tickable)) {
	    holder.<ComponentTickable>getComponent(ComponentType.Tickable).addTickServer(this::tickServer);
	} else {
	    throw new UnsupportedOperationException(
		    "You need to implement a tickable component to use the processor component!");
	}
    }

    private void tickServer(ComponentTickable tickable) {
	ticks++;
	if (canProcess.test(this)) {
	    if (holder.hasComponent(ComponentType.Electrodynamic)) {
		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.extractPower(TransferPack.joulesVoltage(joulesPerTick, electro.getVoltage()), false);
	    }
	    speedMultiplier = 1;
	    ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	    for (int slot : upgradeSlots) {
		ItemStack stack = inv.getStackInSlot(slot);
		if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		    speedMultiplier *= ((ItemProcessorUpgrade) stack.getItem()).subtype.speedMultiplier;
		}
	    }
	    if (operatingTicks >= requiredTicks && process != null) {
		process.accept(this);
	    }
	} else if (operatingTicks > 0) {
	    operatingTicks = 0;
	    if (failed != null) {
		failed.accept(this);
	    }
	}
    }
    

    @Override
    public void saveToNBT(CompoundNBT nbt) {
	nbt.putDouble("operatingTicks", operatingTicks);
    }

    @Override
    public void loadFromNBT(BlockState state, CompoundNBT nbt) {
	operatingTicks = nbt.getDouble("operatingTicks");
    }

    public ComponentProcessor setProcess(Consumer<ComponentProcessor> process) {
	this.process = process;
	return this;
    }

    public ComponentProcessor setFailed(Consumer<ComponentProcessor> failed) {
	this.failed = failed;
	return this;
    }

    public ComponentProcessor addUpgradeSlots(int... slot) {
	for (int i : slot) {
	    upgradeSlots.add(i);
	}
	return this;
    }

    public ComponentProcessor setJoulesPerTick(double joulesPerTick) {
	this.joulesPerTick = joulesPerTick;
	return this;
    }

    public ComponentProcessor setRequiredTicks(long requiredTicks) {
	this.requiredTicks = requiredTicks;
	return this;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Processor;
    }

}

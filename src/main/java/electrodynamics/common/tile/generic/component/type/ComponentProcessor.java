package electrodynamics.common.tile.generic.component.type;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.tile.generic.GenericTile;
import electrodynamics.common.tile.generic.component.Component;
import electrodynamics.common.tile.generic.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ComponentProcessor implements Component {
    private GenericTile holder;

    @Override
    public void setHolder(GenericTile holder) {
	this.holder = holder;
    }

    public double operatingSpeed;
    public double operatingTicks;
    public double joulesPerTick;
    public long requiredTicks = 100;
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
	if (holder.hasComponent(ComponentType.PacketHandler)) {
	    ComponentPacketHandler handler = holder.getComponent(ComponentType.PacketHandler);
	    handler.addGuiPacketWriter(this::writeGuiPacket);
	    handler.addGuiPacketReader(this::readGuiPacket);
	}
    }

    private void tickServer(ComponentTickable tickable) {
	if (holder.hasComponent(ComponentType.Electrodynamic)) {
	    ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	    electro.setMaxJoules(joulesPerTick * operatingSpeed * 10);
	}
	if (canProcess.test(this)) {
	    operatingSpeed = 1;
	    ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	    for (int slot : upgradeSlots) {
		ItemStack stack = inv.getStackInSlot(slot);
		if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		    operatingSpeed *= ((ItemProcessorUpgrade) stack.getItem()).subtype.speedMultiplier;
		}
	    }
	    operatingTicks += operatingSpeed;
	    if (operatingTicks >= requiredTicks && process != null) {
		process.accept(this);
		operatingTicks = 0;
	    }
	    if (holder.hasComponent(ComponentType.Electrodynamic)) {
		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.setJoules(electro.getJoulesStored() - joulesPerTick * operatingSpeed);
	    }
	} else if (operatingTicks > 0) {
	    operatingTicks = 0;
	    if (failed != null) {
		failed.accept(this);
	    }
	}
    }

    private void writeGuiPacket(CompoundNBT nbt) {
	nbt.putDouble("operatingTicks", operatingTicks);
	nbt.putDouble("joulesPerTick", joulesPerTick * operatingSpeed);
	nbt.putLong("requiredTicks", requiredTicks);
    }

    private void readGuiPacket(CompoundNBT nbt) {
	operatingTicks = nbt.getDouble("operatingTicks");
	joulesPerTick = nbt.getDouble("joulesPerTick");
	requiredTicks = nbt.getLong("requiredTicks");
    }

    public ComponentProcessor setProcess(Consumer<ComponentProcessor> process) {
	this.process = process;
	return this;
    }

    public ComponentProcessor setFailed(Consumer<ComponentProcessor> failed) {
	this.failed = failed;
	return this;
    }

    public ComponentProcessor setCanProcess(Predicate<ComponentProcessor> canProcess) {
	this.canProcess = canProcess;
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

    public double getJoulesPerTick() {
	return joulesPerTick;
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

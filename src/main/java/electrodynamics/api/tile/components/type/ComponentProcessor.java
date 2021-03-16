package electrodynamics.api.tile.components.type;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.api.tile.GenericTile;
import electrodynamics.api.tile.components.Component;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.common.item.ItemProcessorUpgrade;
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
    public long requiredTicks;
    private Predicate<ComponentProcessor> canProcess = component -> false;
    private Consumer<ComponentProcessor> process;
    private Consumer<ComponentProcessor> failed;
    private ComponentProcessorType processorType;
    private HashSet<Integer> upgradeSlots = new HashSet<>();
    private int inputOne = 0;
    private int inputTwo = 1;
    private int output = 1;

    public ComponentProcessor(GenericTile source) {
	setHolder(source);
	if (!holder.hasComponent(ComponentType.Inventory)) {
	    throw new UnsupportedOperationException("You need to implement an inventory component to use the processor component!");
	}
	if (holder.hasComponent(ComponentType.Tickable)) {
	    holder.<ComponentTickable>getComponent(ComponentType.Tickable).addTickServer(this::tickServer);
	} else {
	    throw new UnsupportedOperationException("You need to implement a tickable component to use the processor component!");
	}
	if (holder.hasComponent(ComponentType.PacketHandler)) {
	    ComponentPacketHandler handler = holder.getComponent(ComponentType.PacketHandler);
	    handler.addGuiPacketWriter(this::writeGuiPacket);
	    handler.addGuiPacketReader(this::readGuiPacket);
	}
    }

    private void tickServer(ComponentTickable tickable) {
	double calculatedOperatingSpeed = 1;
	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	for (int slot : upgradeSlots) {
	    ItemStack stack = inv.getStackInSlot(slot);
	    if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		calculatedOperatingSpeed *= ((ItemProcessorUpgrade) stack.getItem()).subtype.speedMultiplier;
	    }
	}
	if (calculatedOperatingSpeed > 0 && calculatedOperatingSpeed != operatingSpeed) {
	    operatingSpeed = calculatedOperatingSpeed;
	}
	if (holder.hasComponent(ComponentType.Electrodynamic)) {
	    ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	    electro.setMaxJoules(joulesPerTick * operatingSpeed * 10);
	}
	if (canProcess.test(this)) {
	    operatingTicks += operatingSpeed;
	    if (operatingTicks >= requiredTicks) {
		if (process != null) {
		    process.accept(this);
		}
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

    public ComponentProcessorType getProcessorType() {
	return processorType;
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

    public ComponentProcessor setType(ComponentProcessorType type) {
	processorType = type;
	inputOne = 0;
	inputTwo = 1;
	output = type == ComponentProcessorType.DoubleObjectToObject ? 2 : 1;
	return this;
    }

    public ComponentProcessor addUpgradeSlots(int... slot) {
	for (int i : slot) {
	    upgradeSlots.add(i);
	}
	return this;
    }

    public ComponentProcessor setInputSlot(int inputOne) {
	this.inputOne = inputOne;
	return this;
    }

    public ComponentProcessor setSecondInputSlot(int inputTwo) {
	this.inputTwo = inputTwo;
	return this;
    }

    public ComponentProcessor setOutputSlot(int output) {
	this.output = output;
	return this;
    }

    public ItemStack getInput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(inputOne);
    }

    public ItemStack getSecondInput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(inputTwo);
    }

    public ItemStack getOutput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(output);
    }

    public ComponentProcessor setOutput(ItemStack stack) {
	holder.<ComponentInventory>getComponent(ComponentType.Inventory).setInventorySlotContents(output, stack);
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

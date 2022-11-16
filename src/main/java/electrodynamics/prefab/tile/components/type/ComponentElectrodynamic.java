package electrodynamics.prefab.tile.components.type;

import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class ComponentElectrodynamic implements Component, ICapabilityElectrodynamic {
	private GenericTile holder;

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	public GenericTile getHolder() {
		return holder;
	}

	protected BiFunction<TransferPack, Boolean, TransferPack> functionReceivePower = ICapabilityElectrodynamic.super::receivePower;
	protected BiFunction<TransferPack, Boolean, TransferPack> functionExtractPower = ICapabilityElectrodynamic.super::extractPower;
	protected Consumer<Double> setJoules = null;
	protected HashSet<Direction> relativeOutputDirections = new HashSet<>();
	protected HashSet<Direction> relativeInputDirections = new HashSet<>();
	protected HashSet<Direction> outputDirections = new HashSet<>();
	protected HashSet<Direction> inputDirections = new HashSet<>();
	protected Property<Double> voltage;
	protected Property<Double> maxJoules;
	protected Property<Double> joules;
	protected DoubleSupplier getJoules = () -> joules.get();
	protected BooleanSupplier hasCapability = () -> true;
	private Direction lastReturnedSide = Direction.UP;

	public ComponentElectrodynamic(GenericTile source) {
		holder(source);
		voltage = source.property(new Property<Double>(PropertyType.Double, "voltage")).set(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE);
		maxJoules = source.property(new Property<Double>(PropertyType.Double, "maxJoules")).set(0.0);
		joules = source.property(new Property<Double>(PropertyType.Double, "joules")).set(0.0).save();
	}

	@Override
	public double getVoltage() {
		return voltage.get();
	}

	@Override
	@Deprecated(forRemoval = false, since = "This is only if you need to force the internal joules count and is overriden in classes where you can do this.")
	public void setJoulesStored(double joules) {
		joules(joules);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, Direction side) {
		lastReturnedSide = side;
		if (capability != ElectrodynamicsCapabilities.ELECTRODYNAMIC || !hasCapability.getAsBoolean()) {
			return false;
		}
		if (side == null || inputDirections.contains(side) || outputDirections.contains(side)) {
			return true;
		}
		Direction dir = holder.hasComponent(ComponentType.Direction) ? holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection() : null;
		if (dir != null) {
			return relativeInputDirections.contains(BlockEntityUtils.getRelativeSide(dir, side)) || relativeOutputDirections.contains(BlockEntityUtils.getRelativeSide(dir, side));
		}
		return false;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		lastReturnedSide = side;
		return hasCapability(capability, side) ? (LazyOptional<T>) LazyOptional.of(() -> this) : LazyOptional.empty();
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, boolean debug) {
		if (outputDirections.contains(lastReturnedSide) || holder.hasComponent(ComponentType.Direction) && relativeOutputDirections.contains(BlockEntityUtils.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), lastReturnedSide))) {
			return functionExtractPower.apply(transfer, debug);
		}
		return TransferPack.EMPTY;
	}

	@Override
	public TransferPack receivePower(TransferPack transfer, boolean debug) {
		if (inputDirections.contains(lastReturnedSide) || holder.hasComponent(ComponentType.Direction) && relativeInputDirections.contains(BlockEntityUtils.getRelativeSide(holder.<ComponentDirection>getComponent(ComponentType.Direction).getDirection(), lastReturnedSide))) {
			return functionReceivePower.apply(transfer, debug);
		}
		return TransferPack.EMPTY;
	}

	public ComponentElectrodynamic joules(double joules) {
		if (setJoules != null) {
			setJoules.accept(joules);
		} else {
			this.joules.set(Math.max(0, Math.min(maxJoules.get(), joules)));
		}
		return this;
	}

	public ComponentElectrodynamic maxJoules(double maxJoules) {
		this.maxJoules.set(Math.max(maxJoules, 0));
		if (joules.get() > maxJoules) {
			joules.set(maxJoules);
		}
		return this;
	}

	public ComponentElectrodynamic universalInput() {
		for (Direction dir : Direction.values()) {
			input(dir);
		}
		return this;
	}

	public ComponentElectrodynamic input(Direction dir) {
		inputDirections.add(dir);
		return this;
	}

	public ComponentElectrodynamic output(Direction dir) {
		outputDirections.add(dir);
		return this;
	}

	public ComponentElectrodynamic relativeInput(Direction dir) {
		relativeInputDirections.add(dir);
		return this;
	}

	public ComponentElectrodynamic relativeOutput(Direction dir) {
		relativeOutputDirections.add(dir);
		return this;
	}

	public ComponentElectrodynamic receivePower(BiFunction<TransferPack, Boolean, TransferPack> receivePower) {
		functionReceivePower = receivePower;
		return this;
	}

	public ComponentElectrodynamic extractPower(BiFunction<TransferPack, Boolean, TransferPack> extractPower) {
		functionExtractPower = extractPower;
		return this;
	}

	public ComponentElectrodynamic setJoules(Consumer<Double> setJoules) {
		this.setJoules = setJoules;
		return this;
	}

	public ComponentElectrodynamic getJoules(DoubleSupplier getJoules) {
		this.getJoules = getJoules;
		return this;
	}

	public ComponentElectrodynamic voltage(double voltage) {
		this.voltage.set(voltage);
		return this;
	}

	public ComponentElectrodynamic drainElectricItem(int slot) {
		if (holder.hasComponent(ComponentType.Inventory)) {
			ComponentInventory inventory = holder.getComponent(ComponentType.Inventory);
			ItemStack stack = inventory.getItem(slot);
			if (stack.getItem() instanceof ItemElectric) {
				IItemElectric el = (IItemElectric) stack.getItem();
				functionReceivePower.apply(el.extractPower(stack, maxJoules.get() - joules.get(), false), false);
			}
		}
		return this;
	}

	public ComponentElectrodynamic fillElectricItem(int slot) {
		if (holder.hasComponent(ComponentType.Inventory)) {
			ComponentInventory inventory = holder.getComponent(ComponentType.Inventory);
			ItemStack stack = inventory.getItem(slot);
			if (stack.getItem() instanceof ItemElectric) {
				IItemElectric el = (IItemElectric) stack.getItem();
				functionExtractPower.apply(el.receivePower(stack, TransferPack.joulesVoltage(joules.get(), voltage.get()), false), false);
			}
		}
		return this;
	}

	@Override
	public double getJoulesStored() {
		return getJoules.getAsDouble();
	}

	@Override
	public double getMaxJoulesStored() {
		return maxJoules.get();
	}

	@Override
	public void overVoltage(TransferPack transfer) {
		Level world = holder.getLevel();
		BlockPos pos = holder.getBlockPos();
		world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()), BlockInteraction.DESTROY);
	}

	@Override
	public ComponentType getType() {
		return ComponentType.Electrodynamic;
	}

	public ComponentElectrodynamic setCapabilityTest(BooleanSupplier test) {
		hasCapability = test;
		return this;
	}

}

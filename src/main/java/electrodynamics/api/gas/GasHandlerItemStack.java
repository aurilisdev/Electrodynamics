package electrodynamics.api.gas;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.gas.IGasHandlerItem;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Default implementation of IGasHandlerItem to be bound to an item's capability
 * 
 * @author skip999
 *
 */
public class GasHandlerItemStack implements IGasHandlerItem, ICapabilityProvider {

	public static final String GAS_NBT_KEY = "Gas";

	private final LazyOptional<IGasHandlerItem> holder = LazyOptional.of(() -> this);

	private Predicate<GasStack> isGasValid = gas -> true;
	protected ItemStack container;
	private ItemStack slag = new ItemStack(ElectrodynamicsItems.ITEM_SLAG.get(), 1);
	protected double capacity;
	protected double maxTemperature;
	protected int maxPressure;

	public GasHandlerItemStack(ItemStack container, double capacity, double maxTemperature, int maxPressure) {
		this.container = container;
		this.capacity = capacity;
		this.maxTemperature = maxTemperature;
		this.maxPressure = maxPressure;
	}

	public GasHandlerItemStack setPredicate(Predicate<GasStack> predicate) {
		isGasValid = predicate;
		return this;
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ElectrodynamicsCapabilities.GAS_HANDLER_ITEM) {
			return holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public int getTanks() {
		return 1;
	}

	public void setGas(GasStack gas) {

		CompoundTag tag = container.getOrCreateTag();

		tag.put(GAS_NBT_KEY, gas.writeToNbt());
	}

	@Override
	public GasStack getGasInTank(int tank) {

		CompoundTag tag = container.getOrCreateTag();

		if (!tag.contains(GAS_NBT_KEY)) {
			return GasStack.EMPTY;
		}

		return GasStack.readFromNbt(tag.getCompound(GAS_NBT_KEY));
	}

	@Override
	public double getTankCapacity(int tank) {
		return capacity;
	}

	@Override
	public double getTankMaxTemperature(int tank) {
		return maxTemperature;
	}

	@Override
	public int getTankMaxPressure(int tank) {
		return maxPressure;
	}

	@Override
	public boolean isGasValid(int tank, GasStack gas) {
		return isGasValid.test(gas);
	}

	@Override
	public double fillTank(int tank, GasStack resource, GasAction action) {
		if (resource.isEmpty()) {
			return 0;
		}

		if (!isGasValid(tank, resource)) {
			return 0;
		}

		if (isEmpty()) {

			double accepted = resource.getAmount() > capacity ? capacity : resource.getAmount();

			if (action == GasAction.EXECUTE) {

				setGas(new GasStack(resource.getGas(), accepted, resource.getTemperature(), resource.getPressure()));

				if (resource.getTemperature() > maxTemperature) {

					onOverheat();

				}

				if (resource.getPressure() > maxPressure) {

					onOverpressure();

				}

			}

			return accepted;

		} else {

			GasStack gas = getGasInTank(0);

			if (!gas.isSameGas(resource)) {
				return 0;
			}

			double canTake = GasStack.getMaximumAcceptance(gas, resource, capacity);

			if (canTake == 0) {
				return 0;
			}

			if (action == GasAction.EXECUTE) {

				GasStack accepted = resource.copy();

				accepted.setAmount(canTake);

				GasStack equalized = GasStack.equalizePresrsureAndTemperature(gas, accepted);

				setGas(equalized);

				if (gas.getTemperature() > maxTemperature) {

					onOverheat();

				}

				if (gas.getPressure() > maxPressure) {

					onOverpressure();

				}

			}

			return canTake;

		}
	}

	@Override
	public GasStack drainTank(int tank, GasStack resource, GasAction action) {

		GasStack gas = getGasInTank(0);

		if (resource.isEmpty() || !gas.isSameGas(resource) || !gas.isSamePressure(resource) || !gas.isSameTemperature(resource)) {
			return GasStack.EMPTY;
		}

		return drainTank(tank, resource.getAmount(), action);

	}

	@Override
	public GasStack drainTank(int tank, double amount, GasAction action) {

		if (isEmpty() || amount == 0) {
			return GasStack.EMPTY;
		}

		GasStack gas = getGasInTank(0);

		double taken = gas.getAmount() > amount ? amount : gas.getAmount();

		GasStack takenStack = new GasStack(gas.getGas(), taken, gas.getTemperature(), gas.getPressure());

		if (action == GasAction.EXECUTE) {

			gas.shrink(taken);

			if (gas.getAmount() == 0) {

				setContainerToEmpty();

			} else {

				setGas(gas);

			}
		}

		return takenStack;
	}

	@Override
	public double heat(int tank, double deltaTemperature, GasAction action) {

		GasStack gas = getGasInTank(0);

		if (gas.isAbsoluteZero() && deltaTemperature < 0) {
			return -1;
		}

		GasStack updated = gas.copy();

		updated.heat(deltaTemperature);

		if (updated.getAmount() > capacity) {
			return -1;
		}

		if (action == GasAction.EXECUTE) {

			setGas(updated);

			if (gas.getTemperature() > maxTemperature) {

				onOverheat();

			}

		}

		return capacity - updated.getAmount();
	}

	@Override
	public double bringPressureTo(int tank, int atm, GasAction action) {

		GasStack gas = getGasInTank(0);

		if (gas.isVacuum() && atm < GasStack.VACUUM) {
			return -1;
		}

		GasStack updated = gas.copy();

		updated.bringPressureTo(atm);

		if (updated.getAmount() > capacity) {

			return -1;

		}

		if (action == GasAction.EXECUTE) {

			setGas(updated);

			if (gas.getPressure() > maxPressure) {

				onOverpressure();

			}

		}

		return capacity - updated.getAmount();
	}

	@Override
	public ItemStack getContainer() {
		return container;
	}

	public void onOverheat() {
		container = slag;
	}

	public void onOverpressure() {
		container.shrink(1);
	}

	public boolean isEmpty() {
		return getGasInTank(0).isEmpty();
	}

	public void setContainerToEmpty() {
		container.getOrCreateTag().remove(GAS_NBT_KEY);
	}

	/**
	 * Destroys the container item when it's emptied.
	 */
	public static class Consumable extends GasHandlerItemStack {

		public Consumable(ItemStack container, double capacity, double maxTemperature, int maxPressure) {
			super(container, capacity, maxTemperature, maxPressure);
		}

		@Override
		public void setContainerToEmpty() {
			super.setContainerToEmpty();
			container.shrink(1);
		}
	}

	/**
	 * Swaps the container item for a different one when it's emptied.
	 */
	public static class SwapEmpty extends GasHandlerItemStack {

		protected final ItemStack emptyContainer;

		public SwapEmpty(ItemStack container, ItemStack emptyContainer, double capacity, double maxTemperature, int maxPressure) {
			super(container, capacity, maxTemperature, maxPressure);
			this.emptyContainer = emptyContainer;
		}

		@Override
		public void setContainerToEmpty() {
			super.setContainerToEmpty();
			container = emptyContainer;
		}
	}

}

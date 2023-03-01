package electrodynamics.prefab.tile.components.type;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.fluid2fluid.Fluid2FluidRecipe;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.categories.item2fluid.Item2FluidRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ComponentProcessor implements Component {
	private GenericTile holder;

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	public Property<Double> operatingSpeed;
	public Property<Double> operatingTicks;
	public Property<Double> usage;
	public Property<Double> requiredTicks;
	private Predicate<ComponentProcessor> canProcess = component -> false;
	private Consumer<ComponentProcessor> process;
	private Consumer<ComponentProcessor> failed;
	private int processorNumber = 0;
	private int totalProcessors = 1;

	private List<ElectrodynamicsRecipe> cachedRecipes = new ArrayList<>();
	private ElectrodynamicsRecipe recipe;
	private double storedXp = 0.0;

	public ComponentProcessor(GenericTile source) {
		this(source, 0, 1);
	}

	public ComponentProcessor(GenericTile source, int processorNumber, int totalProcessors) {
		holder(source);
		this.processorNumber = processorNumber;
		this.totalProcessors = totalProcessors;
		operatingSpeed = holder.property(new Property<>(PropertyType.Double, "operatingSpeed" + processorNumber, 1.0));
		operatingTicks = holder.property(new Property<>(PropertyType.Double, "operatingTicks" + processorNumber, 0.0));
		usage = holder.property(new Property<>(PropertyType.Double, "recipeUsage" + processorNumber, 0.0));
		requiredTicks = holder.property(new Property<>(PropertyType.Double, "requiredTicks" + processorNumber, 0.0));
		if (!holder.hasComponent(ComponentType.Inventory)) {
			throw new UnsupportedOperationException("You need to implement an inventory component to use the processor component!");
		}
		if (holder.hasComponent(ComponentType.Tickable)) {
			holder.<ComponentTickable>getComponent(ComponentType.Tickable).tickServer(this::tickServer);
		} else {
			throw new UnsupportedOperationException("You need to implement a tickable component to use the processor component!");
		}
	}

	private void tickServer(ComponentTickable tickable) {
		ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
		for (ItemStack stack : inv.getUpgradeContents()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade && !upgrade.subtype.isEmpty) {
				for (int i = 0; i < stack.getCount(); i++) {
					upgrade.subtype.applyUpgrade.accept(holder, this, stack);
				}
			}
		}
		if (canProcess.test(this)) {
			operatingTicks.set(operatingTicks.get() + operatingSpeed.get());
			if (operatingTicks.get() >= requiredTicks.get()) {
				if (process != null) {
					process.accept(this);
				}
				operatingTicks.set(0.0);
			}
			if (holder.hasComponent(ComponentType.Electrodynamic)) {
				ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
				electro.joules(electro.getJoulesStored() - usage.get() * operatingSpeed.get());
			}
		} else if (operatingTicks.get() > 0) {
			// TODO look at keeping progress if the recipe is unchanged
			operatingTicks.set(0.0);
			if (failed != null) {
				failed.accept(this);
			}
		}
	}

	public ComponentProcessor process(Consumer<ComponentProcessor> process) {
		this.process = process;
		return this;
	}

	public ComponentProcessor failed(Consumer<ComponentProcessor> failed) {
		this.failed = failed;
		return this;
	}

	public ComponentProcessor canProcess(Predicate<ComponentProcessor> canProcess) {
		this.canProcess = canProcess;
		return this;
	}

	public ComponentProcessor usage(double usage) {
		this.usage.set(usage);
		return this;
	}

	public double getUsage() {
		return usage.get() * operatingSpeed.get();
	}

	public ComponentProcessor requiredTicks(long requiredTicks) {
		this.requiredTicks.set((double) requiredTicks);
		return this;
	}

	public int getProcessorNumber() {
		return processorNumber;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.Processor;
	}

	public ComponentProcessor consumeBucket() {
		FluidUtilities.drainItem(holder, holder.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks());
		return this;
	}

	public ComponentProcessor dispenseBucket() {
		FluidUtilities.fillItem(holder, holder.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getOutputTanks());
		return this;
	}

	public ComponentProcessor outputToPipe() {
		ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
		FluidUtilities.outputToPipe(holder, handler.getOutputTanks(), handler.outputDirections);
		return this;
	}

	public GenericTile getHolder() {
		return holder;
	}

	public ElectrodynamicsRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(ElectrodynamicsRecipe recipe) {
		this.recipe = recipe;
	}

	public void setStoredXp(double val) {
		storedXp = val;
	}

	public double getStoredXp() {
		return storedXp;
	}

	// Instead of checking all at once, we check one at a time; more efficient
	public boolean canProcessItem2ItemRecipe(ComponentProcessor pr, RecipeType<?> typeIn) {
		Item2ItemRecipe locRecipe;
		if (!checkExistingRecipe(pr)) {
			pr.operatingTicks.set(0.0);
			locRecipe = (Item2ItemRecipe) getRecipe(pr, typeIn);
			if (locRecipe == null) {
				return false;
			}
		} else {
			locRecipe = (Item2ItemRecipe) recipe;
		}

		setRecipe(locRecipe);

		requiredTicks.set((double) locRecipe.getTicks());
		usage.set(locRecipe.getUsagePerTick());

		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.maxJoules(usage.get() * operatingSpeed.get() * 10 * totalProcessors);

		if (electro.getJoulesStored() < pr.getUsage()) {
			return false;
		}

		ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
		ItemStack output = inv.getOutputContents().get(processorNumber);
		ItemStack result = recipe.getResultItem();
		boolean isEmpty = output.isEmpty();
		if (!isEmpty && !ItemUtils.testItems(output.getItem(), result.getItem())) {
			return false;
		}

		int locCap = isEmpty ? 64 : output.getMaxStackSize();
		if (locCap < output.getCount() + result.getCount()) {
			return false;
		}

		if (locRecipe.hasItemBiproducts()) {
			boolean itemBiRoom = roomInItemBiSlots(inv.getBiprodsForProcessor(pr.getProcessorNumber()), locRecipe.getFullItemBiStacks());
			if (!itemBiRoom) {
				return false;
			}
		}
		if (locRecipe.hasFluidBiproducts()) {
			ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
			boolean fluidBiRoom = roomInBiproductTanks(handler.getOutputTanks(), locRecipe.getFullFluidBiStacks());
			if (!fluidBiRoom) {
				return false;
			}
		}
		return true;
	}

	public boolean canProcessFluid2ItemRecipe(ComponentProcessor pr, RecipeType<?> typeIn) {
		Fluid2ItemRecipe locRecipe;
		if (!checkExistingRecipe(pr)) {
			pr.operatingTicks.set(0.0);
			locRecipe = (Fluid2ItemRecipe) getRecipe(pr, typeIn);
			if (locRecipe == null) {
				return false;
			}
		} else {
			locRecipe = (Fluid2ItemRecipe) recipe;
		}
		setRecipe(locRecipe);

		requiredTicks.set((double) locRecipe.getTicks());
		usage.set(locRecipe.getUsagePerTick());

		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.maxJoules(usage.get() * operatingSpeed.get() * 10 * totalProcessors);

		if (electro.getJoulesStored() < pr.getUsage()) {
			return false;
		}

		ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
		ItemStack output = inv.getOutputContents().get(processorNumber);
		ItemStack result = recipe.getResultItem();
		boolean isEmpty = output.isEmpty();

		if (!isEmpty && !ItemUtils.testItems(output.getItem(), result.getItem())) {
			return false;
		}

		int locCap = isEmpty ? 64 : output.getMaxStackSize();
		if (locCap < output.getCount() + result.getCount()) {
			return false;
		}
		if (locRecipe.hasItemBiproducts()) {
			boolean itemBiRoom = roomInItemBiSlots(inv.getBiprodsForProcessor(pr.getProcessorNumber()), locRecipe.getFullItemBiStacks());
			if (!itemBiRoom) {
				return false;
			}
		}
		if (locRecipe.hasFluidBiproducts()) {
			ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
			boolean fluidBiRoom = roomInBiproductTanks(handler.getOutputTanks(), locRecipe.getFullFluidBiStacks());
			if (!fluidBiRoom) {
				return false;
			}
		}
		return true;
	}

	public boolean canProcessFluid2FluidRecipe(ComponentProcessor pr, RecipeType<?> typeIn) {
		Fluid2FluidRecipe locRecipe;
		if (!checkExistingRecipe(pr)) {
			pr.operatingTicks.set(0.0);
			locRecipe = (Fluid2FluidRecipe) getRecipe(pr, typeIn);
			if (locRecipe == null) {
				return false;
			}
		} else {
			locRecipe = (Fluid2FluidRecipe) recipe;
		}
		setRecipe(locRecipe);

		requiredTicks.set((double) locRecipe.getTicks());
		usage.set(locRecipe.getUsagePerTick());

		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.maxJoules(usage.get() * operatingSpeed.get() * 10 * totalProcessors);

		if (electro.getJoulesStored() < pr.getUsage()) {
			return false;
		}

		ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
		int amtAccepted = handler.getOutputTanks()[0].fill(locRecipe.getFluidRecipeOutput(), FluidAction.SIMULATE);
		if (amtAccepted < locRecipe.getFluidRecipeOutput().getAmount()) {
			return false;
		}
		if (locRecipe.hasItemBiproducts()) {
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			boolean itemBiRoom = roomInItemBiSlots(inv.getBiprodsForProcessor(pr.getProcessorNumber()), locRecipe.getFullItemBiStacks());
			if (!itemBiRoom) {
				return false;
			}
		}
		if (locRecipe.hasFluidBiproducts()) {
			boolean fluidBiRoom = roomInBiproductTanks(handler.getOutputTanks(), locRecipe.getFullFluidBiStacks());
			if (!fluidBiRoom) {
				return false;
			}
		}
		return true;
	}

	public boolean canProcessItem2FluidRecipe(ComponentProcessor pr, RecipeType<?> typeIn) {
		Item2FluidRecipe locRecipe;
		if (!checkExistingRecipe(pr)) {
			pr.operatingTicks.set(0.0);
			locRecipe = (Item2FluidRecipe) getRecipe(pr, typeIn);
			if (locRecipe == null) {
				return false;
			}
		} else {
			locRecipe = (Item2FluidRecipe) recipe;
		}
		setRecipe(locRecipe);

		requiredTicks.set((double) locRecipe.getTicks());
		usage.set(locRecipe.getUsagePerTick());

		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.maxJoules(usage.get() * operatingSpeed.get() * 10 * totalProcessors);

		if (electro.getJoulesStored() < pr.getUsage()) {
			return false;
		}

		ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
		int amtAccepted = handler.getOutputTanks()[0].fill(locRecipe.getFluidRecipeOutput(), FluidAction.SIMULATE);
		if (amtAccepted < locRecipe.getFluidRecipeOutput().getAmount()) {
			return false;
		}
		if (locRecipe.hasItemBiproducts()) {
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			boolean itemBiRoom = roomInItemBiSlots(inv.getBiprodsForProcessor(pr.getProcessorNumber()), locRecipe.getFullItemBiStacks());
			if (!itemBiRoom) {
				return false;
			}
		}
		if (locRecipe.hasFluidBiproducts()) {
			boolean fluidBiRoom = roomInBiproductTanks(handler.getOutputTanks(), locRecipe.getFullFluidBiStacks());
			if (!fluidBiRoom) {
				return false;
			}
		}
		return true;
	}

	public boolean canProcessFluidItem2FluidRecipe(ComponentProcessor pr, RecipeType<?> typeIn) {
		FluidItem2FluidRecipe locRecipe;
		if (!checkExistingRecipe(pr)) {
			pr.operatingTicks.set(0.0);
			locRecipe = (FluidItem2FluidRecipe) getRecipe(pr, typeIn);
			if (locRecipe == null) {
				return false;
			}
		} else {
			locRecipe = (FluidItem2FluidRecipe) recipe;
		}
		setRecipe(locRecipe);

		requiredTicks.set((double) locRecipe.getTicks());
		usage.set(locRecipe.getUsagePerTick());

		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.maxJoules(usage.get() * operatingSpeed.get() * 10 * totalProcessors);

		if (electro.getJoulesStored() < pr.getUsage()) {
			return false;
		}

		ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
		int amtAccepted = handler.getOutputTanks()[0].fill(locRecipe.getFluidRecipeOutput(), FluidAction.SIMULATE);
		if (amtAccepted < locRecipe.getFluidRecipeOutput().getAmount()) {
			return false;
		}
		if (locRecipe.hasItemBiproducts()) {
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			boolean itemBiRoom = roomInItemBiSlots(inv.getBiprodsForProcessor(pr.getProcessorNumber()), locRecipe.getFullItemBiStacks());
			if (!itemBiRoom) {
				return false;
			}
		}
		if (locRecipe.hasFluidBiproducts()) {
			boolean fluidBiRoom = roomInBiproductTanks(handler.getOutputTanks(), locRecipe.getFullFluidBiStacks());
			if (!fluidBiRoom) {
				return false;
			}
		}
		return true;
	}

	public boolean canProcessFluidItem2ItemRecipe(ComponentProcessor pr, RecipeType<?> typeIn) {
		FluidItem2ItemRecipe locRecipe;
		if (!checkExistingRecipe(pr)) {
			pr.operatingTicks.set(0.0);
			locRecipe = (FluidItem2ItemRecipe) getRecipe(pr, typeIn);
			if (locRecipe == null) {
				return false;
			}
		} else {
			locRecipe = (FluidItem2ItemRecipe) recipe;
		}
		setRecipe(locRecipe);

		requiredTicks.set((double) locRecipe.getTicks());
		usage.set(locRecipe.getUsagePerTick());

		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.maxJoules(usage.get() * operatingSpeed.get() * 10 * totalProcessors);

		if (electro.getJoulesStored() < pr.getUsage()) {
			return false;
		}

		ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
		ItemStack output = inv.getOutputContents().get(processorNumber);
		ItemStack result = recipe.getResultItem();
		boolean isEmpty = output.isEmpty();

		if (!isEmpty && !ItemUtils.testItems(output.getItem(), result.getItem())) {
			return false;
		}

		int locCap = isEmpty ? 64 : output.getMaxStackSize();
		if (locCap < output.getCount() + result.getCount()) {
			return false;
		}
		if (locRecipe.hasItemBiproducts()) {
			boolean itemBiRoom = roomInItemBiSlots(inv.getBiprodsForProcessor(pr.getProcessorNumber()), locRecipe.getFullItemBiStacks());
			if (!itemBiRoom) {
				return false;
			}
		}
		if (locRecipe.hasFluidBiproducts()) {
			ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
			boolean fluidBiRoom = roomInBiproductTanks(handler.getOutputTanks(), locRecipe.getFullFluidBiStacks());
			if (!fluidBiRoom) {
				return false;
			}
		}
		return true;
	}

	/*
	 * CONVENTIONS TO NOTE:
	 * 
	 * Biproducts will be output in the order they appear in the recipe JSON
	 * 
	 * The output FluidTanks will contain both the recipe output tank and the
	 * biproduct tanks The first tank is ALWAYS the main output tank, and the
	 * following tanks will be filled in the order of the fluid biproducts
	 * 
	 * 
	 * 
	 * 
	 * Also, no checks outside of the null recipe check will be performed in these
	 * methods All validity checks will take place in the recipe validator methods
	 * 
	 */

	public void processItem2ItemRecipe(ComponentProcessor pr) {
		if (getRecipe() != null) {
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			Item2ItemRecipe locRecipe = (Item2ItemRecipe) getRecipe();
			int procNumber = pr.getProcessorNumber();
			List<Integer> slotOrientation = locRecipe.getItemArrangment(procNumber);

			if (locRecipe.hasItemBiproducts()) {

				ProbableItem[] itemBi = locRecipe.getItemBiproducts();
				int index = 0;

				for (int slot : inv.getBiprodSlotsForProcessor(procNumber)) {

					ItemStack stack = inv.getItem(slot);
					if (stack.isEmpty()) {
						inv.setItem(slot, itemBi[index].roll().copy());
					} else {
						stack.grow(itemBi[index].roll().getCount());
						inv.setItem(slot, stack);
					}
				}

			}

			if (locRecipe.hasFluidBiproducts()) {
				ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.Inventory);
				ProbableFluid[] fluidBi = locRecipe.getFluidBiproducts();
				FluidTank[] outTanks = handler.getOutputTanks();
				for (int i = 0; i < fluidBi.length; i++) {

					outTanks[i].fill(fluidBi[i].roll(), FluidAction.EXECUTE);
				}
			}

			int outputSlot = inv.getOutputSlots().get(procNumber);

			if (inv.getOutputContents().get(procNumber).isEmpty()) {
				inv.setItem(outputSlot, locRecipe.getResultItem().copy());
			} else {
				ItemStack stack = inv.getOutputContents().get(procNumber);
				stack.grow(locRecipe.getResultItem().getCount());
				inv.setItem(outputSlot, stack);

			}
			List<Integer> inputs = inv.getInputSlotsForProcessor(procNumber);
			for (int i = 0; i < inputs.size(); i++) {
				int index = inputs.get(slotOrientation.get(i));
				ItemStack stack = inv.getItem(index);
				stack.shrink(locRecipe.getCountedIngredients().get(i).getStackSize());
				inv.setItem(index, stack);
			}
			dispenseExperience(inv, locRecipe.getXp());
			setChanged();
		}
	}

	public void processFluidItem2FluidRecipe(ComponentProcessor pr) {
		if (getRecipe() != null) {
			FluidItem2FluidRecipe locRecipe = (FluidItem2FluidRecipe) getRecipe();
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
			List<Integer> slotOrientation = locRecipe.getItemArrangment(pr.getProcessorNumber());
			int procNumber = pr.getProcessorNumber();
			if (locRecipe.hasItemBiproducts()) {

				ProbableItem[] itemBi = locRecipe.getItemBiproducts();
				int index = 0;

				for (int slot : inv.getBiprodSlotsForProcessor(procNumber)) {

					ItemStack stack = inv.getItem(slot);
					if (stack.isEmpty()) {
						inv.setItem(slot, itemBi[index].roll().copy());
					} else {
						stack.grow(itemBi[index].roll().getCount());
						inv.setItem(slot, stack);
					}
				}

			}

			if (locRecipe.hasFluidBiproducts()) {
				ProbableFluid[] fluidBi = locRecipe.getFluidBiproducts();
				FluidTank[] outTanks = handler.getOutputTanks();
				for (int i = 0; i < fluidBi.length; i++) {
					outTanks[i + 1].fill(fluidBi[i].roll(), FluidAction.EXECUTE);
				}
			}

			handler.getOutputTanks()[0].fill(locRecipe.getFluidRecipeOutput(), FluidAction.EXECUTE);

			List<Integer> inputs = inv.getInputSlotsForProcessor(procNumber);
			for (int i = 0; i < inputs.size(); i++) {
				int index = inputs.get(slotOrientation.get(i));
				ItemStack stack = inv.getItem(index);
				stack.shrink(locRecipe.getCountedIngredients().get(i).getStackSize());
				inv.setItem(index, stack);
			}

			FluidTank[] tanks = handler.getInputTanks();
			List<FluidIngredient> fluidIngs = locRecipe.getFluidIngredients();
			List<Integer> tankOrientation = locRecipe.getFluidArrangement();
			for (int i = 0; i < handler.tankCount(true); i++) {
				tanks[tankOrientation.get(i)].drain(fluidIngs.get(i).getFluidStack().getAmount(), FluidAction.EXECUTE);
			}
			dispenseExperience(inv, locRecipe.getXp());
			setChanged();
		}
	}

	public void processFluidItem2ItemRecipe(ComponentProcessor pr) {
		if (getRecipe() != null) {
			FluidItem2ItemRecipe locRecipe = (FluidItem2ItemRecipe) getRecipe();
			int procNumber = pr.getProcessorNumber();
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);
			List<Integer> slotOrientation = locRecipe.getItemArrangment(procNumber);

			if (locRecipe.hasItemBiproducts()) {

				ProbableItem[] itemBi = locRecipe.getItemBiproducts();
				int index = 0;

				for (int slot : inv.getBiprodSlotsForProcessor(procNumber)) {

					ItemStack stack = inv.getItem(slot);
					if (stack.isEmpty()) {
						inv.setItem(slot, itemBi[index].roll().copy());
					} else {
						stack.grow(itemBi[index].roll().getCount());
						inv.setItem(slot, stack);
					}
				}

			}

			if (locRecipe.hasFluidBiproducts()) {
				ProbableFluid[] fluidBi = locRecipe.getFluidBiproducts();
				FluidTank[] outTanks = handler.getOutputTanks();
				for (int i = 0; i < fluidBi.length; i++) {
					outTanks[i].fill(fluidBi[i].roll(), FluidAction.EXECUTE);
				}
			}
			if (inv.getOutputContents().get(procNumber).isEmpty()) {
				inv.setItem(inv.getOutputSlots().get(procNumber), locRecipe.getResultItem().copy());
			} else {
				inv.getOutputContents().get(procNumber).grow(locRecipe.getResultItem().getCount());
			}

			List<Integer> inputs = inv.getInputSlotsForProcessor(procNumber);
			for (int i = 0; i < inputs.size(); i++) {
				int index = inputs.get(slotOrientation.get(i));
				ItemStack stack = inv.getItem(index);
				stack.shrink(locRecipe.getCountedIngredients().get(i).getStackSize());
				inv.setItem(index, stack);
			}

			FluidTank[] tanks = handler.getInputTanks();
			List<FluidIngredient> fluidIngs = locRecipe.getFluidIngredients();
			List<Integer> tankOrientation = locRecipe.getFluidArrangement();
			for (int i = 0; i < handler.tankCount(true); i++) {
				tanks[tankOrientation.get(i)].drain(fluidIngs.get(i).getFluidStack().getAmount(), FluidAction.EXECUTE);
			}
			dispenseExperience(inv, locRecipe.getXp());
			setChanged();
		}
	}

	public void processFluid2ItemRecipe(ComponentProcessor pr) {
		if (getRecipe() != null) {
			Fluid2ItemRecipe locRecipe = (Fluid2ItemRecipe) getRecipe();
			int procNumber = pr.getProcessorNumber();
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);

			if (locRecipe.hasItemBiproducts()) {

				ProbableItem[] itemBi = locRecipe.getItemBiproducts();
				int index = 0;

				for (int slot : inv.getBiprodSlotsForProcessor(procNumber)) {

					ItemStack stack = inv.getItem(slot);
					if (stack.isEmpty()) {
						inv.setItem(slot, itemBi[index].roll().copy());
					} else {
						stack.grow(itemBi[index].roll().getCount());
						inv.setItem(slot, stack);
					}
				}

			}

			if (locRecipe.hasFluidBiproducts()) {
				ProbableFluid[] fluidBi = locRecipe.getFluidBiproducts();
				FluidTank[] outTanks = handler.getOutputTanks();
				for (int i = 0; i < fluidBi.length; i++) {
					outTanks[i].fill(fluidBi[i].roll(), FluidAction.EXECUTE);
				}
			}
			if (inv.getOutputContents().get(procNumber).isEmpty()) {
				inv.setItem(inv.getOutputSlots().get(procNumber), locRecipe.getResultItem().copy());
			} else {
				inv.getOutputContents().get(procNumber).grow(locRecipe.getResultItem().getCount());
			}

			FluidTank[] tanks = handler.getInputTanks();
			List<FluidIngredient> fluidIngs = locRecipe.getFluidIngredients();
			List<Integer> tankOrientation = locRecipe.getFluidArrangement();
			for (int i = 0; i < handler.tankCount(true); i++) {
				tanks[tankOrientation.get(i)].drain(fluidIngs.get(i).getFluidStack().getAmount(), FluidAction.EXECUTE);
			}
			dispenseExperience(inv, locRecipe.getXp());
			setChanged();
		}
	}

	public void processFluid2FluidRecipe(ComponentProcessor pr) {
		if (getRecipe() != null) {
			Fluid2FluidRecipe locRecipe = (Fluid2FluidRecipe) getRecipe();
			ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
			ComponentFluidHandlerMulti handler = holder.getComponent(ComponentType.FluidHandler);

			if (locRecipe.hasItemBiproducts()) {

				ProbableItem[] itemBi = locRecipe.getItemBiproducts();
				int index = 0;

				for (int slot : inv.getBiprodSlotsForProcessor(pr.getProcessorNumber())) {

					ItemStack stack = inv.getItem(slot);
					if (stack.isEmpty()) {
						inv.setItem(slot, itemBi[index].roll().copy());
					} else {
						stack.grow(itemBi[index].roll().getCount());
						inv.setItem(slot, stack);
					}
				}

			}

			if (locRecipe.hasFluidBiproducts()) {
				ProbableFluid[] fluidBi = locRecipe.getFluidBiproducts();
				FluidTank[] outTanks = handler.getOutputTanks();
				for (int i = 0; i < fluidBi.length; i++) {
					outTanks[i + 1].fill(fluidBi[i].roll(), FluidAction.EXECUTE);
				}
			}

			handler.getOutputTanks()[0].fill(locRecipe.getFluidRecipeOutput(), FluidAction.EXECUTE);

			FluidTank[] tanks = handler.getInputTanks();
			List<FluidIngredient> fluidIngs = locRecipe.getFluidIngredients();
			List<Integer> tankOrientation = locRecipe.getFluidArrangement();
			for (int i = 0; i < handler.tankCount(true); i++) {
				tanks[tankOrientation.get(i)].drain(fluidIngs.get(i).getFluidStack().getAmount(), FluidAction.EXECUTE);
			}
			dispenseExperience(inv, locRecipe.getXp());
			setChanged();
		}
	}

	private void dispenseExperience(ComponentInventory inv, double experience) {
		storedXp += experience;
		for (ItemStack stack : inv.getUpgradeContents()) {

			if (!stack.isEmpty()) {
				ItemUpgrade upgrade = (ItemUpgrade) stack.getItem();
				if (upgrade.subtype == SubtypeItemUpgrade.experience) {
					CompoundTag tag = stack.getOrCreateTag();
					tag.putDouble(NBTUtils.XP, tag.getDouble(NBTUtils.XP) + getStoredXp());
					setStoredXp(0);
					break;
				}
			}

		}
	}

	private static boolean roomInItemBiSlots(List<ItemStack> slots, ItemStack[] biproducts) {
		for (int i = 0; i < slots.size(); i++) {
			ItemStack slotStack = slots.get(i);
			ItemStack biStack = biproducts[Math.min(i, biproducts.length - 1)];
			if (!slotStack.isEmpty() && ItemUtils.testItems(slotStack.getItem(), biStack.getItem()) && (slotStack.getCount() + biStack.getCount() > slotStack.getMaxStackSize())) {
				return false;
			}
		}
		return true;
	}

	private static boolean roomInBiproductTanks(FluidTank[] tanks, FluidStack[] stacks) {
		for (int i = 1; i < tanks.length; i++) {
			FluidTank tank = tanks[i];
			FluidStack stack = stacks[Math.min(i, stacks.length - 1)];
			int amtTaken = tank.fill(stack, FluidAction.SIMULATE);
			if (amtTaken < stack.getAmount()) {
				return false;
			}
		}
		return true;
	}

	private boolean checkExistingRecipe(ComponentProcessor pr) {
		if (recipe != null) {
			return recipe.matchesRecipe(pr);
		}
		return false;
	}

	@Nullable
	private ElectrodynamicsRecipe getRecipe(ComponentProcessor pr, RecipeType<?> typeIn) {
		if (cachedRecipes.isEmpty()) {
			cachedRecipes = ElectrodynamicsRecipe.findRecipesbyType((RecipeType<ElectrodynamicsRecipe>) typeIn, pr.getHolder().getLevel());
		}
		return ElectrodynamicsRecipe.getRecipe(pr, cachedRecipes);
	}

	private void setChanged() {
		// hook method; empty for now
	}

	// now it only calculates it when the upgrades in the inventory change
	public void onInventoryChange(ComponentInventory inv, int slot) {
		if (inv.getUpgradeContents().size() > 0 && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1)) {
			operatingSpeed.set(1.0);
			for (ItemStack stack : inv.getUpgradeContents()) {
				if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade && upgrade.subtype.isEmpty) {
					for (int i = 0; i < stack.getCount(); i++) {
						if (upgrade.subtype == SubtypeItemUpgrade.basicspeed) {
							operatingSpeed.set(Math.min(operatingSpeed.get() * 1.5, Math.pow(1.5, 3)));
						} else if (upgrade.subtype == SubtypeItemUpgrade.advancedspeed) {
							operatingSpeed.set(Math.min(operatingSpeed.get() * 2.25, Math.pow(2.25, 3)));
						}
					}
				}
			}

			if (holder.hasComponent(ComponentType.Electrodynamic)) {
				ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
				electro.maxJoules(usage.get() * operatingSpeed.get() * 10);
			}
		}
	}

}

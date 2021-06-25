package electrodynamics.prefab.tile.components.type;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.ItemCanister;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.item.subtype.SubtypeCanister;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ComponentProcessor implements Component {
    private GenericTile holder;

    @Override
    public void holder(GenericTile holder) {
	this.holder = holder;
    }

    public double operatingSpeed;
    public double operatingTicks;
    public double usage;
    public long requiredTicks;
    private Predicate<ComponentProcessor> canProcess = component -> false;
    private Consumer<ComponentProcessor> process;
    private Consumer<ComponentProcessor> failed;
    private ComponentProcessorType processorType;
    private HashSet<Integer> upgradeSlots = new HashSet<>();
    private int inputOne = 0;
    private int inputTwo = 1;
    private int output = 1;

    private ElectrodynamicsRecipe RECIPE;
    private int OUTPUT_CAP;

    public ComponentProcessor(GenericTile source) {
	holder(source);
	if (!holder.hasComponent(ComponentType.Inventory)) {
	    throw new UnsupportedOperationException("You need to implement an inventory component to use the processor component!");
	}
	if (holder.hasComponent(ComponentType.Tickable)) {
	    holder.<ComponentTickable>getComponent(ComponentType.Tickable).tickServer(this::tickServer);
	} else {
	    throw new UnsupportedOperationException("You need to implement a tickable component to use the processor component!");
	}
	if (holder.hasComponent(ComponentType.PacketHandler)) {
	    ComponentPacketHandler handler = holder.getComponent(ComponentType.PacketHandler);
	    handler.guiPacketWriter(this::writeGuiPacket);
	    handler.guiPacketReader(this::readGuiPacket);
	}
    }

    private void tickServer(ComponentTickable tickable) {
	double calculatedOperatingSpeed = 1;
	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	if (holder.hasComponent(ComponentType.PacketHandler) && holder.<ComponentTickable>getComponent(ComponentType.Tickable).getTicks() % 20 == 0) {
	    holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
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
	    electro.maxJoules(usage * operatingSpeed * 10);
	}

	/**
	 * OVERRIDE THIS
	 * 
	 * @param stack
	 * @return
	 */
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
		electro.joules(electro.getJoulesStored() - usage * operatingSpeed);
	    }
	} else if (operatingTicks > 0) {
	    operatingTicks = 0;
	    if (failed != null) {
		failed.accept(this);
	    }
	}

    }

    private void writeGuiPacket(CompoundNBT nbt) {
	int offset = holder.getProcessor(0) == this ? 0 : holder.getProcessor(1) == this ? 1 : holder.getProcessor(2) == this ? 2 : 0;
	nbt.putDouble("operatingTicks" + offset, operatingTicks);
	nbt.putDouble("joulesPerTick" + offset, usage * operatingSpeed);
	nbt.putLong("requiredTicks" + offset, requiredTicks);
    }

    private void readGuiPacket(CompoundNBT nbt) {
	int offset = holder.getProcessor(0) == this ? 0 : holder.getProcessor(1) == this ? 1 : holder.getProcessor(2) == this ? 2 : 0;
	operatingTicks = nbt.getDouble("operatingTicks" + offset);
	usage = nbt.getDouble("joulesPerTick" + offset);
	requiredTicks = nbt.getLong("requiredTicks" + offset);
    }

    public ComponentProcessorType getProcessorType() {
	return processorType;
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

    public ComponentProcessor type(ComponentProcessorType type) {
	processorType = type;
	inputOne = 0;
	inputTwo = 1;
	output = type == ComponentProcessorType.DoubleObjectToObject ? 2 : 1;
	return this;
    }

    public ComponentProcessor upgradeSlots(int... slot) {
	for (int i : slot) {
	    upgradeSlots.add(i);
	}
	return this;
    }

    public ComponentProcessor inputSlot(int inputOne) {
	this.inputOne = inputOne;
	return this;
    }

    public ComponentProcessor secondInputSlot(int inputTwo) {
	this.inputTwo = inputTwo;
	return this;
    }

    public ComponentProcessor outputSlot(int output) {
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

    public ComponentProcessor output(ItemStack stack) {
	holder.<ComponentInventory>getComponent(ComponentType.Inventory).setInventorySlotContents(output, stack);
	return this;
    }

    public ComponentProcessor usage(double usage) {
	this.usage = usage;
	return this;
    }

    public double getUsage() {
	return usage;
    }

    public ComponentProcessor requiredTicks(long requiredTicks) {
	this.requiredTicks = requiredTicks;
	return this;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Processor;
    }

    /**
     * Consumes a bucket in a particular inventory slot if the container is able to
     * accept the fluid.
     * 
     * @param maxCapacity
     * @param fluids
     * @param slot
     * @return
     */
    public ComponentProcessor consumeBucket(int maxCapacity, Fluid[] fluids, int slot) {
	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	ComponentFluidHandler tank = holder.getComponent(ComponentType.FluidHandler);
	ItemStack bucketStack = inv.getStackInSlot(slot);
	Fluid matchingFluid = null;
	boolean isCanister = false;

	if (!bucketStack.isEmpty() && bucketStack.getCount() > 0) {
	    boolean validBucket = false;
	    for (Fluid fluid : fluids) {
			Item bucket = fluid.getFilledBucket();
			if(bucket instanceof ItemCanister) {
				isCanister = true;
			}
			if (bucket != null) {
			    ItemStack inputBucket = new ItemStack(bucket, 1);
			    if (inputBucket.equals(bucketStack, true)) {
				matchingFluid = fluid;
				validBucket = true;
				break;
			    }
		}

	    }
	    if (validBucket && tank.getStackFromFluid(matchingFluid).getAmount() <= maxCapacity - 1000) {
			
	    	if(isCanister) {
	    		inv.setInventorySlotContents(slot, new ItemStack((Item)DeferredRegisters.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypeCanister.empty).get()));
	    	}else {
	    		inv.setInventorySlotContents(slot, new ItemStack(Items.BUCKET));
	    	}
	
			tank.getStackFromFluid(matchingFluid).setAmount(Math.min(tank.getStackFromFluid(matchingFluid).getAmount() + 1000, maxCapacity));
	    }
	}
	return this;
    }

    public GenericTile getHolder() {
	return holder;
    }

    public ElectrodynamicsRecipe getRecipe() {
	return RECIPE;
    }

    public int getOutputCap() {
	return OUTPUT_CAP;
    }

    private void setRecipe(ElectrodynamicsRecipe recipe) {
	RECIPE = recipe;
    }

    private void setOutputCap(int outputCap) {
	OUTPUT_CAP = outputCap;
    }

    /*
     * I could technically get two recipe classes with this method, but I would
     * prefer that each ReicpeType have its own method when practical, as it makes
     * custom logic and what not easier to implement long-term. Maybe once we're in
     * the matainence stage of development, we can change it.
     */
    public <T extends O2ORecipe> boolean canProcessO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, IRecipeType<?> typeIn) {

	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	O2ORecipe recipe = pr.holder.getO2ORecipe(pr, recipeClass, typeIn);
	int outputCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(recipe);
	setOutputCap(outputCap);

	return recipe != null && electro.getJoulesStored() > pr.getUsage()
		&& outputCap >= pr.getOutput().getCount() + recipe.getRecipeOutput().getCount();
    }

    public <T extends DO2ORecipe> boolean canProcessDO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, IRecipeType<?> typeIn) {

	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	DO2ORecipe recipe = pr.holder.getDO2ORecipe(pr, recipeClass, typeIn);
	int outputCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(recipe);
	setOutputCap(outputCap);

	return recipe != null && electro.getJoulesStored() > pr.getUsage()
		&& outputCap >= pr.getOutput().getCount() + recipe.getRecipeOutput().getCount();
    }

    public <T extends FluidItem2FluidRecipe> boolean canProcessFluidItem2FluidRecipe(ComponentProcessor pr, Class<T> recipeClass,
	    IRecipeType<?> typeIn) {

	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	ComponentFluidHandler fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	FluidItem2FluidRecipe recipe = pr.holder.getFluidItem2FluidRecipe(pr, recipeClass, typeIn);

	int outputCap = 0;
	Fluid outputFluid = null;

	setRecipe(recipe);

	if (recipe != null) {
	    outputFluid = recipe.getFluidRecipeOutput().getFluid();
	    outputCap = fluid.getTankFromFluid(outputFluid).getCapacity();
	}
	setOutputCap(outputCap);

	return recipe != null && electro.getJoulesStored() >= pr.getUsage()
		&& outputCap >= fluid.getTankFromFluid(outputFluid).getFluidAmount() + recipe.getFluidRecipeOutput().getAmount();
    }

    public <T extends FluidItem2ItemRecipe> boolean canProcessFluidItem2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass,
	    IRecipeType<?> typeIn) {
	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	FluidItem2ItemRecipe recipe = pr.holder.getFluidItem2ItemRecipe(pr, recipeClass, typeIn);

	int outputCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(recipe);
	setOutputCap(outputCap);

	return recipe != null && electro.getJoulesStored() >= pr.getUsage()
		&& outputCap >= pr.getOutput().getCount() + recipe.getRecipeOutput().getCount();
    }

    /*
     * Same here!
     */
    public <T extends O2ORecipe> void processO2ORecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T recipe = recipeClass.cast(getRecipe());
	    if (getOutputCap() >= pr.getOutput().getCount() + recipe.getRecipeOutput().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(recipe.getRecipeOutput().copy());
		} else {
		    pr.getOutput().setCount(pr.getOutput().getCount() + recipe.getRecipeOutput().getCount());
		}
		pr.getInput().setCount(pr.getInput().getCount() - ((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
	    }
	}
    }

    public <T extends DO2ORecipe> void processDO2ORecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T recipe = recipeClass.cast(getRecipe());
	    if (getOutputCap() >= pr.getOutput().getCount() + recipe.getRecipeOutput().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(recipe.getRecipeOutput().copy());
		} else {
		    pr.getOutput().setCount(pr.getOutput().getCount() + recipe.getRecipeOutput().getCount());
		}
		pr.getInput().setCount(pr.getInput().getCount() - ((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
		pr.getSecondInput().setCount(pr.getSecondInput().getCount() - ((CountableIngredient) recipe.getIngredients().get(1)).getStackSize());
	    }
	}
    }

    public <T extends FluidItem2FluidRecipe> void processFluidItem2FluidRecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T recipe = recipeClass.cast(getRecipe());

	    ComponentFluidHandler fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    FluidStack outputFluid = recipe.getFluidRecipeOutput();
	    FluidStack inputFluid = ((FluidIngredient) recipe.getIngredients().get(1)).getFluidStack();

	    FluidTank outputFluidTank = fluid.getTankFromFluid(outputFluid.getFluid());

	    if (getOutputCap() >= outputFluid.getAmount() + outputFluidTank.getFluidAmount()) {
		pr.getInput().setCount(pr.getInput().getCount() - ((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
		fluid.getStackFromFluid(inputFluid.getFluid()).shrink(inputFluid.getAmount());
		fluid.getStackFromFluid(outputFluid.getFluid()).grow(outputFluid.getAmount());
		pr.holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	}
    }

    public <T extends FluidItem2ItemRecipe> void processFluidItem2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T recipe = recipeClass.cast(getRecipe());
	    ComponentFluidHandler fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    FluidStack inputFluid = ((FluidIngredient) recipe.getIngredients().get(1)).getFluidStack();

	    if (getOutputCap() >= pr.getOutput().getCount() + recipe.getRecipeOutput().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(recipe.getRecipeOutput().copy());
		} else {
		    pr.getOutput().setCount(pr.getOutput().getCount() + recipe.getRecipeOutput().getCount());
		}
		pr.getInput().setCount(pr.getInput().getCount() - ((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
		fluid.getStackFromFluid(inputFluid.getFluid()).shrink(inputFluid.getAmount());
		pr.holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	}
    }

}

package electrodynamics.prefab.tile.components.type;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.do2o.DO2ORecipe;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.categories.fluid3items2item.Fluid3Items2ItemRecipe;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
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
    private int inputThree = 2;
    private int output = 1;

    private ElectrodynamicsRecipe recipe;
    private int outputCap = 0;

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
	    ItemStack stack = inv.getItem(slot);
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

    private void writeGuiPacket(CompoundTag nbt) {
	int offset = holder.getProcessor(0) == this ? 0 : holder.getProcessor(1) == this ? 1 : holder.getProcessor(2) == this ? 2 : 0;
	nbt.putDouble("operatingTicks" + offset, operatingTicks);
	nbt.putDouble("joulesPerTick" + offset, usage * operatingSpeed);
	nbt.putLong("requiredTicks" + offset, requiredTicks);
    }

    private void readGuiPacket(CompoundTag nbt) {
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
	inputThree = 2;
	output = type == ComponentProcessorType.DoubleObjectToObject ? 2 : type == ComponentProcessorType.TripleObjectToObject ? 3 : 1;
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

    public ComponentProcessor thirdInputSlot(int inputThree) {
	this.inputThree = inputThree;
	return this;
    }

    public ComponentProcessor outputSlot(int output) {
	this.output = output;
	return this;
    }

    public ItemStack getInput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getItem(inputOne);
    }

    public int getInputOne() {
	return inputOne;
    }

    public ItemStack getSecondInput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getItem(inputTwo);
    }

    public int getInputTwo() {
	return inputTwo;
    }

    public ItemStack getThirdInput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getItem(inputThree);
    }

    public int getInputThree() {
	return inputThree;
    }

    public ItemStack getOutput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getItem(output);
    }

    public ComponentProcessor output(ItemStack stack) {
	holder.<ComponentInventory>getComponent(ComponentType.Inventory).setItem(output, stack);
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

    public ComponentProcessor consumeBucket(int slot) {
	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	AbstractFluidHandler<?> tank = holder.getComponent(ComponentType.FluidHandler);
	ItemStack bucketStack = inv.getItem(slot);

	if (!bucketStack.isEmpty() && !CapabilityUtils.isFluidItemNull()) {

	    Fluid filledFluid = null;
	    for (Fluid fluid : tank.getValidInputFluids()) {
		if (tank.getTankFromFluid(fluid, true).getFluidAmount() > 0) {
		    filledFluid = fluid;
		    break;
		}
	    }

	    if (filledFluid == null) {
		FluidStack containerFluid = CapabilityUtils.simDrain(bucketStack, Integer.MAX_VALUE);
		if (!containerFluid.getFluid().isSame(Fluids.EMPTY) && tank.getValidInputFluids().contains(containerFluid.getFluid())) {
		    CapabilityUtils.drain(bucketStack, containerFluid);
		    tank.addFluidToTank(containerFluid, true);
		    if (bucketStack.getItem() instanceof BucketItem) {
			inv.setItem(slot, new ItemStack(Items.BUCKET, 1));
		    }
		}
	    } else {
		FluidTank fluidTank = tank.getTankFromFluid(filledFluid, true);
		int room = fluidTank.getCapacity() - fluidTank.getFluidAmount();
		FluidStack amtDrained = CapabilityUtils.simDrain(bucketStack, new FluidStack(fluidTank.getFluid().getFluid(), room));
		if (amtDrained.getAmount() > 0) {
		    CapabilityUtils.drain(bucketStack, amtDrained);
		    tank.addFluidToTank(amtDrained, true);
		    if (bucketStack.getItem() instanceof BucketItem) {
			inv.setItem(slot, new ItemStack(Items.BUCKET, 1));
		    }
		}
	    }
	}
	return this;
    }

    public ComponentProcessor dispenseBucket(int slot) {

	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	AbstractFluidHandler<?> tank = holder.getComponent(ComponentType.FluidHandler);
	ItemStack bucketStack = inv.getItem(slot);
	if (!bucketStack.isEmpty() && !(bucketStack.getItem() instanceof BucketItem) && !CapabilityUtils.isFluidItemNull()) {
	    for (Fluid fluid : tank.getValidOutputFluids()) {
		FluidStack stack = tank.getTankFromFluid(fluid, false).getFluid();
		int amtFilled = CapabilityUtils.simFill(bucketStack, stack);
		if (amtFilled > 0) {
		    CapabilityUtils.fill(bucketStack, new FluidStack(stack.getFluid(), amtFilled));
		    tank.drainFluidFromTank(new FluidStack(stack.getFluid(), amtFilled), false);
		    break;
		}
	    }
	}

	return this;
    }

    public ComponentProcessor outputToPipe(ComponentProcessor pr) {
	ComponentDirection direction = pr.getHolder().getComponent(ComponentType.Direction);
	AbstractFluidHandler<?> tank = pr.getHolder().getComponent(ComponentType.FluidHandler);
	BlockPos face = pr.getHolder().getBlockPos().relative(direction.getDirection().getClockWise().getOpposite());
	BlockEntity faceTile = pr.getHolder().getLevel().getBlockEntity(face);
	if (faceTile != null) {
	    LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		    direction.getDirection().getClockWise().getOpposite().getOpposite());
	    if (cap.isPresent()) {
		IFluidHandler handler = cap.resolve().get();
		for (Fluid fluid : tank.getValidOutputFluids()) {
		    if (tank.getTankFromFluid(fluid, false).getFluidAmount() > 0) {
			tank.getStackFromFluid(fluid, false).shrink(handler.fill(tank.getStackFromFluid(fluid, false), FluidAction.EXECUTE));
			break;
		    }
		}
	    }
	}
	return this;
    }

    public GenericTile getHolder() {
	return holder;
    }

    public ElectrodynamicsRecipe getRecipe() {
	return recipe;
    }

    public int getOutputCap() {
	return outputCap;
    }

    public void setRecipe(ElectrodynamicsRecipe recipe) {
	this.recipe = recipe;
    }

    public void setOutputCap(int outputCap) {
	this.outputCap = outputCap;
    }

    public <T extends O2ORecipe> boolean canProcessO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {

	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	O2ORecipe locRecipe = pr.holder.getO2ORecipe(pr, recipeClass, typeIn);
	int locCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(locRecipe);
	setOutputCap(locCap);

	return locRecipe != null && electro.getJoulesStored() > pr.getUsage()
		&& locCap >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount();
    }

    public <T extends DO2ORecipe> boolean canProcessDO2ORecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {

	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	DO2ORecipe locRecipe = pr.holder.getDO2ORecipe(pr, recipeClass, typeIn);
	int locCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(locRecipe);
	setOutputCap(locCap);

	return locRecipe != null && electro.getJoulesStored() > pr.getUsage()
		&& locCap >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount();
    }

    public <T extends FluidItem2FluidRecipe> boolean canProcessFluidItem2FluidRecipe(ComponentProcessor pr, Class<T> recipeClass,
	    RecipeType<?> typeIn) {

	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	FluidItem2FluidRecipe localRecipe = pr.holder.getFluidItem2FluidRecipe(pr, recipeClass, typeIn);

	int locCap = 0;
	Fluid outputFluid = null;

	setRecipe(localRecipe);

	if (localRecipe != null) {
	    outputFluid = localRecipe.getFluidRecipeOutput().getFluid();
	    locCap = fluid.getTankFromFluid(outputFluid, false).getCapacity();
	}
	setOutputCap(locCap);

	return localRecipe != null && electro.getJoulesStored() >= pr.getUsage()
		&& locCap >= fluid.getTankFromFluid(outputFluid, false).getFluidAmount() + localRecipe.getFluidRecipeOutput().getAmount();

    }

    public <T extends FluidItem2ItemRecipe> boolean canProcessFluidItem2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass,
	    RecipeType<?> typeIn) {
	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	FluidItem2ItemRecipe localRecipe = pr.holder.getFluidItem2ItemRecipe(pr, recipeClass, typeIn);

	int locCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(localRecipe);
	setOutputCap(locCap);

	return localRecipe != null && electro.getJoulesStored() >= pr.getUsage()
		&& locCap >= pr.getOutput().getCount() + localRecipe.getResultItem().getCount();
    }

    public <T extends Fluid3Items2ItemRecipe> boolean canProcessFluid3Items2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass,
	    RecipeType<?> typeIn) {
	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	Fluid3Items2ItemRecipe localRecipe = pr.holder.getFluid3Items2ItemRecipe(pr, recipeClass, typeIn);

	int locCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(localRecipe);
	setOutputCap(locCap);

	return localRecipe != null && electro.getJoulesStored() >= pr.getUsage()
		&& locCap >= pr.getOutput().getCount() + localRecipe.getResultItem().getCount();
    }

    public <T extends Fluid2ItemRecipe> boolean canProcessFluid2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass, RecipeType<?> typeIn) {
	ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	Fluid2ItemRecipe locRecipe = pr.holder.getFluid2ItemRecipe(pr, recipeClass, typeIn);
	boolean matchingOutputs = false;
	int locCap = pr.getOutput().isEmpty() ? 64 : pr.getOutput().getMaxStackSize();

	setRecipe(locRecipe);
	setOutputCap(locCap);

	if (locRecipe != null) {
	    matchingOutputs = pr.getOutput().isEmpty() || ItemStack.isSame(pr.getOutput(), locRecipe.getResultItem());
	}

	return locRecipe != null && electro.getJoulesStored() >= pr.getUsage() && matchingOutputs
		&& locCap >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount();
    }

    public <T extends O2ORecipe> void processO2ORecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T locRecipe = (T) getRecipe();
	    if (getOutputCap() >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(locRecipe.getResultItem().copy());
		} else {
		    pr.getOutput().grow(locRecipe.getResultItem().getCount());
		}
		pr.getInput().shrink(((CountableIngredient) locRecipe.getIngredients().get(0)).getStackSize());
	    }
	}
    }

    public <T extends DO2ORecipe> void processDO2ORecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T locRecipe = (T) getRecipe();
	    if (getOutputCap() >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(locRecipe.getResultItem().copy());
		} else {
		    pr.getOutput().grow(locRecipe.getResultItem().getCount());
		}
		CountableIngredient ing = ((CountableIngredient) locRecipe.getIngredients().get(0));
		if(ing.testStack(pr.getInput())) {
			pr.getInput().shrink(ing.getStackSize());
			pr.getSecondInput().shrink(((CountableIngredient) locRecipe.getIngredients().get(1)).getStackSize());
		} else { 
			pr.getInput().shrink(((CountableIngredient) locRecipe.getIngredients().get(1)).getStackSize());
			pr.getSecondInput().shrink(ing.getStackSize());
		}
	    }
	}
    }

    public <T extends FluidItem2FluidRecipe> void processFluidItem2FluidRecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T locRecipe = (T) getRecipe();

	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    FluidStack outputFluid = locRecipe.getFluidRecipeOutput();
	    FluidStack inputFluid = ((FluidIngredient) locRecipe.getIngredients().get(1)).getFluidStack();

	    FluidTank outputFluidTank = fluid.getTankFromFluid(outputFluid.getFluid(), false);

	    if (getOutputCap() >= outputFluid.getAmount() + outputFluidTank.getFluidAmount()) {
		pr.getInput().shrink(((CountableIngredient) locRecipe.getIngredients().get(0)).getStackSize());
		fluid.getStackFromFluid(inputFluid.getFluid(), true).shrink(inputFluid.getAmount());
		fluid.getStackFromFluid(outputFluid.getFluid(), false).grow(outputFluid.getAmount());
		pr.holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	}
    }

    public <T extends FluidItem2ItemRecipe> void processFluidItem2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T locRecipe = (T) getRecipe();
	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    FluidStack inputFluid = ((FluidIngredient) locRecipe.getIngredients().get(1)).getFluidStack();

	    if (getOutputCap() >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(locRecipe.getResultItem().copy());
		} else {
		    pr.getOutput().grow(locRecipe.getResultItem().getCount());
		}
		pr.getInput().shrink(((CountableIngredient) locRecipe.getIngredients().get(0)).getStackSize());
		fluid.getStackFromFluid(inputFluid.getFluid(), true).shrink(inputFluid.getAmount());
		pr.holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	}
    }

    public <T extends Fluid3Items2ItemRecipe> void processFluid3Items2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (pr.getRecipe() != null) {
	    T locRecipe = (T) pr.getRecipe();
	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    FluidStack inputFluid = ((FluidIngredient) locRecipe.getIngredients().get(3)).getFluidStack();

	    if (pr.getOutputCap() >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(locRecipe.getResultItem().copy());
		} else {
		    pr.getOutput().grow(locRecipe.getResultItem().getCount());
		}
		
		CountableIngredient ing1 = (CountableIngredient) locRecipe.getIngredients().get(0);
		CountableIngredient ing2 = (CountableIngredient) locRecipe.getIngredients().get(1);
		CountableIngredient ing3 = (CountableIngredient) locRecipe.getIngredients().get(2);
		
		//this will not be as nasty when I rework the recipe classes I promist
		//this is a temp soluition that I also have to do for 1.16.5
		
		if(ing1.testStack(getInput())) {
			pr.getInput().shrink(ing1.getStackSize());
			if(ing2.testStack(pr.getSecondInput())) {
				pr.getSecondInput().shrink(ing2.getStackSize());
				pr.getThirdInput().shrink(ing3.getStackSize());
			} else {
				pr.getSecondInput().shrink(ing3.getStackSize());
				pr.getThirdInput().shrink(ing2.getStackSize());
			}
		} else if (ing2.testStack(pr.getInput())){
			pr.getInput().shrink(ing2.getStackSize());
			if(ing2.testStack(pr.getSecondInput())) {
				pr.getSecondInput().shrink(ing1.getStackSize());
				pr.getThirdInput().shrink(ing3.getStackSize());
			} else {
				pr.getSecondInput().shrink(ing3.getStackSize());
				pr.getThirdInput().shrink(ing1.getStackSize());
			}
		} else {
			pr.getInput().shrink(ing3.getStackSize());
			if(ing1.testStack(pr.getSecondInput())) {
				pr.getSecondInput().shrink(ing1.getStackSize());
				pr.getThirdInput().shrink(ing2.getStackSize());
			} else {
				pr.getSecondInput().shrink(ing2.getStackSize());
				pr.getThirdInput().shrink(ing1.getStackSize());
			}
		}
		
		fluid.getStackFromFluid(inputFluid.getFluid(), true).shrink(inputFluid.getAmount());
		pr.holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	}
    }

    public <T extends Fluid2ItemRecipe> void processFluid2ItemRecipe(ComponentProcessor pr, Class<T> recipeClass) {
	if (getRecipe() != null) {
	    T locRecipe = (T) getRecipe();
	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    FluidStack inputFluid = ((FluidIngredient) locRecipe.getIngredients().get(0)).getFluidStack();
	    if (getOutputCap() >= pr.getOutput().getCount() + locRecipe.getResultItem().getCount()) {
		if (pr.getOutput().isEmpty()) {
		    pr.output(locRecipe.getResultItem().copy());
		} else {
		    pr.getOutput().grow(locRecipe.getResultItem().getCount());
		}
		fluid.getStackFromFluid(inputFluid.getFluid(), true).shrink(inputFluid.getAmount());
		pr.holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	}
    }

}

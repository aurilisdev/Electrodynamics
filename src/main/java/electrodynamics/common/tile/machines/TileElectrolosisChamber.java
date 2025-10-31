package electrodynamics.common.tile.machines;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectrolosisChamber;
import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolosisChamberRecipe;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import voltaic.api.IWrenchItem;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.api.multiblock.assemblybased.Multiblock;
import voltaic.api.multiblock.assemblybased.TileMultiblockController;
import voltaic.api.multiblock.assemblybased.TileMultiblockSlave;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.common.recipe.VoltaicRecipe;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.CapabilityInputType;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.components.utils.IComponentFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileElectrolosisChamber extends TileMultiblockController {

    public static final ResourceLocation ID = Electrodynamics.rl("electrolosischamber");
    public static final ResourceKey<Multiblock> RESOURCE_KEY = Multiblock.makeKey(ID);

    public static final int MAX_INPUT_TANK_CAPACITY = 5000;
    public static final int MAX_OUTPUT_TANK_CAPACITY = 5000;

    public final SingleProperty<Integer> processAmount = property(
	    new SingleProperty<>(PropertyTypes.INTEGER, "processamount", 0));
    public final SingleProperty<Double> operatingTicks = property(
	    new SingleProperty<>(PropertyTypes.DOUBLE, "operatingticks", 0.0));
    public final SingleProperty<Double> neededTicks = property(
	    new SingleProperty<>(PropertyTypes.DOUBLE, "neededticks", 0.0));
    public final SingleProperty<Boolean> isActive = property(
	    new SingleProperty<>(PropertyTypes.BOOLEAN, "isactive", false));

    private @Nullable ElectrolosisChamberRecipe currRecipe = null;

    public TileElectrolosisChamber(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_ELECTROLOSISCHAMBER.get(), worldPos, blockState);

	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BACK)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 16)
		.maxJoules(ElectrodynamicsConfig.INSTANCE.ELECTROLOSIS_CHAMBER_TARGET_JOULES.get() * 20 * 100));
	addComponent(new ComponentFluidHandlerMulti(this).setInputDirections(BlockEntityUtils.MachineDirection.RIGHT)
		.setInputTanks(1, arr(MAX_INPUT_TANK_CAPACITY))
		.setOutputDirections(BlockEntityUtils.MachineDirection.LEFT).setOutputTanks(1, MAX_OUTPUT_TANK_CAPACITY)
		.setRecipeType(ElectrodynamicsRecipies.ELECTROLOSIS_CHAMBER_TYPE.get()));
	addComponent(new ComponentContainerProvider(SubtypeMachine.electrolosischamber.tag(), this)
		.createMenu((id, player) -> new ContainerElectrolosisChamber(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().bucketInputs(1).bucketOutputs(1))
		.valid(machineValidator()));

    }

    @Override
    public void tickServer(ComponentTickable tickable) {
	super.tickServer(tickable);

	ComponentFluidHandlerMulti fluidHandler = getComponent(IComponentType.FluidHandler);
	ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

	FluidUtilities.drainItem(this, fluidHandler.getInputTanks());
	FluidUtilities.fillItem(this, fluidHandler.getOutputTanks());

	outputToPipe();

	if (currRecipe == null) {
	    for (RecipeHolder<ElectrolosisChamberRecipe> recipe : getLevel().getRecipeManager()
		    .getAllRecipesFor(ElectrodynamicsRecipies.ELECTROLOSIS_CHAMBER_TYPE.get())) {
		if (testRecipe(recipe.value(), fluidHandler.getInputTanks())) {
		    currRecipe = recipe.value();
		    break;
		}
	    }
	} else if (!testRecipe(currRecipe, fluidHandler.getInputTanks())) {
	    currRecipe = null;
	}

	if (currRecipe == null || electro.getJoulesStored() <= 0 || (!fluidHandler.getOutputTanks()[0].isEmpty()
		&& !fluidHandler.getOutputTanks()[0].getFluid().is(currRecipe.getFluidRecipeOutput().getFluid()))) {
	    operatingTicks.setValue(0.0);
	    isActive.setValue(false);
	    processAmount.setValue(0);
	    neededTicks.setValue(0.0);
	    return;
	}

	double energySatisfaction = electro.getJoulesStored()
		/ ElectrodynamicsConfig.INSTANCE.ELECTROLOSIS_CHAMBER_TARGET_JOULES.get();

	if (energySatisfaction < 1) {
	    neededTicks.setValue(1.0 / energySatisfaction);
	    processAmount.setValue(1);
	} else {
	    neededTicks.setValue(0.0);
	    operatingTicks.setValue(0.0);
	    processAmount.setValue((int) energySatisfaction);
	}

	int room = fluidHandler.getOutputTanks()[0].getCapacity() - fluidHandler.getOutputTanks()[0].getFluidAmount();

	if (room <= 0) {
	    isActive.setValue(false);
	    return;
	}

	int amtToProcess = Math.min(room, processAmount.getValue());

	electro.setJoulesStored(0);

	isActive.setValue(true);

	if (neededTicks.getValue() > 0 && operatingTicks.getValue() < neededTicks.getValue()) {
	    operatingTicks.setValue(operatingTicks.getValue() + 1.0);
	    return;
	}

	operatingTicks.setValue(0.0);

	fluidHandler.getInputTanks()[0].drain(amtToProcess, IFluidHandler.FluidAction.EXECUTE);
	fluidHandler.getOutputTanks()[0].fill(
		new FluidStack(currRecipe.getFluidRecipeOutput().getFluidHolder(), amtToProcess),
		IFluidHandler.FluidAction.EXECUTE);

    }

    private static boolean testRecipe(ElectrolosisChamberRecipe recipe, FluidTank[] inputTanks) {
	Pair<List<Integer>, Boolean> pair = VoltaicRecipe.areFluidsValid(recipe.getFluidIngredients(), inputTanks);
	if (pair.getSecond()) {
	    recipe.setFluidArrangement(pair.getFirst());
	    return true;
	}
	return false;
    }

    private void outputToPipe() {

	ComponentFluidHandlerMulti component = getComponent(IComponentType.FluidHandler);
	Direction[] outputDirections = component.outputDirections;

	Direction facing = getFacing();

	for (Direction relative : outputDirections) {

	    Direction direction = BlockEntityUtils.getRelativeSide(facing, relative);

	    BlockEntity faceTile = getLevel().getBlockEntity(getBlockPos().relative(direction).offset(2, 0, 2));

	    if (faceTile == null) {
		continue;
	    }

	    IFluidHandler handler = getLevel().getCapability(Capabilities.FluidHandler.BLOCK, faceTile.getBlockPos(),
		    faceTile.getBlockState(), faceTile, direction.getOpposite());

	    if (handler == null) {
		continue;
	    }

	    for (FluidTank fluidTank : component.getOutputTanks()) {

		FluidStack tankFluid = fluidTank.getFluid();

		int amtAccepted = handler.fill(tankFluid, IFluidHandler.FluidAction.EXECUTE);

		FluidStack taken = new FluidStack(tankFluid.getFluid(), amtAccepted);

		fluidTank.drain(taken, IFluidHandler.FluidAction.EXECUTE);
	    }
	}
    }

    @Override
    public @Nullable IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
	return null;
    }

    @Nullable
    @Override
    public IFluidHandler getSlaveFluidHandlerCapability(TileMultiblockSlave slave, @Nullable Direction side) {
	if (slave.index.getValue() != 35 && slave.index.getValue() != 39) {
	    return null;
	}
	return this.<IComponentFluidHandler>getComponent(IComponentType.FluidHandler).getCapability(side,
		CapabilityInputType.NONE);
    }

    @Override
    public @Nullable ICapabilityElectrodynamic getElectrodynamicCapability(@Nullable Direction side) {
	return null;
    }

    @Nullable
    @Override
    public ICapabilityElectrodynamic getSlaveCapabilityElectrodynamic(TileMultiblockSlave slave,
	    @Nullable Direction side) {
	if (slave.index.getValue() != 7) {
	    return null;
	}
	return this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getCapability(side,
		CapabilityInputType.NONE);
    }

    @Override
    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
	return null;
    }

    @Override
    public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
	if (!level.isClientSide() && hit.getBlockPos().equals(getBlockPos()) && used.getItem() instanceof IWrenchItem) {
	    checkFormed();
	    if (isFormed.getValue()) {
		formMultiblock();
	    } else {
		destroyMultiblock();
	    }
	    return ItemInteractionResult.CONSUME;

	}
	return super.useWithItem(used, player, hand, hit);
    }

    @Override
    public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
	if (!isFormed.getValue()) {
	    return InteractionResult.FAIL;
	}
	return super.useWithoutItem(player, hit);
    }

    @Override
    public ResourceLocation getMultiblockId() {
	return ID;
    }

    @Override
    public ResourceKey<Multiblock> getResourceKey() {
	return RESOURCE_KEY;
    }
}

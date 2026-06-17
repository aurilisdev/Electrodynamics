package electrodynamics.common.tile.machines.arcfurnace;

import java.util.List;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricArcFurnace;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.Voltaic;
import voltaic.client.particle.lavawithphysics.ParticleOptionLavaWithPhysics;
import voltaic.common.item.ItemUpgrade;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicDataComponentTypes;

public class TileElectricArcFurnace extends GenericTile implements ITickableSound {

    protected BlastingRecipe[] cachedRecipe = null;

    private List<RecipeHolder<BlastingRecipe>> cachedRecipes = null;

    private boolean isSoundPlaying = false;

    private final int procCount;

    public TileElectricArcFurnace(BlockPos worldPosition, BlockState blockState) {
	this(ElectrodynamicsTiles.TILE_ELECTRICARCFURNACE.get(), 1, worldPosition, blockState);
	addComponent(new ComponentContainerProvider(SubtypeMachine.electricarcfurnace.tag(), this)
		.createMenu((id, player) -> new ContainerElectricArcFurnace(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    public TileElectricArcFurnace(BlockEntityType<?> type, int procCount, BlockPos worldPosition,
	    BlockState blockState) {
	super(type, worldPosition, blockState);

	this.procCount = procCount;

	int inputsPerProc = 1;
	int outputPerProc = 1;

	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentTickable(this).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BACK)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * Math.pow(2, procCount - 1))
		.maxJoules(ElectrodynamicsConfig.INSTANCE.ELECTRICARCFURNACE_USAGE_PER_TICK.get() * 20 * procCount));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().processors(procCount, inputsPerProc, outputPerProc, 0)
			.upgrades(3))
		.validUpgrades(ContainerElectricArcFurnace.VALID_UPGRADES).valid(machineValidator())
		.implementMachineInputsAndOutputs());
	addComponent(new ComponentProcessor(this, procCount).canProcess(this::canProcess).process(this::process));

	cachedRecipe = new BlastingRecipe[procCount];
    }

    protected boolean canProcess(ComponentProcessor component, int procNumber) {
	boolean canProcess = checkConditions(component, procNumber);

	if (BlockEntityUtils.isLit(this) ^ (canProcess || component.isAnyActive()) || component.isActive(procNumber)) {
	    BlockEntityUtils.updateLit(this, canProcess || component.isActive(procNumber));
	}

	return canProcess;
    }

    private boolean checkConditions(ComponentProcessor component, int procNumber) {
	component.setShouldKeepProgress(true, procNumber);
	ComponentInventory inv = getComponent(IComponentType.Inventory);
	ItemStack input = inv.getInputsForProcessor(procNumber).get(0);
	if (input.isEmpty()) {
	    component.setShouldKeepProgress(false, procNumber);
	    component.operatingTicks.setValue(0.0, procNumber);
	    component.usage(0.0, procNumber);
	    return false;
	}

	cachedRecipes = level.getRecipeManager().getAllRecipesFor(RecipeType.BLASTING);
	if (cachedRecipes == null) {
	}

	if (cachedRecipe == null) {
	    component.setShouldKeepProgress(false, procNumber);
	    component.operatingTicks.setValue(0.0, procNumber);
	    component.usage(0.0, procNumber);
	    return false;
	}

	if (cachedRecipe[procNumber] == null) {
	    cachedRecipe[procNumber] = getMatchedRecipe(input);
	    if (cachedRecipe[procNumber] == null) {
		component.setShouldKeepProgress(false, procNumber);
		component.operatingTicks.setValue(0.0, procNumber);
		component.usage(0.0, procNumber);
		return false;
	    }
	}

	if (!cachedRecipe[procNumber].matches(new SingleRecipeInput(input), level)) {
	    cachedRecipe[procNumber] = null;
	    component.setShouldKeepProgress(false, procNumber);
	    component.operatingTicks.setValue(0.0, procNumber);
	    component.usage(0.0, procNumber);
	    return false;
	}

	component.usage.setValue(ElectrodynamicsConfig.INSTANCE.ELECTRICARCFURNACE_USAGE_PER_TICK.get(), procNumber);
	component.requiredTicks
		.setValue((double) ElectrodynamicsConfig.INSTANCE.ELECTRICARCFURNACE_REQUIRED_TICKS.get(), procNumber);

	ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
	if (electro.getJoulesStored() < component.getUsage(procNumber) * component.operatingSpeed.getValue()) {
	    return false;
	}

	ItemStack output = inv.getOutputContents().get(procNumber);
	ItemStack result = cachedRecipe[procNumber].getResultItem(level.registryAccess());
	return (output.isEmpty() || output.getItem() == result.getItem())
		&& output.getCount() + result.getCount() <= output.getMaxStackSize();

    }

    protected void process(ComponentProcessor component, int procNumber) {
	ComponentInventory inv = getComponent(IComponentType.Inventory);
	ItemStack input = inv.getInputsForProcessor(procNumber).get(0);
	ItemStack output = inv.getOutputsForProcessor(procNumber).get(0);
	ItemStack result = cachedRecipe[procNumber].getResultItem(level.registryAccess());
	int index = inv.getOutputSlots().get(procNumber);
	if (!output.isEmpty()) {
	    output.setCount(output.getCount() + result.getCount());
	    inv.setItem(index, output);
	} else {
	    inv.setItem(index, result.copy());
	}
	input.shrink(1);
	inv.setItem(inv.getInputSlotsForProcessor(procNumber).get(0), input.copy());
	for (ItemStack stack : inv.getUpgradeContents()) {
	    if (!stack.isEmpty() && ((ItemUpgrade) stack.getItem()).subtype == SubtypeItemUpgrade.experience) {
		stack.set(VoltaicDataComponentTypes.XP, stack.getOrDefault(VoltaicDataComponentTypes.XP, 0.0)
			+ cachedRecipe[procNumber].getExperience());
		break;
	    }
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	if (!this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive()) {
	    return;
	}

	double threshhold = 0.5;

	if (procCount == 2) {
	    threshhold = 0.75;
	} else if (procCount == 3) {
	    threshhold = 0.9;
	}

	if (level.random.nextDouble() < threshhold) {

	    Direction direction = getFacing();

	    double axisShift = 0.5;

	    if (procCount == 2) {
		axisShift = Voltaic.RANDOM.nextDouble(0.5) + 0.25;
	    } else if (procCount == 3) {
		axisShift = Voltaic.RANDOM.nextDouble(0.6) + 0.22;
	    }

	    double yShift = 0.6;

	    double xShift = direction.getAxis() == Direction.Axis.X
		    ? direction.getStepX() * (direction.getStepX() == -0.5 ? 0 : 0.5)
		    : axisShift;
	    double zShift = direction.getAxis() == Direction.Axis.Z
		    ? direction.getStepZ() * (direction.getStepZ() == -0.5 ? 0 : 0.5)
		    : axisShift;

	    double xVel = (Math.random() * 2.0 - 1.0) * 0.4F;
	    double yVel = Math.random() * 0.4F;
	    double zVel = (Math.random() * 2.0 - 1.0) * 0.4F;
	    double rand = (Math.random() + Math.random() + 1.0) * 0.15F;
	    double vectorMag = Math.sqrt(xVel * xVel + yVel * yVel + zVel * zVel);
	    xVel = xVel / vectorMag * rand * 0.4F;
	    yVel = Math.max(0.05, yVel / vectorMag * rand * 0.4F + 0.1F);
	    zVel = zVel / vectorMag * rand * 0.4F;

	    level.addParticle(new ParticleOptionLavaWithPhysics().setParameters(0.05F, 1, 1),
		    worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, xVel,
		    yVel, zVel);
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + xShift, worldPosition.getY() + yShift,
		    worldPosition.getZ(), 0, 0, 0);

	}
	if (!isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
	}
    }

    @Override
    public void setNotPlaying() {
	isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive();
    }

    private BlastingRecipe getMatchedRecipe(ItemStack stack) {
	for (RecipeHolder<BlastingRecipe> recipe : cachedRecipes) {
	    if (recipe.value().matches(new SingleRecipeInput(stack), level)) {
		return recipe.value();
	    }
	}
	return null;
    }

    @Override
    public int getComparatorSignal() {
	return (int) ((double) this.<ComponentProcessor>getComponent(IComponentType.Processor).getTotalActive()
		/ (double) Math.max(1,
			this.<ComponentProcessor>getComponent(IComponentType.Processor).getProcessorCount())
		* 15.0);
    }

}

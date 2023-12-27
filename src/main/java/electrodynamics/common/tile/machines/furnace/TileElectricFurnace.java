package electrodynamics.common.tile.machines.furnace;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceTriple;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.*;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;

import java.util.List;

public class TileElectricFurnace extends GenericTile implements ITickableSound {

	protected FurnaceRecipe[] cachedRecipe = null;

	private List<FurnaceRecipe> cachedRecipes = null;

	private boolean isSoundPlaying = false;

	public TileElectricFurnace() {
		this(SubtypeMachine.electricfurnace, 0);
	}

	public TileElectricFurnace(SubtypeMachine machine, int extra) {
		super(extra == 1 ? ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACEDOUBLE.get() : extra == 2 ? ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACETRIPLE.get() : ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACE.get());

		int processorCount = extra + 1;
		int inputsPerProc = 1;
		int outputPerProc = 1;

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * Math.pow(2, extra)).maxJoules(Constants.ELECTRICFURNACE_USAGE_PER_TICK * 20 * (extra + 1)));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(processorCount, inputsPerProc, outputPerProc, 0).upgrades(3)).validUpgrades(ContainerElectricFurnace.VALID_UPGRADES).valid(machineValidator()).implementMachineInputsAndOutputs());
		addComponent(new ComponentContainerProvider(machine, this).createMenu(
				(id, player) -> (extra == 0 ? new ContainerElectricFurnace(id, player, getComponent(IComponentType.Inventory), getCoordsArray()) : extra == 1 ? new ContainerElectricFurnaceDouble(id, player, getComponent(IComponentType.Inventory), getCoordsArray()) : extra == 2 ? new ContainerElectricFurnaceTriple(id, player, getComponent(IComponentType.Inventory), getCoordsArray()) : null)));

		for (int i = 0; i <= extra; i++) {
			addProcessor(new ComponentProcessor(this, i, extra + 1).canProcess(this::canProcess).process(this::process).requiredTicks(Constants.ELECTRICFURNACE_REQUIRED_TICKS).usage(Constants.ELECTRICFURNACE_USAGE_PER_TICK));
		}
		cachedRecipe = new FurnaceRecipe[extra + 1];
	}

	protected void process(ComponentProcessor component) {
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack input = inv.getInputsForProcessor(component.getProcessorNumber()).get(0);
		ItemStack output = inv.getOutputsForProcessor(component.getProcessorNumber()).get(0);
		ItemStack result = cachedRecipe[component.getProcessorNumber()].getResultItem();
		int index = inv.getOutputSlots().get(component.getProcessorNumber());
		if (!output.isEmpty()) {
			output.setCount(output.getCount() + result.getCount());
			inv.setItem(index, output);
		} else {
			inv.setItem(index, result.copy());
		}
		input.shrink(1);
		inv.setItem(inv.getInputSlotsForProcessor(component.getProcessorNumber()).get(0), input.copy());
		for (ItemStack stack : inv.getUpgradeContents()) {
			if (!stack.isEmpty() && ((ItemUpgrade) stack.getItem()).subtype == SubtypeItemUpgrade.experience) {
				CompoundNBT tag = stack.getOrCreateTag();
				tag.putDouble(NBTUtils.XP, tag.getDouble(NBTUtils.XP) + cachedRecipe[component.getProcessorNumber()].getExperience());
				break;
			}
		}
	}

	protected boolean canProcess(ComponentProcessor component) {
		boolean canProcess = checkConditions(component);

		if (BlockEntityUtils.isLit(this) ^ canProcess || isProcessorActive()) {
			BlockEntityUtils.updateLit(this, canProcess || isProcessorActive());
		}

		if (getBlockState().is(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacerunning))) {

			level.setBlockAndUpdate(worldPosition, ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnace).defaultBlockState().setValue(GenericEntityBlock.FACING, getFacing()));

		} else if (getBlockState().is(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedoublerunning))) {

			level.setBlockAndUpdate(worldPosition, ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedouble).defaultBlockState().setValue(GenericEntityBlock.FACING, getFacing()));

		} else if (getBlockState().is(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriplerunning))) {

			level.setBlockAndUpdate(worldPosition, ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriple).defaultBlockState().setValue(GenericEntityBlock.FACING, getFacing()));

		}

		return canProcess;
	}

	private boolean checkConditions(ComponentProcessor component) {
		component.setShouldKeepProgress(true);
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack input = inv.getInputsForProcessor(component.getProcessorNumber()).get(0);
		if (input.isEmpty()) {
			component.setShouldKeepProgress(false);
			return false;
		}

		if (cachedRecipes == null) {
			cachedRecipes = level.getRecipeManager().getAllRecipesFor(IRecipeType.SMELTING);
		}

		if (cachedRecipe == null) {
			component.setShouldKeepProgress(false);
			return false;
		}

		if (cachedRecipe[component.getProcessorNumber()] == null) {
			cachedRecipe[component.getProcessorNumber()] = getMatchedRecipe(input);
			if (cachedRecipe[component.getProcessorNumber()] == null) {
				component.setShouldKeepProgress(false);
				return false;
			}
			component.operatingTicks.set(0.0);
		}

		if (!cachedRecipe[component.getProcessorNumber()].matches(new Inventory(input), level)) {
			cachedRecipe[component.getProcessorNumber()] = null;
			component.setShouldKeepProgress(false);
			return false;
		}

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		if (electro.getJoulesStored() < component.getUsage() * component.operatingSpeed.get()) {
			return false;
		}
		electro.maxJoules(component.getUsage() * component.operatingSpeed.get() * 10 * component.totalProcessors);

		ItemStack output = inv.getOutputContents().get(component.getProcessorNumber());
		ItemStack result = cachedRecipe[component.getProcessorNumber()].getResultItem();
		return (output.isEmpty() || output.getItem() == result.getItem()) && output.getCount() + result.getCount() <= output.getMaxStackSize();

	}

	protected void tickClient(ComponentTickable tickable) {
		if (!isProcessorActive()) {
			return;
		}
		if (level.random.nextDouble() < 0.15) {
			Direction direction = getFacing();
			double d4 = level.random.nextDouble();
			double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : d4;
			double d6 = level.random.nextDouble();
			double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : d4;
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
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
		return isProcessorActive();
	}

	private FurnaceRecipe getMatchedRecipe(ItemStack stack) {
		for (FurnaceRecipe recipe : cachedRecipes) {
			if (recipe.matches(new Inventory(stack), level)) {
				return recipe;
			}
		}
		return null;
	}

	@Override
	public int getComparatorSignal() {
		return (int) (((double) getNumActiveProcessors() / (double) Math.max(1, getNumProcessors())) * 15.0);
	}

}
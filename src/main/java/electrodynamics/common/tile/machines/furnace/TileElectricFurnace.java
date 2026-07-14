package electrodynamics.common.tile.machines.furnace;

import java.util.List;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnace;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import voltaic.Voltaic;
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
import voltaic.prefab.utilities.NBTUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileElectricFurnace extends GenericTile implements ITickableSound {

	protected FurnaceRecipe[] cachedRecipe = null;

	private List<FurnaceRecipe> cachedRecipes = null;

	private boolean isSoundPlaying = false;

	private final int procCount;

	public TileElectricFurnace() {
		this(ElectrodynamicsTiles.TILE_ELECTRICFURNACE.get(), 1);

		addComponent(new ComponentContainerProvider(SubtypeMachine.electricfurnace.tag(), this).createMenu((id, player) -> new ContainerElectricFurnace(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}

	public TileElectricFurnace(TileEntityType<?> type, int procCount) {
		super(type);

		this.procCount = procCount;

		int inputsPerProc = 1;
		int outputPerProc = 1;

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BACK).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * Math.pow(2, procCount - 1)).maxJoules(ElectroConstants.ELECTRICFURNACE_USAGE_PER_TICK * 20 * procCount));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(procCount, inputsPerProc, outputPerProc, 0).upgrades(3)).validUpgrades(ContainerElectricFurnace.VALID_UPGRADES).valid(machineValidator()).implementMachineInputsAndOutputs());
		addComponent(new ComponentProcessor(this, procCount).canProcess(this::canProcess).process(this::process));

		cachedRecipe = new FurnaceRecipe[procCount];
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

		cachedRecipes = level.getRecipeManager().getAllRecipesFor(IRecipeType.SMELTING);
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

		if (!cachedRecipe[procNumber].matches(new Inventory(input), level)) {
			cachedRecipe[procNumber] = null;
			component.setShouldKeepProgress(false, procNumber);
			component.operatingTicks.setValue(0.0, procNumber);
			component.usage(0.0, procNumber);
			return false;
		}
		
		component.usage.setValue(ElectroConstants.ELECTRICFURNACE_USAGE_PER_TICK, procNumber);
		component.requiredTicks.setValue((double) ElectroConstants.ELECTRICFURNACE_REQUIRED_TICKS, procNumber);

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		if (electro.getJoulesStored() < component.getUsage(procNumber) * component.operatingSpeed.getValue()) {
			return false;
		}

		ItemStack output = inv.getOutputContents().get(procNumber);
		ItemStack result = cachedRecipe[procNumber].getResultItem();
		return (output.isEmpty() || output.getItem() == result.getItem()) && output.getCount() + result.getCount() <= output.getMaxStackSize();

	}

	protected void process(ComponentProcessor component, int procNumber) {
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack input = inv.getInputsForProcessor(procNumber).get(0);
		ItemStack output = inv.getOutputsForProcessor(procNumber).get(0);
		ItemStack result = cachedRecipe[procNumber].getResultItem();
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
				CompoundNBT tag = stack.getOrCreateTag();
				tag.putDouble(NBTUtils.XP, tag.getDouble(NBTUtils.XP) + cachedRecipe[procNumber].getExperience());
				break;
			}
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive()) {
			return;
		}

		double threshhold = 0.15;


		if(procCount == 2){
			threshhold = 0.20;
		} else if (procCount == 3){
			threshhold = 0.30;
		}

		double random = level.random.nextDouble();

		if (random < threshhold) {

			Direction direction = getFacing();
			double axisShift = 0;
			double yShift = 0;

			switch(procCount){

				case 1:

					axisShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.64) + 0.18;
					yShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.57) + 0.25;

					double xShift = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : axisShift;
					double zShift = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : axisShift;

					level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);
					level.addParticle(ParticleTypes.FLAME, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);

					break;
				case 2:

					int randInt = level.random.nextInt(4);

					axisShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.64) + 0.18;
					yShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.38) + 0.37;

					xShift = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : axisShift;
					zShift = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : axisShift;

					level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);
					level.addParticle(ParticleTypes.FLAME, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);

					if(randInt == 1) {
						direction = direction.getClockWise();
					} else if (randInt == 2) {
						direction = direction.getCounterClockWise();
					}

					axisShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.64) + 0.18;
					yShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.38) + 0.37;

					xShift = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : axisShift;
					zShift = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : axisShift;

					level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);
					//level.addParticle(ParticleTypes.FLAME, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);

					break;
				case 3:

					randInt = level.random.nextInt(4);

					axisShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.64) + 0.18;
					yShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.38) + 0.37;

					xShift = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : axisShift;
					zShift = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : axisShift;

					level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);
					level.addParticle(ParticleTypes.FLAME, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);

					if(randInt == 1) {
						direction = direction.getClockWise();
					} else if (randInt == 2) {
						direction = direction.getCounterClockWise();
					}

					axisShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.64) + 0.18;
					yShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.38) + 0.37;

					xShift = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : axisShift;
					zShift = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : axisShift;

					level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);


					break;
				default:
					break;


			}


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
		return (int) ((double) this.<ComponentProcessor>getComponent(IComponentType.Processor).getTotalActive() / (double) Math.max(1, this.<ComponentProcessor>getComponent(IComponentType.Processor).getProcessorCount()) * 15.0);
	}

}

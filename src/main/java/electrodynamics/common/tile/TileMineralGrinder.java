package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorTriple;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSoundTile;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.InventoryUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralGrinder extends GenericTile implements ITickableSoundTile {
	public long clientRunningTicks = 0;
	
	private boolean isSoundPlaying = false;

	public TileMineralGrinder(BlockPos pos, BlockState state) {
		this(SubtypeMachine.mineralgrinder, 0, pos, state);
	}

	public TileMineralGrinder(SubtypeMachine machine, int extra, BlockPos pos, BlockState state) {
		super(extra == 1 ? ElectrodynamicsBlockTypes.TILE_MINERALGRINDERDOUBLE.get() : extra == 2 ? ElectrodynamicsBlockTypes.TILE_MINERALGRINDERTRIPLE.get() : ElectrodynamicsBlockTypes.TILE_MINERALGRINDER.get(), pos, state);

		int processorInputs = 1;
		int processorCount = extra + 1;
		int inputCount = processorInputs * (extra + 1);
		int outputCount = 1 * (extra + 1);
		int biproducts = 1 + extra;
		int invSize = 3 + inputCount + outputCount + biproducts;

		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * Math.pow(2, extra)).maxJoules(Constants.MINERALGRINDER_USAGE_PER_TICK * 20 * (extra + 1)));

		int[] ints = new int[extra + 1];
		for (int i = 0; i <= extra; i++) {
			ints[i] = i * 2;
		}

		addComponent(new ComponentInventory(this).size(invSize).inputs(inputCount).outputs(outputCount).biproducts(biproducts).upgrades(3).processors(processorCount).processorInputs(processorInputs).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator(ints)).setMachineSlots(extra));
		addComponent(new ComponentContainerProvider(machine).createMenu((id, player) -> (extra == 0 ? new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 1 ? new ContainerO2OProcessorDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 2 ? new ContainerO2OProcessorTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));

		for (int i = 0; i <= extra; i++) {
			addProcessor(new ComponentProcessor(this).setProcessorNumber(i).canProcess(component -> component.canProcessItem2ItemRecipe(component, ElectrodynamicsRecipeInit.MINERAL_GRINDER_TYPE.get())).process(component -> component.processItem2ItemRecipe(component)).requiredTicks(Constants.MINERALGRINDER_REQUIRED_TICKS).usage(Constants.MINERALGRINDER_USAGE_PER_TICK));
		}
	}

	protected void tickServer(ComponentTickable tick) {
		InventoryUtils.handleExperienceUpgrade(this);
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean has = shouldPlaySound();
		if (has) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.2 + 0.8, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
			int amount = getType() == ElectrodynamicsBlockTypes.TILE_MINERALGRINDERDOUBLE.get() ? 2 : getType() == ElectrodynamicsBlockTypes.TILE_MINERALGRINDERTRIPLE.get() ? 3 : 1;
			for (int i = 0; i < amount; i++) {
				ComponentInventory inv = getComponent(ComponentType.Inventory);
				ItemStack stack = inv.getInputContents().get(getProcessor(i).getProcessorNumber()).get(0);
				if (stack.getItem() instanceof BlockItem it) {
					Block block = it.getBlock();
					double d4 = level.random.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
					double d6 = level.random.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
					ParticleAPI.addGrindedParticle(level, worldPosition.getX() + d4, worldPosition.getY() + 0.8, worldPosition.getZ() + d6, 0.0D, 5D, 0.0D, block.defaultBlockState(), worldPosition);
				}
			}
			clientRunningTicks++;
		}
		if (has && !isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_MINERALGRINDER.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return getType() == ElectrodynamicsBlockTypes.TILE_MINERALGRINDERDOUBLE.get() ? getProcessor(0).operatingTicks.get() + getProcessor(1).operatingTicks.get() > 0 : getType() == ElectrodynamicsBlockTypes.TILE_MINERALGRINDERTRIPLE.get() ? getProcessor(0).operatingTicks.get() + getProcessor(1).operatingTicks.get() + getProcessor(2).operatingTicks.get() > 0 : getProcessor(0).operatingTicks.get() > 0;
	}
}

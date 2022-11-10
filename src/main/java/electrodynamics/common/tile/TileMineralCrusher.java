package electrodynamics.common.tile;

import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorDouble;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessorTriple;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileMineralCrusher extends GenericTile {
	public long clientRunningTicks = 0;

	public TileMineralCrusher(BlockPos pos, BlockState state) {
		this(0, pos, state);
	}

	public TileMineralCrusher(int extra, BlockPos pos, BlockState state) {
		super(extra == 1 ? ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERDOUBLE.get() : extra == 2 ? ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERTRIPLE.get() : ElectrodynamicsBlockTypes.TILE_MINERALCRUSHER.get(), pos, state);

		int processorInputs = 1;
		int processorCount = extra + 1;
		int inputCount = processorInputs * (extra + 1);
		int outputCount = 1 * (extra + 1);
		int biproducts = 1 + extra;
		int invSize = 3 + inputCount + outputCount + biproducts;

		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2 * Math.pow(2, extra)));

		int[] ints = new int[extra + 1];
		for (int i = 0; i <= extra; i++) {
			ints[i] = i * 2;
		}

		addComponent(new ComponentInventory(this).size(invSize).inputs(inputCount).outputs(outputCount).upgrades(3).processors(processorCount).processorInputs(processorInputs).biproducts(biproducts).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator(ints)).setMachineSlots(extra).shouldSendInfo());
		addComponent(new ComponentContainerProvider("container.mineralcrusher" + extra).createMenu((id, player) -> (extra == 0 ? new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 1 ? new ContainerO2OProcessorDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 2 ? new ContainerO2OProcessorTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));

		for (int i = 0; i <= extra; i++) {
			addProcessor(new ComponentProcessor(this).setProcessorNumber(i).canProcess(component -> component.canProcessItem2ItemRecipe(component, ElectrodynamicsRecipeInit.MINERAL_CRUSHER_TYPE.get())).process(component -> component.processItem2ItemRecipe(component)).requiredTicks(Constants.MINERALCRUSHER_REQUIRED_TICKS).usage(Constants.MINERALCRUSHER_USAGE_PER_TICK));
		}
	}

	protected void tickServer(ComponentTickable tick) {
		InventoryUtils.handleExperienceUpgrade(this);
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean has = getType() == ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERDOUBLE.get() ? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks > 0 : getType() == ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERTRIPLE.get() ? getProcessor(0).operatingTicks + getProcessor(1).operatingTicks + getProcessor(2).operatingTicks > 0 : getProcessor(0).operatingTicks > 0;
		if (has) {
			Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
			if (level.random.nextDouble() < 0.15) {
				double d4 = level.random.nextDouble();
				double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : d4;
				double d6 = level.random.nextDouble();
				double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : d4;
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
			}
			double progress = Math.sin(0.05 * Math.PI * (clientRunningTicks % 20));
			if (progress == 1) {
				SoundAPI.playSound(SoundRegister.SOUND_MINERALCRUSHER.get(), SoundSource.BLOCKS, 5, .75f, worldPosition);
			} else if (progress < 0.3) {
				for (int i = 0; i < 5; i++) {
					double d4 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
					double d6 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
					level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d4 + direction.getStepX() * 0.2, worldPosition.getY() + 0.4, worldPosition.getZ() + d6 + direction.getStepZ() * 0.2, 0.0D, 0.0D, 0.0D);
				}
				int amount = getType() == ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERDOUBLE.get() ? 2 : getType() == ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERTRIPLE.get() ? 3 : 0;
				for (int in = 0; in < amount; in++) {
					ComponentInventory inv = getComponent(ComponentType.Inventory);
					ItemStack stack = inv.getInputContents().get(getProcessor(in).getProcessorNumber()).get(0);
					if (stack.getItem() instanceof BlockItem it) {
						Block block = it.getBlock();
						for (int i = 0; i < 5; i++) {
							double d4 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
							double d6 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
							ParticleAPI.addGrindedParticle(level, worldPosition.getX() + d4 + direction.getStepX() * 0.2, worldPosition.getY() + 0.4, worldPosition.getZ() + d6 + direction.getStepZ() * 0.2, 0.0D, 0.0D, 0.0D, block.defaultBlockState(), worldPosition);
						}
					}
				}
			}
			clientRunningTicks++;
		}
	}

}

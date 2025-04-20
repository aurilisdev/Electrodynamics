package electrodynamics.common.tile.machines.mineralgrinder;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.particle.ParticleAPI;
import voltaic.common.inventory.container.ContainerO2OProcessor;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileMineralGrinder extends GenericTile implements ITickableSound {

	private final int procCount;

	public long clientRunningTicks = 0;
	private boolean isSoundPlaying = false;

	public TileMineralGrinder(BlockPos pos, BlockState state) {
		this(ElectrodynamicsTiles.TILE_MINERALGRINDER.get(), 1, pos, state);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralgrinder.tag(), this).createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public TileMineralGrinder(BlockEntityType<?> type, int procCount, BlockPos pos, BlockState state) {
		super(type, pos, state);

		this.procCount = procCount;

		int inputsPerProc = 1;
		int outputPerProc = 1;
		int biprodsPerProc = 1;

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BACK).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * Math.pow(2, procCount - 1)));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(procCount, inputsPerProc, outputPerProc, biprodsPerProc).upgrades(3)).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator()).implementMachineInputsAndOutputs());
		addComponent(new ComponentProcessor(this, procCount).canProcess((component, procNumber) -> component.canProcessItem2ItemRecipe(procNumber, ElectrodynamicsRecipies.MINERAL_GRINDER_TYPE.get())).process(ComponentProcessor::processItem2ItemRecipe));

	}

	protected void tickClient(ComponentTickable tickable) {
		if (!this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive()) {
			return;
		}

		if (level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.2 + 0.8, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
		for (int i = 0; i < procCount; i++) {
			ComponentInventory inv = getComponent(IComponentType.Inventory);
			ItemStack stack = inv.getInputsForProcessor(i).get(0);
			if (stack.getItem() instanceof BlockItem it) {
				Block block = it.getBlock();
				double d4 = level.random.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
				double d6 = level.random.nextDouble() * 12.0 / 16.0 + 0.5 - 6.0 / 16.0;
				ParticleAPI.addGrindedParticle(level, worldPosition.getX() + d4, worldPosition.getY() + 0.8, worldPosition.getZ() + d6, 0.0D, 5D, 0.0D, block.defaultBlockState(), worldPosition);
			}
		}
		clientRunningTicks++;

		if (!isSoundPlaying) {
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
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive();
	}

	@Override
	public int getComparatorSignal() {
		return (int) (((double) this.<ComponentProcessor>getComponent(IComponentType.Processor).getTotalActive() / (double) Math.max(1, this.<ComponentProcessor>getComponent(IComponentType.Processor).getProcessorCount())) * 15.0);
	}
}

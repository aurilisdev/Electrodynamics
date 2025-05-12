package electrodynamics.common.tile.machines.mineralcrusher;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import voltaic.api.particle.ParticleAPI;
import voltaic.common.inventory.container.ContainerO2OProcessor;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileMineralCrusher extends GenericTile implements ITickableSound {

	private final int procCount;

	public long clientRunningTicks = 0;
	private boolean isSoundPlaying = false;

	public TileMineralCrusher() {
		this(ElectrodynamicsTiles.TILE_MINERALCRUSHER.get(), 1);

		addComponent(new ComponentContainerProvider(SubtypeMachine.mineralcrusher.tag(), this).createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public TileMineralCrusher(TileEntityType<?> type, int procCount) {
		super(type);

		this.procCount = procCount;

		int inputsPerProc = 1;
		int outputPerProc = 1;
		int biprodsPerProc = 1;

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BACK).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2 * Math.pow(2, procCount - 1)));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(procCount, inputsPerProc, outputPerProc, biprodsPerProc).upgrades(3)).validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator()).implementMachineInputsAndOutputs());
		addComponent(new ComponentProcessor(this, procCount).canProcess((component, procNumber) -> component.canProcessItem2ItemRecipe(procNumber, ElectrodynamicsRecipies.MINERAL_CRUSHER_TYPE)).process(ComponentProcessor::processItem2ItemRecipe));
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive()) {
			return;
		}

		Direction direction = getFacing();
		if (level.random.nextDouble() < 0.15) {
			double d4 = level.random.nextDouble();
			double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : d4;
			double d6 = level.random.nextDouble();
			double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : d4;
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
		}
		double progress = Math.sin(0.05 * Math.PI * (clientRunningTicks % 20));
		if (progress < 0.3) {
			for (int i = 0; i < 5; i++) {
				double d4 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
				double d6 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d4 + direction.getStepX() * 0.2, worldPosition.getY() + 0.4, worldPosition.getZ() + d6 + direction.getStepZ() * 0.2, 0.0D, 0.0D, 0.0D);
			}
			for (int procNum = 0; procNum < procCount; procNum++) {

				ComponentInventory inv = getComponent(IComponentType.Inventory);

				ItemStack stack = inv.getInputsForProcessor(procNum).get(0);

				if (stack.getItem() instanceof BlockItem) {
					BlockItem it = (BlockItem) stack.getItem();

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

		if (!isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_MINERALCRUSHER.get(), this, true);
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

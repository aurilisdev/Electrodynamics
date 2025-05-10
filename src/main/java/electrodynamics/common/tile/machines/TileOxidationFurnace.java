package electrodynamics.common.tile.machines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import voltaic.Voltaic;
import voltaic.common.inventory.container.ContainerDO2OProcessor;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileOxidationFurnace extends GenericTile implements ITickableSound {

	private boolean isSoundPlaying = false;

	public TileOxidationFurnace() {
		super(ElectrodynamicsTiles.TILE_OXIDATIONFURNACE.get());
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BACK).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 2, 1, 1).upgrades(3)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0).setSlotsByDirection(BlockEntityUtils.MachineDirection.RIGHT, 1)
				//
				.setDirectionsBySlot(2, BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.LEFT).setDirectionsBySlot(3, BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.LEFT).validUpgrades(ContainerDO2OProcessor.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.oxidationfurnace.tag(), this).createMenu((id, player) -> new ContainerDO2OProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
		addComponent(new ComponentProcessor(this).canProcess(this::canProcessOxideFurn).process(ComponentProcessor::processItem2ItemRecipe));
	}

	protected boolean canProcessOxideFurn(ComponentProcessor component, int procNumber) {
		boolean canProcess = component.canProcessItem2ItemRecipe(procNumber, ElectrodynamicsRecipies.OXIDATION_FURNACE_TYPE);
		if (BlockEntityUtils.isLit(this) ^ canProcess) {
			BlockEntityUtils.updateLit(this, canProcess);
		}

		return canProcess;
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!shouldPlaySound()) {
			return;
		}
		if (level.random.nextDouble() < 0.5) {

			Direction direction = getFacing();

			int next = level.random.nextInt(4);

			double yShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.2) + 0.5;
			double axisShift = Math.min(Voltaic.RANDOM.nextDouble(), 0.7) + 0.18;

			if(next == 1) {
				direction = direction.getClockWise();
				yShift = 0.6;
			} else if (next == 2) {
				direction = direction.getCounterClockWise();
				yShift = 0.6;
			}

			//double d4 = level.random.nextDouble();
			double xShift = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : axisShift;
			//double d6 = level.random.nextDouble();
			double zShift = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : axisShift;




			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + xShift, worldPosition.getY() + yShift, worldPosition.getZ() + zShift, 0.0D, 0.0D, 0.0D);
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
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0);
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
	}

}

package electrodynamics.common.tile.machines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.block.Blocks;
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

public class TileLathe extends GenericTile implements ITickableSound {

	private boolean isSoundPlaying = false;

	public TileLathe() {
		super(ElectrodynamicsTiles.TILE_LATHE.get());
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BACK).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 1, 1).upgrades(3)).valid(machineValidator()).setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.FRONT)
				//
				.setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.BOTTOM).setDirectionsBySlot(2, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.BOTTOM));
		addComponent(new ComponentContainerProvider(SubtypeMachine.lathe.tag(), this).createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
		addComponent(new ComponentProcessor(this).canProcess((component, procNumber) -> component.canProcessItem2ItemRecipe(procNumber, ElectrodynamicsRecipies.LATHE_TYPE)).process(ComponentProcessor::processItem2ItemRecipe));
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0)) {
			return;
		}
		Direction direction = getFacing();
		if (level.random.nextDouble() < 0.10) {
			for (int i = 0; i < 5; i++) {
				double d4 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
				double d6 = level.random.nextDouble() * 4.0 / 16.0 + 0.5 - 2.0 / 16.0;
				ParticleAPI.addGrindedParticle(level, worldPosition.getX() + d4 + direction.getStepX() * 0.2, worldPosition.getY() + 0.7, worldPosition.getZ() + d6 + direction.getStepZ() * 0.2, 0.0D, 0.0D, 0.0D, Blocks.IRON_BLOCK.defaultBlockState(), worldPosition);
			}
		}
		if (!isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_LATHEPLAYING.get(), this, true);
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

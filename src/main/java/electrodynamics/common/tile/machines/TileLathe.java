package electrodynamics.common.tile.machines;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.particle.ParticleAPI;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerO2OProcessor;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TileLathe extends GenericTile implements ITickableSound {

	private boolean isSoundPlaying = false;

	public TileLathe(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_LATHE.get(), worldPosition, blockState);
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 1, 1).upgrades(3)).valid(machineValidator()).setDirectionsBySlot(0, Direction.EAST, Direction.UP).setDirectionsBySlot(1, Direction.WEST, Direction.DOWN).setDirectionsBySlot(2, Direction.WEST, Direction.DOWN));
		addComponent(new ComponentContainerProvider(SubtypeMachine.lathe, this).createMenu((id, player) -> new ContainerO2OProcessor(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
		addProcessor(new ComponentProcessor(this).canProcess(component -> component.canProcessItem2ItemRecipe(component, ElectrodynamicsRecipeInit.LATHE_TYPE.get())).process(component -> component.processItem2ItemRecipe(component)));
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!isProcessorActive()) {
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
		return isProcessorActive();
	}

	@Override
	public int getComparatorSignal() {
		return (int) (((double) getNumActiveProcessors() / (double) Math.max(1, getNumProcessors())) * 15.0);
	}

}

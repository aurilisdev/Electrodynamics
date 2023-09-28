package electrodynamics.common.tile.machines;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerDO2OProcessor;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;

public class TileOxidationFurnace extends GenericTile implements ITickableSound {

	private boolean isSoundPlaying = false;

	public TileOxidationFurnace(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_OXIDATIONFURNACE.get(), worldPosition, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 2, 1, 1).upgrades(3)).faceSlots(Direction.UP, 0, 1).relativeFaceSlots(Direction.EAST, 1).relativeSlotFaces(2, Direction.DOWN, Direction.WEST).validUpgrades(ContainerDO2OProcessor.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.oxidationfurnace, this).createMenu((id, player) -> new ContainerDO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
		addComponent(new ComponentProcessor(this).canProcess(this::canProcessOxideFurn).process(component -> component.processItem2ItemRecipe(component)));
	}

	protected boolean canProcessOxideFurn(ComponentProcessor component) {
		boolean canProcess = component.canProcessItem2ItemRecipe(component, ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE.get());
		if (BlockEntityUtils.isLit(this) ^ canProcess) {
			BlockEntityUtils.updateLit(this, canProcess);
		}

		return canProcess;
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!shouldPlaySound()) {
			return;
		}
		if (level.random.nextDouble() < 0.15) {
			Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
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
		return this.<ComponentProcessor>getComponent(ComponentType.Processor).isActive();
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(ComponentType.Processor).isActive() ? 15 : 0;
	}

}

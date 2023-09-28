package electrodynamics.common.tile.machines;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerChemicalCrystallizer;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericMaterialTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileChemicalCrystallizer extends GenericMaterialTile implements ITickableSound {
	public static final int MAX_TANK_CAPACITY = 5000;
	// client-exclusive variable that is never saved
	private boolean isSoundPlaying = false;

	public TileChemicalCrystallizer(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_CHEMICALCRYSTALLIZER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, MAX_TANK_CAPACITY).universalInput().setRecipeType(ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE.get()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 0, 1, 0).bucketInputs(1).upgrades(3)).relativeSlotFaces(0, Direction.values()).validUpgrades(ContainerChemicalCrystallizer.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.consumeBucket().canProcessFluid2ItemRecipe(component, ElectrodynamicsRecipeInit.CHEMICAL_CRYSTALIZER_TYPE.get())).process(component -> component.processFluid2ItemRecipe(component)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.chemicalcrystallizer, this).createMenu((id, player) -> new ContainerChemicalCrystallizer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
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

	static {

		VoxelShape shape = Block.box(0, 0, 0, 16, 5, 16);

		shape = Shapes.or(shape, Block.box(0, 5, 0, 2, 6, 16));
		shape = Shapes.or(shape, Block.box(2, 5, 0, 4, 6, 2));
		shape = Shapes.or(shape, Block.box(2, 5, 14, 4, 6, 16));

		shape = Shapes.or(shape, Block.box(4, 5, 0, 12, 12, 1));
		shape = Shapes.or(shape, Block.box(4, 5, 15, 12, 12, 16));

		shape = Shapes.or(shape, Block.box(12, 5, 0, 13, 6, 2));
		shape = Shapes.or(shape, Block.box(12, 5, 14, 13, 6, 16));

		shape = Shapes.or(shape, Block.box(13, 5, 0, 16, 16, 2));
		shape = Shapes.or(shape, Block.box(13, 14, 2, 16, 16, 14));
		shape = Shapes.or(shape, Block.box(13, 5, 14, 16, 16, 16));

		shape = Shapes.or(shape, Block.box(15, 5, 4, 16, 12, 12));
		shape = Shapes.or(shape, Block.box(14, 5, 2, 15, 14, 14));

		VoxelShapes.registerShape(SubtypeMachine.chemicalcrystallizer, shape, Direction.WEST);

	}

}

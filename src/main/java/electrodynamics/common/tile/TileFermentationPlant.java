package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFermentationPlant;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
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
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileFermentationPlant extends GenericMaterialTile {

	public static final int MAX_TANK_CAPACITY = 5000;

	public TileFermentationPlant(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_FERMENTATIONPLANT.get(), worldPosition, blockState);
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE));
		addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, new int[] { MAX_TANK_CAPACITY }, new int[] { MAX_TANK_CAPACITY }).setInputDirections(Direction.WEST).setOutputDirections(Direction.EAST).setRecipeType(ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE.get()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).bucketOutputs(1).upgrades(3)).faceSlots(Direction.DOWN, 1).relativeSlotFaces(0, Direction.EAST, Direction.UP).validUpgrades(ContainerFermentationPlant.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(processor -> processor.outputToFluidPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(processor, ElectrodynamicsRecipeInit.FERMENTATION_PLANT_TYPE.get())).process(component -> component.processFluidItem2FluidRecipe(component)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.fermentationplant, this).createMenu((id, player) -> new ContainerFermentationPlant(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (this.<ComponentProcessor>getComponent(ComponentType.Processor).isActive()) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
			Direction dir = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getClockWise();
			double x = worldPosition.getX() + 0.55 - dir.getStepX() * 0.2;
			double z = worldPosition.getZ() + 0.55 - dir.getStepZ() * 0.2;
			level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, worldPosition.getY() + 0.4, z, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(ComponentType.Processor).isActive() ? 15 : 0;
	}

	static {

		VoxelShape shape = Block.box(1, 0, 0, 15, 5, 16);

		shape = Shapes.or(shape, Block.box(5, 5, 0, 11, 11, 1));
		shape = Shapes.or(shape, Block.box(5, 5, 15, 11, 11, 16));

		shape = Shapes.or(shape, Block.box(5, 5, 1, 11, 8, 8));

		shape = Shapes.or(shape, Block.box(3, 5, 2, 4, 8, 3));
		shape = Shapes.or(shape, Block.box(3, 7, 3, 4, 8, 6));
		shape = Shapes.or(shape, Block.box(3, 5, 6, 4, 8, 7));
		shape = Shapes.or(shape, Block.box(12, 5, 2, 13, 8, 3));
		shape = Shapes.or(shape, Block.box(12, 7, 3, 13, 8, 6));
		shape = Shapes.or(shape, Block.box(12, 5, 6, 13, 8, 7));

		shape = Shapes.or(shape, Block.box(4, 8, 2, 5, 9, 3));
		shape = Shapes.or(shape, Block.box(4, 8, 4, 5, 9, 5));
		shape = Shapes.or(shape, Block.box(4, 8, 6, 5, 9, 7));
		shape = Shapes.or(shape, Block.box(11, 8, 2, 12, 9, 3));
		shape = Shapes.or(shape, Block.box(11, 8, 4, 12, 9, 5));
		shape = Shapes.or(shape, Block.box(11, 8, 6, 12, 9, 7));

		shape = Shapes.or(shape, Block.box(1, 5, 4, 2, 8, 5));
		shape = Shapes.or(shape, Block.box(2, 7, 4, 3, 8, 5));
		shape = Shapes.or(shape, Block.box(14, 5, 4, 15, 8, 5));
		shape = Shapes.or(shape, Block.box(13, 7, 4, 14, 8, 5));

		shape = Shapes.or(shape, Block.box(2, 5, 11, 3, 8, 12));
		shape = Shapes.or(shape, Block.box(3, 7, 11, 4, 8, 12));
		shape = Shapes.or(shape, Block.box(13, 5, 11, 14, 8, 12));
		shape = Shapes.or(shape, Block.box(12, 7, 11, 13, 8, 12));

		shape = Shapes.or(shape, Block.box(4, 5, 9, 5, 8, 10));
		shape = Shapes.or(shape, Block.box(4, 7, 10, 5, 8, 13));
		shape = Shapes.or(shape, Block.box(4, 5, 13, 5, 8, 14));
		shape = Shapes.or(shape, Block.box(11, 5, 9, 12, 8, 10));
		shape = Shapes.or(shape, Block.box(11, 7, 10, 12, 8, 13));
		shape = Shapes.or(shape, Block.box(11, 5, 13, 12, 8, 14));

		shape = Shapes.or(shape, Block.box(4, 8, 10, 5, 14, 11));
		shape = Shapes.or(shape, Block.box(5, 13, 10, 6, 14, 11));
		shape = Shapes.or(shape, Block.box(4, 8, 12, 5, 14, 13));
		shape = Shapes.or(shape, Block.box(5, 13, 12, 6, 14, 13));
		shape = Shapes.or(shape, Block.box(11, 8, 10, 12, 14, 11));
		shape = Shapes.or(shape, Block.box(10, 13, 10, 11, 14, 11));
		shape = Shapes.or(shape, Block.box(11, 8, 12, 12, 14, 13));
		shape = Shapes.or(shape, Block.box(10, 13, 12, 11, 14, 13));
		
		shape = Shapes.or(shape, Block.box(6, 8, 10, 10, 15, 13));
		shape = Shapes.or(shape, Block.box(6, 5, 10, 9, 6, 13));
		shape = Shapes.or(shape, Block.box(7, 6, 11, 8, 7, 12));
		
		shape = Shapes.or(shape, Block.box(7.5, 9, 9, 8.5, 10, 10));
		shape = Shapes.or(shape, Block.box(7.5, 7, 8, 8.5, 9, 9));

		VoxelShapes.registerShape(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant), shape, Direction.WEST);
	}

}

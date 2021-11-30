package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerDO2OProcessor;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.item2item.specificmachines.OxidationFurnaceRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TileOxidationFurnace extends GenericTile {

	private static int inputSlots = 2;
    private static int outputSize = 1;
    private static int itemBiSize = 0;
    private static int inputBucketSlots = 0;
    private static int outputBucketSlots = 0;
    private static int upgradeSlots = 3;
    
    private static int processorCount = 1;
    private static int inputPerProc = 2;
    
    private static int invSize = 
    	inputSlots + outputSize + inputBucketSlots + outputBucketSlots + upgradeSlots + itemBiSize;
	
    public TileOxidationFurnace(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_OXIDATIONFURNACE.get(), worldPosition, blockState);
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable().tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentInventory(this).size(invSize).faceSlots(Direction.UP, 0, 1).relativeFaceSlots(Direction.EAST, 1)
		.relativeSlotFaces(2, Direction.DOWN, Direction.WEST)
		.slotSizes(inputSlots, outputSize, itemBiSize, upgradeSlots, inputBucketSlots, outputBucketSlots, processorCount, inputPerProc)
		.valid(getPredicate(inputSlots, outputSize, itemBiSize,inputBucketSlots + outputBucketSlots, upgradeSlots, invSize)));
	addComponent(new ComponentContainerProvider("container.oxidationfurnace")
		.createMenu((id, player) -> new ContainerDO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	addComponent(new ComponentProcessor(this).setProcessorNumber(0).canProcess(this::canProcessOxideFurn)
		.process(component -> component.processItem2ItemRecipe(component, OxidationFurnaceRecipe.class))
		.requiredTicks(Constants.OXIDATIONFURNACE_REQUIRED_TICKS).usage(Constants.OXIDATIONFURNACE_USAGE_PER_TICK));
    }

    protected boolean canProcessOxideFurn(ComponentProcessor component) {
	if (component.canProcessItem2ItemRecipe(component, OxidationFurnaceRecipe.class, ElectrodynamicsRecipeInit.OXIDATION_FURNACE_TYPE)) {
	    if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace)) {
		level.setBlock(worldPosition,
			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning).defaultBlockState()
				.setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING))
				.setValue(BlockStateProperties.WATERLOGGED, getBlockState().getValue(BlockStateProperties.WATERLOGGED)),
			2 | 16 | 32);
	    }
	    return true;
	} else if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning)) {
	    level.setBlock(worldPosition,
		    DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace).defaultBlockState()
			    .setValue(GenericEntityBlock.FACING, getBlockState().getValue(GenericEntityBlock.FACING))
			    .setValue(BlockStateProperties.WATERLOGGED, getBlockState().getValue(BlockStateProperties.WATERLOGGED)),
		    2 | 16 | 32);
	}
	return false;
    }

    protected void tickClient(ComponentTickable tickable) {
	ComponentProcessor processor = getComponent(ComponentType.Processor);
	if (processor.operatingTicks > 0 && level.random.nextDouble() < 0.15) {
	    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	    double d4 = level.random.nextDouble();
	    double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : d4;
	    double d6 = level.random.nextDouble();
	    double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : d4;
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
	}
	if (processor.operatingTicks > 0 && tickable.getTicks() % 200 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_HUM.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
	}
    }

}

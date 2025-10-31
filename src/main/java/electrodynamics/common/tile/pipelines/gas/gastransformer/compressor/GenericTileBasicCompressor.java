package electrodynamics.common.tile.pipelines.gas.gastransformer.compressor;

import electrodynamics.common.inventory.container.tile.ContainerCompressor;
import electrodynamics.common.inventory.container.tile.ContainerDecompressor;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.pipelines.gas.gastransformer.IAddonTankManager;
import electrodynamics.common.tile.pipelines.gas.gastransformer.TileGasTransformerAddonTank;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public abstract class GenericTileBasicCompressor extends GenericTileCompressor implements IAddonTankManager {

    public GenericTileBasicCompressor(BlockEntityType<?> type, BlockPos worldPos, BlockState blockState) {
	super(type, worldPos, blockState);
    }

    @Override
    public void tickClient(ComponentTickable tickable) {

	super.tickClient(tickable);

	if (level.getRandom().nextDouble() < 0.15) {

	    // TODO add particles?

	}

    }

    @Override
    public void updateTankCount() {
	BlockPos abovePos = getBlockPos().above();
	BlockState aboveState = getLevel().getBlockState(abovePos);
	BlockEntity aboveTile;
	int tankCount = 0;
	for (int i = 0; i < ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_LIMIT.get(); i++) {
	    if (!aboveState.is(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK)) {
		break;
	    }
	    aboveTile = getLevel().getBlockEntity(abovePos);
	    if ((aboveTile == null) || !(aboveTile instanceof TileGasTransformerAddonTank tank)) {
		break;
	    }
	    abovePos = abovePos.above();
	    aboveState = getLevel().getBlockState(abovePos);
	    tank.setOwnerPos(getBlockPos());
	    tankCount++;
	}
	ComponentGasHandlerMulti handler = getComponent(IComponentType.GasHandler);
	handler.getInputTanks()[0].setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_INPUT_CAPACITY.get()
		+ ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * tankCount);
	handler.getOutputTanks()[0]
		.setCapacity(ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_BASE_OUTPUT_CAPACITY.get()
			+ ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_CAPACITY.get() * tankCount);
    }

    @Override
    public void outputToPipe(ComponentProcessor processor, ComponentGasHandlerMulti multi, Direction facing) {
	processor.outputToGasPipe();
    }

    @Override
    public void updateLit(boolean isHeating, Direction facing) {
	if (BlockEntityUtils.isLit(this) ^ isHeating) {
	    BlockEntityUtils.updateLit(this, isHeating);
	}
    }

    public static class TileCompressor extends GenericTileBasicCompressor {
	public TileCompressor(BlockPos worldPos, BlockState blockState) {
	    super(ElectrodynamicsTiles.TILE_COMPRESSOR.get(), worldPos, blockState);
	}

	@Override
	public double getPressureMultiplier() {
	    return 2;
	}

	@Override
	public SoundEvent getSound() {
	    return ElectrodynamicsSounds.SOUND_COMPRESSORRUNNING.get();
	}

	@Override
	public ComponentContainerProvider getContainerProvider() {
	    return new ComponentContainerProvider("compressor", this).createMenu((id,
		    inv) -> new ContainerCompressor(id, inv, getComponent(IComponentType.Inventory), getCoordsArray()));
	}

	@Override
	public double getUsagePerTick() {
	    return ElectrodynamicsConfig.INSTANCE.COMPRESSOR_USAGE_PER_TICK.get();
	}

	@Override
	public int getConversionRate() {
	    return ElectrodynamicsConfig.INSTANCE.COMPRESSOR_CONVERSION_RATE.get();
	}
    }

    public static class TileDecompressor extends GenericTileBasicCompressor {
	public TileDecompressor(BlockPos worldPos, BlockState blockState) {
	    super(ElectrodynamicsTiles.TILE_DECOMPRESSOR.get(), worldPos, blockState);
	}

	@Override
	public double getPressureMultiplier() {
	    return 0.5;
	}

	@Override
	public SoundEvent getSound() {
	    return ElectrodynamicsSounds.SOUND_DECOMPRESSORRUNNING.get();
	}

	@Override
	public ComponentContainerProvider getContainerProvider() {
	    return new ComponentContainerProvider("container.decompressor", this)
		    .createMenu((id, inv) -> new ContainerDecompressor(id, inv, getComponent(IComponentType.Inventory),
			    getCoordsArray()));
	}

	@Override
	public double getUsagePerTick() {
	    return ElectrodynamicsConfig.INSTANCE.DECOMPRESSOR_USAGE_PER_TICK.get();
	}

	@Override
	public int getConversionRate() {
	    return ElectrodynamicsConfig.INSTANCE.DECOMPRESSOR_CONVERSION_RATE.get();
	}
    }

}

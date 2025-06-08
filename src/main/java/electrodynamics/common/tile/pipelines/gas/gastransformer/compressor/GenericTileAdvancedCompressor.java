package electrodynamics.common.tile.pipelines.gas.gastransformer.compressor;

import electrodynamics.common.inventory.container.tile.ContainerAdvancedCompressor;
import electrodynamics.common.inventory.container.tile.ContainerAdvancedDecompressor;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.pipelines.gas.gastransformer.IMultiblockGasTransformer;
import electrodynamics.common.tile.pipelines.gas.gastransformer.TileGasTransformerSideBlock;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.GasTank;
import voltaic.api.gas.IGasHandler;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public abstract class GenericTileAdvancedCompressor extends GenericTileCompressor implements IMultiblockGasTransformer {

    public boolean hasBeenDestroyed = false;

    public final SingleProperty<Double> pressureMultiplier;

    public GenericTileAdvancedCompressor(BlockEntityType<?> type, BlockPos worldPos, BlockState blockState, double defaultMultiplier) {
        super(type, worldPos, blockState);
        pressureMultiplier = property(new SingleProperty<>(PropertyTypes.DOUBLE, "pressuremultiplier", defaultMultiplier));
    }


    @Override
    public void tickClient(ComponentTickable tickable) {

        super.tickClient(tickable);

        if (level.getRandom().nextDouble() < 0.15) {


            //TODO add particles?


        }

    }

    @Override
    public void updateAddonTanks(int count, boolean isLeft) {
        ComponentGasHandlerMulti handler = getComponent(IComponentType.GasHandler);
        if (isLeft) {
            handler.getInputTanks()[0].setCapacity(ElectroConstants.GAS_TRANSFORMER_BASE_INPUT_CAPCITY + ElectroConstants.GAS_TRANSFORMER_ADDON_TANK_CAPCITY * count);
        } else {
            handler.getOutputTanks()[0].setCapacity(ElectroConstants.GAS_TRANSFORMER_BASE_OUTPUT_CAPCITY + ElectroConstants.GAS_TRANSFORMER_ADDON_TANK_CAPCITY * count);
        }
    }

    @Override
    public boolean hasBeenDestroyed() {
        return hasBeenDestroyed;
    }

    @Override
    public void onPlace(BlockState oldState, boolean isMoving) {
        super.onPlace(oldState, isMoving);
        if (level.isClientSide) {
            return;
        }
        Direction facing = getFacing();

        BlockEntity left = getLevel().getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.EAST)));
        BlockEntity right = getLevel().getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.WEST)));

        if (left != null && right != null && left instanceof TileGasTransformerSideBlock leftTile && right instanceof TileGasTransformerSideBlock rightTile) {
            leftTile.setOwnerPos(getBlockPos());
            leftTile.setIsLeft();
            leftTile.setChanged();
            rightTile.setOwnerPos(getBlockPos());
            rightTile.setChanged();
        }
    }

    @Override
    public void onBlockDestroyed() {
        if (level.isClientSide || hasBeenDestroyed) {
            return;
        }
        hasBeenDestroyed = true;
        Direction facing = getFacing();
        getLevel().destroyBlock(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.WEST)), false);
        getLevel().destroyBlock(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.EAST)), false);
    }

    @Override
    public void outputToPipe(ComponentProcessor processor, ComponentGasHandlerMulti gasHandler, Direction facing) {
        Direction direction = BlockEntityUtils.getRelativeSide(facing, BlockEntityUtils.MachineDirection.LEFT.mappedDir);// opposite of west is east
        BlockPos face = getBlockPos().relative(direction, 2);
        BlockEntity faceTile = getLevel().getBlockEntity(face);
        if (faceTile != null) {

            IGasHandler handler = faceTile.getCapability(VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK, direction.getOpposite()).orElse(CapabilityUtils.EMPTY_GAS);

            if (handler != CapabilityUtils.EMPTY_GAS) {
                GasTank gasTank = gasHandler.getOutputTanks()[0];
                for (int i = 0; i < handler.getTanks(); i++) {
                    GasStack tankGas = gasTank.getGas();
                    int amtAccepted = handler.fill(tankGas, GasAction.EXECUTE);
                    GasStack taken = new GasStack(tankGas.getGas(), amtAccepted, tankGas.getTemperature(), tankGas.getPressure());
                    gasTank.drain(taken, GasAction.EXECUTE);
                }
            }
        }
    }

    @Override
    public void updateLit(boolean canProcess, Direction facing) {
        if (BlockEntityUtils.isLit(this) ^ canProcess) {
            BlockEntityUtils.updateLit(this, canProcess);
            BlockEntity left = getLevel().getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.EAST)));
            BlockEntity right = getLevel().getBlockEntity(getBlockPos().relative(BlockEntityUtils.getRelativeSide(facing, Direction.WEST)));
            if (left != null && left instanceof TileGasTransformerSideBlock leftTile && right != null && right instanceof TileGasTransformerSideBlock rightTile) {
                BlockEntityUtils.updateLit(leftTile, canProcess);
                BlockEntityUtils.updateLit(rightTile, canProcess);
            }
        }
    }

    @Override
    public double getPressureMultiplier() {
        return pressureMultiplier.getValue();
    }

    public static class TileAdvancedCompressor extends GenericTileAdvancedCompressor {
        public TileAdvancedCompressor(BlockPos worldPos, BlockState blockState) {
            super(ElectrodynamicsTiles.TILE_ADVANCEDCOMPRESSOR.get(), worldPos, blockState, 2);
        }

        @Override
        public SoundEvent getSound() {
            return ElectrodynamicsSounds.SOUND_COMPRESSORRUNNING.get();
        }

        @Override
        public ComponentContainerProvider getContainerProvider() {
            return new ComponentContainerProvider("advancedcompressor", this).createMenu((id, inv) -> new ContainerAdvancedCompressor(id, inv, getComponent(IComponentType.Inventory), getCoordsArray()));
        }

        @Override
        public double getUsagePerTick() {
            return ElectroConstants.ADVACNED_COMPRESSOR_USAGE_PER_TICK;
        }

        @Override
        public int getConversionRate() {
            return ElectroConstants.ADVACNED_COMPRESSOR_CONVERSION_RATE;
        }
    }

    public static class TileAdvancedDecompressor extends GenericTileAdvancedCompressor {
        public TileAdvancedDecompressor(BlockPos worldPos, BlockState blockState) {
            super(ElectrodynamicsTiles.TILE_ADVANCEDDECOMPRESSOR.get(), worldPos, blockState, 0.5);
        }

        @Override
        public SoundEvent getSound() {
            return ElectrodynamicsSounds.SOUND_DECOMPRESSORRUNNING.get();
        }

        @Override
        public ComponentContainerProvider getContainerProvider() {
            return new ComponentContainerProvider("advanceddecompressor", this).createMenu((id, inv) -> new ContainerAdvancedDecompressor(id, inv, getComponent(IComponentType.Inventory), getCoordsArray()));
        }

        @Override
        public double getUsagePerTick() {
            return ElectroConstants.ADVANCED_DECOMPRESSOR_USAGE_PER_TICK;
        }

        @Override
        public int getConversionRate() {
            return ElectroConstants.ADVANCED_DECOMPRESSOR_CONVERSION_RATE;
        }
    }

}

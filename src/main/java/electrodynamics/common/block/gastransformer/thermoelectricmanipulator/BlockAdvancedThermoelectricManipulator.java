package electrodynamics.common.block.gastransformer.thermoelectricmanipulator;

import electrodynamics.common.block.gastransformer.util.BlockGenericAdvancedGasTransformer;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.tile.pipelines.gas.gastransformer.thermoelectricmanipulator.TileAdvancedThermoelectricManipulator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import voltaic.common.block.states.VoltaicBlockStates;

public class BlockAdvancedThermoelectricManipulator extends BlockGenericAdvancedGasTransformer {

    public BlockAdvancedThermoelectricManipulator() {
        super(TileAdvancedThermoelectricManipulator::new);
        registerDefaultState(stateDefinition.any().setValue(VoltaicBlockStates.LIT, false).setValue(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ElectrodynamicsBlockStates.ManipulatorHeatingStatus.OFF));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.hasProperty(VoltaicBlockStates.LIT) && state.getValue(VoltaicBlockStates.LIT)) {
            return 15;
        }
        return super.getLightEmission(state, level, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(VoltaicBlockStates.LIT, false).setValue(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ElectrodynamicsBlockStates.ManipulatorHeatingStatus.OFF);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VoltaicBlockStates.LIT);
        builder.add(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS);
    }

}

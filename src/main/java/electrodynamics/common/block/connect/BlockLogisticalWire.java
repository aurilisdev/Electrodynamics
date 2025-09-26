package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.Electrodynamics;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import voltaic.api.network.cable.type.IWire;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.utilities.math.Color;

public class BlockLogisticalWire extends BlockWire {

    public static final Color REDSTONE_ON = new Color(211, 5, 5, 255);
    public static final Color REDSTONE_OFF = new Color(124, 25, 25, 255);

    public static final HashSet<Block> WIRES = new HashSet<>();

    public BlockLogisticalWire(IWire wire) {
        super(wire);
        WIRES.add(this);
        stateDefinition.any().setValue(VoltaicBlockStates.LIT, false);
    }

    @Override
    public void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VoltaicBlockStates.LIT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState superState = super.getStateForPlacement(context);
        return superState.setValue(VoltaicBlockStates.LIT, false);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileLogisticalWire(pos, state);
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
    private static class ColorHandler {

        @SubscribeEvent
        public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
            WIRES.forEach(block -> event.register((state, level, pos, tintIndex) -> {
                if (tintIndex == 0) {
                    return ((BlockLogisticalWire) block).wire.getWireColor().getColor().color();
                }
                if (tintIndex != 1) {
                    return 0xFFFFFFFF;
                }
                if (state.getValue(VoltaicBlockStates.LIT)) {
                    return REDSTONE_ON.color();
                }
                return REDSTONE_OFF.color();
            }, block));
        }
    }

}

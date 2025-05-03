package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.utilities.math.Color;

public class BlockLogisticalWire extends BlockWire {

	public static final Color REDSTONE_ON = new Color(211, 5, 5, 255);
    public static final Color REDSTONE_OFF = new Color(124, 25, 25, 255);

    public static final HashSet<Block> WIRES = new HashSet<>();

	public BlockLogisticalWire(SubtypeWire wire) {
		super(wire);
		WIRES.add(this);
	}
	
	@Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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
        public static void registerColoredBlocks(ColorHandlerEvent.Block event) {
            WIRES.forEach(block -> event.getBlockColors().register((state, level, pos, tintIndex) -> {
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

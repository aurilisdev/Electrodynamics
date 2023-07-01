package electrodynamics.common.block.connect;

import java.util.HashSet;

import electrodynamics.api.References;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.network.electric.TileLogisticalWire;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BlockLogisticalWire extends BlockWire {

	private static final HashSet<BlockLogisticalWire> WIRES = new HashSet<>();
	
	public BlockLogisticalWire(SubtypeWire wire) {
		super(wire);
		WIRES.add(this);
		stateDefinition.any().setValue(BlockMachine.ON, false);
	}
	
	@Override
	public void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockMachine.ON);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState superState = super.getStateForPlacement(context);
		return superState.setValue(BlockMachine.ON, false);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileLogisticalWire(pos, state);
	}
	
	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class ColorHandler {

		@SubscribeEvent
		public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
			WIRES.forEach(block -> event.register((state, level, pos, tintIndex) -> {
				if (tintIndex == 0) {
					return block.wire.color.color;
				} else if (tintIndex == 1) {
					if(state.getValue(BlockMachine.ON)) {
						return RenderingUtils.getRGBA(255, 211, 5, 5);
					} else {
						return RenderingUtils.getRGBA(255, 124, 25, 25);
					}
				} else {
					return 0xFFFFFFFF;
				}
			}, block));
		}
	}

}

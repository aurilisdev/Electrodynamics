package electrodynamics.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSimpleGenericMachine extends BlockGenericMachine {
    private BlockEntitySupplier<BlockEntity> blockEntitySupplier;

    public BlockSimpleGenericMachine(BlockEntitySupplier<BlockEntity> blockEntitySupplier) {
	this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return blockEntitySupplier.create(pos, state);
    }
}

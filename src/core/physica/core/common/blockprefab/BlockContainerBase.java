package physica.core.common.blockprefab;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import physica.core.common.blockprefab.state.IBlockStateInfo;

public abstract class BlockContainerBase<T extends Enum<T> & IBlockStateInfo> extends BlockStateHolder<T> {
	public BlockContainerBase(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
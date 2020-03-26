package physica.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockContainerBase extends BlockBase {
	public BlockContainerBase(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
}
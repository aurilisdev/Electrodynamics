package physica.content.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.Physica;
import physica.References;
import physica.api.lib.block.BlockBaseContainer;
import physica.content.common.tile.TileAccelerator;

public class BlockAccelerator extends BlockBaseContainer {
	public BlockAccelerator() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setCreativeTab(Physica.creativeTab);
		setBlockName(References.PREFIX + "accelerator");
		setBlockTextureName(References.PREFIX + "accelerator");

	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileAccelerator();
	}
}

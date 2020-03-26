package physica.block;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.block.state.BlockStateFacing;
import physica.tile.ITileRotatable;

public class BlockRotatable extends BlockContainerBase {
	protected BlockRotatable(String name, Material material) {
		super(material, name);

		setDefaultState(blockState.getBaseState().withProperty(BlockStateFacing.FACING, EnumFacing.NORTH));
	}

	@Override
	@Nonnull
	public BlockStateContainer createBlockState() {
		return new BlockStateFacing(this);
	}

	@Override
	@Nonnull
	public IBlockState getStateFromMeta(int metadata) {
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof ITileRotatable) {
			ITileRotatable tileRotatable = (ITileRotatable) tile;

			return state.withProperty(BlockStateFacing.FACING, tileRotatable.getFacing());
		}

		return super.getActualState(state, world, pos);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity,
			ItemStack itemStack) {
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof ITileRotatable) {
			ITileRotatable tileRotatable = (ITileRotatable) tile;

			tileRotatable.setFacing(entity.getHorizontalFacing().getOpposite());
		}
	}

	@Override
	@Nonnull
	public EnumFacing[] getValidRotations(World world, @Nonnull BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		EnumFacing[] valid = new EnumFacing[6];

		if (tile instanceof ITileRotatable) {
			ITileRotatable tileRotatable = (ITileRotatable) tile;

			for (EnumFacing facing : EnumFacing.VALUES) {
				if (tileRotatable.canSetFacing(facing)) {
					valid[facing.ordinal()] = facing;
				}
			}
		}

		return valid;
	}

	@Override
	public boolean rotateBlock(World world, @Nonnull BlockPos pos, EnumFacing side) {
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof ITileRotatable) {
			ITileRotatable tileRotatable = (ITileRotatable) tile;

			if (tileRotatable.canSetFacing(side)) {
				tileRotatable.setFacing(side);
			}

			return true;
		}

		return false;
	}
}
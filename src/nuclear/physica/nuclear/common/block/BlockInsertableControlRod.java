package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.api.core.abstraction.Face;
import physica.api.core.misc.IRotatable;
import physica.library.block.BlockBaseContainerModelled;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileInsertableControlRod;

public class BlockInsertableControlRod extends BlockBaseContainerModelled {

	public BlockInsertableControlRod() {
		super(Material.iron);
		setHardness(10);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "insertableControlRod");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileInsertableControlRod();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		float minX = 0.25f;
		float minY = 0;
		float minZ = 0.25f;
		float maxX = 0.75f;
		float maxY = 1;
		float maxZ = 0.75f;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileInsertableControlRod)
		{
			TileInsertableControlRod controlRod = (TileInsertableControlRod) tile;
			Face dir = controlRod.getFacing().getOpposite();
			if (dir == Face.DOWN)
			{
				maxY = (float) (0.25f + (100 - controlRod.getInsertion()) / 120.0);
			} else
			{
				minY = 1 - (float) (0.25f + (100 - controlRod.getInsertion()) / 120.0);
				maxY = 1;
			}
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item)
	{
		IRotatable tile = (IRotatable) world.getTileEntity(x, y, z);
		for (Face dir : Face.VALID)
		{
			if (dir.ordinal() <= 1)
			{
				if (world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) instanceof TileFissionReactor)
				{
					if (!(world.getTileEntity(x + dir.offsetX, y + dir.offsetY * 2, z + dir.offsetZ) instanceof TileInsertableControlRod))
					{
						tile.setFacing(dir.getOpposite());
					}
				}
			} else
			{
				break;
			}
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		Face dir = Face.getOrientation(side).getOpposite();
		return side <= 1 && super.canPlaceBlockOnSide(world, x, y, z, side) && world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) instanceof TileFissionReactor
				&& !(world.getTileEntity(x + dir.offsetX, y + dir.offsetY * 2, z + dir.offsetZ) instanceof TileInsertableControlRod);
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "SMS", "IAI", "SSS", 'I', NuclearBlockRegister.blockControlRod, 'S', "plateSteel", 'A', "circuitElite", 'M', "motor");
	}

	@Override
	public boolean canRotate(int ordinal)
	{
		return false;
	}

	@Override
	public String getSide()
	{
		return "Nuclear";
	}
}

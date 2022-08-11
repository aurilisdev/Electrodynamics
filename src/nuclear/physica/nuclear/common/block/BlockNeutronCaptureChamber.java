package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.api.core.abstraction.Face;
import physica.api.core.misc.IRotatable;
import physica.core.common.CoreItemRegister;
import physica.library.block.BlockBaseContainerModelled;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;

public class BlockNeutronCaptureChamber extends BlockBaseContainerModelled {

	public BlockNeutronCaptureChamber() {
		super(Material.iron);
		setHardness(5);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "neutronCaptureChamber");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float minX = 0;
		float minY = 0;
		float minZ = 0;
		float maxX = 1;
		float maxY = 1;
		float maxZ = 1;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileNeutronCaptureChamber) {
			TileNeutronCaptureChamber neutronChamber = (TileNeutronCaptureChamber) tile;
			minY = 0.05f;
			maxY = 0.95f;
			Face dir = neutronChamber.getFacing().getOpposite();
			switch (dir) {
			case EAST:
				minX = 0.8f;
				maxX = 1.1f;
				minZ = 0.265f;
				maxZ = 1 - 0.265f;
				break;
			case NORTH:
				minZ = 0.8f - 1;
				maxZ = 1 - 0.8f;
				minX = 0.265f;
				maxX = 1 - 0.265f;
				break;
			case SOUTH:
				minZ = 0.8f;
				maxZ = 1.1f;
				minX = 0.265f;
				maxX = 1 - 0.265f;
				break;
			case WEST:
				minX = 0.8f - 1;
				maxX = 1 - 0.8f;
				minZ = 0.265f;
				maxZ = 1 - 0.265f;
				break;
			default:
				break;

			}
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
		IRotatable tile = (IRotatable) world.getTileEntity(x, y, z);
		for (Face dir : Face.VALID) {
			if (dir.ordinal() > 1) {
				if (world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) instanceof TileFissionReactor) {
					tile.setFacing(dir.getOpposite());
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		Face dir = Face.getOrientation(side).getOpposite();
		return side > 1 && super.canPlaceBlockOnSide(world, x, y, z, side) && world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) instanceof TileFissionReactor;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileNeutronCaptureChamber();
	}

	@Override
	public void registerRecipes() {
		addRecipe(this, "SSS", "SGC", "SSS", 'S', "plateSteel", 'G', new ItemStack(Blocks.glass), 'C', CoreItemRegister.itemEmptyCell);
	}

	@Override
	public String getSide() {
		return "Nuclear";
	}

}

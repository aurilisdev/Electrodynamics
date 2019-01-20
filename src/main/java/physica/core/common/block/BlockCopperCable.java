package physica.core.common.block;

import cofh.api.energy.IEnergyConnection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.api.core.IBaseUtilities;
import physica.core.client.render.tile.TileRenderCopperCable;
import physica.core.common.CoreBlockRegister;
import physica.core.common.CoreTabRegister;
import physica.core.common.tile.TileCopperCable;
import physica.library.recipe.IRecipeRegister;
import physica.library.recipe.RecipeSide;

public class BlockCopperCable extends Block implements ITileEntityProvider, IBaseUtilities, IRecipeRegister {

	public BlockCopperCable() {
		super(Material.iron);
		setHardness(3.5F);
		setResistance(20);
		setLightLevel(0.1f);
		setHarvestLevel("pickaxe", 2);
		setBlockName(CoreReferences.PREFIX + "copperCable");
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockBounds(TileRenderCopperCable.pixelElevenTwo, TileRenderCopperCable.pixelElevenTwo, TileRenderCopperCable.pixelElevenTwo, 1 - TileRenderCopperCable.pixelElevenTwo,
				1 - TileRenderCopperCable.pixelElevenTwo, 1 - TileRenderCopperCable.pixelElevenTwo);
		addToRegister(RecipeSide.Core, this);
	}

	@Override
	public void initialize() {
		addRecipe(new ItemStack(CoreBlockRegister.blockCable, 6), "SIS", "SIS", "SIS", 'S', "ingotSteel", 'I', "ingotCopper");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float tempMinX = TileRenderCopperCable.pixelElevenTwo;
		float tempMinY = TileRenderCopperCable.pixelElevenTwo;
		float tempMinZ = TileRenderCopperCable.pixelElevenTwo;
		float tempMaxX = 1 - TileRenderCopperCable.pixelElevenTwo;
		float tempMaxY = 1 - TileRenderCopperCable.pixelElevenTwo;
		float tempMaxZ = 1 - TileRenderCopperCable.pixelElevenTwo;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity sideTile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (sideTile instanceof IEnergyConnection && ((IEnergyConnection) sideTile).canConnectEnergy(dir.getOpposite())) {
				switch (dir) {
				case DOWN:
					tempMinY -= TileRenderCopperCable.pixelElevenTwo;
					break;
				case EAST:
					tempMaxX += TileRenderCopperCable.pixelElevenTwo;
					break;
				case NORTH:
					tempMinZ -= TileRenderCopperCable.pixelElevenTwo;
					break;
				case SOUTH:
					tempMaxZ += TileRenderCopperCable.pixelElevenTwo;
					break;
				case UP:
					tempMaxY += TileRenderCopperCable.pixelElevenTwo;
					break;
				case WEST:
					tempMinX -= TileRenderCopperCable.pixelElevenTwo;
					break;
				default:
					break;
				}
			}
		}
		setBlockBounds(tempMinX, tempMinY, tempMinZ, tempMaxX, tempMaxY, tempMaxZ);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = Blocks.iron_block.getIcon(0, 0);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileCopperCable();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

}

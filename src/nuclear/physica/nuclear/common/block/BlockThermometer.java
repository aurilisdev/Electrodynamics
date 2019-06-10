package physica.nuclear.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.IBaseUtilities;
import physica.library.recipe.IRecipeRegister;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileFissionReactor;

public class BlockThermometer extends Block implements IBaseUtilities, IRecipeRegister {

	@SideOnly(Side.CLIENT)
	private static IIcon iconTop, iconGlass;

	public BlockThermometer() {
		super(Material.iron);
		setHardness(3.5F);
		setResistance(20);
		setHarvestLevel("pickaxe", 2);
		setBlockTextureName(CoreReferences.PREFIX + "thermometer");
		setBlockName(NuclearReferences.PREFIX + "thermometer");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setTickRandomly(true);
		addToRegister(RecipeSide.Nuclear, this);
	}

	@Override
	public void initialize() {
		addRecipe(this, "SSS", "SWS", "SSS", 'S', "ingotSteel", 'W', "circuitAdvanced");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		TileEntity tile = world.getTileEntity(x, y + 1, z);
		if (!world.isRemote) {
			if (player.isSneaking()) {
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlockMetadataWithNotify(x, y, z, meta = meta == 0 ? 1 : meta == 1 ? 2 : meta == 2 ? 3 : meta == 3 ? 4 : 0, 2);
				String temp = meta == 0 ? "4500" : meta == 1 ? "4000" : meta == 2 ? "3500" : meta == 3 ? "3000" : "2500";
				player.addChatMessage(new ChatComponentText("Signal at: " + temp + ".0C"));
			} else if (tile instanceof TileFissionReactor) {
				player.addChatMessage(new ChatComponentText("Heat: " + IBaseUtilities.roundPreciseStatic((double) ((TileFissionReactor) tile).getTemperature(), 2) + "C"));
			} else {
				player.addChatMessage(new ChatComponentText("Heat: 14.0C"));
			}
		}
		return true;
	}

	@Override
	public int tickRate(World world) {
		return 1;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		world.notifyBlocksOfNeighborChange(x, y, z, this);
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		TileEntity tile = world.getTileEntity(x, y + 1, z);
		if (tile instanceof TileFissionReactor) {
			world.notifyBlocksOfNeighborChange(x, y, z, this);
		}
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return isProvidingStrongPower(world, x, y, z, side);
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y + 1, z);
		if (tile instanceof TileFissionReactor) {
			int meta = world.getBlockMetadata(x, y, z);
			if (((TileFissionReactor) tile).getTemperature() > (meta == 0 ? 4500 : meta == 1 ? 4000 : meta == 2 ? 3500 : meta == 3 ? 3000 : 2500) - 25) {
				return 15;
			}
		}
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		iconTop = iconRegister.registerIcon(CoreReferences.PREFIX + "thermometerTop");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 || side == 1 ? iconTop : super.getIcon(side, metadata);
	}
}

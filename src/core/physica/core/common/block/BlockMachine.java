package physica.core.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.Face;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.tile.IMachineTile;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.CoreTabRegister;
import physica.library.block.BlockBaseContainer;
import physica.library.tile.TileBaseRotateable;

public abstract class BlockMachine extends BlockBaseContainer implements IBaseUtilities, IRecipeRegister {
	@SideOnly(Side.CLIENT)
	private IIcon						iconFacing;
	@SideOnly(Side.CLIENT)
	private IIcon						iconFacingRunning;
	@SideOnly(Side.CLIENT)
	private IIcon						iconBottom;
	@SideOnly(Side.CLIENT)
	private IIcon						iconTop;
	private Class<? extends TileEntity>	tileClazz;

	public BlockMachine(String name, Class<? extends TileBaseRotateable> tileClazz) {
		super(Material.iron);
		this.tileClazz = tileClazz;
		setHardness(1);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(CoreTabRegister.coreTab);
		setBlockName(CoreReferences.PREFIX + name);
		setBlockTextureName(CoreReferences.PREFIX_TEXTURE_MACHINE + name);
		addToRegister("Core", this);
	}

	public boolean hazard;

	public BlockMachine setHazard(boolean hazard)
	{
		this.hazard = hazard;
		return this;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		if (hazard)
		{
			blockIcon = reg.registerIcon(CoreReferences.PREFIX_TEXTURE_MACHINE + "machineSideHazmat");
			iconTop = reg.registerIcon(CoreReferences.PREFIX_TEXTURE_MACHINE + "machineSide");
			iconBottom = reg.registerIcon(CoreReferences.PREFIX_TEXTURE_MACHINE + "machineBottomHazmat");
			iconFacing = reg.registerIcon(getTextureName() + "Facing");
			iconFacingRunning = reg.registerIcon(getTextureName() + "FacingRunning");
		} else
		{
			blockIcon = iconTop = iconBottom = reg.registerIcon(CoreReferences.PREFIX_TEXTURE_MACHINE + "machineSide");
			iconFacing = reg.registerIcon(getTextureName() + "Facing");
			iconFacingRunning = reg.registerIcon(getTextureName() + "FacingRunning");
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IMachineTile)
		{
			return ((IMachineTile) tile).isRunning() ? Blocks.lava.getLightValue() : 0;
		}
		return 0;
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		TileEntity tile = access.getTileEntity(x, y, z);
		if (tileClazz.isInstance(tile) && tile instanceof IMachineTile)
		{
			Face facing = ((TileBaseRotateable) tile).getFacing();
			if (side == facing.ordinal())
			{
				return ((IMachineTile) tile).isRunning() ? iconFacingRunning : iconFacing;
			} else if (side == Face.DOWN.ordinal())
			{
				return iconBottom;
			} else if (side == Face.UP.ordinal())
			{
				return iconTop;
			}
		}
		return blockIcon;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 4)
		{
			return iconFacing;
		} else if (side == 0)
		{
			return iconBottom;
		} else if (side == 1)
		{
			return iconTop;
		}
		return blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		try
		{
			return tileClazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}

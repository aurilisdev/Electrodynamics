package physica.nuclear.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;
import physica.core.common.CoreBlockRegister;
import physica.library.block.BlockBaseContainer;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileReactorControlPanel;

public class BlockReactorControlPanel extends BlockBaseContainer implements IBaseUtilities, IRecipeRegister {

	@SideOnly(Side.CLIENT)
	protected IIcon blockIconOff;

	public BlockReactorControlPanel() {
		super(Material.iron);
		setHardness(10);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "reactorControlPanel");
		setBlockTextureName(NuclearReferences.PREFIX + "reactorcontrolpanel");
		addToRegister("Nuclear", this);
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		blockIcon = register.registerIcon(getTextureName());
		blockIconOff = register.registerIcon(getTextureName() + "off");
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileReactorControlPanel)
		{
			TileReactorControlPanel control = (TileReactorControlPanel) tile;
			if (control.getFacing().ordinal() == side)
			{
				return control.reactor == null ? blockIconOff : blockIcon;
			}
		}
		return CoreBlockRegister.blockLead.getIcon(side, 0);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 4)
		{
			return super.getIcon(side, meta);
		}
		return CoreBlockRegister.blockLead.getIcon(side, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileReactorControlPanel();
	}

	@Override
	public void registerRecipes()
	{
		addRecipe(this, "IPI", "PCP", "IPI", 'I', CoreBlockRegister.blockLead, 'P', "plateLead", 'C', "circuitElite");
	}

}

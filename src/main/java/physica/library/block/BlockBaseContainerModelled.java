package physica.library.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import physica.CoreReferences;
import physica.api.core.abstraction.recipe.IRecipeRegister;
import physica.api.core.utilities.IBaseUtilities;

public abstract class BlockBaseContainerModelled extends BlockBaseContainer implements IBaseUtilities, IRecipeRegister {

	protected BlockBaseContainerModelled(Material material) {
		super(material);
		addToRegister(getSide(), this);
	}

	public abstract String getSide();

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = reg.registerIcon(CoreReferences.PREFIX + "siren");
	}

	@Override
	public final boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public final int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public final boolean isNormalCube()
	{
		return false;
	}
}

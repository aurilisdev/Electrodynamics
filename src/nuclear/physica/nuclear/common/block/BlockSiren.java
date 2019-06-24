package physica.nuclear.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.IBaseUtilities;
import physica.library.recipe.IRecipeRegister;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileSiren;

public class BlockSiren extends Block implements IBaseUtilities, IRecipeRegister {

	public BlockSiren() {
		super(Material.iron);
		setHardness(3.5F);
		setResistance(20);
		setHarvestLevel("pickaxe", 2);
		setBlockTextureName(CoreReferences.PREFIX + "siren");
		setBlockName(NuclearReferences.PREFIX + "siren");
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		addToRegister(RecipeSide.Nuclear, this);
	}

	@Override
	public void initialize()
	{
		addRecipe(this, "SNS", "NAN", "SNS", 'S', "plateSteel", 'N', Blocks.noteblock, 'A', "circuitBasic");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit)
	{
		if (!world.isRemote) {
			int pitch = world.getBlockMetadata(x, y, z);
			if (player.isSneaking()) {
				pitch--;
			} else {
				pitch++;
			}
			pitch = Math.max(pitch % 16, 0);
			player.addChatMessage(new ChatComponentText("Pitch: " + pitch));
			world.setBlockMetadataWithNotify(x, y, z, pitch, 2);
		}
		return true;
	}

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileSiren();
	}
}

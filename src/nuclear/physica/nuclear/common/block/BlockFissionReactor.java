package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import physica.core.common.CoreItemRegister;
import physica.library.block.BlockBaseContainerModelled;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileFissionReactor;

public class BlockFissionReactor extends BlockBaseContainerModelled {

	public BlockFissionReactor() {
		super(Material.iron);
		setHardness(1);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "fissionReactor");
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.90F, 1F, 0.90F);
	}

	@Override
	public void initialize()
	{
		addRecipe(this, "PEP", "MCM", "PEP", 'M', "motor", 'P', "plateSteel", 'C',
				"circuitAdvanced", 'C', CoreItemRegister.itemEmptyCell, 'E', "circuitElite");
	}

	@Override
	public RecipeSide getSide()
	{
		return RecipeSide.Nuclear;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit)
	{
		if (player.getHeldItem() != null
				&& (player.getHeldItem().getItem() == NuclearItemRegister.itemHighEnrichedFuelCell || player.getHeldItem().getItem() == NuclearItemRegister.itemLowEnrichedFuelCell))
		{
			if (player.getHeldItem().stackSize == 1)
			{
				if (world.getTileEntity(x, y, z) instanceof TileFissionReactor)
				{
					TileFissionReactor reactor = (TileFissionReactor) world.getTileEntity(x, y, z);
					if (!reactor.hasFuelRod())
					{
						reactor.setInventorySlotContents(TileFissionReactor.SLOT_INPUT, player.getHeldItem());
						player.inventory.mainInventory[player.inventory.currentItem] = null;
						return true;
					}
				}
			}
		} else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Item.getItemFromBlock(NuclearBlockRegister.blockNeutronCaptureChamber))
		{
			return false;
		} else if (player.getHeldItem() == null && player.isSneaking())
		{

			if (world.getTileEntity(x, y, z) instanceof TileFissionReactor)
			{
				TileFissionReactor reactor = (TileFissionReactor) world.getTileEntity(x, y, z);
				if (reactor.hasFuelRod())
				{
					player.inventory.mainInventory[player.inventory.currentItem] = reactor.getStackInSlot(TileFissionReactor.SLOT_INPUT);
					reactor.setInventorySlotContents(TileFissionReactor.SLOT_INPUT, null);
					return true;
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileFissionReactor)
		{
			TileFissionReactor reactor = (TileFissionReactor) tile;
			if (reactor.hasFuelRod())
			{
				reactor.performMeltdown();
			}
		}
		super.onBlockExploded(world, x, y, z, explosion);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileFissionReactor();
	}
}

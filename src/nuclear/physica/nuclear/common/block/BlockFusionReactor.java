package physica.nuclear.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.library.block.BlockBaseContainerModelled;
import physica.library.recipe.RecipeSide;
import physica.nuclear.NuclearReferences;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearTabRegister;
import physica.nuclear.common.tile.TileFusionReactor;

public class BlockFusionReactor extends BlockBaseContainerModelled {

	public BlockFusionReactor() {
		super(Material.iron);
		setHardness(15);
		setResistance(5);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setBlockName(NuclearReferences.PREFIX + "fusionReactor");
	}

	@Override
	public void initialize()
	{
		addRecipe(this, "ECE", "CFC", "ECE", 'C', new ItemStack(NuclearBlockRegister.blockElectromagnet, 1, BlockElectromagnet.EnumElectromagnet.CONTAINMENT_NORMAL.ordinal()), 'F', NuclearBlockRegister.blockFissionReactor, 'E',
				"circuitElite");
	}

	@Override
	public RecipeSide getSide()
	{
		return RecipeSide.Nuclear;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit)
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileFusionReactor)
			{
				TileFusionReactor reactor = (TileFusionReactor) tile;
				if (reactor.canInsertItem(TileFusionReactor.SLOT_DEUTERIUM, player.getCurrentEquippedItem(), side))
				{
					if (reactor.getStackInSlot(TileFusionReactor.SLOT_DEUTERIUM) != null)
					{
						reactor.getStackInSlot(TileFusionReactor.SLOT_DEUTERIUM).stackSize += player.getCurrentEquippedItem().stackSize;
					} else
					{
						reactor.setInventorySlotContents(TileFusionReactor.SLOT_DEUTERIUM, player.getCurrentEquippedItem());
					}
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					for (int i = 0; i < player.inventory.mainInventory.length; i++)
					{
						ItemStack stack = player.inventory.mainInventory[i];
						if (reactor.canInsertItem(TileFusionReactor.SLOT_DEUTERIUM, stack, side))
						{
							reactor.getStackInSlot(TileFusionReactor.SLOT_DEUTERIUM).stackSize += stack.stackSize;
							player.inventory.setInventorySlotContents(i, null);
						}
					}
					player.inventoryContainer.detectAndSendChanges();
				} else if (reactor.canInsertItem(TileFusionReactor.SLOT_TRITIUM, player.getCurrentEquippedItem(), side))
				{
					if (reactor.getStackInSlot(TileFusionReactor.SLOT_TRITIUM) != null)
					{
						reactor.getStackInSlot(TileFusionReactor.SLOT_TRITIUM).stackSize += player.getCurrentEquippedItem().stackSize;
					} else
					{
						reactor.setInventorySlotContents(TileFusionReactor.SLOT_TRITIUM, player.getCurrentEquippedItem());
					}
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					for (int i = 0; i < player.inventory.mainInventory.length; i++)
					{
						ItemStack stack = player.inventory.mainInventory[i];
						if (reactor.canInsertItem(TileFusionReactor.SLOT_TRITIUM, stack, side))
						{
							reactor.getStackInSlot(TileFusionReactor.SLOT_TRITIUM).stackSize += stack.stackSize;
							player.inventory.setInventorySlotContents(i, null);
						}
					}
					player.inventoryContainer.detectAndSendChanges();
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileFusionReactor();
	}
}

package physica.api.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.Physica;
import physica.api.base.IGuiInterface;
import physica.proxy.sided.CommonProxy;

public abstract class BlockBaseContainer extends BlockRotatable {

	protected BlockBaseContainer(Material material) { // TODO: Perhaps change
														// all of the machine
														// blocks into metadata
														// blocks to compactify
														// the block id's?
		super(material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {

		if (!world.isRemote)
		{
			if (world.getTileEntity(x, y, z) instanceof IGuiInterface)
			{
				player.openGui(getModInstance(), CommonProxy.TILE_GUI_ID, world, x, y, z);
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, xHit, yHit, zHit);
	}

	public Object getModInstance() {
		return Physica.INSTANCE;
	}

	@Override
	public void breakBlock(World worldObj, int x, int y, int z, Block block, int meta) {
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		if (tile instanceof IInventory)
		{
			IInventory inv = (IInventory) tile;
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null)
				{
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, x + 0.5, y + 0.5, z + 0.5, stack));
					inv.setInventorySlotContents(i, null);
				}
			}
		}
		super.breakBlock(worldObj, x, y, z, block, meta);
	}
}

package physica.library.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import physica.Physica;
import physica.api.core.abstraction.Face;
import physica.api.core.inventory.IGuiInterface;
import physica.api.core.misc.IRotatable;
import physica.core.common.event.WrenchEventHandler;
import physica.proxy.CommonProxy;

public abstract class BlockBaseContainer extends BlockRotatable {

	protected BlockBaseContainer(Material material) {
		super(material);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		TileEntity tile = world.getTileEntity(x, y, z);
		ItemStack item = player.getCurrentEquippedItem();
		if (WrenchEventHandler.canWrench(item, x, y, z, player)) {
			if (tile instanceof IRotatable) {
				((IRotatable) tile).setFacing(Face.VALID[determineOrientation(world, x, y, z, player)]);
			}
		} else if (!world.isRemote) {
			if (tile instanceof IGuiInterface) {
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
		if (tile instanceof IInventory) {
			IInventory inv = (IInventory) tile;
			for (int index = 0; index < inv.getSizeInventory(); ++index) {
				ItemStack itemstack = inv.getStackInSlot(index);

				if (itemstack != null) {
					float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = worldObj.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; worldObj.spawnEntityInWorld(entityitem)) {
						int remove = worldObj.rand.nextInt(21) + 10;
						if (remove > itemstack.stackSize) {
							remove = itemstack.stackSize;
						}
						itemstack.stackSize -= remove;
						entityitem = new EntityItem(worldObj, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), remove, itemstack.getItemDamage()));
						entityitem.motionX = (float) worldObj.rand.nextGaussian() * 0.05F;
						entityitem.motionY = (float) worldObj.rand.nextGaussian() * 0.05F + 0.2F;
						entityitem.motionZ = (float) worldObj.rand.nextGaussian() * 0.05F;
						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}
		}
		super.breakBlock(worldObj, x, y, z, block, meta);
	}

	public boolean canWrench(World worldObj, int x, int y, int z) {
		return true;
	}
}

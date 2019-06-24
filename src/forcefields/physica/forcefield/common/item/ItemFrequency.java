package physica.forcefield.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import physica.api.forcefield.IInvFortronTile;
import physica.forcefield.common.ForcefieldBlockRegister;
import physica.forcefield.common.ForcefieldTabRegister;
import physica.library.item.ItemInformationHolder;

public class ItemFrequency extends ItemInformationHolder {

	public ItemFrequency(String name) {
		super(name);
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote) {
			Vec3 posVec = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
			Vec3 lookVec = player.getLookVec();
			MovingObjectPosition mop = world.rayTraceBlocks(posVec, lookVec);
			Block blockHit = mop == null ? null : world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if (blockHit != ForcefieldBlockRegister.blockCoercionDriver && blockHit != ForcefieldBlockRegister.blockFortronCapacitor && blockHit != ForcefieldBlockRegister.blockFortronConstructor
					&& blockHit != ForcefieldBlockRegister.blockInterdictionMatrix) {
				if (!stack.hasTagCompound()) {
					stack.setTagCompound(stack.writeToNBT(new NBTTagCompound()));
				}
				if (stack.getTagCompound().hasKey("frequency")) {
					int frequency = stack.getTagCompound().getInteger("frequency");
					if (player.isSneaking()) {
						frequency--;
						if (frequency < 0) {
							frequency = 20;
						}
					} else {
						frequency++;
						if (frequency > 20) {
							frequency = 0;
						}
					}
					stack.getTagCompound().setInteger("frequency", frequency);
				} else {
					stack.getTagCompound().setInteger("frequency", 0);
				}
				player.addChatMessage(new ChatComponentText("Frequency: " + stack.getTagCompound().getInteger("frequency")));
			}
		}
		return super.onItemRightClick(stack, world, player);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("frequency")) {
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile != null && tile instanceof IInvFortronTile) {
					((IInvFortronTile) tile).setFrequency(stack.getTagCompound().getInteger("frequency"));
					if (!world.isRemote) {
						player.addChatMessage(new ChatComponentText("Set tile frequency: " + stack.getTagCompound().getInteger("frequency")));
					}
				}
			}
		}
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List infoList, boolean par4)
	{
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("frequency")) {
				infoList.add("Frequency: " + stack.getTagCompound().getInteger("frequency"));
			}
		}
	}
}

package physica.library.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.PhysicaAPI;
import physica.api.core.abstraction.Face;
import physica.api.core.misc.IRotatable;

public abstract class BlockRotatable extends BlockContainer {

	protected byte		rotationMask	= Byte.parseByte("111100", 2);
	protected boolean	isFlipPlacement	= false;

	protected BlockRotatable(Material material) {
		super(material);
	}

	public boolean canRotate(int ordinal)
	{
		return (rotationMask & 1 << ordinal) != 0;
	}

	public int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity)
	{
		if (MathHelper.abs((float) entity.posX - x) < 2 && MathHelper.abs((float) entity.posZ - z) < 2)
		{
			double d0 = entity.posY + 1.82D - entity.yOffset;

			if (canRotate(1) && d0 - y > 2)
			{
				return 1;
			}

			if (canRotate(0) && y - d0 > 0)
			{
				return 0;
			}
		}

		int playerSide = MathHelper.floor_double(entity.rotationYaw * 4 / 360 + 0.5) & 0x3;
		int returnSide = playerSide == 3 && canRotate(4) ? 4 : playerSide == 2 && canRotate(3) ? 3 : playerSide == 1 && canRotate(5) ? 5 : playerSide == 0 && canRotate(2) ? 2 : 0;

		if (isFlipPlacement)
		{
			return Face.getOrientation(returnSide).getOpposite().ordinal();
		}

		return returnSide;
	}

	@Override
	public ForgeDirection[] getValidRotations(World world, int x, int y, int z)
	{
		return ForgeDirection.VALID_DIRECTIONS;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit)
	{
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof IRotatable)
		{
			if (PhysicaAPI.isDebugMode)
			{
				if (!world.isRemote && player.canCommandSenderUseCommand(2, ""))
				{
					player.addChatComponentMessage(new ChatComponentText("Server: " + !world.isRemote + ", Rotation: " + ((IRotatable) tile).getFacing()));
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item)
	{
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof IRotatable)
		{
			IRotatable tileRotatable = (IRotatable) tile;

			tileRotatable.setFacing(Face.VALID[determineOrientation(world, x, y, z, entity)]);
		}
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection side)
	{
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof IRotatable)
		{
			IRotatable tileRotatable = (IRotatable) tile;

			tileRotatable.setFacing(Face.Parse(side));

			return true;
		}

		return false;
	}
}

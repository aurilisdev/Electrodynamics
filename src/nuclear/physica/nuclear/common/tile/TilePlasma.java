package physica.nuclear.common.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.nuclear.IElectromagnet;
import physica.library.tile.TileBase;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.effect.damage.DamageSourcePlasma;

public class TilePlasma extends TileBase {

	public static boolean canPlace(Block block, World world, int x, int y, int z)
	{
		if (block == NuclearBlockRegister.blockFusionReactor)
		{
			return false;
		}
		return block.getBlockHardness(world, x, y, z) != -1 && !(block instanceof IElectromagnet);
	}

	public int				strength			= ConfigNuclearPhysics.PLASMA_STRENGTH;

	public static final int	TARGET_TEMPERATURE	= 4407;

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);
		if (ticks == 20)
		{
			if (isServer())
			{
				if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(worldObj.getWorldInfo().getWorldName().toLowerCase()))
				{
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
					return;
				}

				int power = Math.max(strength - 1, 0);

				if (power <= 0)
				{
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.fire);
					return;
				}
				float directions = 1;
				for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
				{
					if (worldObj.rand.nextFloat() < directions)
					{
						Block block = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
						if (canPlace(block, worldObj, xCoord, yCoord, zCoord))
						{
							directions -= 1 / 6f;
							worldObj.setBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ, NuclearBlockRegister.blockPlasma, 0, 3);

							TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
							if (tile instanceof TilePlasma)
							{
								int newPower = power + worldObj.rand.nextInt(2) - 1;
								((TilePlasma) tile).strength = (int) (newPower / Math.max(1, block.getBlockHardness(worldObj, xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ)));
							}
						}
					}
				}
			}
		} else if (ticks > 20)
		{
			if (ticks > strength * 5 && worldObj.rand.nextFloat() < 0.333f)
			{
				worldObj.setBlock(xCoord, yCoord, zCoord, worldObj.rand.nextFloat() < 0.025f ? Blocks.fire : Blocks.air);
			}
		}

		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
		@SuppressWarnings("unchecked")
		List<Entity> entitiesNearby = worldObj.getEntitiesWithinAABB(Entity.class, bounds);
		for (Entity entity : entitiesNearby)
		{
			entity.attackEntityFrom(DamageSourcePlasma.INSTANCE, (float) (10 / entity.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5)));
		}
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			if (direction.ordinal() > 0)
			{
				if (worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) == NuclearBlockRegister.blockElectromagnet)
				{
					Block blockAboveMagnet = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY + 1, zCoord + direction.offsetZ);
					if (blockAboveMagnet == Blocks.water)
					{
						TileEntity tileAboveWater = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY + 2, zCoord + direction.offsetZ);
						if (tileAboveWater instanceof TileTurbine)
						{
							TileTurbine turbine = (TileTurbine) tileAboveWater;
							float temperature = (float) (TARGET_TEMPERATURE * 1.25f / (TileFusionReactor.PLASMA_SPAWN_STRENGTH * 0.15226));
							int steam = (int) temperature * (worldObj.getBlockMetadata(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) > 1 ? 100 : 5);
							if (steam > 0)
							{
								turbine.addSteam(steam);
							}
						}
					}
				}
			}
		}
	}
}

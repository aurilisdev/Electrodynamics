package physica.nuclear.common.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import physica.library.tile.TileBase;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.radiation.RadiationSystem;

public class TileMeltedReactor extends TileBase {

	public static final float RADIATION_RADIUS = 20;
	public static final float RADIATION_PARTICLES = 10;
	public int radiation = 8766000;
	public int heatTime = 6000;

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		if (ticks % 10 == 0) {
			if (worldObj.getBlock(xCoord, yCoord - 1, zCoord).getMaterial().isReplaceable()) {
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				worldObj.setBlock(xCoord, yCoord - 1, zCoord, NuclearBlockRegister.blockMeltedReactor);
				return;
			}
		}
		if (heatTime > 0) {
			heatTime--;
			double x2 = xCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
			double y2 = yCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
			double z2 = zCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
			double d3 = xCoord - x2;
			double d4 = yCoord - y2;
			double d5 = zCoord - z2;
			double distance = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
			if (worldObj.rand.nextDouble() > distance / RADIATION_RADIUS) {
				int x = (int) Math.floor(x2);
				int y = (int) Math.floor(y2);
				int z = (int) Math.floor(z2);
				Block block = worldObj.getBlock(x, y, z);
				if (block.getMaterial() == Material.air) {
					if (worldObj.getBlock(x, y - 1, z).getMaterial() != Material.air) {
						worldObj.setBlock(x, y, z, Blocks.fire);
					}
				} else if (block == Blocks.cobblestone || block == Blocks.stone) {
					worldObj.setBlock(x, y, z, Blocks.lava);
				} else if (block == Blocks.water || block == Blocks.flowing_water) {
					worldObj.setBlockToAir(x, y, z);
				} else if (block == Blocks.sand) {
					worldObj.setBlock(x, y, z, Blocks.glass);
				}
			}
		}
		if (radiation > 0) {
			@SuppressWarnings("unchecked")
			List<EntityLiving> entities = worldObj.getEntitiesWithinAABB(Entity.class,
					AxisAlignedBB.getBoundingBox(xCoord - RADIATION_RADIUS, yCoord - RADIATION_RADIUS, zCoord - RADIATION_RADIUS, xCoord + RADIATION_RADIUS, yCoord + RADIATION_RADIUS,
							zCoord + RADIATION_RADIUS));
			for (Entity entity : entities) {
				if (entity instanceof EntityLivingBase) {
					double scale = RADIATION_RADIUS - entity.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
					RadiationSystem.applyRontgenEntity((EntityLivingBase) entity, (float) scale / 2f, (float) scale * 2f, (float) entity.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5),
							RADIATION_RADIUS);
				}
			}
			radiation--;
			double x2 = xCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double y2 = yCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double z2 = zCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double d3 = xCoord - x2;
			double d4 = yCoord - y2;
			double d5 = zCoord - z2;
			double distance = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
			if (worldObj.rand.nextDouble() > distance / RADIATION_RADIUS) {
				int x = (int) Math.floor(x2);
				int y = (int) Math.floor(y2);
				int z = (int) Math.floor(z2);
				Block block = worldObj.getBlock(x, y, z);
				if (block == Blocks.grass) {
					worldObj.setBlock(x, y, z, NuclearBlockRegister.blockRadioactiveGrass);
				}
			}
		}
	}

	@Override
	public void updateClient(int ticks) {
		super.updateClient(ticks);
		int i = 0;
		while (true) {
			double x2 = xCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double y2 = yCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double z2 = zCoord + 0.5 + (worldObj.rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double d3 = xCoord - x2;
			double d4 = yCoord - y2;
			double d5 = zCoord - z2;
			double distance = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
			if (worldObj.rand.nextDouble() > distance / RADIATION_RADIUS) {
				worldObj.spawnParticle("reddust", x2, y2, z2, 0.01f, 1, 0.01f);
				i++;
			}
			if (i > RADIATION_RADIUS) {
				break;
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("radiation", radiation);
		tag.setInteger("heatTime", heatTime);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		radiation = tag.getInteger("radiation");
		heatTime = tag.getInteger("heatTime");
	}
}

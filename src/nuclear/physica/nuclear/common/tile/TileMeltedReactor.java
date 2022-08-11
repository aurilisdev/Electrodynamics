package physica.nuclear.common.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import physica.library.location.GridLocation;
import physica.library.tile.TileBase;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.radiation.RadiationSystem;

public class TileMeltedReactor extends TileBase {

	public static final float RADIATION_RADIUS = 30;
	public static final float RADIATION_PARTICLES = 10;
	public static final float START_RADIATION = 8766000 * 5;
	public int radiation = (int) START_RADIATION;
	public int temperature = 6000;

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		GridLocation loc = getLocation();
		if (ticks % 10 == 0) {
			if (World().getBlock(loc.xCoord, loc.yCoord - 1, loc.zCoord).getMaterial().isReplaceable()) {
				World().setBlockToAir(loc.xCoord, loc.yCoord, loc.zCoord);
				World().setBlock(loc.xCoord, loc.yCoord - 1, loc.zCoord, NuclearBlockRegister.blockMeltedReactor);
				TileEntity entity = World().getTileEntity(loc.xCoord, loc.yCoord, loc.zCoord);
				if (entity instanceof TileMeltedReactor) {
					TileMeltedReactor tile = (TileMeltedReactor) entity;
					tile.radiation = radiation;
					tile.temperature = temperature;
				}
				return;
			}
		}
		if (temperature > 0) {
			temperature--;
			double x2 = loc.xCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
			double y2 = loc.yCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
			double z2 = loc.zCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
			double d3 = loc.xCoord - x2;
			double d4 = loc.yCoord - y2;
			double d5 = loc.zCoord - z2;
			double distance = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
			if (distance <= RADIATION_RADIUS) {
				if (World().rand.nextDouble() > distance / RADIATION_RADIUS) {
					int x = (int) Math.floor(x2);
					int y = (int) Math.floor(y2);
					int z = (int) Math.floor(z2);
					Block block = World().getBlock(x, y, z);
					if (block.getMaterial() == Material.air) {
						if (World().getBlock(x, y - 1, z).getMaterial() != Material.air) {
							World().setBlock(x, y, z, Blocks.fire);
						}
					} else if (block == Blocks.stone) {
						if (temperature < 2100) {
							World().setBlock(x, y, z, NuclearBlockRegister.blockRadioactiveStone, (int) Math.min(15, RADIATION_RADIUS - distance), 3);
						} else {
							World().setBlock(x, y, z, Blocks.cobblestone);
						}
					} else if (block == Blocks.cobblestone) {
						World().setBlock(x, y, z, Blocks.lava);
					} else if (block == Blocks.water || block == Blocks.flowing_water) {
						World().setBlockToAir(x, y, z);
					} else if (block == Blocks.sand) {
						World().setBlock(x, y, z, Blocks.glass);
					}
				}
			}
		}
		if (radiation > 0) {

			radiation--;
			double x2 = loc.xCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS * (radiation / START_RADIATION) * 2;
			double y2 = Math.min(255, Math.max(0, loc.yCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS * (radiation / START_RADIATION) * 2));
			double z2 = loc.zCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS * (radiation / START_RADIATION) * 2;
			double d3 = loc.xCoord - x2;
			double d4 = loc.yCoord - y2;
			double d5 = loc.zCoord - z2;
			double distance = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
			if (distance <= RADIATION_RADIUS) {
				if (World().rand.nextDouble() > distance / RADIATION_RADIUS) {
					int x = (int) Math.floor(x2);
					int y = (int) Math.floor(y2);
					int z = (int) Math.floor(z2);
					Block block = World().getBlock(x, y, z);
					if (block == Blocks.grass) {
						World().setBlock(x, y, z, NuclearBlockRegister.blockRadioactiveGrass, (int) Math.min(15, RADIATION_RADIUS - distance), 3);
					} else if (block == Blocks.dirt) {
						World().setBlock(x, y, z, NuclearBlockRegister.blockRadioactiveDirt, (int) Math.min(15, RADIATION_RADIUS - distance), 3);
					}
				}
			}
		}
	}

	@Override
	public void updateCommon(int ticks) {
		super.updateCommon(ticks);
		if (radiation > 0) {
			GridLocation loc = getLocation();
			List<EntityLivingBase> entities = World().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(loc.xCoord - RADIATION_RADIUS, loc.yCoord - RADIATION_RADIUS, loc.zCoord - RADIATION_RADIUS, loc.xCoord + RADIATION_RADIUS, loc.yCoord + RADIATION_RADIUS, loc.zCoord + RADIATION_RADIUS));
			for (EntityLivingBase entity : entities) {
				double scale = RADIATION_RADIUS - entity.getDistance(loc.xCoord + 0.5, loc.yCoord + 0.5, loc.zCoord + 0.5);
				RadiationSystem.applyRontgenEntity(entity, (float) (scale / 1.75f) * (radiation / START_RADIATION), (float) scale * 2f, (float) entity.getDistance(loc.xCoord + 0.5, loc.yCoord + 0.5, loc.zCoord + 0.5), RADIATION_RADIUS);
			}
		}
	}

	@Override
	public void updateClient(int ticks) {
		super.updateClient(ticks);

		int i = 0;
		GridLocation loc = getLocation();
		while (true) {
			double x2 = loc.xCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double y2 = loc.yCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double z2 = loc.zCoord + 0.5 + (World().rand.nextDouble() - 0.5) * RADIATION_RADIUS * 2;
			double d3 = loc.xCoord - x2;
			double d4 = loc.yCoord - y2;
			double d5 = loc.zCoord - z2;
			double distance = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
			if (World().rand.nextDouble() > distance / RADIATION_RADIUS) {
				World().spawnParticle("reddust", x2, y2, z2, 0.01f, 1, 0.01f);
				i++;
			}
			if (i > RADIATION_RADIUS) {
				break;
			}
		}
	}

	@Override
	public void writeSynchronizationPacket(List<Object> dataList, EntityPlayer player) {
		super.writeSynchronizationPacket(dataList, player);
		dataList.add(radiation);
	}

	@Override
	public void readSynchronizationPacket(ByteBuf buf, EntityPlayer player) {
		super.readSynchronizationPacket(buf, player);
		radiation = buf.readInt();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("radiation", radiation);
		tag.setInteger("heatTime", temperature);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		radiation = tag.getInteger("radiation");
		temperature = tag.getInteger("heatTime");
	}
}

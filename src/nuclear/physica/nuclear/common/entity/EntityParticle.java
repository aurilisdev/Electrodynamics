package physica.nuclear.common.entity;

import java.util.List;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.Physica;
import physica.api.nuclear.IElectromagnet;
import physica.library.energy.base.Measurement;
import physica.library.util.RotationUtility;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.effect.damage.DamageSourceRadiation;
import physica.nuclear.common.tile.TileParticleAccelerator;

public class EntityParticle extends Entity implements IEntityAdditionalSpawnData {

	private static int		movementTicketUpdateId	= 20;

	public Ticket			updateTicket;
	private boolean			didCollide;
	private int				hostLocationX, hostLocationY, hostLocationZ;
	private ForgeDirection	movementDirection		= ForgeDirection.NORTH;

	public EntityParticle(World world) {
		super(world);
		ignoreFrustumCheck = true;
		setSize(0.1F, 0.1F);
	}

	public EntityParticle(World world, int x, int y, int z, ForgeDirection movementDirection) {
		this(world);
		this.movementDirection = movementDirection;
		hostLocationX = x + this.movementDirection.getOpposite().offsetX;
		hostLocationY = y + this.movementDirection.getOpposite().offsetY;
		hostLocationZ = z + this.movementDirection.getOpposite().offsetZ;
		accelerateParticle(0.001f);
		setPosition(x + 0.5, y + 0.5, z + 0.5);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		return true;
	}

	protected void accelerateParticle(float acceleration)
	{
		double accelerationX = movementDirection.offsetX * acceleration;
		double accelerationY = movementDirection.offsetY * acceleration;
		double accelerationZ = movementDirection.offsetZ * acceleration;
		motionX = Math.min(motionX + accelerationX, ConfigNuclearPhysics.ANTIMATTER_CREATION_SPEED + 0.1);
		motionY = Math.min(motionY + accelerationY, ConfigNuclearPhysics.ANTIMATTER_CREATION_SPEED + 0.1);
		motionZ = Math.min(motionZ + accelerationZ, ConfigNuclearPhysics.ANTIMATTER_CREATION_SPEED + 0.1);
	}

	public static boolean canSpawnParticle(World world, int x, int y, int z)
	{
		if (world.isAirBlock(x, y, z))
		{
			int electromagnetCount = 0;
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			{
				if (isElectromagnet(world, x, y, z, side))
				{
					electromagnetCount++;
					if (electromagnetCount >= 4)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isElectromagnet(World world, int x, int y, int z, ForgeDirection facing)
	{
		return world.getBlock(x + facing.offsetX, y + facing.offsetY, z + facing.offsetZ) instanceof IElectromagnet;
	}

	@Override
	public void writeSpawnData(ByteBuf data)
	{
		data.writeInt(hostLocationX);
		data.writeInt(hostLocationY);
		data.writeInt(hostLocationZ);
		data.writeInt(movementDirection.ordinal());
	}

	@Override
	public void readSpawnData(ByteBuf data)
	{
		hostLocationX = data.readInt();
		hostLocationY = data.readInt();
		hostLocationZ = data.readInt();
		movementDirection = ForgeDirection.getOrientation(data.readInt());
	}

	@Override
	public void onUpdate()
	{
		TileEntity tile = worldObj.getTileEntity(hostLocationX, hostLocationY, hostLocationZ);
		if (tile instanceof TileParticleAccelerator)
		{
			int flPosX = (int) Math.floor(posX);
			int flPosY = (int) Math.floor(posY);
			int flPosZ = (int) Math.floor(posZ);
			TileParticleAccelerator accelerator = (TileParticleAccelerator) tile;
			if (ConfigNuclearPhysics.ENABLE_PARTICLE_CHUNKLOADING)
			{
				ForgeChunkManager.forceChunk(updateTicket, new ChunkCoordIntPair((int) posX >> 4, (int) posZ >> 4));
			}
			if (ticksExisted % 10 == 0)
			{
				worldObj.playSound(posX, posY, posZ, CoreReferences.PREFIX + "block.accelerator", 1, (float) (0.6 + 0.4 * (getTotalVelocity() / ConfigNuclearPhysics.ANTIMATTER_CREATION_SPEED)), true);
			}
			double acceleration = 0.002;
			if (accelerator.getParticle() == null)
			{
				accelerator.setParticle(this);
			}
			if (accelerator.getSessionUse() > Measurement.GIGA.value)
			{
				setDead();
				return;
			}
			if (!worldObj.isRemote)
			{
				dataWatcher.updateObject(movementTicketUpdateId, (byte) movementDirection.ordinal());
			} else
			{
				movementDirection = ForgeDirection.getOrientation(dataWatcher.getWatchableObjectByte(movementTicketUpdateId));
				if (rand.nextFloat() < 2)
				{
					worldObj.spawnParticle("portal", posX, posY - 1, posZ, motionX, motionY, motionZ);
					worldObj.spawnParticle("smoke", posX, posY, posZ, motionX, motionY, motionZ);
				}
			}
			if (isElectromagnet(worldObj, flPosX, flPosY, flPosZ, movementDirection))
			{
				acceleration = turn();
				motionX = 0;
				motionY = 0;
				motionZ = 0;
			}
			accelerateParticle((float) acceleration);
			isAirBorne = true;

			lastTickPosX = posX;
			lastTickPosY = posY;
			lastTickPosZ = posZ;
			moveEntity(motionX, motionY, motionZ);
			setPosition(posX, posY, posZ);
			if (lastTickPosX == posX && lastTickPosY == posY && lastTickPosZ == posZ && getTotalVelocity() <= 0 && ticksExisted > 1)
			{
				setDead();
			}

			if (ConfigNuclearPhysics.ENABLE_PARTICLE_COLLISION)
			{
				float radius = 0.85F;
				if (getDistance(lastCheckX, lastCheckY, lastCheckZ) > radius / 6)
				{
					lastCheckX = posX;
					lastCheckY = posY;
					lastCheckZ = posZ;
					boolean withParticle = false;
					int amount = 0;
					@SuppressWarnings("unchecked")
					List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
							AxisAlignedBB.getBoundingBox(posX - radius * 1.5, posY - radius * 1.5, posZ - radius * 1.5, posX + radius * 1.5, posY + radius * 1.5, posZ + radius * 1.5));
					for (Entity entity : entities)
					{
						if (getDistanceToEntity(entity) < radius)
						{
							amount++;
							if (entity instanceof EntityParticle && entity != this)
							{
								((EntityParticle) entity).onParticleSmash(this);
								if (!isDead)
								{
									onParticleSmash((EntityParticle) entity);
								}
								withParticle = true;
							} else
							{
								entity.attackEntityFrom(DamageSourceRadiation.INSTANCE, 3.5f);
							}
						}
					}
					if (!withParticle && amount > 1)
					{
						handleCollisionWithEntity();
					}
				}
			}
			if (ticksExisted % 2 == 0 && !canSpawnParticle(worldObj, flPosX, flPosY, flPosZ))
			{
				handleCollisionWithEntity();
			}
		} else
		{
			setDead();
		}
	}

	public double lastCheckX, lastCheckY, lastCheckZ;

	@SuppressWarnings("unchecked")
	private void onParticleSmash(EntityParticle entityParticle)
	{
		int radius = (int) ((getTotalVelocity() + entityParticle.getTotalVelocity()) * 2.5f);
		if (!worldObj.isRemote)
		{
			entityParticle.setDead();
			worldObj.createExplosion(this, posX, posY, posZ, ticksExisted > 20 ? (float) (getTotalVelocity() + entityParticle.getTotalVelocity()) * 1.5f : 0, true);
			if (getTotalVelocity() + entityParticle.getTotalVelocity() > ConfigNuclearPhysics.ANTIMATTER_CREATION_SPEED / 2)
			{
				didCollide = true;
			}
			setDead();
		}

		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius);
		for (EntityLivingBase living : (List<EntityLivingBase>) worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bounds))
		{
			living.attackEntityFrom(DamageSourceRadiation.INSTANCE, Math.max(1, radius - living.getDistanceToEntity(this)));
		}
	}

	@Override
	protected void entityInit()
	{
		dataWatcher.addObject(movementTicketUpdateId, (byte) EnumFacing.SOUTH.ordinal());
		if (updateTicket == null)
		{
			updateTicket = ForgeChunkManager.requestTicket(Physica.INSTANCE, worldObj, Type.ENTITY);
			if (updateTicket != null)
			{
				updateTicket.getModData();
				updateTicket.bindEntity(this);
			}
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		hostLocationX = tag.getInteger("tileLocationX");
		hostLocationY = tag.getInteger("tileLocationY");
		hostLocationZ = tag.getInteger("tileLocationZ");
		movementDirection = ForgeDirection.getOrientation(tag.getByte("direction"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setDouble("tileLocationX", hostLocationX);
		tag.setDouble("tileLocationY", hostLocationY);
		tag.setDouble("tileLocationZ", hostLocationZ);
		tag.setByte("direction", (byte) movementDirection.ordinal());
	}

	@Override
	public void applyEntityCollision(Entity entity)
	{
		if (entity != this)
		{
			handleCollisionWithEntity();
		}
	}

	@Override
	public void setDead()
	{
		super.setDead();
		ForgeChunkManager.releaseTicket(updateTicket);
	}

	public double turn()
	{
		ForgeDirection leftDirection = RotationUtility.getRelativeSide(ForgeDirection.WEST, movementDirection);
		ForgeDirection rightDirection = RotationUtility.getRelativeSide(ForgeDirection.EAST, movementDirection);
		ForgeDirection upDirection = RotationUtility.getRelativeSide(ForgeDirection.UP, movementDirection);
		ForgeDirection downDirection = RotationUtility.getRelativeSide(ForgeDirection.DOWN, movementDirection);

		if (worldObj.isAirBlock((int) Math.floor(posX + leftDirection.offsetX), (int) Math.floor(posY + leftDirection.offsetY), (int) Math.floor(posZ + leftDirection.offsetZ)))
		{
			movementDirection = leftDirection;
		} else if (worldObj.isAirBlock((int) Math.floor(posX + rightDirection.offsetX), (int) Math.floor(posY + rightDirection.offsetY), (int) Math.floor(posZ + rightDirection.offsetZ)))
		{
			movementDirection = rightDirection;
		} else if (worldObj.isAirBlock((int) Math.floor(posX + upDirection.offsetX), (int) Math.floor(posY + upDirection.offsetY), (int) Math.floor(posZ + upDirection.offsetZ)))
		{
			movementDirection = upDirection;
		} else if (worldObj.isAirBlock((int) Math.floor(posX + downDirection.offsetX), (int) Math.floor(posY + downDirection.offsetY), (int) Math.floor(posZ + downDirection.offsetZ)))
		{
			movementDirection = downDirection;
		} else
		{
			setDead();
			return 0;
		}

		setPosition(Math.floor(posX) + 0.5, Math.floor(posY) + 0.5, Math.floor(posZ) + 0.5);
		return getTotalVelocity() * 0.9075f;
	}

	@SuppressWarnings("unchecked")
	private void handleCollisionWithEntity()
	{
		if (!worldObj.isRemote)
		{
			worldObj.createExplosion(this, posX, posY, posZ, (float) (ticksExisted > 20 ? getTotalVelocity() * 2.5F : 0), true);
		}
		int radius = (int) (getTotalVelocity() * 1.5);
		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius);

		for (EntityLivingBase living : (List<EntityLivingBase>) worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bounds))
		{
			living.attackEntityFrom(DamageSourceRadiation.INSTANCE, Math.max(1, radius - living.getDistanceToEntity(this)));
		}
		setDead();
	}

	public double getTotalVelocity()
	{
		return Math.abs(motionX) + Math.abs(motionY) + Math.abs(motionZ);
	}

	public boolean didCollide()
	{
		return didCollide;
	}
}

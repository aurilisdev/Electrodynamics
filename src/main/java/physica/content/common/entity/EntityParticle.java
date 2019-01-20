package physica.content.common.entity;

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
import physica.Physica;
import physica.api.electromagnet.IElectromagnet;
import physica.api.energy.base.Measurement;
import physica.api.lib.RotationUtility;
import physica.content.common.configuration.ConfigMain;
import physica.content.common.effect.damage.DamageSourceRadiation;
import physica.content.common.tile.TileAccelerator;

public class EntityParticle extends Entity implements IEntityAdditionalSpawnData {
	private static int		movementDirectionUpdateId	= 20;

	public Ticket			updateTicket;
	private boolean			didCollide;
	private int				hostLocationX, hostLocationY, hostLocationZ;
	private ForgeDirection	movementDirection			= ForgeDirection.NORTH;

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
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	protected void accelerateParticle(float acceleration) {
		double accelerationX = movementDirection.offsetX * acceleration;
		double accelerationY = movementDirection.offsetY * acceleration;
		double accelerationZ = movementDirection.offsetZ * acceleration;

		motionX = Math.min(motionX + accelerationX, ConfigMain.antimatterCreationSpeed + 0.1);
		motionY = Math.min(motionY + accelerationY, ConfigMain.antimatterCreationSpeed + 0.1);
		motionZ = Math.min(motionZ + accelerationZ, ConfigMain.antimatterCreationSpeed + 0.1);

	}

	public static boolean canSpawnParticle(World world, int x, int y, int z) {
		if (world.isAirBlock(x, y, z))
		{
			int electromagnetCount = 0;

			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			{
				if (isElectromagnet(world, x, y, z, side))
				{
					electromagnetCount++;
				}
			}
			return electromagnetCount >= 3;
		}
		return false;
	}

	public static boolean isElectromagnet(World world, int x, int y, int z, ForgeDirection facing) {
		TileEntity tile = world.getTileEntity(x + facing.offsetX, y + facing.offsetY, z + facing.offsetZ);
		return tile instanceof IElectromagnet && ((IElectromagnet) tile).isRunning();
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeInt(hostLocationX);
		data.writeInt(hostLocationY);
		data.writeInt(hostLocationZ);
		data.writeInt(movementDirection.ordinal());
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		hostLocationX = data.readInt();
		hostLocationY = data.readInt();
		hostLocationZ = data.readInt();
		movementDirection = ForgeDirection.getOrientation(data.readInt());
	}

	@Override
	public void onUpdate() {

		TileEntity tile = worldObj.getTileEntity(hostLocationX, hostLocationY, hostLocationZ);

		if (tile instanceof TileAccelerator)
		{
			TileAccelerator accelerator = (TileAccelerator) tile;
			double acceleration = 0.0006;

			if (accelerator.getParticle() == null)
			{
				accelerator.setParticle(this);
			}
			if (accelerator.getSessionUse() > Measurement.GIGA.value)
			{
				setDead();
				return;
			}
			for (int x = -1; x < 1; x++)
			{
				for (int z = -1; z < 1; z++)
				{
					ForgeChunkManager.forceChunk(updateTicket, new ChunkCoordIntPair(((int) posX >> 4) + x, ((int) posZ >> 4) + z));
				}
			}

			if (!worldObj.isRemote)
			{
				dataWatcher.updateObject(movementDirectionUpdateId, (byte) movementDirection.ordinal());
			} else
			{
				movementDirection = ForgeDirection.getOrientation(dataWatcher.getWatchableObjectByte(movementDirectionUpdateId));

				worldObj.spawnParticle("portal", posX, posY, posZ, motionX, 0, motionZ);
				worldObj.spawnParticle("largesmoke", posX, posY, posZ, motionX, 0, motionZ);

			}
			if (isElectromagnet(worldObj, (int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), movementDirection))
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

			if (lastTickPosX == posX && lastTickPosY == posY && lastTickPosZ == posZ && getVelocity() <= 0 && ticksExisted > 1)
			{
				setDead();
			}
			float radius = 0.85F;
			AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius);
			List<Entity> entitiesNearby = worldObj.getEntitiesWithinAABB(Entity.class, bounds);

			if (entitiesNearby.size() > 1)
			{
				boolean withParticle = false;
				for (Entity entity : entitiesNearby)
				{
					if (entity instanceof EntityParticle && entity != this)
					{
						((EntityParticle) entity).onCollide(this);
						if (!isDead)
						{
							onCollide((EntityParticle) entity);
						}
						withParticle = true;
					} else
					{
						entity.attackEntityFrom(DamageSourceRadiation.INSTANCE, 3.5f);
					}
				}
				if (!withParticle)
				{
					handleCollisionWithEntity();
				}
			}

			if (!canSpawnParticle(worldObj, (int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ)))
			{
				handleCollisionWithEntity();
			}
		} else
		{
			setDead();
		}
	}

	private void onCollide(EntityParticle entityParticle) {
		if (!worldObj.isRemote)
		{
			entityParticle.setDead();
			worldObj.createExplosion(this, posX, posY, posZ, (float) (getVelocity() + entityParticle.getVelocity()) * 1.5f, true);
			{
				if (getVelocity() + entityParticle.getVelocity() > ConfigMain.antimatterCreationSpeed / 2)
				{
					didCollide = true;
				}
			}
			setDead();
		}
		int radius = (int) ((getVelocity() + entityParticle.getVelocity()) * 2.5f);
		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius);
		List<EntityLivingBase> entitiesNearby = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bounds);

		for (EntityLivingBase living : entitiesNearby)
		{
			living.attackEntityFrom(DamageSourceRadiation.INSTANCE, Math.max(1, radius - living.getDistanceToEntity(this)));
		}
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(movementDirectionUpdateId, (byte) EnumFacing.SOUTH.ordinal());

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
	protected void readEntityFromNBT(NBTTagCompound tag) {
		hostLocationX = tag.getInteger("tileLocationX");
		hostLocationY = tag.getInteger("tileLocationY");
		hostLocationZ = tag.getInteger("tileLocationZ");
		movementDirection = ForgeDirection.getOrientation(tag.getByte("direction"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setDouble("tileLocationX", hostLocationX);
		tag.setDouble("tileLocationY", hostLocationY);
		tag.setDouble("tileLocationZ", hostLocationZ);
		tag.setByte("direction", (byte) movementDirection.ordinal());
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		if (entity != this)
		{
			handleCollisionWithEntity();
		}
	}

	@Override
	public void setDead() {
		super.setDead();
		ForgeChunkManager.releaseTicket(updateTicket);
	}

	public double turn() {
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
		return getVelocity() * 0.8f;
	}

	private void handleCollisionWithEntity() {

		if (!worldObj.isRemote)
		{
			worldObj.createExplosion(this, posX, posY, posZ, (float) getVelocity() * 2.5F, true);
		}
		int radius = (int) (getVelocity() * 1.5);
		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius);
		List<EntityLivingBase> entitiesNearby = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bounds);

		for (EntityLivingBase living : entitiesNearby)
		{
			living.attackEntityFrom(DamageSourceRadiation.INSTANCE, Math.max(1, radius - living.getDistanceToEntity(this)));
		}
		setDead();
	}

	public double getVelocity() {
		return Math.abs(motionX) + Math.abs(motionY) + Math.abs(motionZ);
	}

	public boolean didCollide() {
		return didCollide;
	}
}

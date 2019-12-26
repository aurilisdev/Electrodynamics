package physica.missiles.common.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import physica.missiles.common.explosive.blast.types.EnumShrapnel;

public class EntityFragment extends Entity implements IEntityAdditionalSpawnData {

	private int			xTile					= -1;
	private int			yTile					= -1;
	private int			zTile					= -1;
	private Block		inTile					= null;
	private int			inData					= 0;
	private boolean		inGround				= false;
	public boolean		doesArrowBelongToPlayer	= false;
	public EnumShrapnel	type					= EnumShrapnel.SHRAPNEL;
	private boolean		isExploding				= false;
	public int			projectileSPin			= 0;
	private int			ticksInAir				= 0;
	private final int	damage					= 11;
	private int			knowBackStrength;
	public boolean		isCritical				= false;
	public float		explosionSize			= 1.5F;

	public EntityFragment(World par1World) {
		super(par1World);
		setSize(0.5F, 0.5F);
	}

	public EntityFragment(World world, double x, double y, double z, EnumShrapnel type) {
		super(world);
		setPosition(x, y, z);
		yOffset = 0.0F;
		this.type = type;

		if (type == EnumShrapnel.ANVIL)
		{
			setSize(1, 1);
		} else
		{
			setSize(0.5f, 0.5f);
		}
	}

	@Override
	public void writeSpawnData(ByteBuf data)
	{
		data.writeInt(type.ordinal());
	}

	@Override
	public void readSpawnData(ByteBuf data)
	{
		type = EnumShrapnel.values()[data.readInt()];
	}

	@Override
	protected void entityInit()
	{
	}

	@Override
	public String getCommandSenderName()
	{
		return "Fragment";
	}

	/**
	 * Uses the provided coordinates as a heading and determines the velocity from
	 * it with the set force and random variance. Args: x, y, z, force,
	 * forceVariation
	 */
	public void setArrowHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par3 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par5 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
	}

	/** Sets the velocity to the args. Args: x, y, z */
	@Override
	public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}

	private void explode()
	{
		if (!isExploding && !worldObj.isRemote)
		{
			isExploding = true;
			worldObj.createExplosion(this, xTile, yTile, zTile, explosionSize * (type == EnumShrapnel.SHRAPNEL ? 1 : type == EnumShrapnel.ANVIL ? 0 : 3), true);
			setDead();
		}
	}

	/** Called to update the entity's position/logic. */
	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (type == EnumShrapnel.ANVIL)
		{
			@SuppressWarnings("unchecked")
			ArrayList<?> entities = new ArrayList<Object>(worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox));

			Iterator<?> var5 = entities.iterator();

			while (var5.hasNext())
			{
				Entity entity = (Entity) var5.next();
				entity.attackEntityFrom(DamageSource.anvil, 15);
			}
		}

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI);
		}

		Block block = worldObj.getBlock(xTile, yTile, zTile);

		if (!worldObj.isAirBlock(xTile, yTile, zTile))
		{
			block.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			AxisAlignedBB var2 = block.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);

			if (var2 != null && var2.isVecInside(Vec3.createVectorHelper(posX, posY, posZ)))
			{
				inGround = true;
			}
		}

		if (projectileSPin > 0)
		{
			--projectileSPin;
		}

		if (inGround)
		{
			block = worldObj.getBlock(xTile, yTile, zTile);
			int var18 = worldObj.getBlockMetadata(xTile, yTile, zTile);

			if (block == inTile && var18 == inData)
			{
				if (type != EnumShrapnel.ANVIL)
				{
					explode();
				} else
				{
					if (type == EnumShrapnel.ANVIL && worldObj.rand.nextFloat() > 0.5f)
					{
						worldObj.playAuxSFX(1022, (int) posX, (int) posY, (int) posZ, 0);
					}

					setDead();
				}
			} else
			{
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInAir = 0;
			}
		} else
		{
			++ticksInAir;
			Vec3 var16 = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 var17 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingObjPos = worldObj.func_147447_a(var16, var17, false, true, false);
			var16 = Vec3.createVectorHelper(posX, posY, posZ);
			var17 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

			if (movingObjPos != null)
			{
				var17 = Vec3.createVectorHelper(movingObjPos.hitVec.xCoord, movingObjPos.hitVec.yCoord, movingObjPos.hitVec.zCoord);
			}

			Entity var4 = null;
			List<?> var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;
			int var8;
			float var10;

			for (var8 = 0; var8 < var5.size(); ++var8)
			{
				Entity var9 = (Entity) var5.get(var8);

				if (var9.canBeCollidedWith() && ticksInAir >= 5)
				{
					var10 = 0.3F;
					AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
					MovingObjectPosition var12 = var11.calculateIntercept(var16, var17);

					if (var12 != null)
					{
						double var13 = var16.distanceTo(var12.hitVec);

						if (var13 < var6 || var6 == 0.0D)
						{
							var4 = var9;
							var6 = var13;
						}
					}
				}
			}

			if (var4 != null)
			{
				movingObjPos = new MovingObjectPosition(var4);
			}

			float speed;

			if (movingObjPos != null)
			{
				if (movingObjPos.entityHit != null)
				{
					speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					int damage = (int) Math.ceil(speed * this.damage);

					if (isCritical)
					{
						damage += rand.nextInt(damage / 2 + 2);
					}

					DamageSource damageSource = new EntityDamageSourceIndirect("arrow", this, this).setProjectile();

					if (isBurning())
					{
						movingObjPos.entityHit.setFire(5);
					}

					if (movingObjPos.entityHit.attackEntityFrom(damageSource, damage))
					{
						if (movingObjPos.entityHit instanceof EntityLiving)
						{
							EntityLiving var24 = (EntityLiving) movingObjPos.entityHit;

							if (!worldObj.isRemote)
							{
								var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
							}

							if (knowBackStrength > 0)
							{
								float var21 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

								if (var21 > 0.0F)
								{
									movingObjPos.entityHit.addVelocity(motionX * knowBackStrength * 0.6000000238418579D / var21, 0.1D, motionZ * knowBackStrength * 0.6000000238418579D / var21);
								}
							}
						}

						worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						setDead();
					} else
					{
						motionX *= -0.10000000149011612D;
						motionY *= -0.10000000149011612D;
						motionZ *= -0.10000000149011612D;
						rotationYaw += 180.0F;
						prevRotationYaw += 180.0F;
						ticksInAir = 0;
					}
				} else
				{
					xTile = movingObjPos.blockX;
					yTile = movingObjPos.blockY;
					zTile = movingObjPos.blockZ;
					inTile = worldObj.getBlock(xTile, yTile, zTile);
					inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
					motionX = (float) (movingObjPos.hitVec.xCoord - posX);
					motionY = (float) (movingObjPos.hitVec.yCoord - posY);
					motionZ = (float) (movingObjPos.hitVec.zCoord - posZ);
					speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / speed * 0.05000000074505806D;
					posY -= motionY / speed * 0.05000000074505806D;
					posZ -= motionZ / speed * 0.05000000074505806D;
					worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					inGround = true;
					projectileSPin = 7;
					isCritical = false;
				}
			}

			if (isCritical)
			{
				for (var8 = 0; var8 < 4; ++var8)
				{
					worldObj.spawnParticle("crit", posX + motionX * var8 / 4.0D, posY + motionY * var8 / 4.0D, posZ + motionZ * var8 / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
				}
			}

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			speed = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

			for (rotationPitch = (float) (Math.atan2(motionY, speed) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			{

			}

			while (rotationPitch - prevRotationPitch >= 180.0F)
			{
				prevRotationPitch += 360.0F;
			}

			while (rotationYaw - prevRotationYaw < -180.0F)
			{
				prevRotationYaw -= 360.0F;
			}

			while (rotationYaw - prevRotationYaw >= 180.0F)
			{
				prevRotationYaw += 360.0F;
			}

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float var23 = 0.99F;
			var10 = 0.05F;

			if (isInWater())
			{
				for (int var25 = 0; var25 < 4; ++var25)
				{
					float var24 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * var24, posY - motionY * var24, posZ - motionZ * var24, motionX, motionY, motionZ);
				}

				var23 = 0.8F;
			}

			motionX *= var23;
			motionY *= var23;
			motionZ *= var23;
			motionY -= var10;
			setPosition(posX, posY, posZ);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("xTile", xTile);
		tag.setInteger("yTile", yTile);
		tag.setInteger("zTile", zTile);
		tag.setInteger("inTile", Block.getIdFromBlock(inTile));
		tag.setByte("inData", (byte) inData);
		tag.setByte("shake", (byte) projectileSPin);
		tag.setByte("inGround", (byte) (inGround ? 1 : 0));

		tag.setInteger("type", type.ordinal());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		xTile = tag.getInteger("xTile");
		yTile = tag.getInteger("yTile");
		zTile = tag.getInteger("zTile");
		inTile = Block.getBlockById(tag.getInteger("inTile"));
		inData = tag.getByte("inData") & 255;
		projectileSPin = tag.getByte("shake") & 255;
		inGround = tag.getByte("inGround") == 1;

		type = EnumShrapnel.values()[tag.getInteger("type")];
	}

	/** Called by a player entity when they collide with an entity */
	@Override
	public void applyEntityCollision(Entity par1Entity)
	{
		super.applyEntityCollision(par1Entity);

		if (type != EnumShrapnel.ANVIL && ticksExisted < 20 * 2)
		{
			explode();
		}
	}

	@Override
	public float getShadowSize()
	{
		return 0.0F;
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem()
	{
		return false;
	}
}

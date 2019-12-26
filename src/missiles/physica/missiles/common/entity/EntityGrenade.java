package physica.missiles.common.entity;

import java.util.HashMap;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import physica.library.location.VectorLocation;
import physica.missiles.common.explosive.Explosive;
import physica.missiles.common.explosive.blast.BlastTemplate;
import physica.missiles.common.explosive.blast.IStateHolder;

public class EntityGrenade extends Entity implements IEntityAdditionalSpawnData, IStateHolder {

	/**
	 * Is the entity that throws this 'thing' (snowball, ender pearl, eye of ender
	 * or potion)
	 */
	protected EntityLivingBase	thrower;

	public int					explosiveID;

	public EntityGrenade(World par1World) {
		super(par1World);
		setSize(0.3F, 0.3F);
		renderDistanceWeight = 8;
	}

	public EntityGrenade(World par1World, VectorLocation position, int explosiveID) {
		this(par1World);
		setPosition(position.x, position.y, position.z);
		this.explosiveID = explosiveID;
	}

	public EntityGrenade(World par1World, EntityLivingBase par2EntityLiving, int explosiveID, float par4) {
		this(par1World);
		thrower = par2EntityLiving;
		setSize(0.25F, 0.25F);
		setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		float var3 = 0.4F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * var3;
		setThrowableHeading(motionX, motionY, motionZ, 1.8f * par4, 1.0F);
		this.explosiveID = explosiveID;
	}

	@Override
	public String getCommandSenderName()
	{
		if (Explosive.get(explosiveID) != null)
		{
			return "Grenade." + Explosive.get(explosiveID).localeName;
		}

		return "Grenade";
	}

	@Override
	public void writeSpawnData(ByteBuf data)
	{
		data.writeInt(explosiveID);
	}

	@Override
	public void readSpawnData(ByteBuf data)
	{
		explosiveID = data.readInt();
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
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
		}
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk
	 * on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{
	}

	/** Called to update the entity's position/logic. */
	@Override
	public void onUpdate()
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();

		moveEntity(motionX, motionY, motionZ);
		float var16 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, var16) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
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
		float var17 = 0.98F;
		float gravity = 0.03F;

		if (isInWater())
		{
			for (int var7 = 0; var7 < 4; ++var7)
			{
				float var19 = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * var19, posY - motionY * var19, posZ - motionZ * var19, motionX, motionY, motionZ);
			}

			var17 = 0.8F;
		}

		motionX *= var17;
		motionY *= var17;
		motionZ *= var17;

		if (onGround)
		{
			motionX *= 0.5;
			motionZ *= 0.5;
			motionY *= 0.5;
		} else
		{
			motionY -= gravity;
			func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0D, posZ);
		}

		if (ticksExisted > Math.max(60, getExplosiveType().getBlastTemplate().fuseTime))
		{
			explode();
		}
	}

	private boolean					isPrepared	= false;
	private int						callcount;
	private HashMap<String, Object>	register	= new HashMap<>();

	@Override
	public void addObject(String register, Object object)
	{
		this.register.put(register, object);
	}

	@Override
	public Object getObject(String register)
	{
		return this.register.get(register);
	}

	public void explode()
	{

		BlastTemplate template = getExplosiveType().getBlastTemplate();
		if (!isPrepared)
		{
			isPrepared = true;
			register.put("cause", this);
			template.prepare(worldObj, new VectorLocation(this).BlockLocation(), this);
		}
		template.call(worldObj, new VectorLocation(this).BlockLocation(), callcount, this);
		callcount++;
		if (callcount >= template.callCount)
		{
			template.end(worldObj, new VectorLocation(this).BlockLocation(), this);
			setDead();
		}
	}

	private Explosive getExplosiveType()
	{
		return Explosive.get(explosiveID);
	}

	/**
	 * Returns if this entity is in water and will end up adding the waters velocity
	 * to the entity
	 */
	@Override
	public boolean handleWaterMovement()
	{
		return worldObj.handleMaterialAcceleration(boundingBox, Material.water, this);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this
	 * Entity.
	 */
	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when
	 * colliding.
	 */
	@Override
	public boolean canBePushed()
	{
		return true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		explosiveID = nbt.getInteger("explosiveId");

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("explosiveId", explosiveID);
	}

}

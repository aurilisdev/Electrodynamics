package physica.missiles.common.entity;

import java.util.HashMap;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import physica.library.location.VectorLocation;
import physica.missiles.common.explosive.Explosive;
import physica.missiles.common.explosive.blast.BlastTemplate;
import physica.missiles.common.explosive.blast.IStateHolder;

public class EntityPrimedExplosive extends Entity implements IEntityAdditionalSpawnData, IStateHolder {

	public int				fuse		= 90;
	public int				callcount	= 0;

	public int				explosiveID	= 0;

	public NBTTagCompound	nbtData		= new NBTTagCompound();

	public EntityPrimedExplosive(World par1World) {
		super(par1World);
		fuse = 0;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
	}

	public EntityPrimedExplosive(World par1World, double x, double y, double z, int explosiveID) {
		this(par1World);
		setPosition(x, y, z);
		float var8 = (float) (Math.random() * Math.PI * 2.0D);
		motionX = -((float) Math.sin(var8)) * 0.02F;
		motionY = 0.20000000298023224D;
		motionZ = -((float) Math.cos(var8)) * 0.02F;
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;
		this.explosiveID = explosiveID;
		fuse = Explosive.get(explosiveID).getBlastTemplate().fuseTime;
	}

	@Override
	public String getCommandSenderName()
	{
		return "PrimedExplosives";
	}

	/** Called to update the entity's position/logic. */
	@Override
	public void onUpdate()
	{
		if (fuse > 0)
		{
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;

			motionX *= 0.95;
			motionY -= 0.045D;
			motionZ *= 0.95;

			moveEntity(motionX, motionY, motionZ);
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
		}
		if (fuse < 1)
		{
			explode();
		} else
		{
			fuse--;
		}
		if (fuse > 0 || isDead)
		{
			super.onUpdate();
		}
	}

	private boolean					isPrepared	= false;
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

	@Override
	public boolean isEntityInvulnerable()
	{
		return true;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		fuse = nbt.getByte("Fuse");
		explosiveID = nbt.getInteger("explosiveID");
		nbtData = nbt.getCompoundTag("data");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setByte("Fuse", (byte) fuse);
		nbt.setInteger("explosiveID", explosiveID);
		nbt.setTag("data", nbtData);
	}

	@Override
	public float getShadowSize()
	{
		return 0.5F;
	}

	@Override
	protected void entityInit()
	{
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return true;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public boolean canBePushed()
	{
		return true;
	}

	@Override
	public void writeSpawnData(ByteBuf data)
	{
		data.writeInt(explosiveID);
		data.writeInt(fuse);
	}

	@Override
	public void readSpawnData(ByteBuf data)
	{
		explosiveID = data.readInt();
		fuse = data.readInt();
	}

	public Explosive getExplosiveType()
	{
		return Explosive.get(explosiveID);
	}

}

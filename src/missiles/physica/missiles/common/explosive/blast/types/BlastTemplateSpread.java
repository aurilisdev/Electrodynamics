package physica.missiles.common.explosive.blast.types;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import physica.library.location.GridLocation;
import physica.missiles.common.entity.EntityFragment;
import physica.missiles.common.explosive.blast.BlastTemplate;
import physica.missiles.common.explosive.blast.IStateHolder;

public class BlastTemplateSpread extends BlastTemplate {
	private EnumShrapnel type;

	public BlastTemplateSpread(int fuseTime, int tier, int callCount, EnumShrapnel type) {
		super(fuseTime, tier, callCount);
		this.type = type;
	}

	@Override
	public void call(World world, GridLocation loc, int callCount, IStateHolder holder)
	{
		if (!world.isRemote)
		{
			float rad = type == EnumShrapnel.FRAGMENT ? 8.0f : 5.0f;
			for (int i = 0; i < rad; i++)
			{
				float rotationYaw = world.rand.nextFloat() * 360.0f;
				for (int j = 0; j < rad; j++)
				{
					EntityFragment fragment = new EntityFragment(world, loc.xCoord + 0.5, loc.yCoord + 1 + 0.5, loc.zCoord + 0.5, type);

					if (((Entity) holder.getObject("cause")).isBurning())
					{
						fragment.isCritical = true;
						fragment.setFire(100);
					}

					float rotationPitch = rotationYaw / (world.rand.nextFloat() * 3);
					fragment.setLocationAndAngles(loc.xCoord + 0.5, loc.yCoord + 1 + 0.5, loc.zCoord + 0.5, rotationYaw, rotationPitch);
					fragment.posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
					fragment.posY -= 0.10000000149011612D;
					fragment.posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
					fragment.setPosition(fragment.posX, fragment.posY, fragment.posZ);
					fragment.yOffset = 0.0F;
					fragment.motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
					fragment.motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
					fragment.motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
					fragment.setArrowHeading(fragment.motionX * world.rand.nextFloat(), fragment.motionY * world.rand.nextFloat(), fragment.motionZ * world.rand.nextFloat(), 0.5f + 0.7f * world.rand.nextFloat(), 1.0F);
					world.spawnEntityInWorld(fragment);
				}
			}
		}
	}

}

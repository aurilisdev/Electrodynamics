package physica.forcefield.common.block;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.FaceDirection;
import physica.api.forcefield.IFortronBlock;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.PhysicaForcefields;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.ForcefieldTabRegister;
import physica.forcefield.common.effect.damage.DamageSourceForcefield;
import physica.forcefield.common.tile.TileFortronField;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.library.location.Location;
import physica.library.location.VectorLocation;
import physica.library.util.PhysicaMath;

public class BlockFortronField extends Block implements ITileEntityProvider, IFortronBlock {

	public BlockFortronField() {
		super(Material.glass);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
		setBlockTextureName(CoreReferences.PREFIX + "animatedFortronField");
		setBlockName(ForcefieldReferences.PREFIX + "fortronField");
		setCreativeTab(ForcefieldTabRegister.forcefieldTab);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		Location loc = new Location(x, y, z);
		TileEntity tile = loc.getTile(world);
		if (tile instanceof TileFortronField)
		{
			TileFortronField field = (TileFortronField) tile;
			return field.fieldColor;
		}
		return PhysicaForcefields.DEFAULT_COLOR;
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return false;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float bound = world.isRemote ? 0.01f : 0.0625F;
		@SuppressWarnings("unchecked")
		List<EntityPlayer> entities = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x - bound, y - bound, z - bound, x + 1 + bound, y + 0.9 + bound, z + 1 + bound));
		for (EntityPlayer player : entities)
		{
			if (player.isSneaking())
			{
				if (player.capabilities.isCreativeMode)
				{
					return null;
				}
			}
		}
		return AxisAlignedBB.getBoundingBox(x + bound, y + bound, z + bound, x + 1 - bound, y + 1 - bound, z + 1 - bound);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return Blocks.glass.getRenderType();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		return block != this && super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return blockIcon;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileFortronField)
			{
				TileFortronField field = (TileFortronField) tileEntity;
				TileEntity possibleConstructor = field.getConstructorCoord().getTile(world);
				if (world.getChunkProvider().chunkExists(x >> 4, z >> 4))
				{
					if (!(possibleConstructor instanceof TileFortronFieldConstructor))
					{
						if (!world.isRemote)
						{
							world.setBlockToAir(x, y, z);
						}
					} else
					{
						TileFortronFieldConstructor constructor = (TileFortronFieldConstructor) possibleConstructor;
						if (!world.isRemote)
						{
							if (!constructor.isActivated())
							{
								HashSet<Location> removed = new HashSet<>();
								LinkedList<TileFortronField> invalidQueue = new LinkedList<>();
								invalidQueue.push(field);

								while (!invalidQueue.isEmpty() && removed.size() < 1000)
								{
									TileFortronField element = invalidQueue.pop();
									Location loc = element.getLocation();
									if (removed.contains(loc))
									{
										continue;
									}

									world.setBlock(loc.xCoord, loc.yCoord, loc.zCoord, Blocks.air, 0, 2);
									removed.add(loc);

									for (FaceDirection direction : FaceDirection.VALID_DIRECTIONS)
									{
										TileEntity tile = world.getTileEntity(loc.xCoord + direction.offsetX, loc.yCoord + direction.offsetY, loc.zCoord + direction.offsetZ);
										if (tile instanceof TileFortronField && !((TileFortronField) tile).isValidField())
										{
											invalidQueue.add((TileFortronField) tile);
										}
									}
								}
								return;
							}
							float volatilityPercent = (float) constructor.getHealthLost() / TileFortronFieldConstructor.MAX_HEALTH_LOSS;
							if (volatilityPercent > 0.5)
							{
								world.createExplosion(null, entity.posX, entity.posY, entity.posZ, PhysicaMath.map(volatilityPercent, 0.5F, 1F, 0.5F, 5F), true);
							}
						}
						int shockAmount = constructor.getModuleCount(ForcefieldItemRegister.moduleMap.get("moduleUpgradeShock"), TileFortronFieldConstructor.SLOT_UPGRADES[0],
								TileFortronFieldConstructor.SLOT_UPGRADES[TileFortronFieldConstructor.SLOT_UPGRADES.length - 1]);
						if (shockAmount > 0)
						{
							if (!world.isRemote)
							{
								@SuppressWarnings("unchecked")
								List<EntityLiving> entities = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(x - 1, y - 1, z - 1, x + 2, y + 2, z + 2));
								for (Entity living : entities)
								{
									VectorLocation pos = new VectorLocation(entity);
									pos.sub(((TileFortronField) tileEntity).getLocation().Vector());

									pos.floor();
									pos.y = 1;
									float norm = pos.norm();
									if (norm > 0.5f)
									{
										pos.div(norm);
									}
									double knockback = 0.2;
									entity.motionX += pos.x * knockback;
									entity.motionZ += pos.z * knockback;
									living.attackEntityFrom(DamageSourceForcefield.INSTANCE, shockAmount);
								}
							}
							if (!(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode))
							{

							}
						}
					}
				}
			}
		}
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double d, double d1, double d2)
	{
		return Float.MAX_VALUE - 1;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileFortronField();
	}

}

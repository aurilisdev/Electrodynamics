package physica.nuclear.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.api.core.IGuiInterface;
import physica.api.core.PhysicaAPI;
import physica.core.common.CoreBlockRegister;
import physica.library.location.BlockLocation;
import physica.library.tile.TileBaseContainer;
import physica.nuclear.client.gui.GuiFissionReactor;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.inventory.ContainerFissionReactor;
import physica.nuclear.common.radiation.RadiationSystem;

public class TileFissionReactor extends TileBaseContainer implements IGuiInterface {

	public static final int SLOT_INPUT = 0;
	public static final int MELTDOWN_TEMPERATURE = 4407;
	public static final int AIR_TEMPERATURE = 15;
	public static final int WATER_TEMPERATURE = 10;
	private static final int[] ACCESSIBLE_SLOTS_UP = new int[] { SLOT_INPUT };

	protected float temperature = AIR_TEMPERATURE;
	protected int surroundingWater;
	private int insertion;
	private boolean isIncased;

	@Override
	public void updateServer(int ticks)
	{
		super.updateServer(ticks);

		Block[] adjacentBlocks = new Block[ForgeDirection.VALID_DIRECTIONS.length];
		for (int i = 0; i < adjacentBlocks.length; i++)
		{
			ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[i];
			adjacentBlocks[i] = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
		}
		cooldownReactor(adjacentBlocks);
		insertion = 0;
		if (hasFuelRod() && !isBeingControlled(adjacentBlocks))
		{
			processFuelRod();
			if (temperature > MELTDOWN_TEMPERATURE + 101 + worldObj.rand.nextInt(5) && hasFuelRod())
			{
				performMeltdown();
			}
		}
	}

	@Override
	public void updateCommon(int ticks)
	{
		super.updateCommon(ticks);
		if (hasFuelRod() && temperature > 300)
		{

			if (ticks % 100 == 0)
			{
				int radius = 3;
				isIncased = true;
				loops:
				{
					for (int i = -radius; i <= radius; i++)
					{
						for (int k = -radius; k <= radius; k++)
						{
							if (worldObj.getBlock(xCoord + i, yCoord - radius, zCoord + k) != CoreBlockRegister.blockLead)
							{
								isIncased = false;
								break loops;
							}
						}
					}
					for (int i = -radius; i <= radius; i++)
					{
						for (int j = -radius + 1; j <= radius - 1; j++)
						{
							for (int k = -radius; k <= radius; k++)
							{
								if (i == -radius || i == radius || k == radius || k == -radius)
								{
									if (worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != CoreBlockRegister.blockLead)
									{
										isIncased = false;
										break loops;
									}
								}
							}
						}
					}
				}
			}
			double tempScale = temperature / 300.0;
			double dRadius = isIncased ? Math.min(tempScale, 3) : tempScale;

			@SuppressWarnings("unchecked")
			List<EntityLiving> entities = worldObj.getEntitiesWithinAABB(Entity.class,
					AxisAlignedBB.getBoundingBox(xCoord - dRadius, yCoord - dRadius, zCoord - dRadius, xCoord + dRadius + 1, yCoord + tempScale, zCoord + dRadius + 1));
			for (Entity entity : entities)
			{
				if (entity instanceof EntityLivingBase)
				{
					double scale = (tempScale - entity.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5)) / 3.0;
					RadiationSystem.applyRontgenEntity((EntityLivingBase) entity, (float) scale * 1.5f, (float) scale * 15, (float) entity.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5),
							(float) tempScale);
				}
			}
		}
		if (ticks % 4 == 0)
		{
			produceSteam();
		}
	}

	private boolean isBeingControlled(Block[] adjacentBlocks)
	{
		boolean beingControlled = false;
		for (int i = 0; i < adjacentBlocks.length; i++)
		{
			Block block = adjacentBlocks[i];
			ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[i];
			if (block == NuclearBlockRegister.blockControlRod)
			{
				beingControlled = true;
			} else if (block == NuclearBlockRegister.blockThermometer)
			{
				block.updateTick(worldObj, xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ, worldObj.rand);
			} else if (block == NuclearBlockRegister.blockInsertableControlRod)
			{
				TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
				if (tile instanceof TileInsertableControlRod)
				{
					TileInsertableControlRod rod = (TileInsertableControlRod) tile;
					insertion = rod.getInsertion();
				}
			}
		}
		return beingControlled;
	}

	public boolean isFissileRod()
	{
		return hasFuelRod() && getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemHighEnrichedFuelCell;
	}

	public boolean isBreederRod()
	{
		return hasFuelRod() && getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemLowEnrichedFuelCell;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateClient(int ticks)
	{
		super.updateClient(ticks);
		if (worldObj.getWorldTime() % 100 == 0 && temperature >= 100)
		{
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, CoreReferences.PREFIX + "block.fission_reactor", Math.min(temperature / 100, 1), 1);
		}
		float radius = 0.15f;
		for (int k = 0; k < 4; k++)
		{
			float outerRods = 0.15f;
			float xCoordOffset = k == 0 ? -outerRods : k == 1 ? outerRods : 0;
			float zCoordOffset = k == 2 ? -outerRods : k == 3 ? outerRods : 0;
			for (float i = 0.175f; i < 0.8; i += 0.1)
			{
				if (worldObj.rand.nextFloat() < (temperature - AIR_TEMPERATURE) / (MELTDOWN_TEMPERATURE * 3) / 4.0)
				{
					worldObj.spawnParticle("reddust", xCoord + xCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2,
							yCoord + i + worldObj.rand.nextDouble() * radius - radius / 2,
							zCoord + zCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, 0.01f, 0.4f, 0.01f);
				}
			}
		}
		radius = temperature / 300.0f;
		if (radius > 0.5)
		{
			for (int i = 0; i < (int) (Math.pow(radius, 1.5) / 10f); i++)
			{
				for (int k = 0; k < 4; k++)
				{
					if (worldObj.rand.nextFloat() < 1.0 / 16.0)
					{
						float outerRods = 0.15f;
						float xCoordOffset = k == 0 ? -outerRods : k == 1 ? outerRods : 0;
						float zCoordOffset = k == 2 ? -outerRods : k == 3 ? outerRods : 0;
						worldObj.spawnParticle("reddust", xCoord + xCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, yCoord + worldObj.rand.nextDouble() * radius - radius / 2,
								zCoord + zCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, 0.01f, 0.5f, 0.01f);
					}
				}
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	public void performMeltdown()
	{
		if (PhysicaAPI.isDebugMode)
		{
			PhysicaAPI.logger.info("Fission reactor had a meltdown at: " + getLocation().toString() + ". Reactor stats: temp: " + temperature + " insertion: " + insertion);
		}
		if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(worldObj.getWorldInfo().getWorldName().toLowerCase()))
		{
			if (PhysicaAPI.isDebugMode)
			{
				PhysicaAPI.logger.info("World " + worldObj.getWorldInfo().getWorldName().toLowerCase() + " is protected so the meltdown did not occur fully.");
			}
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			return;
		}
		int power = (int) (temperature / 125.0f);
		setInventorySlotContents(SLOT_INPUT, null);
		BlockLocation location = new BlockLocation(xCoord, yCoord, zCoord);
		for (int i = -power; i <= power; i++)
		{
			for (int j = -power; j <= power; j++)
			{
				for (int k = -power; k <= power; k++)
				{
					location.set(xCoord + i, yCoord + j, zCoord + k);
					Block block = location.getBlock(worldObj);
					if (block == Blocks.water || block == Blocks.flowing_water)
					{
						location.setBlockAirNonUpdate(worldObj);
					}
				}
			}
		}
		worldObj.createExplosion(null, xCoord, yCoord, zCoord, power * 2, true);
		worldObj.setBlock(xCoord, yCoord, zCoord, NuclearBlockRegister.blockMeltedReactor);
	}

	private void cooldownReactor(Block[] adjacentBlocks)
	{
		double decrease = (temperature - AIR_TEMPERATURE) / 3000f;
		if (!hasFuelRod())
		{
			decrease *= 25;
		}
		surroundingWater = 0;
		for (Block block : adjacentBlocks)
		{
			if (block == Blocks.water || block == Blocks.flowing_water)
			{
				surroundingWater++;
				decrease += (temperature - WATER_TEMPERATURE) / 20000f;
			} else if (block == Blocks.lava)
			{
				if (temperature < 1165)
				{
					decrease -= (1165 - temperature) / 10f;
				} else if (hasFuelRod())
				{
					decrease += (1165 - temperature) / 1000f;
				}
				break;
			}
		}
		if (decrease != 0)
		{
			temperature -= decrease < 0.001 && decrease > 0 ? 0.001 : decrease > -0.001 && decrease < 0 ? -0.001 : decrease;
		}
	}

	private void processFuelRod()
	{
		ItemStack fuelRod = getStackInSlot(SLOT_INPUT);
		double insertDecimal = (100 - insertion) / 100.0;
		if (worldObj.rand.nextFloat() < insertDecimal)
		{
			fuelRod.setItemDamage(fuelRod.getItemDamage() + 1 + Math.round(temperature / (MELTDOWN_TEMPERATURE / 2)));
		}
		if (isFissileRod())
		{
			if (fuelRod.getItemDamage() >= fuelRod.getMaxDamage())
			{
				setInventorySlotContents(SLOT_INPUT, new ItemStack(NuclearItemRegister.itemLowEnrichedFuelCell, 1,
						(int) (NuclearItemRegister.itemLowEnrichedFuelCell.getMaxDamage() / 3 + worldObj.rand.nextFloat() * (NuclearItemRegister.itemLowEnrichedFuelCell.getMaxDamage() / 5))));
			}
			temperature += (MELTDOWN_TEMPERATURE * insertDecimal * (1.25f + worldObj.rand.nextFloat() / 5) - temperature) / (200 + 20 * surroundingWater);
		} else if (isBreederRod())
		{
			if (fuelRod.getItemDamage() >= fuelRod.getMaxDamage())
			{
				setInventorySlotContents(SLOT_INPUT, null);
			}
			temperature += (MELTDOWN_TEMPERATURE * insertDecimal * (0.25f + worldObj.rand.nextFloat() / 5) - temperature) / (200 + 20 * surroundingWater);
		}
		temperature = Math.max(AIR_TEMPERATURE, temperature);
	}

	public static final int STEAM_GEN_DIAMETER = 5;
	public static final int STEAM_GEN_HEIGHT = 2;
	private TileTurbine[][][] cachedTurbines = new TileTurbine[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];

	private void produceSteam()
	{
		if (temperature <= 100)
		{
			return;
		}
		for (int i = 0; i < STEAM_GEN_DIAMETER; i++)
		{
			for (int j = 0; j < STEAM_GEN_HEIGHT; j++)
			{
				for (int k = 0; k < STEAM_GEN_DIAMETER; k++)
				{
					boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
					if (isReactor2d && j == 0)
					{
						continue;
					}
					int offsetX = xCoord + i - STEAM_GEN_DIAMETER / 2;
					int offsetY = yCoord + j;
					int offsetZ = zCoord + k - STEAM_GEN_DIAMETER / 2;
					Block offset = worldObj.getBlock(offsetX, offsetY, offsetZ);
					if (offset == Blocks.water)
					{
						boolean isFaceWater = worldObj.getBlock(offsetX, yCoord, zCoord) == Blocks.water || worldObj.getBlock(xCoord, yCoord, offsetZ) == Blocks.water || isReactor2d;
						if (isFaceWater)
						{
							if (isServer())
							{
								float temperatureVarient = temperature / MELTDOWN_TEMPERATURE / 2400;
								if (worldObj.rand.nextFloat() < temperatureVarient + temperatureVarient * Math.pow(temperature / MELTDOWN_TEMPERATURE, 250))
								{
									worldObj.setBlockToAir(offsetX, offsetY, offsetZ);
									continue;
								}
								TileTurbine turbine = cachedTurbines[i][j][k];
								if (turbine == null || !worldObj.loadedTileEntityList.contains(turbine))
								{
									TileEntity above = worldObj.getTileEntity(offsetX, offsetY + 1, offsetZ);
									if (above instanceof TileTurbine)
									{
										cachedTurbines[i][j][k] = (TileTurbine) above;
										turbine = (TileTurbine) above;
									} else
									{
										cachedTurbines[i][j][k] = null;
										turbine = null;
									}
								}
								if (turbine != null)
								{
									turbine.addSteam((int) ((temperature - 100) / 10 * 0.65f) * 20 * 20);
								}
							} else if (isClient())
							{
								if (worldObj.rand.nextFloat() < temperature / MELTDOWN_TEMPERATURE)
								{
									if (worldObj.rand.nextInt(80) == 0)
									{
										worldObj.playSoundEffect(offsetX + 0.5D, offsetY + 0.5D, offsetZ + 0.5D, "liquid.lava", 0.5F,
												2.1F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.85F);
									}
									if (worldObj.rand.nextInt(40) == 0)
									{
										worldObj.playSoundEffect(offsetX + 0.5D, offsetY + 0.5D, offsetZ + 0.5D, "liquid.lavapop", 0.5F,
												2.6F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.8F);
									}
									double offsetFX = offsetX + worldObj.rand.nextDouble() / 2.0 * (worldObj.rand.nextBoolean() ? -1 : 1);
									double offsetFY = offsetY + worldObj.rand.nextDouble() / 2.0 * (worldObj.rand.nextBoolean() ? -1 : 1);
									double offsetFZ = offsetZ + worldObj.rand.nextDouble() / 2.0 * (worldObj.rand.nextBoolean() ? -1 : 1);
									worldObj.spawnParticle("bubble", offsetFX + 0.5D, offsetFY + 0.20000000298023224D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
									if (worldObj.rand.nextInt(3) == 0)
									{
										worldObj.spawnParticle("smoke", offsetFX + 0.5D, offsetFY + 0.5D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean hasFuelRod()
	{
		return getStackInSlot(SLOT_INPUT) != null
				&& (getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemHighEnrichedFuelCell || getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemLowEnrichedFuelCell);
	}

	public float getTemperature()
	{
		return temperature;
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeClientGuiPacket(dataList, player);
		dataList.add(temperature);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readClientGuiPacket(buf, player);
		temperature = buf.readFloat();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("Temperature", temperature);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		temperature = nbt.getFloat("Temperature");
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return stack == null ? false : slot == SLOT_INPUT ? stack.getItem() == NuclearItemRegister.itemHighEnrichedFuelCell || stack.getItem() == NuclearItemRegister.itemLowEnrichedFuelCell : false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiFissionReactor(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerFissionReactor(player, this);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return side == ForgeDirection.UP.ordinal() ? ACCESSIBLE_SLOTS_UP : ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return true;
	}

}

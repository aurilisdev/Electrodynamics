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
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import physica.CoreReferences;
import physica.api.core.IGuiInterface;
import physica.core.common.CoreItemRegister;
import physica.library.location.BlockLocation;
import physica.library.tile.TileBaseContainer;
import physica.nuclear.client.gui.GuiFissionReactor;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearItemRegister;
import physica.nuclear.common.configuration.ConfigNuclearPhysics;
import physica.nuclear.common.effect.potion.PotionRadiation;
import physica.nuclear.common.inventory.ContainerFissionReactor;
import physica.nuclear.common.items.armor.ItemHazmatArmor;

public class TileFissionReactor extends TileBaseContainer implements IGuiInterface {

	public static final int SLOT_INPUT = 0;
	public static final int MELTDOWN_TEMPERATURE = 4407;
	public static final int AIR_TEMPERATURE = 25;
	public static final int WATER_TEMPERATURE = 10;
	private static final int[] ACCESSIBLE_SLOTS_UP = new int[] { SLOT_INPUT };

	protected float temperature = AIR_TEMPERATURE;
	protected int surroundingWater;

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		cooldownReactor();
		produceSteam();
		if (hasFuelRod() && !isBeingControlled()) {
			processFuelRod();
			if (temperature > MELTDOWN_TEMPERATURE + 101 + worldObj.rand.nextInt(5) && hasFuelRod()) {
				performMeltdown();
			}
			double radius = temperature / 400;

			if (this.getTicksRunning() % 4 != 0) {
				return;
			}

			@SuppressWarnings("unchecked")
			List<EntityLiving> entities = worldObj.getEntitiesWithinAABB(Entity.class,
				AxisAlignedBB.getBoundingBox(xCoord - radius, yCoord - radius, zCoord - radius, xCoord + radius, yCoord + radius, zCoord + radius));
			for (Entity entity : entities) {
				if (entity instanceof EntityLivingBase) {
					int vulnerability = 0;
					double scale = (radius - entity.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5)) / 3.0;
					if (entity instanceof EntityPlayer) {
						EntityPlayer player = (EntityPlayer) entity;
						if (player.capabilities.isCreativeMode) {
							continue;
						}

						for (int i = 0; i < player.inventory.armorInventory.length; i++) {
							ItemStack armorStack = player.getCurrentArmor(i);
							if (armorStack != null && armorStack.getItem() instanceof ItemHazmatArmor) {
								if (armorStack.attemptDamageItem((int) Math.max(1, scale * 3), worldObj.rand)) {
									player.setCurrentItemOrArmor(i + 1, null);
								}
							} else {
								vulnerability++;
							}
						}
					} else {
						vulnerability = 4;
					}

					if (vulnerability > 0) {
						((EntityLivingBase) entity).addPotionEffect(new PotionEffect(PotionRadiation.INSTANCE.getId(), (int) (300 * scale), vulnerability * (int) Math.max(0, scale)));
					}
				}
			}
		}

	}

	private boolean isBeingControlled() {
		boolean beingControlled = false;
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			Block block = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
			if (block == NuclearBlockRegister.blockControlRod) {
				beingControlled = true;
			} else if (block == NuclearBlockRegister.blockThermometer) {
				block.updateTick(worldObj, xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ, worldObj.rand);
			}
		}
		return beingControlled;
	}

	public boolean isFissileRod() {
		return hasFuelRod() && getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemHighEnrichedFuelCell;
	}

	public boolean isBreederRod() {
		return hasFuelRod() && getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemLowEnrichedFuelCell;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateClient(int ticks) {
		super.updateClient(ticks);
		if (worldObj.getWorldTime() % 100 == 0 && temperature >= 100) {
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, CoreReferences.PREFIX + "block.fission_reactor", Math.min(temperature / 100, 1), 1);
		}
		float radius = 0.15f;
		for (int k = 0; k < 4; k++) {
			float outerRods = 0.15f;
			float xCoordOffset = k == 0 ? -outerRods : k == 1 ? outerRods : 0;
			float zCoordOffset = k == 2 ? -outerRods : k == 3 ? outerRods : 0;
			for (float i = 0.175f; i < 0.8; i += 0.1) {
				if (worldObj.rand.nextFloat() < (temperature - AIR_TEMPERATURE) / (MELTDOWN_TEMPERATURE * 3)) {
					worldObj.spawnParticle("reddust", xCoord + xCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2,
							yCoord + i + worldObj.rand.nextDouble() * radius - radius / 2,
							zCoord + zCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, 0.01f, 1, 0.01f);
				}
			}
			if (temperature > MELTDOWN_TEMPERATURE) {
				radius = 0.05f + (temperature - MELTDOWN_TEMPERATURE) / 20;
				worldObj.spawnParticle("reddust", xCoord + xCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, yCoord + worldObj.rand.nextDouble() * radius - radius / 2,
						zCoord + zCoordOffset + 0.5f + worldObj.rand.nextDouble() * radius - radius / 2, 0.01f, 1, 0.01f);
			}

		}
		produceSteam();
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	public void performMeltdown() {
		if (ConfigNuclearPhysics.PROTECTED_WORLDS.contains(worldObj.getWorldInfo().getWorldName().toLowerCase())) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			return;
		}
		float power = isFissileRod() ? 20 : isBreederRod() ? 7 : 0;
		setInventorySlotContents(SLOT_INPUT, null);
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			BlockLocation location = new BlockLocation(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
			if (location.getBlock(worldObj) == Blocks.water) {
				location.setBlockAirFast(worldObj);
			}
		}
		worldObj.createExplosion(null, xCoord, yCoord, zCoord, power, true);
	}

	private void cooldownReactor() {
		double decrease = (temperature - AIR_TEMPERATURE) / 3000f;
		if (!hasFuelRod()) {
			decrease *= 25;
		}
		surroundingWater = 0;
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			Block block = worldObj.getBlock(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
			if (block == Blocks.water || block == Blocks.flowing_water) {
				surroundingWater++;
				decrease += (temperature - WATER_TEMPERATURE) / 20000f;
			} else if (block == Blocks.lava) {
				if (temperature < 1165) {
					decrease -= (1165 - temperature) / 10f;
				} else if (hasFuelRod()) {
					decrease += (1165 - temperature) / 1000f;
				}
				break;
			}
		}
		if (decrease != 0) {
			temperature -= decrease < 0.001 && decrease > 0 ? 0.001 : decrease > -0.001 && decrease < 0 ? -0.001 : decrease;
		}
	}

	private void processFuelRod() {
		ItemStack fuelRod = getStackInSlot(SLOT_INPUT);
		fuelRod.setItemDamage(fuelRod.getItemDamage() + 1);
		if (isFissileRod()) {
			if (fuelRod.getItemDamage() >= fuelRod.getMaxDamage()) {
				setInventorySlotContents(SLOT_INPUT, new ItemStack(NuclearItemRegister.itemLowEnrichedFuelCell, 1,
						(int) (NuclearItemRegister.itemLowEnrichedFuelCell.getMaxDamage() / 3 + worldObj.rand.nextFloat() * (NuclearItemRegister.itemLowEnrichedFuelCell.getMaxDamage() / 5))));
			}
			temperature += (MELTDOWN_TEMPERATURE * (1.25f + worldObj.rand.nextFloat() / 5) - temperature) / (200 + 20 * surroundingWater);
		} else if (isBreederRod()) {
			if (fuelRod.getItemDamage() >= fuelRod.getMaxDamage()) {
				setInventorySlotContents(SLOT_INPUT, new ItemStack(CoreItemRegister.itemEmptyCell));
			}
			temperature += (MELTDOWN_TEMPERATURE * (0.25f + worldObj.rand.nextFloat() / 5) - temperature) / (200 + 20 * surroundingWater);
		}
	}

	private static final int diameter = 3;
	private static final int radius = diameter / 2;
	private TileTurbine[][][] turbines = new TileTurbine[diameter][radius + 1][diameter];

	private void produceSteam() {
		if (temperature <= 100) {
			return;
		}
		if (isServer()) {
			for (int i = 0; i < diameter; i++) {
				for (int j = 0; j <= radius; j++) {
					for (int k = 0; k < diameter; k++) {
						int offsetX = xCoord + i - radius;
						int offsetY = yCoord + j;
						int offsetZ = zCoord + k - radius;

						boolean isReactorBlock = offsetX == 0 && offsetY == 0 && offsetZ == 0;

						if (isReactorBlock) {
							continue;
						}

						Block block = worldObj.getBlock(offsetX, offsetY, offsetZ);
						if (block == Blocks.water) {
							if (worldObj.rand.nextFloat() < temperature / MELTDOWN_TEMPERATURE / 2400) {
								worldObj.setBlockToAir(offsetX, offsetY, offsetZ);
								continue;
							}
						} else if (block != Blocks.flowing_water) {
							continue;
						}

						TileTurbine turbine = turbines[i][j][k];
						if (turbine == null || turbine.isInvalid()){
							TileEntity above = worldObj.getTileEntity(offsetX, offsetY + 1, offsetZ);
							if (above instanceof TileTurbine) {
								turbines[i][j][k] = (TileTurbine) above;
							} else {
								turbines[i][j][k] = null;
							}
						} else {
							turbine.addSteam((int) ((temperature - 100) / 10 * 0.65f) * 20);
						}
					}
				}
			}
		} else if (isClient()) {
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					for (int k = -radius; k <= radius; k++) {
						if (worldObj.rand.nextFloat() < temperature / MELTDOWN_TEMPERATURE) {
							float steamRadius = 0.5f;
							worldObj.spawnParticle("bubble", xCoord + steamRadius + i + worldObj.rand.nextDouble() * steamRadius - steamRadius / 2,
								yCoord + steamRadius + j + worldObj.rand.nextDouble() * steamRadius - steamRadius / 2,
								zCoord + steamRadius + k + worldObj.rand.nextDouble() * steamRadius - steamRadius / 2, 0, 0, 0);
						}
					}
				}
			}
		}
	}

	public boolean hasFuelRod() {
		return getStackInSlot(SLOT_INPUT) != null
				&& (getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemHighEnrichedFuelCell || getStackInSlot(SLOT_INPUT).getItem() == NuclearItemRegister.itemLowEnrichedFuelCell);
	}

	public float getTemperature() {
		return temperature;
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player) {
		super.writeClientGuiPacket(dataList, player);
		dataList.add(temperature);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readClientGuiPacket(buf, player);
		temperature = buf.readFloat();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("Temperature", temperature);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		temperature = nbt.getFloat("Temperature");
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack == null ? false : slot == SLOT_INPUT ? stack.getItem() == NuclearItemRegister.itemHighEnrichedFuelCell || stack.getItem() == NuclearItemRegister.itemLowEnrichedFuelCell : false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiFissionReactor(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		return new ContainerFissionReactor(player, this);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == ForgeDirection.UP.ordinal() ? ACCESSIBLE_SLOTS_UP : ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}

}

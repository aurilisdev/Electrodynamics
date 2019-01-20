package physica.content.common.tile;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.base.IGuiInterface;
import physica.api.electromagnet.IElectromagnet;
import physica.api.lib.RotationUtility;
import physica.api.lib.tile.TileBasePoweredContainer;
import physica.content.client.gui.GuiAccelerator;
import physica.content.common.ItemRegister;
import physica.content.common.configuration.ConfigMain;
import physica.content.common.entity.EntityParticle;
import physica.content.common.inventory.ContainerAccelerator;

public class TileAccelerator extends TileBasePoweredContainer implements IGuiInterface, IElectromagnet {
	public static final int		SLOT_INPUTMATTER		= 0;
	public static final int		SLOT_INPUTCELLS			= 1;
	public static final int		SLOT_OUTPUT				= 2;

	private static final int[]	ACCESSIBLE_SLOTS_UP		= new int[] { SLOT_INPUTMATTER };
	private static final int[]	ACCESSIBLE_SLOTS_DOWN	= new int[] { SLOT_OUTPUT };
	private static final int[]	ACCESSIBLE_SLOTS_LEFT	= new int[] { SLOT_INPUTCELLS };

	protected EntityParticle	particle				= null;

	protected int				currentSessionUse		= 0;
	protected double			velocity				= 0;
	protected int				antimatterAmount		= 0;

	@Override
	protected void update(int ticks) {
		super.update(ticks);
		if (isServer())
		{
			ItemStack stackMatter = getStackInSlot(SLOT_INPUTMATTER);
			ItemStack stackEmptyCell = getStackInSlot(SLOT_INPUTCELLS);
			ItemStack stackOutputSlot = getStackInSlot(SLOT_OUTPUT);
			if (particle != null)
			{
				if (hasEnoughEnergy() && isPoweredByRedstone())
				{
					velocity = particle.getVelocity();

					if (stackEmptyCell != null)
					{
						if (antimatterAmount >= 125)
						{
							decrStackSize(SLOT_INPUTCELLS, 1);
							antimatterAmount -= 125;
							if (stackOutputSlot != null)
							{
								if (stackOutputSlot.getItem() == ItemRegister.itemAntimatterContainer125Milligram)
								{
									stackOutputSlot.stackSize++;
								}
							} else
							{
								setInventorySlotContents(SLOT_OUTPUT, new ItemStack(ItemRegister.itemAntimatterContainer125Milligram));
							}
						}
					}
					if (currentSessionUse + 1 == Integer.MAX_VALUE)
					{
						currentSessionUse = 1;
					}
					currentSessionUse += extractEnergy();
					if (particle.isDead)
					{
						if (particle.didCollide())
						{
							if (stackEmptyCell != null)
							{
								if (antimatterAmount > 100 && worldObj.rand.nextFloat() < 0.333f)
								{
									decrStackSize(SLOT_INPUTCELLS, 1);
									antimatterAmount = 0;
									if (stackOutputSlot != null)
									{
										if (stackOutputSlot.getItem() == ItemRegister.itemDarkmatterContainer)
										{
											stackOutputSlot.stackSize++;
										}
									} else
									{
										setInventorySlotContents(SLOT_OUTPUT, new ItemStack(ItemRegister.itemDarkmatterContainer));
									}
								}
							}
						}
						particle = null;
					} else if (velocity >= ConfigMain.antimatterCreationSpeed)
					{
						antimatterAmount = Math.min(1000, antimatterAmount + 7 + worldObj.rand.nextInt(5));
						particle.setDead();
						particle = null;
					}
				} else
				{
					particle.setDead();
					particle = null;
					velocity = 0;
					currentSessionUse = 0;
				}
			} else
			{
				if (isPoweredByRedstone() && hasEnoughEnergy())
				{
					ForgeDirection opposite = getFacing().getOpposite();
					if (stackMatter != null && stackEmptyCell != null && EntityParticle.canSpawnParticle(worldObj, xCoord + opposite.offsetX, yCoord + opposite.offsetY, zCoord + opposite.offsetZ))
					{
						currentSessionUse = extractEnergy();
						particle = new EntityParticle(worldObj, xCoord + opposite.offsetX, yCoord + opposite.offsetY, zCoord + opposite.offsetZ, opposite);
						worldObj.spawnEntityInWorld(particle);

						decrStackSize(SLOT_INPUTMATTER, 1);
					}
				}
			}
		} else
		{
			if (particle != null) if (particle.isDead)
			{
				particle = null;
			}
		}
	}

	public AcceleratorStatus getAcceleratorStatus() {
		if (particle != null)
		{
			if (hasEnoughEnergy() && isPoweredByRedstone())
			{
				if (particle.isDead)
				{
					if (particle.didCollide()) return AcceleratorStatus.Done;
					return AcceleratorStatus.Failure;
				} else if (velocity > ConfigMain.antimatterCreationSpeed) return AcceleratorStatus.Done;
				else return AcceleratorStatus.Accelerating;
			} else return AcceleratorStatus.Failure;
		} else
		{
			if (isPoweredByRedstone() && hasEnoughEnergy())
			{
				ForgeDirection opposite = getFacing().getOpposite();
				if (EntityParticle.canSpawnParticle(worldObj, xCoord + opposite.offsetX, yCoord + opposite.offsetY, zCoord + opposite.offsetZ)) return AcceleratorStatus.Ready;
				return AcceleratorStatus.Failure;
			} else if (!hasEnoughEnergy()) return AcceleratorStatus.Disabled;
			else return AcceleratorStatus.Idle;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("currentSessionUse", currentSessionUse);
		nbt.setInteger("antimatterAmount", antimatterAmount);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		currentSessionUse = nbt.getInteger("currentSessionUse");
		antimatterAmount = nbt.getInteger("antimatterAmount");
	}

	@Override
	protected void writeGuiPacket(List<Object> dataList, EntityPlayer player) {
		super.writeGuiPacket(dataList, player);
		dataList.add(currentSessionUse);
		dataList.add(antimatterAmount);
		dataList.add(velocity);
	}

	@Override
	protected void readGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readGuiPacket(buf, player);
		currentSessionUse = buf.readInt();
		antimatterAmount = buf.readInt();
		velocity = buf.readDouble();
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack == null || slot == SLOT_OUTPUT ? false : slot == SLOT_INPUTMATTER && stack.isStackable() ? true : slot == SLOT_INPUTCELLS && stack.getItem() == ItemRegister.itemEmptyContainer;
	}

	@Override
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiAccelerator(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		return new ContainerAccelerator(player, this);
	}

	public enum AcceleratorStatus
	{
		Disabled, Idle, Accelerating, Done, Ready, Failure
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return getEnergyUsage() * 5;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return !from.equals(getFacing().getOpposite());
	}

	@Override
	public int getEnergyUsage() {
		return 14000;
	}

	public float getParticleVelocity() {
		return (float) velocity;
	}

	public double getSessionUse() {
		return currentSessionUse;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == ForgeDirection.DOWN.ordinal() ? ACCESSIBLE_SLOTS_DOWN
				: side == ForgeDirection.UP.ordinal() ? ACCESSIBLE_SLOTS_UP
						: side == RotationUtility.getRelativeSide(ForgeDirection.WEST, getFacing().getOpposite()).ordinal() ? ACCESSIBLE_SLOTS_LEFT : ACCESSIBLE_SLOTS_NONE;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == SLOT_OUTPUT;
	}

	public int getAntimatterAmount() {
		return antimatterAmount;
	}

	public Entity getParticle() {
		return particle;
	}

	public void setParticle(EntityParticle particle) {
		this.particle = particle;
	}

	@Override
	public boolean isRunning() {
		return hasEnoughEnergy();
	}
}

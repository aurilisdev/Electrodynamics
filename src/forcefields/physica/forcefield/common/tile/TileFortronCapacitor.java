package physica.forcefield.common.tile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidTank;
import physica.api.core.abstraction.Face;
import physica.api.core.inventory.IGuiInterface;
import physica.api.core.tile.ITileBase;
import physica.api.forcefield.IInvFortronTile;
import physica.forcefield.client.gui.GuiFortronCapacitor;
import physica.forcefield.common.ForcefieldFluidRegister;
import physica.forcefield.common.ForcefieldItemRegister;
import physica.forcefield.common.inventory.ContainerFortronCapacitor;
import physica.library.location.GridLocation;
import physica.library.network.IPacket;
import physica.library.network.netty.PacketSystem;
import physica.library.network.packet.PacketTile;
import physica.library.tile.TileBaseContainer;

public class TileFortronCapacitor extends TileBaseContainer implements IInvFortronTile, IGuiInterface {

	public static final int BASE_FORTRON = 1000;

	public static final int SLOT_CARD = 0;
	public static final int SLOT_MODULE1 = 1;
	public static final int SLOT_MODULE2 = 2;
	public static final int SLOT_MODULE3 = 3;

	public boolean isActivated = false;
	private boolean isOverriden = false;
	protected Set<ITileBase> fortronConnections = new HashSet<>();
	protected FluidTank fortronTank = new FluidTank(ForcefieldFluidRegister.LIQUID_FORTRON, 0, getMaxFortron());

	public int getMaxFortron() {
		return (int) (BASE_FORTRON + BASE_FORTRON * 10 * Math.pow(1.051, getModuleCount(ForcefieldItemRegister.moduleMap.get("moduleUpgradeSpeed"), SLOT_MODULE1, SLOT_MODULE3)) + BASE_FORTRON * 30 * Math.pow(1.021, getModuleCount(ForcefieldItemRegister.moduleMap.get("moduleUpgradeCapacity"), SLOT_MODULE1, SLOT_MODULE3) * 2));
	}

	public int getFortronTransferRate() {
		return (int) (BASE_FORTRON + BASE_FORTRON * 300 * Math.pow(1.051, getModuleCount(ForcefieldItemRegister.moduleMap.get("moduleUpgradeSpeed"), SLOT_MODULE1, SLOT_MODULE3))) / 2;
	}

	@Override
	public void onFirstUpdate() {
		invalidateConnections();
		fortronConnections.clear();
		findNearbyConnections(TileFortronCapacitor.class, TileCoercionDriver.class, TileFortronFieldConstructor.class, TileInterdictionMatrix.class);
	}

	@Override
	public void updateCommon(int ticks) {
		super.updateCommon(ticks);
		fortronTank.setCapacity(getMaxFortron());
		if (fortronTank.getCapacity() < fortronTank.getFluidAmount()) {
			fortronTank.getFluid().amount = fortronTank.getCapacity();
		}

		if (getStackInSlot(SLOT_CARD) != null) {
			if (getStackInSlot(SLOT_CARD).stackTagCompound != null) {
				setFrequency(getStackInSlot(SLOT_CARD).stackTagCompound.getInteger("frequency"));
			}
		}
	}

	@Override
	public void updateServer(int ticks) {
		super.updateServer(ticks);
		isActivated = isOverriden || isPoweredByRedstone();
		if (!isActivated) {
			return;
		}
		if (ticks % 20 == 0) {
			validateConnections();
		}
		if (canSendBeam()) {
			fortronTank.getFluid().amount -= sendFortronTo(fortronTank.getFluidAmount() - 1, TileFortronFieldConstructor.class, TileInterdictionMatrix.class);
			if (fortronTank.getFluid().amount > 1) {
				fortronTank.getFluid().amount -= sendFortronTo(Math.min(fortronTank.getFluidAmount(), getFortronTransferRate()) - 1, TileFortronCapacitor.class);
			}
		}
	}

	@Override
	public FluidTank getFortronTank() {
		return fortronTank;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		invalidateConnections();
	}

	@Override
	public Set<ITileBase> getFortronConnections() {
		return fortronConnections;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return canSendBeam() ? super.getRenderBoundingBox().expand(5, 5, 5) : super.getRenderBoundingBox();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack == null ? false : stack.getItem() == null ? false : slot >= SLOT_MODULE1 && slot <= SLOT_MODULE3 ? stack.getItem() == ForcefieldItemRegister.itemMetaUpgradeModule : slot == SLOT_CARD ? stack.getItem() == ForcefieldItemRegister.itemFrequency : true;
	}

	private int frequency;

	@Override
	public int getFrequency() {
		return frequency;
	}

	@Override
	public void setFrequency(int freq) {
		int oldFrequency = frequency;
		frequency = freq;
		if (oldFrequency != freq) {
			onFirstUpdate();
		}
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player) {
		super.writeClientGuiPacket(dataList, player);
		dataList.add(isActivated);
		dataList.add(frequency);
		dataList.add(fortronTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player) {
		super.readClientGuiPacket(buf, player);
		isActivated = buf.readBoolean();
		frequency = buf.readInt();
		fortronTank.readFromNBT(ByteBufUtils.readTag(buf));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("frequency", frequency);
		tag.setBoolean("override", isOverriden);
		fortronTank.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		frequency = tag.getInteger("frequency");
		isOverriden = tag.getBoolean("override");
		fortronTank.readFromNBT(tag);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, Face face) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, Face face) {
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public boolean isActivated() {
		return isActivated;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player) {
		return new GuiFortronCapacitor(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player) {
		return new ContainerFortronCapacitor(player, this);
	}

	public static final int GUI_BUTTON_PACKET_ID = 22;

	public void actionPerformed(int amount, Side side) {
		if (side == Side.CLIENT) {
			GridLocation loc = getLocation();
			PacketSystem.INSTANCE.sendToServer(new PacketTile("", GUI_BUTTON_PACKET_ID, loc.xCoord, loc.yCoord, loc.zCoord, amount));
		} else if (side == Side.SERVER) {
			isOverriden = !isOverriden;
		}
	}

	@Override
	public void readCustomPacket(int id, EntityPlayer player, Side side, IPacket type) {
		if (id == GUI_BUTTON_PACKET_ID && side.isServer() && type instanceof PacketTile) {
			actionPerformed(((PacketTile) type).customInteger, side);
		}
	}

}

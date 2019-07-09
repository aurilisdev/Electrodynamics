package physica.nuclear.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;
import physica.api.core.IGuiInterface;
import physica.api.core.ITileBasePowered;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.inventory.ContainerBase;
import physica.library.tile.TileBaseRotateable;
import physica.nuclear.client.gui.GuiReactorControlPanel;

public class TileReactorControlPanel extends TileBaseRotateable implements ITileBasePowered, IGuiInterface {

	private int					energyStored;
	public TileFissionReactor	reactor;

	@Override
	public void updateClient(int ticks)
	{
		super.updateClient(ticks);
		if (ticks % 20 == 0)
		{
			worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void updateCommon(int ticks)
	{
		super.updateCommon(ticks);
		if (reactor != null && (reactor.isInvalid() || !reactor.isIncased))
		{
			reactor = null;
			worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void writeClientGuiPacket(List<Object> dataList, EntityPlayer player)
	{
		super.writeClientGuiPacket(dataList, player);
	}

	@Override
	public void readClientGuiPacket(ByteBuf buf, EntityPlayer player)
	{
		super.readClientGuiPacket(buf, player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getClientGuiElement(int id, EntityPlayer player)
	{
		return new GuiReactorControlPanel(player, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player)
	{
		return new ContainerBase<>(player, this, null);
	}

	@Override
	public int getEnergyStored()
	{
		return energyStored;
	}

	@Override
	public void setEnergyStored(int energy)
	{
		energyStored = energy;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return false;
	}

	@Override
	public int getEnergyUsage()
	{
		return ElectricityUtilities.convertEnergy(450, Unit.WATT, Unit.RF);
	}
}

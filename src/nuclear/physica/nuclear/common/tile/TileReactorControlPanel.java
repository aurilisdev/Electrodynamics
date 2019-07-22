package physica.nuclear.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import physica.api.core.abstraction.FaceDirection;
import physica.api.core.inventory.IGuiInterface;
import physica.library.inventory.ContainerBase;
import physica.library.tile.TileBaseRotateable;
import physica.nuclear.client.gui.GuiReactorControlPanel;

public class TileReactorControlPanel extends TileBaseRotateable implements IGuiInterface {

	public TileFissionReactor		reactor;
	public TileInsertableControlRod	rod;

	@Override
	public void updateClient(int ticks)
	{
		super.updateClient(ticks);
		if (ticks % 20 == 0)
		{
			worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
			rod = null;
			if (reactor != null)
			{
				TileEntity tile = reactor.getBlockLocation().TranslateTo(FaceDirection.UP).getTile(worldObj);
				if (tile instanceof TileInsertableControlRod)
				{
					rod = (TileInsertableControlRod) tile;
				} else
				{
					tile = reactor.getBlockLocation().TranslateTo(FaceDirection.DOWN).getTile(worldObj);
					if (tile instanceof TileInsertableControlRod)
					{
						rod = (TileInsertableControlRod) tile;
					}
				}
			}
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
}

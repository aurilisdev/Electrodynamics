package physica.api.forcefield;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import physica.api.core.tile.ITileBase;
import physica.forcefield.common.ForcefieldFluidRegister;

public interface IInvFortronTile extends ITileBase {

	public boolean isActivated();

	public Set<ITileBase> getFortronConnections();

	public FluidTank getFortronTank();

	default boolean canSendBeam()
	{
		return isActivated() && getFortronTank().getFluidAmount() > 0 && getFortronConnections().size() > 0;
	}

	default boolean canRecieveFortron(IInvFortronTile tile)
	{
		return getFortronTank().getCapacity() - getFortronTank().getFluidAmount() > 0;
	}

	default int recieveFortron(int maxFill)
	{
		return getFortronTank().fill(new FluidStack(ForcefieldFluidRegister.LIQUID_FORTRON, maxFill), true);
	}

	default int sendFortronTo(int maxSend, Class<?>... types)
	{
		int ret = 0;
		Set<IInvFortronTile> send = new HashSet<>();
		for (ITileBase base : getFortronConnections())
		{
			IInvFortronTile fortron = (IInvFortronTile) base;
			if (fortron.canRecieveFortron(this) && Arrays.asList(types).contains(base.getClass()))
			{
				send.add(fortron);
			}
		}
		int size = send.size();
		for (IInvFortronTile tile : send)
		{
			int sent = tile.recieveFortron(maxSend / size);
			ret += sent;
			maxSend -= sent;
			size--;
		}
		return ret;
	}

	default void invalidateConnections()
	{
		for (ITileBase base : getFortronConnections())
		{
			if (base instanceof IInvFortronTile)
			{
				((IInvFortronTile) base).getFortronConnections().remove(this);
			}
		}
		getFortronConnections().clear();
	}

	default void validateConnections()
	{
		Set<ITileBase> invalid = new HashSet<>();
		for (ITileBase base : getFortronConnections())
		{
			if (base instanceof IInvFortronTile)
			{
				if (((IInvFortronTile) base).getFrequency() != getFrequency())
				{
					((IInvFortronTile) base).getFortronConnections().remove(this);
					invalid.add(base);
				}
			}
		}
		getFortronConnections().removeAll(invalid);
	}

	default int getFrequency()
	{
		return 0;
	}

	default void setFrequency(int freq)
	{

	}

	default void findNearbyConnections(Class<?>... classes)
	{
		for (TileEntity tile : getNearbyTiles(5))
		{
			if (tile instanceof IInvFortronTile && !tile.isInvalid() && Arrays.asList(classes).contains(tile.getClass()))
			{
				IInvFortronTile fortronTile = (IInvFortronTile) tile;
				if (fortronTile.getFrequency() == getFrequency())
				{
					getFortronConnections().add((ITileBase) tile);
					if (!fortronTile.getFortronConnections().contains(this))
					{
						fortronTile.getFortronConnections().add(this);
					}
				}
			}
		}
	}

	default int getModuleCount(ItemStack compare, int fromSlot, int toSlot)
	{
		int ret = 0;
		if (compare != null)
		{
			IInventory inv = (IInventory) this;
			for (int i = fromSlot; i <= toSlot; i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && compare.getItem() == stack.getItem() && stack.getItemDamage() == compare.getItemDamage())
				{
					ret += stack.stackSize;
				}
			}
		}
		return ret;
	}

	default boolean findModule(ItemStack compare, int fromSlot, int toSlot)
	{
		if (compare != null)
		{
			IInventory inv = (IInventory) this;
			for (int i = fromSlot; i <= toSlot; i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && compare.getItem() == stack.getItem() && stack.getItemDamage() == compare.getItemDamage())
				{
					return true;
				}
			}
		}
		return false;
	}

	default int getModuleCountIn(ItemStack compare, int... slots)
	{
		int ret = 0;
		if (compare != null && slots.length > 0)
		{
			IInventory inv = (IInventory) this;
			for (int slot : slots)
			{
				if (slot < inv.getSizeInventory())
				{
					ItemStack stack = inv.getStackInSlot(slot);
					if (stack != null && compare.getItem() == stack.getItem() && stack.getItemDamage() == compare.getItemDamage())
					{
						ret += stack.stackSize;
					}
				}
			}
		}
		return ret;
	}
}

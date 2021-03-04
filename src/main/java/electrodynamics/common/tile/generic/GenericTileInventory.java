package electrodynamics.common.tile.generic;

import java.util.HashMap;
import java.util.HashSet;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class GenericTileInventory extends GenericTileBase implements INamedContainerProvider, ISidedInventory {
    protected static final int[] SLOTS_EMPTY = new int[] {};
    protected NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
    protected LazyOptional<?> itemHandler = LazyOptional.of(() -> createUnSidedHandler());
    protected LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP,
	    Direction.DOWN, Direction.NORTH);
    protected HashSet<PlayerEntity> viewing = new HashSet<>();

    protected GenericTileInventory(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	ItemStackHelper.loadAllItems(compound, items);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	super.write(compound);
	ItemStackHelper.saveAllItems(compound, items);
	return compound;
    }

    @Override
    @Nullable
    public final Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
	if (isUsableByPlayer(player)) {
	    viewing.add(player);
	    return createMenu(id, inv);
	}
	return null;
    }

    @Override
    public void closeInventory(PlayerEntity player) {
	viewing.remove(player);
    }

    protected abstract Container createMenu(int id, PlayerInventory player);

    public HashSet<PlayerEntity> getViewing() {
	return viewing;
    }

    @Override
    public ITextComponent getDisplayName() {
	return getName();
    }

    protected IItemHandler createUnSidedHandler() {
	return new InvWrapper(this);
    }

    @Override
    public boolean isEmpty() {
	for (ItemStack itemstack : items) {
	    if (!itemstack.isEmpty()) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
	return items.get(index);
    }

    @Override
    public void remove() {
	super.remove();
	for (LazyOptional<? extends IItemHandler> handler : handlers) {
	    handler.invalidate();
	}
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
	return ItemStackHelper.getAndSplit(items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
	return ItemStackHelper.getAndRemove(items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
	if (isItemValidForSlot(index, stack)) {
	    items.set(index, stack);
	    if (stack.getCount() > getInventoryStackLimit()) {
		stack.setCount(getInventoryStackLimit());
	    }
	}
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
	return world.getTileEntity(pos) == this
		? player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64
		: false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
	if (!removed && facing != null
		&& capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
	    if (facing == Direction.UP) {
		return handlers[0].cast();
	    } else if (facing == Direction.DOWN) {
		return handlers[1].cast();
	    } else {
		return handlers[2].cast();
	    }
	}
	if (!removed && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
	    return itemHandler.cast();
	}
	return super.getCapability(capability, facing);
    }

    private HashMap<Integer, Integer> extradata = new HashMap<>();

    private final IIntArray inventorydata = new IIntArray() {
	@Override
	public int get(int index) {
	    if (index == 0) {
		return pos.getX();
	    } else if (index == 1) {
		return pos.getY();
	    } else if (index == 2) {
		return pos.getZ();
	    }
	    if (extradata != null && extradata.containsKey(index)) {
		return extradata.get(index);
	    }
	    return 0;
	}

	@Override
	public void set(int index, int value) {
	    // Doesn't do anything
	}

	@Override
	public int size() {
	    return 3 + extradata.size();
	}
    };

    protected void trackInteger(Integer id, Integer value) {
	extradata.put(id + 3, value);
    }

    public IIntArray getInventoryData() {
	return inventorydata;
    }

    @Override
    public void clear() {
	items.clear();
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
	return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
	return true;
    }

}

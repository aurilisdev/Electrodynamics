package electrodynamics.common.tile.generic;

import electrodynamics.api.tile.IUpdateableTile;
import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;

public abstract class GenericTileBase extends TileEntity implements INameable, IUpdateableTile {

    private ITextComponent customName;

    protected GenericTileBase(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	customName = ITextComponent.Serializer.getComponentFromJson(compound.getString("CustomName"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	super.write(compound);
	if (customName != null) {
	    compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
	}
	return compound;
    }

    @Override
    public void markDirty() {
	super.markDirty();
	world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
    }

    @Override
    public ITextComponent getCustomName() {
	return customName;
    }

    @Override
    public ITextComponent getName() {
	return getCustomName();
    }

    protected Direction getFacing() {
	return getBlockState().get(BlockGenericMachine.FACING);
    }

    @Override
    public CompoundNBT writeCustomPacket() {
	return new CompoundNBT();
    }

    @Override
    public void readCustomPacket(CompoundNBT nbt) {
    }

    @Override
    public CompoundNBT writeGUIPacket() {
	return new CompoundNBT();
    }

    @Override
    public void readGUIPacket(CompoundNBT nbt) {
    }
}

package electrodynamics.prefab.properties;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;

public enum PropertyType {
	Byte,
	Boolean,
	Integer,
	Float,
	Double,
	UUID,
	CompoundTag,
	BlockPos,
	InventoryItems((thisList, otherList) -> {
		NonNullList<ItemStack> thisCasted = (NonNullList<ItemStack>) thisList;
		NonNullList<ItemStack> otherCasted = (NonNullList<ItemStack>) otherList;
		if (thisCasted.size() != otherCasted.size()) {
			return false;
		}
		ItemStack a, b;
		for (int i = 0; i < thisCasted.size(); i++) {
			a = thisCasted.get(i);
			b = otherCasted.get(i);
			if (!ItemStack.isSameItemSameTags(a, b)) {
				return false;
			}
		}
		return true;
	}),
	Fluidstack((thisStack, otherStack) -> {
		FluidStack thisCasted = (FluidStack) thisStack;
		FluidStack otherCasted = (FluidStack) otherStack;
		if (thisCasted.getAmount() != otherCasted.getAmount()) {
			return false;
		}
		return thisCasted.getFluid().isSame(otherCasted.getFluid());
	}),
	BlockPosList((thisList, otherList) -> {
		List<BlockPos> thisCasted = (List<BlockPos>) thisList;
		List<BlockPos> otherCasted = (List<BlockPos>) otherList;
		if (thisCasted.size() != otherCasted.size()) {
			return false;
		}
		BlockPos a, b;
		for (int i = 0; i < thisCasted.size(); i++) {
			a = thisCasted.get(i);
			b = otherCasted.get(i);
			if (!a.equals(b)) {
				return false;
			}
		}
		return true;
	}),
	Location;

	// this allows us to deal with classes that don't implement the equals method
	@Nullable
	public BiPredicate<Object, Object> predicate = Object::equals;

	PropertyType() {
	}

	PropertyType(BiPredicate<Object, Object> predicate) {
		this.predicate = predicate;
	}

	public void write(Property<?> prop, FriendlyByteBuf buf) {
		Object val = prop.get();
		switch (this) {
		case Boolean:
			buf.writeBoolean((Boolean) val);
			break;
		case Byte:
			buf.writeByte((Byte) val);
			break;
		case CompoundTag:
			buf.writeNbt((CompoundTag) val);
			break;
		case Double:
			buf.writeDouble((Double) val);
			break;
		case Float:
			buf.writeFloat((Float) val);
			break;
		case Integer:
			buf.writeInt((Integer) val);
			break;
		case UUID:
			buf.writeUUID((UUID) val);
			break;
		case BlockPos:
			buf.writeBlockPos((BlockPos) val);
			break;
		case InventoryItems:
			NonNullList<ItemStack> list = (NonNullList<ItemStack>) prop.get();
			int size = list.size();
			buf.writeInt(size);
			CompoundTag tag = new CompoundTag();
			ContainerHelper.saveAllItems(tag, list);
			buf.writeNbt(tag);
			break;
		case Fluidstack:
			buf.writeFluidStack((FluidStack) prop.get());
			break;
		case BlockPosList:
			List<BlockPos> posList = (List<BlockPos>) prop.get();
			buf.writeInt(posList.size());
			posList.forEach(pos -> buf.writeBlockPos(pos));
			break;
		case Location:
			Location loc = (Location) val;
			loc.toBuffer(buf);
			break;
		default:
			break;
		}

	}

	public Object read(FriendlyByteBuf buf) {
		switch (this) {
		case Boolean:
			return buf.readBoolean();
		case Byte:
			return buf.readByte();
		case CompoundTag:
			return buf.readNbt();
		case Double:
			return buf.readDouble();
		case Float:
			return buf.readFloat();
		case Integer:
			return buf.readInt();
		case UUID:
			return buf.readUUID();
		case BlockPos:
			return buf.readBlockPos();
		case InventoryItems:
			int size = buf.readInt();
			NonNullList<ItemStack> toBeFilled = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
			ContainerHelper.loadAllItems(buf.readNbt(), toBeFilled);
			return toBeFilled;
		case Fluidstack:
			return buf.readFluidStack();
		case BlockPosList:
			List<BlockPos> list = new ArrayList<>();
			size = buf.readInt();
			for (int i = 0; i < size; i++) {
				list.add(buf.readBlockPos());
			}
			return list;
		case Location:
			return electrodynamics.prefab.utilities.object.Location.fromBuffer(buf);
		default:
			break;
		}
		return null;
	}

	public void save(Property<?> prop, CompoundTag tag) {
		Object val = prop.get();
		switch (this) {
		case Boolean:
			tag.putBoolean(prop.getName(), (Boolean) val);
			break;
		case Byte:
			tag.putByte(prop.getName(), (Byte) val);
			break;
		case CompoundTag:
			tag.put(prop.getName(), (CompoundTag) val);
			break;
		case Double:
			tag.putDouble(prop.getName(), (Double) val);
			break;
		case Float:
			tag.putFloat(prop.getName(), (Float) val);
			break;
		case Integer:
			tag.putInt(prop.getName(), (Integer) val);
			break;
		case UUID:
			tag.putUUID(prop.getName(), (UUID) val);
			break;
		case BlockPos:
			if (val instanceof BlockPos pos) {
				tag.putInt(prop.getName() + "X", pos.getX());
				tag.putInt(prop.getName() + "Y", pos.getY());
				tag.putInt(prop.getName() + "Z", pos.getZ());
			}
			break;
		case InventoryItems:
			NonNullList<ItemStack> list = (NonNullList<ItemStack>) prop.get();
			tag.putInt(prop.getName() + "_size", list.size());
			ContainerHelper.saveAllItems(tag, list);
			break;
		case Fluidstack:
			CompoundTag fluidTag = new CompoundTag();
			((FluidStack) prop.get()).writeToNBT(fluidTag);
			tag.put(prop.getName(), fluidTag);
			break;
		case BlockPosList:
			List<BlockPos> posList = (List<BlockPos>) prop.get();
			CompoundTag data = new CompoundTag();
			data.putInt("size", posList.size());
			for (int i = 0; i < posList.size(); i++) {
				data.put("pos" + i, NbtUtils.writeBlockPos(posList.get(i)));
			}
			tag.put(prop.getName(), data);
			break;
		case Location:
			((Location) val).writeToNBT(tag, prop.getName());
			break;
		default:
			break;
		}
	}

	public void load(Property<?> prop, CompoundTag tag) {
		Object val = null;
		switch (this) {
		case Boolean:
			val = tag.getBoolean(prop.getName());
			break;
		case Byte:
			val = tag.getByte(prop.getName());
			break;
		case CompoundTag:
			val = tag.get(prop.getName());
			break;
		case Double:
			val = tag.getDouble(prop.getName());
			break;
		case Float:
			val = tag.getFloat(prop.getName());
			break;
		case Integer:
			val = tag.getInt(prop.getName());
			break;
		case UUID:
			val = tag.getUUID(prop.getName());
			break;
		case BlockPos:
			val = new BlockPos(tag.getInt(prop.getName() + "X"), tag.getInt(prop.getName() + "Y"), tag.getInt(prop.getName() + "Z"));
			break;
		case InventoryItems:
			int size = tag.getInt(prop.getName() + "_size");
			NonNullList<ItemStack> toBeFilled = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
			ContainerHelper.loadAllItems(tag, toBeFilled);
			val = toBeFilled;
			break;
		case Fluidstack:
			val = FluidStack.loadFluidStackFromNBT(tag.getCompound(prop.getName()));
			break;
		case BlockPosList:
			List<BlockPos> list = new ArrayList<>();
			CompoundTag data = tag.getCompound(prop.getName());
			size = data.getInt("size");
			for (int i = 0; i < size; i++) {
				list.add(NbtUtils.readBlockPos(data.getCompound("pos" + i)));
			}
			val = list;
			break;
		case Location:
			val = electrodynamics.prefab.utilities.object.Location.readFromNBT(tag, prop.getName());
			break;
		default:
			break;
		}
		tag.remove(prop.getName());
		prop.load(val);
	}

	Object attemptCast(Object updated) {
		// This is done so we remove some issues between different number types.
		switch (this) {
		case Byte:
			if (updated instanceof Number) {
				return (byte) ((Number) updated).byteValue();
			}
			break;
		case Double:
			if (updated instanceof Number) {
				return (double) ((Number) updated).doubleValue();
			}
			break;
		case Float:
			if (updated instanceof Number) {
				return (float) ((Number) updated).floatValue();
			}
			break;
		case Integer:
			if (updated instanceof Number) {
				return (int) ((Number) updated).intValue();
			}
			break;
		default:
			break;
		}
		return updated;

	}
}

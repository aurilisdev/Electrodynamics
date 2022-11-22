package electrodynamics.prefab.properties;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public enum PropertyType {
	Byte,
	Boolean,
	Integer,
	Float,
	Double,
	UUID,
	CompoundTag,
	BlockPos,
	InventoryItems;

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
			net.minecraft.core.BlockPos pos = (net.minecraft.core.BlockPos) val;
			if (pos != null) {
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
		default:
			break;
		}
		prop.setAmbigous(val);
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

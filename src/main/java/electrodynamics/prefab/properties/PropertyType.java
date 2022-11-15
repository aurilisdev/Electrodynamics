package electrodynamics.prefab.properties;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public enum PropertyType {
	Byte,
	Boolean,
	Integer,
	Float,
	Double,
	CompoundTag;

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
		default:
			break;
		}
		prop.set(val);
	}
}

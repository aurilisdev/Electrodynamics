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
		Object val = prop.getValue();
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
}

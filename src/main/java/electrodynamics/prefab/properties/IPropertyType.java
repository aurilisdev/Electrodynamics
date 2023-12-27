package electrodynamics.prefab.properties;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

/**
 * Interface to allow for custom property types in dependent mods
 * 
 * @author skip999
 *
 */

public interface IPropertyType {

	public boolean hasChanged(Object currentValue, Object newValue);

	public void writeToBuffer(BufferWriter writer);

	public Object readFromBuffer(BufferReader reader);

	public void writeToTag(TagWriter writer);

	public Object readFromTag(TagReader reader);

	public Object attemptCast(Object value);

	public ResourceLocation getId();

	public static final class BufferWriter {

		public Object value;
		public PacketBuffer buf;

		public BufferWriter(Object value, PacketBuffer buf) {
			this.value = value;
			this.buf = buf;
		}

	}

	public static final class BufferReader {

		public PacketBuffer buf;

		public BufferReader(PacketBuffer buf) {
			this.buf = buf;
		}

	}

	public static final class TagWriter {

		public Property<?> prop;
		public CompoundNBT tag;

		public TagWriter(Property<?> prop, CompoundNBT tag) {
			this.prop = prop;
			this.tag = tag;
		}

	}

	public static final class TagReader {

		public Property<?> prop;
		public CompoundNBT tag;

		public TagReader(Property<?> prop, CompoundNBT tag) {
			this.prop = prop;
			this.tag = tag;
		}

	}

}

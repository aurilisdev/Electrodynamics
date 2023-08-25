package electrodynamics.prefab.properties;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

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
	
	public static final record BufferWriter(Object value, FriendlyByteBuf buf) {
		
	}
	
	public static final record BufferReader(FriendlyByteBuf buf) {
		
	}
	
	public static final record TagWriter(Property<?> prop, CompoundTag tag) {
		
	}
	
	public static final record TagReader(Property<?> prop, CompoundTag tag) {
		
	}

}

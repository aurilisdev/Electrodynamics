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
	
	public void writeToBuffer(Object value, FriendlyByteBuf buf);
	
	public Object readFromBuffer(FriendlyByteBuf buf);
	
	public void writeToTag(Property<?> prop, CompoundTag tag);
	
	public Object readFromTag(Property<?> prop, CompoundTag tag);
	
	public Object attemptCast(Object value);
	
	public ResourceLocation getId();

}

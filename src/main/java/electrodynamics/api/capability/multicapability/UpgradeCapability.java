package electrodynamics.api.capability.multicapability;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.boolstorage.CapabilityBooleanStorage;
import electrodynamics.api.capability.types.dirstorage.CapabilityDirectionalStorage;
import electrodynamics.api.capability.types.doublestorage.CapabilityDoubleStorage;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class UpgradeCapability implements ICapabilitySerializable<CompoundTag> {

	private CapabilityBooleanStorage bool;
	private CapabilityIntStorage number;
	private CapabilityDirectionalStorage direction;
	private CapabilityDoubleStorage doub;

	public UpgradeCapability(CapabilityBooleanStorage bool, CapabilityIntStorage number, CapabilityDirectionalStorage direciton, CapabilityDoubleStorage doub) {
		this.bool = bool;
		this.number = number;
		direction = direciton;
		this.doub = doub;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY) {
			return bool.holder.cast();
		} else if (cap == ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY) {
			return number.holder.cast();
		} else if (cap == ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY) {
			return direction.holder.cast();
		} else if (cap == ElectrodynamicsCapabilities.DOUBLE_STORAGE_CAPABILITY) {
			return doub.holder.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.put("bool", bool.serializeNBT());
		nbt.put("int", number.serializeNBT());
		nbt.put("dir", direction.serializeNBT());
		nbt.put("doub", doub.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		bool.deserializeNBT((CompoundTag) nbt.get("bool"));
		number.deserializeNBT((CompoundTag) nbt.get("int"));
		direction.deserializeNBT((CompoundTag) nbt.get("dir"));
		if(!nbt.contains("doub")) {
			CompoundTag tag = new CompoundTag();
			tag.putInt(ElectrodynamicsCapabilities.BOOLEAN_KEY, 0);
			tag.putInt(ElectrodynamicsCapabilities.BOOLEAN_KEY + 0, 0);
		}
		doub.deserializeNBT((CompoundTag) nbt.get("doub"));
	}
	
	public static CompoundTag saveToClientNBT(ItemStack stack) {
		CompoundTag tag = new CompoundTag();
		tag.put("bool", CapabilityBooleanStorage.saveToClientNBT(stack));
		tag.put("int", CapabilityIntStorage.saveToClientNBT(stack));
		tag.put("dir", CapabilityDirectionalStorage.saveToClientNBT(stack));
		tag.put("doub", CapabilityDoubleStorage.saveToClientNBT(stack));
		return tag;
	}
	
	public static void readFromClientNBT(ItemStack stack, CompoundTag tag) {
		CapabilityBooleanStorage.readFromClientNBT(tag.getCompound("bool"), stack);
		CapabilityIntStorage.readFromClientNBT(tag.getCompound("int"), stack);
		CapabilityDirectionalStorage.readFromClientNBT(tag.getCompound("dir"), stack);
		CapabilityDoubleStorage.readFromClientNBT(tag.getCompound("doub"), stack);
	}

}

package improvedapi.core.capability;

import improvedapi.core.tile.IElectrical;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityElectrical {
    @CapabilityInject(IElectrical.class)
    public static Capability<IElectrical> INSTANCE = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(IElectrical.class, new IStorage<IElectrical>() {
	    @Override
	    public INBT writeNBT(Capability<IElectrical> capability, IElectrical instance, Direction side) {
		return new CompoundNBT();
	    }

	    @Override
	    @Deprecated
	    public void readNBT(Capability<IElectrical> capability, IElectrical instance, Direction side, INBT nbt) {
	    }
	}, () -> null);
    }
}

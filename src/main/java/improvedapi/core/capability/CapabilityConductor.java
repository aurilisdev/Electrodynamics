package improvedapi.core.capability;

import improvedapi.core.tile.IConductor;
import improvedapi.core.tile.IConnector;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityConductor {
    @CapabilityInject(IConnector.class)
    public static Capability<IConductor> INSTANCE = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(IConductor.class, new IStorage<IConductor>() {
	    @Override
	    public INBT writeNBT(Capability<IConductor> capability, IConductor instance, Direction side) {
		return new CompoundNBT();
	    }

	    @Override
	    @Deprecated
	    public void readNBT(Capability<IConductor> capability, IConductor instance, Direction side, INBT nbt) {
	    }
	}, () -> null);
    }
}

package improvedapi.core.capability;

import improvedapi.core.tile.INetworkProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityNetworkProvider {
    @CapabilityInject(INetworkProvider.class)
    public static Capability<INetworkProvider> INSTANCE = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(INetworkProvider.class, new IStorage<INetworkProvider>() {
	    @Override
	    public INBT writeNBT(Capability<INetworkProvider> capability, INetworkProvider instance, Direction side) {
		return new CompoundNBT();
	    }

	    @Override
	    @Deprecated
	    public void readNBT(Capability<INetworkProvider> capability, INetworkProvider instance, Direction side, INBT nbt) {
	    }
	}, () -> null);
    }
}

package improvedapi.core.capability;

import improvedapi.core.tile.IConnectionProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityConnectionProvider {
    @CapabilityInject(IConnectionProvider.class)
    public static Capability<IConnectionProvider> INSTANCE = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(IConnectionProvider.class, new IStorage<IConnectionProvider>() {
	    @Override
	    public INBT writeNBT(Capability<IConnectionProvider> capability, IConnectionProvider instance, Direction side) {
		return new CompoundNBT();
	    }

	    @Override
	    @Deprecated
	    public void readNBT(Capability<IConnectionProvider> capability, IConnectionProvider instance, Direction side, INBT nbt) {
	    }
	}, () -> null);
    }
}

package improvedapi.core.capability;

import improvedapi.core.tile.IConnector;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityConnector {
    @CapabilityInject(IConnector.class)
    public static Capability<IConnector> INSTANCE = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(IConnector.class, new IStorage<IConnector>() {
	    @Override
	    public INBT writeNBT(Capability<IConnector> capability, IConnector instance, Direction side) {
		return new CompoundNBT();
	    }

	    @Override
	    @Deprecated
	    public void readNBT(Capability<IConnector> capability, IConnector instance, Direction side, INBT nbt) {
	    }
	}, () -> null);
    }
}

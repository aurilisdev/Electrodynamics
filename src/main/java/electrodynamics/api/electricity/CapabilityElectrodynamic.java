package electrodynamics.api.electricity;

import net.minecraft.core.Direction;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityElectrodynamic {
    public static final double DEFAULT_VOLTAGE = 120.0;
    @CapabilityInject(IElectrodynamic.class)
    public static Capability<IElectrodynamic> ELECTRODYNAMIC = null;

    public static void register() {
	CapabilityManager.INSTANCE.register(IElectrodynamic.class, new IStorage<IElectrodynamic>() {
	    @Override
	    public Tag writeNBT(Capability<IElectrodynamic> capability, IElectrodynamic instance, Direction side) {
		return DoubleTag.valueOf(instance.getJoulesStored());
	    }

	    @Override
	    @Deprecated
	    public void readNBT(Capability<IElectrodynamic> capability, IElectrodynamic instance, Direction side, Tag nbt) {
		instance.setJoulesStored(((DoubleTag) nbt).getAsDouble());
	    }
	}, () -> new ElectrodynamicStorage(1000, 0));
    }
}

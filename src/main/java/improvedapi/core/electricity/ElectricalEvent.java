package improvedapi.core.electricity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class ElectricalEvent extends Event {
    @Cancelable
    public static class ElectricProductionEvent extends ElectricalEvent {
	public ElectricityPack electricityPack;
	public TileEntity[] ignoreTiles;

	public ElectricProductionEvent(ElectricityPack electricityPack, TileEntity... ignoreTiles) {
	    this.electricityPack = electricityPack;
	    this.ignoreTiles = ignoreTiles;
	}
    }
}

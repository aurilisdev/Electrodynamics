package electrodynamics.api.fluid;

import javax.annotation.Nullable;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class PropertyFluidTank extends FluidTank {

	@Nullable
	private Property<FluidTank> property;
	private GenericTile holder;
	
	public PropertyFluidTank(int capacity) {
		super(capacity);
	}
	
	public void setProperty(GenericTile holder, String key) {
		this.holder = holder;
		property = holder.property(
				new Property<FluidTank>(PropertyType.FluidTank, "propertyfluidtank" + key, new FluidTank(capacity)));
	}
	
	@Override
	protected void onContentsChanged() {
		if(property != null) {
			property.set(this);
			property.forceDirty();
			if(holder != null) {
				holder.onFluidTankChange(this);
			}
		}
	}
	
	public PropertyFluidTank[] asArray() {
		return new PropertyFluidTank[] {this};
	}

}

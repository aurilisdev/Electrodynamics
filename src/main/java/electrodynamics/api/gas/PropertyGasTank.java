package electrodynamics.api.gas;

import java.util.function.Predicate;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketSendUpdatePropertiesServer;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;

/**
 * An extension of the GasTank class incorporating the Electrodynamics property system
 * 
 * @author skip999
 *
 */
public class PropertyGasTank extends GasTank {

	protected GenericTile holder;

	protected Property<GasStack> gasProperty;
	protected Property<Double> capacityProperty;
	protected Property<Double> maxTemperatureProperty;
	protected Property<Double> maxPressureProperty;

	public PropertyGasTank(GenericTile holder, String key, double capacity, double maxTemperature, double maxPressure) {
		super(capacity, maxTemperature, maxPressure);

		this.holder = holder;

		gasProperty = holder.property(new Property<>(PropertyType.Gasstack, "propertygastankstack" + key, GasStack.EMPTY));
		capacityProperty = holder.property(new Property<>(PropertyType.Double, "propertygastankcapacity" + key, capacity));
		maxTemperatureProperty = holder.property(new Property<>(PropertyType.Double, "propertygastankmaxtemperature" + key, maxTemperature));
		maxPressureProperty = holder.property(new Property<>(PropertyType.Double, "propertygastankmaxpressure", maxPressure));
	}

	public PropertyGasTank(GenericTile holder, String key, double capacity, double maxTemperature, double maxPressure, Predicate<GasStack> isGasValid) {
		super(capacity, maxTemperature, maxPressure, isGasValid);

		this.holder = holder;

		gasProperty = holder.property(new Property<>(PropertyType.Gasstack, "propertygastankstack" + key, GasStack.EMPTY));
		capacityProperty = holder.property(new Property<>(PropertyType.Double, "propertygastankcapacity" + key, capacity));
		maxTemperatureProperty = holder.property(new Property<>(PropertyType.Double, "propertygastankmaxtemperature" + key, maxTemperature));
		maxPressureProperty = holder.property(new Property<>(PropertyType.Double, "propertygastankmaxpressure", maxPressure));

	}

	protected PropertyGasTank(PropertyGasTank other) {
		super(other.getCapacity(), other.getMaxTemperature(), other.getMaxPressure(), other.isGasValid);

		this.holder = other.holder;

		gasProperty = other.gasProperty;
		capacityProperty = other.capacityProperty;
		maxTemperatureProperty = other.maxTemperatureProperty;
		maxPressureProperty = other.maxPressureProperty;

	}

	@Override
	public void setGas(GasStack gas) {
		gasProperty.set(gas);
	}

	@Override
	public void setCapacity(double capacity) {
		capacityProperty.set(capacity);
	}

	@Override
	public void setMaxTemperature(double temperature) {
		maxTemperatureProperty.set(temperature);
	}

	@Override
	public void setMaxPressure(double pressure) {
		maxPressureProperty.set(pressure);
	}

	@Override
	public GasStack getGas() {
		return gasProperty.get();
	}

	@Override
	public double getCapacity() {
		return capacityProperty.get();
	}

	@Override
	public double getMaxTemperature() {
		return maxTemperatureProperty.get();
	}

	@Override
	public double getMaxPressure() {
		return maxPressureProperty.get();
	}

	@Override
	public void onChange() {
		gasProperty.forceDirty();
		if (holder != null) {
			holder.onGasTankChange(this);
		}
	}

	@Override
	public void onOverheat() {
		if(holder != null) {
			Level world = holder.getLevel();
			BlockPos pos = holder.getBlockPos();
			world.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
		}
	}

	@Override
	public void onOverpressure() {
		if(holder != null) {
			Level world = holder.getLevel();
			BlockPos pos = holder.getBlockPos();
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, BlockInteraction.DESTROY);
		}
	}
	
	public PropertyGasTank[] asArray() {
		return new PropertyGasTank[] { this };
	}

	// this must be called to update the server if interacted with on the client
	public void updateServer() {

		if (holder != null) {
			NetworkHandler.CHANNEL.sendToServer(new PacketSendUpdatePropertiesServer(gasProperty.getPropertyManager().getProperties().indexOf(gasProperty), gasProperty, holder.getBlockPos()));
		}

	}

}

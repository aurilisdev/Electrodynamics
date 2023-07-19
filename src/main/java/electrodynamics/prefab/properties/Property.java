package electrodynamics.prefab.properties;

import java.util.function.BiConsumer;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketSendUpdatePropertiesServer;

public class Property<T> {
	private PropertyManager manager;
	private final PropertyType type;
	private boolean isDirty = true;
	private boolean shouldSave = true;
	private boolean shouldUpdateClient = true;
	private String name;
	private T value;
	
	private int index = 0;

	//property has new value and value is old value
	private BiConsumer<Property<T>, T> onChange = (prop, val) -> {
	};
	private BiConsumer<Property<T>, T> onLoad = (prop, val) -> {
	};

	public Property(PropertyType type, String name, T defaultValue) {
		this.type = type;
		if (name == null || name.length() == 0) {
			throw new RuntimeException("The property's name cannot be null or empty");
		}
		this.name = name;
		value = defaultValue;
	}

	public T get() {
		return value;
	}

	public PropertyType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public Property<T> onChange(BiConsumer<Property<T>, T> event) {
		onChange = onChange.andThen(event);
		return this;
	}

	public Property<T> onLoad(BiConsumer<Property<T>, T> event) {
		onLoad = onLoad.andThen(event);
		return this;
	}

	@Deprecated(forRemoval = false, since = "Only use this when absolutely nessisary")
	public void forceDirty() {
		isDirty = true;
		manager.setDirty();
	}

	public void clean() {
		isDirty = false;
	}

	public void setManager(PropertyManager manager) {
		this.manager = manager;
	}

	public Property<T> set(Object updated) {
		verify((T) updated);
		T old = value;
		value = (T) type.attemptCast.apply(updated);
		if(isDirty()) {
			onChange.accept(this, old);
		}

		return this;
	}
	
	public void copy(Property<T> other) {
		T otherVal = other.get();
		if(otherVal == null) {
			return;
		}
		set(otherVal);
	}

	public void verify(T updated) {
		boolean shouldUpdate = value == null && updated != null;
		if (value != null && updated != null) {
			shouldUpdate = !type.predicate.test(value, updated);
		}
		if (shouldUpdate) {
			isDirty = true;
			manager.setDirty();
		}
	}

	public void load(Object val) {
		if (val == null) {
			val = value;
		}
		value = (T) type.attemptCast.apply(val);
		onLoad.accept(this, value);
	}

	public boolean shouldSave() {
		return shouldSave;
	}
	
	public Property<T> setNoSave() {
		shouldSave = false;
		return this;
	}
	
	public boolean shouldUpdateClient() {
		return shouldUpdateClient;
	}
	
	public Property<T> setNoUpdateClient() {
		shouldUpdateClient = false;
		return this;
	}

	@Override
	public String toString() {
		return value == null ? "null" : value.toString();
	}

	public PropertyManager getPropertyManager() {
		return manager;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void updateServer() {
		
		if(manager.getOwner() != null) {
			NetworkHandler.CHANNEL.sendToServer(new PacketSendUpdatePropertiesServer(index, this, manager.getOwner().getBlockPos()));
		}
		
		
	}

}

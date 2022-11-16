package electrodynamics.prefab.properties;

import electrodynamics.prefab.utilities.Scheduler;

public class Property<T> {
	private PropertyManager manager;
	private final PropertyType type;
	private boolean isDirty = true;
	private boolean shouldSave = false;
	private String name;
	private T value;
	private T rawValue;

	public Property(PropertyType type, String name) {
		this.type = type;
		this.name = name;
	}

	public T get() {
		if (rawValue == null) {
			switch (type) { // Fix some possible crashes
			case Boolean:
				return (T) Boolean.FALSE;
			case Byte:
				return (T) (Byte) (byte) 0;
			case CompoundTag:
				break;
			case Double:
				return (T) (Double) 0.0;
			case Float:
				return (T) (Float) 0.0f;
			case Integer:
				return (T) (Integer) 0;
			case InventoryItems:
				break;
			default:
				break;
			}
		}
		return rawValue;
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

	public void setDirty() {
		isDirty = true;
		manager.setDirty();
	}

	public void clean() {
		isDirty = false;
	}

	public void setManager(PropertyManager manager) {
		this.manager = manager;
	}

	public Property<T> set(T updated) {
		return set(updated, false);
	}

	@Deprecated(forRemoval = false) // Try not using this at all and only if you must.
	public Property<T> set(T updated, boolean verifyLater) {
		if (verifyLater) {
			Scheduler.schedule(1, () -> verify(updated));
		} else {
			verify(updated);
		}
		value = (T) type.attemptCast(updated);
		rawValue = value;
		return this;
	}

	@Deprecated(forRemoval = false) // Try not using this at all and only if you must.
	public void setAmbigous(Object val) {
		this.set((T) val);
	}

	public void verify(Object updated) {
		if (value == null ? updated != null : !value.equals(updated)) {
			isDirty = true;
			manager.setDirty();
		}
	}

	public Property<T> save() {
		shouldSave = true;
		return this;
	}

	public boolean shouldSave() {
		return shouldSave;
	}

}

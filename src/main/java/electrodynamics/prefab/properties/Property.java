package electrodynamics.prefab.properties;

import electrodynamics.prefab.utilities.Scheduler;

public class Property<T> {
	private PropertyManager manager;
	private final PropertyType type;
	private boolean isDirty = false;
	private T value;
	private T rawValue;

	public Property(PropertyType type) {
		this.type = type;
	}

	public T get() {
		return rawValue;
	}

	public PropertyType getType() {
		return type;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void clean() {
		isDirty = false;
	}

	public void setManager(PropertyManager manager) {
		this.manager = manager;
	}

	public Property<T> set(Object updated) {
		return set(updated, false);
	}

	@Deprecated(forRemoval = false) // Try not using this at all and only if you must.
	public Property<T> set(Object updated, boolean verifyLater) {
		if (verifyLater) {
			Scheduler.schedule(1, () -> verify(updated));
		} else {
			verify(updated);
		}
		value = (T) updated;
		rawValue = (T) updated;
		return this;
	}

	public void verify(Object updated) {
		if (value == null ? value == updated : !value.equals(updated)) {
			isDirty = true;
			manager.setDirty();
		}
	}
}

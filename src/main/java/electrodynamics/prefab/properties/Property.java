package electrodynamics.prefab.properties;

public class Property<T> {
	private PropertyManager manager;
	private final PropertyType type;
	private boolean isDirty = true;
	private boolean shouldSave = true;
	private String name;
	private T value;
	private T rawValue;

	public Property(PropertyType type, String name, T defaultValue) {
		this.type = type;
		if(name == null || name.length() == 0) {
			throw new RuntimeException("The property's name cannot be null or empty");
		}
		this.name = name;
		value = defaultValue;
		rawValue = defaultValue;
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
	
	@Deprecated(forRemoval = false)
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

	//there is no benefit to delaying value verification. The marginal performance value is traded for potential desync issues
	//you also don't need to set the values like the multiplier on the battery box to one at the beginning of every tick if
	//you do an interrupt-type method of setting them. You can define a default value in the constructor that is saved to NBT
	public Property<T> set(T updated) {
		verify(updated);
		value = (T) type.attemptCast(updated);
		rawValue = value;
		return this;
	}

	@Deprecated(forRemoval = false) // Try not using this at all and only if you must.
	public void setAmbigous(Object val) {
		this.set((T) val);
	}

	public void verify(T updated) {
		boolean shouldUpdate = false;
		if(value == null && updated != null) {
			shouldUpdate = true;
		}
		if(value != null && updated != null) {
			if(type.predicate != null) {
				shouldUpdate = type.predicate.test(value, updated);
			} else {
				shouldUpdate = !value.equals(updated);
			}
		}
		if (shouldUpdate) {
			isDirty = true;
			manager.setDirty();
		}
	}
	
	public void load(Object val) {
		value = (T) type.attemptCast(val);
		rawValue = (T) value;
	}

	public Property<T> noSave() {
		shouldSave = false;
		return this;
	}

	public boolean shouldSave() {
		return shouldSave;
	}
	
	@Override
	public String toString() {
		return rawValue == null ? "null" : rawValue.toString();
	}

}
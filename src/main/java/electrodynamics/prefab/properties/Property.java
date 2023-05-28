package electrodynamics.prefab.properties;

import java.util.function.BiConsumer;

public class Property<T> {
	private PropertyManager manager;
	private final PropertyType type;
	private boolean isDirty = true;
	private boolean shouldSave = true;
	private String name;
	private T value;

	//property has old value and value is new value
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
		onChange.accept(this, (T) updated);
		value = (T) type.attemptCast.apply(updated);

		return this;
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

	@Override
	public String toString() {
		return value == null ? "null" : value.toString();
	}

	public PropertyManager getPropertyManager() {
		return manager;
	}

}

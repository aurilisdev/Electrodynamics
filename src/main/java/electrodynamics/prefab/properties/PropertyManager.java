package electrodynamics.prefab.properties;

import java.util.ArrayList;

import net.minecraft.world.level.block.entity.BlockEntity;

public class PropertyManager {

	private final BlockEntity owner;

	private ArrayList<Property<?>> properties = new ArrayList<>();
	private boolean isDirty = false;

	public PropertyManager(BlockEntity owner) {
		this.owner = owner;
	}

	public <T> Property<T> addProperty(Property<T> prop) {
		properties.add(prop);
		prop.setManager(this);
		prop.setIndex(properties.size() - 1);
		return prop;
	}

	public ArrayList<Property<?>> getProperties() {
		return properties;
	}

	public ArrayList<Property<?>> getPropertiesToSave() {

		ArrayList<Property<?>> toSave = new ArrayList<>();

		for (Property<?> prop : properties) {

			if (prop.isDirty() && prop.shouldSave()) {

				toSave.add(prop);

			} else {

				toSave.add(null);

			}

		}
		
		return toSave;
		
	}
	
	public ArrayList<Property<?>> getClientUpdateProperties() {
		
		ArrayList<Property<?>> toClient = new ArrayList<>();
		
		for(Property<?> prop : properties) {
			
			if(prop.isDirty() && prop.shouldUpdateClient()) {
				
				toClient.add(prop);
				
			} else {
				
				toClient.add(null);
				
			}
			
		}
		
		return toClient;
		
	}

	public void clean() {
		isDirty = false;
		properties.forEach(Property::clean);
	}

	public void update(int indexOf, Object value) {
		properties.get(indexOf).set(value);
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty() {
		isDirty = true;
	}

	@Override
	public String toString() {
		String string = "";
		for (int i = 0; i < properties.size(); i++) {
			string = string + i + ": " + properties.get(i).toString() + "\n";
		}
		return string;
	}

	public BlockEntity getOwner() {
		return owner;
	}
}

package electrodynamics.api.capability.types.boolstorage;

public interface IBooleanStorage {

	void setBoolean(int index, boolean bool);
	
	boolean getBoolean(int index);
}

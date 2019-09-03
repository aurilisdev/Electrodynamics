package physica.forcefield.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.library.location.GridLocation;

import java.util.ArrayList;

public class ConstructorWorldData extends WorldSavedData {

	private static final String DATA_FILE = "constructor_data";

	private static ConstructorWorldData instance;
	private ArrayList<GridLocation> locations = new ArrayList<>();

	public ArrayList<GridLocation> getLocations()
	{
		return locations;
	}

	public ConstructorWorldData(String name)
	{
		super(name);
	}

	public static void load(World world)
	{
		instance = (ConstructorWorldData) world.loadItemData(ConstructorWorldData.class, DATA_FILE);
		if (instance == null)
		{
			instance = new ConstructorWorldData(DATA_FILE);
			System.out.println("Created new constructor world data");
		} else
		{
			System.out.println("Loaded " + instance.locations.size() + " constructor locations");
		}
	}

	public static void register(TileFortronFieldConstructor constructor)
	{
		if (instance.locations.contains(constructor.getLocation()))
		{
			return;
		}
		instance.locations.add(constructor.getLocation());
		instance.markDirty();
		constructor.World().setItemData(DATA_FILE, instance);
		System.out.println("REGISTER: " + constructor + ", " + instance.locations);
	}

	public static void remove(TileFortronFieldConstructor constructor)
	{
		instance.locations.remove(constructor.getLocation());
		instance.markDirty();
		constructor.World().setItemData(DATA_FILE, instance);
		System.out.println("REMOVE: " + constructor + ", " + instance.locations);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		locations.clear();
		int[] locArr = nbt.getIntArray("locations");
		for (int i = 0; i < locArr.length - 2; i += 3)
		{
			locations.add(new GridLocation(locArr[i], locArr[i + 1], locArr[i + 2]));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		int[] locArr = new int[locations.size() * 3];
		int i = 0;
		for (GridLocation location : locations)
		{
			locArr[i] = location.xCoord;
			locArr[i + 1] = location.yCoord;
			locArr[i + 2] = location.zCoord;
			i += 3;
		}
		nbt.setIntArray("locations", locArr);
	}

}


package physica.api.core.abstraction.recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public interface IRecipeRegister {

	public static final HashMap<String, Set<IRecipeRegister>> REGISTER = new HashMap<>();

	public static void callRegister(String id)
	{
		if (!REGISTER.containsKey(id))
		{
			REGISTER.put(id, new HashSet<>());
		}
		for (IRecipeRegister reg : REGISTER.get(id))
		{
			reg.registerRecipes();
		}
	}

	default void addToRegister(String id, IRecipeRegister reg)
	{
		if (!REGISTER.containsKey(id))
		{
			REGISTER.put(id, new HashSet<>());
		}
		REGISTER.get(id).add(reg);
	}

	abstract void registerRecipes();

}

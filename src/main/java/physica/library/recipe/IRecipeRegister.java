package physica.library.recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public interface IRecipeRegister {

	public static final HashMap<RecipeSide, Set<IRecipeRegister>> REGISTER = new HashMap<>();

	public static void InitializeSide(RecipeSide side) {
		for (IRecipeRegister reg : REGISTER.get(side)) {
			reg.initialize();
		}
	}

	default void addToRegister(RecipeSide side, IRecipeRegister reg) {
		if (REGISTER.isEmpty()) {
			for (RecipeSide recipeSide : RecipeSide.values()) {
				REGISTER.put(recipeSide, new HashSet<>());
			}
		}
		REGISTER.get(side).add(reg);
	}

	abstract void initialize();

}

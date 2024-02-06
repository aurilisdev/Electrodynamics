package electrodynamics.compatibility.jei;

import net.neoforged.fml.ModList;

public class JeiBuffer {

	public static boolean isJeiInstalled() {
		return ModList.get().isLoaded("jei");
	}

}

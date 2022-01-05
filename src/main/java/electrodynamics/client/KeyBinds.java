package electrodynamics.client;

import com.mojang.blaze3d.platform.InputConstants;

import electrodynamics.api.References;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyBinds {
	
	//Category
	private static final String ELECTRODYNAMICS_CATEGORY = "keycategory.electrodynamics";
	
	//KEYS
	public static KeyMapping jetpackAscend;
	public static KeyMapping toggleNvgs;
	
	private KeyBinds() {}
	
	public static void registerKeys() {
		jetpackAscend = registerKey("jetpackascend", ELECTRODYNAMICS_CATEGORY, InputConstants.KEY_SPACE);
		toggleNvgs = registerKey("togglenvgs", ELECTRODYNAMICS_CATEGORY, InputConstants.KEY_N);
	}
	
	private static KeyMapping registerKey(String name, String category, int keyCode) {
		final var key = new KeyMapping("key." + References.ID + "." + name, keyCode, category);
		ClientRegistry.registerKeyBinding(key);
		return key;
	}

}

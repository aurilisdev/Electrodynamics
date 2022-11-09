package electrodynamics.client;

import com.mojang.blaze3d.platform.InputConstants;

import electrodynamics.api.References;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class KeyBinds {

	// Category
	private static final String ELECTRODYNAMICS_CATEGORY = "keycategory.electrodynamics";

	// KEYS
	public static KeyMapping jetpackAscend;
	public static KeyMapping switchJetpackMode;
	public static KeyMapping toggleNvgs;
	public static KeyMapping switchServoLeggingsMode;
	public static KeyMapping toggleServoLeggings;

	private KeyBinds() {
	}

	@SubscribeEvent
	public static void keyEVent(RegisterKeyMappingsEvent event) {
		jetpackAscend = registerKey("jetpackascend", ELECTRODYNAMICS_CATEGORY, InputConstants.KEY_SPACE, event);
		switchJetpackMode = registerKey("jetpackmode", ELECTRODYNAMICS_CATEGORY, InputConstants.KEY_M, event);
		toggleNvgs = registerKey("togglenvgs", ELECTRODYNAMICS_CATEGORY, InputConstants.KEY_N, event);
		switchServoLeggingsMode = registerKey("servoleggingsmode", ELECTRODYNAMICS_CATEGORY, InputConstants.KEY_L, event);
		toggleServoLeggings = registerKey("toggleservoleggings", ELECTRODYNAMICS_CATEGORY, InputConstants.KEY_K, event);

	}

	private static KeyMapping registerKey(String name, String category, int keyCode, RegisterKeyMappingsEvent event) {
		final var key = new KeyMapping("key." + References.ID + "." + name, keyCode, category);
		event.register(key);
		return key;
	}

}

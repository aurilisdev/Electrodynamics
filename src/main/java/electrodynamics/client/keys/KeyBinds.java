package electrodynamics.client.keys;

import com.mojang.blaze3d.platform.InputConstants;

import electrodynamics.Electrodynamics;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD, value = { Dist.CLIENT })
public class KeyBinds {

    // Category
    private static final String ELECTRODYNAMICS_CATEGORY = "keycategory.electrodynamics";

    // KEYS
    public static KeyMapping jetpackAscend = createKeyMapping("jetpackascend", InputConstants.KEY_SPACE);
    public static KeyMapping switchJetpackMode = createKeyMapping("jetpackmode", InputConstants.KEY_M);
    public static KeyMapping toggleNvgs = createKeyMapping("togglenvgs", InputConstants.KEY_N);
    public static KeyMapping switchServoLeggingsMode = createKeyMapping("servoleggingsmode", InputConstants.KEY_L);
    public static KeyMapping toggleServoLeggings = createKeyMapping("toggleservoleggings", InputConstants.KEY_K);
    public static KeyMapping swapBattery = createKeyMapping("swapbattery", InputConstants.KEY_R);

    @SubscribeEvent
    public static void keyEvent(RegisterKeyMappingsEvent event) {
	event.register(jetpackAscend);
	event.register(switchJetpackMode);
	event.register(toggleNvgs);
	event.register(switchServoLeggingsMode);
	event.register(toggleServoLeggings);
	event.register(swapBattery);
    }

    private static KeyMapping createKeyMapping(String name, int keyCode) {
	return new KeyMapping("key." + Electrodynamics.ID + "." + name, keyCode, KeyBinds.ELECTRODYNAMICS_CATEGORY);
    }

}

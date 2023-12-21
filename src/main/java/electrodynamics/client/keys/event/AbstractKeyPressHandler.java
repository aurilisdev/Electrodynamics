package electrodynamics.client.keys.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;

public abstract class AbstractKeyPressHandler {

	public abstract void handler(KeyInputEvent event, Minecraft minecraft);

}
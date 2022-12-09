package electrodynamics.client.render.event.levelstage;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;

public abstract class AbstractLevelStageHandler {
	
	public abstract boolean shouldRender(Stage stage);
	
	public abstract void render(RenderLevelStageEvent event, Minecraft minecraft);
	
	public void clear() {
		
	}

}

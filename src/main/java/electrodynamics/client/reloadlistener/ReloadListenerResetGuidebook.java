package electrodynamics.client.reloadlistener;

import electrodynamics.Electrodynamics;
import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

/**
 * Avert your eyes kids
 * 
 * @author skip999
 *
 */
public class ReloadListenerResetGuidebook extends SimplePreparableReloadListener<Integer> {

	@Override
	protected Integer prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
		return 0;
	}

	@Override
	protected void apply(Integer number, ResourceManager resourceManager, ProfilerFiller profiler) {
		Electrodynamics.LOGGER.info("Resetting from client");
		ScreenGuidebook.setInitNotHappened();
	}

	@Override
	public String getName() {
		return "Electrodynamics Guidebook Listener";
	}

}

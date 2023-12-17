package electrodynamics.prefab.reloadlistener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

public abstract class AbstractReloadListener<T> implements IFutureReloadListener {
	public final CompletableFuture<Void> reload(IFutureReloadListener.IStage pStage, IResourceManager pResourceManager, IProfiler pPreparationsProfiler, IProfiler pReloadProfiler, Executor pBackgroundExecutor, Executor pGameExecutor) {
		return CompletableFuture.supplyAsync(() -> {
			return this.prepare(pResourceManager, pPreparationsProfiler);
		}, pBackgroundExecutor).thenCompose(pStage::wait).thenAcceptAsync((p_215269_3_) -> {
			this.apply(p_215269_3_, pResourceManager, pReloadProfiler);
		}, pGameExecutor);
	}

	/**
	 * Performs any reloading that can be done off-thread, such as file IO
	 */
	protected abstract T prepare(IResourceManager pResourceManager, IProfiler pProfiler);

	protected abstract void apply(T pObject, IResourceManager pResourceManager, IProfiler pProfiler);
}

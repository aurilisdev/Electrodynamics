package electrodynamics.common.reloadlistener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.common.packet.types.client.PacketSetClientCombustionFuel;
import electrodynamics.prefab.reloadlistener.AbstractReloadListener;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CombustionFuelRegister extends AbstractReloadListener<HashSet<JsonObject>> {

	public static CombustionFuelRegister INSTANCE = null;

	public static final String FOLDER = "machines/combustion_fuel";

	protected static final String JSON_EXTENSION = ".json";
	protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

	private static final Gson GSON = new Gson();

	private final HashSet<CombustionFuelSource> fuels = new HashSet<>();

	private final Logger logger = Electrodynamics.LOGGER;

	@Override
	protected HashSet<JsonObject> prepare(IResourceManager manager, IProfiler profiler) {

		HashSet<JsonObject> fuels = new HashSet<>();

		List<ResourceLocation> resources = new ArrayList<>(manager.listResources(FOLDER, CombustionFuelRegister::isStringJsonFile));

		Collections.reverse(resources);

		for (ResourceLocation entry : resources) {

			final String namespace = entry.getNamespace();
			final String filePath = entry.getPath();
			final String dataPath = filePath.substring(FOLDER.length() + 1, filePath.length() - JSON_EXTENSION_LENGTH);

			final ResourceLocation jsonFile = new ResourceLocation(namespace, dataPath);

			try {

				for (IResource resource : manager.getResources(entry)) {

					try (final InputStream inputStream = resource.getInputStream(); final Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {

						final JsonObject json = (JsonObject) JSONUtils.fromJson(GSON, reader, JsonElement.class);

						fuels.add(json);

					} catch (RuntimeException | IOException exception) {

						logger.error("Data loader for {} could not read data {} from file {} in data pack {}", FOLDER, jsonFile, resource, resource.getSourceName(), exception);

					} finally {

						IOUtils.closeQuietly(resource);

					}

				}

			} catch (IOException exception) {

				this.logger.error("Data loader for {} could not read data {} from file {}", FOLDER, jsonFile, entry, exception);

			}

		}

		return fuels;
	}

	@Override
	protected void apply(HashSet<JsonObject> jsons, IResourceManager manager, IProfiler profiler) {
		fuels.clear();
		for (JsonObject json : jsons) {
			fuels.add(CombustionFuelSource.fromJson(json));
		}
	}

	public HashSet<CombustionFuelSource> getFuels() {
		return fuels;
	}

	public void setClientValues(HashSet<CombustionFuelSource> values) {
		fuels.clear();
		fuels.addAll(values);
	}

	public IOptionalNamedTag<Fluid>[] getFluidTags() {
		List<IOptionalNamedTag<Fluid>> values = new ArrayList<>();
		for (CombustionFuelSource source : fuels) {
			values.add(source.getTag());
		}
		IOptionalNamedTag<Fluid>[] arr = new IOptionalNamedTag[values.size()];
		return values.toArray(arr);
	}

	public CombustionFuelSource getFuelFromFluid(FluidStack stack) {
		for (CombustionFuelSource fuel : fuels) {
			if (fuel.isFuelSource(stack)) {
				return fuel;
			}
		}
		return CombustionFuelSource.EMPTY;
	}

	public CombustionFuelRegister subscribeAsSyncable(final SimpleChannel channel) {
		MinecraftForge.EVENT_BUS.addListener(getDatapackSyncListener(channel));
		return this;
	}

	private Consumer<OnDatapackSyncEvent> getDatapackSyncListener(final SimpleChannel channel) {
		return event -> {
			ServerPlayerEntity player = event.getPlayer();
			PacketSetClientCombustionFuel packet = new PacketSetClientCombustionFuel(fuels);
			PacketTarget target = player == null ? PacketDistributor.ALL.noArg() : PacketDistributor.PLAYER.with(() -> player);
			channel.send(target, packet);
		};
	}

	private static boolean isStringJsonFile(final String filename) {
		return filename.endsWith(JSON_EXTENSION);
	}

}

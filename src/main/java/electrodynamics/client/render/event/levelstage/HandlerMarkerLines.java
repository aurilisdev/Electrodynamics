package electrodynamics.client.render.event.levelstage;

import java.util.HashMap;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;

public class HandlerMarkerLines extends AbstractLevelStageHandler {

	public static final HandlerMarkerLines INSTANCE = new HandlerMarkerLines();
	
	private final HashMap<BlockPos, List<AABB>> markerLines = new HashMap<>();
	
	@Override
	public boolean shouldRender(Stage stage) {
		return stage == Stage.AFTER_TRANSLUCENT_BLOCKS;
	}

	@Override
	public void render(RenderLevelStageEvent event, Minecraft minecraft) {
		
		MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
		PoseStack matrix = event.getPoseStack();
		GameRenderer renderer = minecraft.gameRenderer;
		Vec3 camera = renderer.getMainCamera().getPosition();
		
		VertexConsumer sheetBuilder = buffer.getBuffer(RenderingUtils.beaconType());
		markerLines.forEach((pos, list) -> list.forEach(aabb -> {
			matrix.pushPose();
			matrix.translate(-camera.x, -camera.y, -camera.z);
			RenderingUtils.renderSolidColorBox(matrix, minecraft, sheetBuilder, aabb, 1.0F, 0F, 0F, 1.0F, 255, 0);
			matrix.popPose();
		}));
		buffer.endBatch(RenderingUtils.beaconType());
		
	}
	
	@Override
	public void clear() {
		markerLines.clear();
	}
	
	public static boolean containsLines(BlockPos pos) {
		return INSTANCE.markerLines.containsKey(pos);
	}
	
	public static void addLines(BlockPos pos, List<AABB> lines) {
		INSTANCE.markerLines.put(pos, lines);
	}
	
	public static void removeLines(BlockPos pos) {
		INSTANCE.markerLines.remove(pos);
	}

}

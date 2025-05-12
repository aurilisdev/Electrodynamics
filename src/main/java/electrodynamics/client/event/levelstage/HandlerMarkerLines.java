package electrodynamics.client.event.levelstage;

import java.util.HashMap;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import voltaic.client.event.AbstractLevelStageHandler;
import voltaic.prefab.utilities.RenderingUtils;

public class HandlerMarkerLines extends AbstractLevelStageHandler {

	public static final HandlerMarkerLines INSTANCE = new HandlerMarkerLines();

	private final HashMap<BlockPos, List<AxisAlignedBB>> markerLines = new HashMap<>();
	@Override
	public void render(WorldRenderer context, MatrixStack stack, float partialTicks, Matrix4f projectionMatrix, long finishTimeNano) {
		Minecraft minecraft = Minecraft.getInstance();
		IRenderTypeBuffer.Impl buffer = minecraft.renderBuffers().bufferSource();
		RenderType beaconType = RenderingUtils.beaconType();
		IVertexBuilder sheetBuilder = buffer.getBuffer(beaconType);
		Vector3d camPos = minecraft.gameRenderer.getMainCamera().getPosition();

		markerLines.forEach((pos, list) -> list.forEach(aabb -> {
			stack.pushPose();
			stack.translate(-camPos.x, -camPos.y, -camPos.z);
			RenderingUtils.renderSolidColorBox(stack, minecraft, sheetBuilder, aabb, 1.0F, 0F, 0F, 1.0F, 255, 0, RenderingUtils.ALL_FACES);
			stack.popPose();
		}));
		buffer.endBatch(beaconType);

	}

	@Override
	public void clear() {
		markerLines.clear();
	}

	public static boolean containsLines(BlockPos pos) {
		return INSTANCE.markerLines.containsKey(pos);
	}

	public static void addLines(BlockPos pos, List<AxisAlignedBB> lines) {
		INSTANCE.markerLines.put(pos, lines);
	}

	public static void removeLines(BlockPos pos) {
		INSTANCE.markerLines.remove(pos);
	}

}

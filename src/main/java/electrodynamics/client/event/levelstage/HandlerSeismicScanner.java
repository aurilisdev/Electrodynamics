package electrodynamics.client.event.levelstage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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

public class HandlerSeismicScanner extends AbstractLevelStageHandler {

	public static final HandlerSeismicScanner INSTANCE = new HandlerSeismicScanner();

	private final HashMap<BlockPos, Long> pingedBlocks = new HashMap<>();

	@Override
	public void render(WorldRenderer context, MatrixStack stack, float partialTicks, Matrix4f projectionMatrix, long finishTimeNano) {
		Minecraft minecraft = Minecraft.getInstance();
		IRenderTypeBuffer.Impl buffer = minecraft.renderBuffers().bufferSource();
		IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
		Vector3d camPos = minecraft.gameRenderer.getMainCamera().getPosition();

		Iterator<Entry<BlockPos, Long>> it = pingedBlocks.entrySet().iterator();
		while (it.hasNext()) {
			Entry<BlockPos, Long> entry = it.next();
			AxisAlignedBB box = new AxisAlignedBB(entry.getKey());
			stack.pushPose();
			stack.translate(-camPos.x, -camPos.y, -camPos.z);
			WorldRenderer.renderLineBox(stack, builder, box, 1.0F, 1.0F, 1.0F, 1.0F);
			stack.popPose();
			if (System.currentTimeMillis() - entry.getValue() > 10000 || minecraft.level.getBlockState(entry.getKey()).isAir(minecraft.level, entry.getKey())) {
				it.remove();
			}
		}
		buffer.endBatch(RenderType.LINES);
	}

	@Override
	public void clear() {
		pingedBlocks.clear();
	}

	public static void addBlock(BlockPos pos) {
		INSTANCE.pingedBlocks.put(pos, System.currentTimeMillis());
	}


}

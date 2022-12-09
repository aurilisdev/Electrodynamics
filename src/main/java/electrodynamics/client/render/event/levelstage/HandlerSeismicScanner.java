package electrodynamics.client.render.event.levelstage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;

public class HandlerSeismicScanner extends AbstractLevelStageHandler {

	public static final HandlerSeismicScanner INSTANCE = new HandlerSeismicScanner();
	
	private final HashMap<BlockPos, Long> pingedBlocks = new HashMap<>();
	
	@Override
	public boolean shouldRender(Stage stage) {
		return stage == Stage.AFTER_TRANSLUCENT_BLOCKS;
	}

	@Override
	public void render(RenderLevelStageEvent event, Minecraft minecraft) {
		
		PoseStack matrix = event.getPoseStack();
		MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
		VertexConsumer builder = buffer.getBuffer(RenderType.LINES);
		GameRenderer renderer = minecraft.gameRenderer;
		Vec3 camera = renderer.getMainCamera().getPosition();
		
		Iterator<Entry<BlockPos, Long>> it = pingedBlocks.entrySet().iterator();
		while (it.hasNext()) {
			Entry<BlockPos, Long> entry = it.next();
			AABB box = new AABB(entry.getKey());
			matrix.pushPose();
			matrix.translate(-camera.x, -camera.y, -camera.z);
			LevelRenderer.renderLineBox(matrix, builder, box, 1.0F, 1.0F, 1.0F, 1.0F);
			matrix.popPose();
			if (System.currentTimeMillis() - entry.getValue() > 10000) {
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

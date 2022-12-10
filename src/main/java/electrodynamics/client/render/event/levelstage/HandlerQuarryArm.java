package electrodynamics.client.render.event.levelstage;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.object.QuarryArmDataHolder;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;

public class HandlerQuarryArm extends AbstractLevelStageHandler {

	public static final HandlerQuarryArm INSTANCE = new HandlerQuarryArm();
	
	private final HashMap<BlockPos, QuarryArmDataHolder> armsToRender = new HashMap<>();
	
	@Override
	public boolean shouldRender(Stage stage) {
		return stage == Stage.AFTER_TRANSLUCENT_BLOCKS;
	}

	@Override
	public void render(Camera camera, Frustum frustum, LevelRenderer renderer, PoseStack stack,
			Matrix4f projectionMatrix, Minecraft minecraft, int renderTick, float partialTick) {
		
		MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
		Vec3 camPos = camera.getPosition();
		
		TextureAtlasSprite cornerFrame = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(ClientRegister.TEXTURE_QUARRYARM);
		float u0Frame = cornerFrame.getU0();
		float u1Frame = cornerFrame.getU1();
		float v0Frame = cornerFrame.getV0();
		float v1Frame = cornerFrame.getV1();
		float[] colorsFrame = RenderingUtils.getColorArray(cornerFrame.getPixelRGBA(0, 10, 10));

		TextureAtlasSprite titanium = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(SubtypeDrillHead.titanium.blockTextureLoc);
		float u0Titanium = titanium.getU0();
		float u1Titanium = titanium.getU1();
		float v0Titanium = titanium.getV0();
		float v1Titanium = titanium.getV1();
		float[] colorsTitanium = RenderingUtils.getColorArray(cornerFrame.getPixelRGBA(0, 10, 10));

		VertexConsumer armBuilder = buffer.getBuffer(Sheets.solidBlockSheet());

		armsToRender.forEach((pos, data) -> {
			data.armParts.forEach(aabb -> {
				stack.pushPose();
				stack.translate(-camPos.x, -camPos.y, -camPos.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsFrame[0], colorsFrame[1], colorsFrame[2], colorsFrame[3], u0Frame, v0Frame, u1Frame, v1Frame, 255);
				stack.popPose();
			});
			data.titaniumParts.forEach(aabb -> {
				stack.pushPose();
				stack.translate(-camPos.x, -camPos.y, -camPos.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsTitanium[0], colorsTitanium[1], colorsTitanium[2], colorsTitanium[3], u0Titanium, v0Titanium, u1Titanium, v1Titanium, 255);
				stack.popPose();
			});
			if (data.headType != null) {
				stack.pushPose();
				stack.translate(-camPos.x, -camPos.y, -camPos.z);
				TextureAtlasSprite headText = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(data.headType.blockTextureLoc);
				float u0Head = headText.getU0();
				float u1Head = headText.getU1();
				float v0Head = headText.getV0();
				float v1Head = headText.getV1();
				float[] colorsHead = RenderingUtils.getColorArray(cornerFrame.getPixelRGBA(0, 10, 10));
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, data.drillHead, colorsHead[0], colorsHead[1], colorsHead[2], colorsHead[3], u0Head, v0Head, u1Head, v1Head, 255);
				stack.popPose();
			}
		});

		buffer.endBatch(Sheets.solidBlockSheet());
	}
	
	@Override
	public void clear() {
		armsToRender.clear();
	}
	
	public static void addRenderData(BlockPos pos, QuarryArmDataHolder data) {
		INSTANCE.armsToRender.put(pos, data);
	}
	
	public static void removeRenderData(BlockPos pos) {
		INSTANCE.armsToRender.remove(pos);
	}
	

}

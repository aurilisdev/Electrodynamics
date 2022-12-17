package electrodynamics.client.render.event.levelstage;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;

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
		
		TextureAtlasSprite armTexture = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(ClientRegister.TEXTURE_QUARRYARM);
		float u0Frame = armTexture.getU0();
		float u1Frame = armTexture.getU1();
		float v0Frame = armTexture.getV0();
		float v1Frame = armTexture.getV1();
		float[] colorsFrame = RenderingUtils.getColorArray(armTexture.getPixelRGBA(0, 10, 10));
		
		TextureAtlasSprite darkArmTexture = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(ClientRegister.TEXTURE_QUARRYARM_DARK);
		float u0FrameDark = darkArmTexture.getU0();
		float u1FrameDark = darkArmTexture.getU1();
		float v0FrameDark = darkArmTexture.getV0();
		float v1FrameDark = darkArmTexture.getV1();
		float[] colorsFrameDark = RenderingUtils.getColorArray(armTexture.getPixelRGBA(0, 10, 10));

		TextureAtlasSprite titaniumTexture = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(SubtypeDrillHead.titanium.blockTextureLoc);
		float u0Titanium = titaniumTexture.getU0();
		float u1Titanium = titaniumTexture.getU1();
		float v0Titanium = titaniumTexture.getV0();
		float v1Titanium = titaniumTexture.getV1();
		float[] colorsTitanium = RenderingUtils.getColorArray(armTexture.getPixelRGBA(0, 10, 10));

		VertexConsumer armBuilder = buffer.getBuffer(Sheets.solidBlockSheet());

		armsToRender.forEach((pos, data) -> {
			//assures a smoother rotation
			long gameTime = minecraft.level.getGameTime();
			long speed = data.speed();
			if(data.speed() < 4) {
				speed = 4;
			}
			int multipler = (int) (gameTime % speed);
			float degressRot = partialTick * 10F;
	
			data.armParts().forEach(aabb -> {
				stack.pushPose();
				stack.translate(-camPos.x, -camPos.y, -camPos.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsFrame[0], colorsFrame[1], colorsFrame[2], colorsFrame[3], u0Frame, v0Frame, u1Frame, v1Frame, 255);
				stack.popPose();
			});
			data.verticalShaftRotateParts().forEach(aabb -> {
				stack.pushPose();
				stack.translate(-camPos.x, -camPos.y, -camPos.z);
				if(data.shouldRotate()) {
					//stack.mulPose(new Quaternion(0, degressRot, 0, true));
				}
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsFrameDark[0], colorsFrameDark[1], colorsFrameDark[2], colorsFrameDark[3], u0FrameDark, v0FrameDark, u1FrameDark, v1FrameDark, 255);
				stack.popPose();
			});
			data.titaniumParts().forEach(aabb -> {
				stack.pushPose();
				stack.translate(-camPos.x, -camPos.y, -camPos.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsTitanium[0], colorsTitanium[1], colorsTitanium[2], colorsTitanium[3], u0Titanium, v0Titanium, u1Titanium, v1Titanium, 255);
				stack.popPose();
			});
			if (data.headType() != null) {
				stack.pushPose();
				stack.translate(-camPos.x, -camPos.y, -camPos.z);
				TextureAtlasSprite headText = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(data.headType().blockTextureLoc);
				float u0Head = headText.getU0();
				float u1Head = headText.getU1();
				float v0Head = headText.getV0();
				float v1Head = headText.getV1();
				float[] colorsHead = RenderingUtils.getColorArray(armTexture.getPixelRGBA(0, 10, 10));
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, data.drillHead(), colorsHead[0], colorsHead[1], colorsHead[2], colorsHead[3], u0Head, v0Head, u1Head, v1Head, 255);
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

package electrodynamics.client.render.event.levelstage;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;

import electrodynamics.client.ClientRegister;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.PrecisionVector;
import electrodynamics.prefab.utilities.object.QuarryArmDataHolder;
import mezz.jei.common.gui.ingredients.RendererOverrides;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.AABB;
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

		TextureAtlasSprite whiteTexture = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(ClientRegister.TEXTURE_WHITE);
		float u0White = whiteTexture.getU0();
		float u1White = whiteTexture.getU1();
		float v0White = whiteTexture.getV0();
		float v1White = whiteTexture.getV1();
		
		VertexConsumer armBuilder = buffer.getBuffer(Sheets.solidBlockSheet());
		
		
		stack.pushPose();
		stack.translate(-camPos.x, -camPos.y, -camPos.z);
		armsToRender.forEach((pos, data) -> {
			data.lightParts().forEach(pair -> {
				PrecisionVector vec = pair.getFirst();
				AABB aabb = vec.shiftRemainder(pair.getSecond());
				if(!frustum.isVisible(vec.shiftWhole(aabb))) {
					return;
				}
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsFrame[0], colorsFrame[1], colorsFrame[2], colorsFrame[3], u0Frame, v0Frame, u1Frame, v1Frame, 255);
				stack.popPose();
			});
			data.darkParts().forEach(pair -> {
				PrecisionVector vec = pair.getFirst();
				AABB aabb = vec.shiftRemainder(pair.getSecond());
				if(!frustum.isVisible(vec.shiftWhole(aabb))) {
					return;
				}
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsFrameDark[0], colorsFrameDark[1], colorsFrameDark[2], colorsFrameDark[3], u0FrameDark, v0FrameDark, u1FrameDark, v1FrameDark, 255);
				stack.popPose();
			});
			data.titaniumParts().forEach(pair -> {
				PrecisionVector vec = pair.getFirst();
				AABB aabb = vec.shiftRemainder(pair.getSecond());
				if(!frustum.isVisible(vec.shiftWhole(aabb))) {
					return;
				}
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsTitanium[0], colorsTitanium[1], colorsTitanium[2], colorsTitanium[3], u0Titanium, v0Titanium, u1Titanium, v1Titanium, 255);
				stack.popPose();
			});
			if (data.headType() != null) {
				PrecisionVector vec = data.drillHead().getFirst();
				AABB aabb = vec.shiftRemainder(data.drillHead().getSecond());
				if(!frustum.isVisible(vec.shiftWhole(aabb))) {
					return;
				}
				TextureAtlasSprite headText = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(data.headType().blockTextureLoc);
				float u0Head = headText.getU0();
				float u1Head = headText.getU1();
				float v0Head = headText.getV0();
				float v1Head = headText.getV1();
				float[] colorsHead = RenderingUtils.getColorArray(armTexture.getPixelRGBA(0, 10, 10));
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				if(data.running()) {
					float speed = (float) Math.max(data.speed(), 5.0);
					float progress = data.progress();
					if(speed == 4.0) {
						progress = Math.abs(minecraft.level.getGameTime() % 5);
					}
					float degrees = 360.0F * (progress / speed);
					stack.translate(vec.remX + 0.5, 0, vec.remZ + 0.5);
					stack.mulPose(new Quaternion(0, degrees, 0, true));
					stack.translate(-vec.remX - 0.5, 0, -vec.remZ - 0.5);
				}
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorsHead[0], colorsHead[1], colorsHead[2], colorsHead[3], u0Head, v0Head, u1Head, v1Head, 255);
				stack.popPose();
			}
			stack.pushPose();
			BlockPos start = data.corners().get(0);
			BlockPos nearCorner = data.corners().get(1);
			BlockPos farCorner = data.corners().get(2);
			BlockPos end = data.corners().get(3);
			
			double deltaX = nearCorner.getX() - start.getX();
			double deltaZ = nearCorner.getZ() - start.getZ();
			double quarterDeltaX = deltaX / 4.0;
			double quarterDeltaZ = deltaZ / 4.0;
			
			stack.translate(start.getX(), start.getY(), start.getZ());
			
			AABB beam1 = new AABB(0.4375, 0.5625, 0.4375, quarterDeltaX + 0.5, 0.6875, quarterDeltaZ + 0.5625);
			
			RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, beam1, 1.0F, 0, 0.01F, 1.0F, u0Titanium, v0Titanium, u1Titanium, v1Titanium, 255);
			stack.popPose();
		});
		
		buffer.endBatch(Sheets.solidBlockSheet());
		
		armsToRender.forEach((pos, data) -> {
			BakedModel wheelStill = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_QUARRYWHEEL_STILL);
			BakedModel wheelRot = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_QUARRYWHEEL_ROT);
			
			stack.pushPose();
			
			PrecisionVector vec = data.leftWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.leftWheel().yAxisRotation(), 0, true));
			
			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.leftWheel().xAxisRotation(), 0, data.leftWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);
			
			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.popPose();
			
			stack.popPose();
			
			stack.pushPose();
			
			vec = data.rightWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.rightWheel().yAxisRotation(), 0, true));
			
			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.rightWheel().xAxisRotation(), 0, data.rightWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);
			
			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.popPose();
			
			stack.popPose();
			
			stack.pushPose();
			
			vec = data.bottomWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.bottomWheel().yAxisRotation(), 0, true));
			
			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.bottomWheel().xAxisRotation(), 0, data.bottomWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);
			
			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.popPose();
			
			stack.popPose();
			
			stack.pushPose();
			
			vec = data.topWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.topWheel().yAxisRotation(), 0, true));
			
			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.topWheel().xAxisRotation(), 0, data.topWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);
			
			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, LevelRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);
			
			stack.popPose();
			
			stack.popPose();
		});
		
		stack.popPose();
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

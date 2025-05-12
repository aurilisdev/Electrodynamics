package electrodynamics.client.event.levelstage;

import java.util.HashMap;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.prefab.utilities.object.QuarryArmDataHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.event.AbstractLevelStageHandler;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;
import voltaic.prefab.utilities.math.PrecisionVector;

public class HandlerQuarryArm extends AbstractLevelStageHandler {

	public static final HandlerQuarryArm INSTANCE = new HandlerQuarryArm();

	private final HashMap<BlockPos, QuarryArmDataHolder> armsToRender = new HashMap<>();
	
	@Override
	public void render(WorldRenderer context, MatrixStack stack, float partialTicks, Matrix4f projectionMatrix, long finishTimeNano) {
		Minecraft minecraft = Minecraft.getInstance();
		
		//ViewFrustum frustum = context.viewArea;
		
		//if(frustum == null) {
		//	Voltaic.LOGGER.info("NUll Frustum");
		//	return;
		//}
		
		//frustum.chunks

		IRenderTypeBuffer.Impl buffer = minecraft.renderBuffers().bufferSource();
		Vector3d camPos = minecraft.gameRenderer.getMainCamera().getPosition();

		TextureAtlasSprite armTexture = ElectrodynamicsClientRegister.getSprite(ElectrodynamicsClientRegister.TEXTURE_QUARRYARM);
		float u0Frame = armTexture.getU0();
		float u1Frame = armTexture.getU1();
		float v0Frame = armTexture.getV0();
		float v1Frame = armTexture.getV1();
		Color colorFrame = new Color(armTexture.getPixelRGBA(0, 10, 10));

		TextureAtlasSprite darkArmTexture = ElectrodynamicsClientRegister.getSprite(ElectrodynamicsClientRegister.TEXTURE_QUARRYARM_DARK);
		float u0FrameDark = darkArmTexture.getU0();
		float u1FrameDark = darkArmTexture.getU1();
		float v0FrameDark = darkArmTexture.getV0();
		float v1FrameDark = darkArmTexture.getV1();
		Color colorFrameDark = new Color(armTexture.getPixelRGBA(0, 10, 10));

		TextureAtlasSprite titaniumTexture = minecraft.getTextureAtlas(ElectrodynamicsClientRegister.BLOCK_ATLAS).apply(SubtypeDrillHead.titanium.blockTextureLoc);
		float u0Titanium = titaniumTexture.getU0();
		float u1Titanium = titaniumTexture.getU1();
		float v0Titanium = titaniumTexture.getV0();
		float v1Titanium = titaniumTexture.getV1();
		Color colorTitanium = new Color(armTexture.getPixelRGBA(0, 10, 10));

		TextureAtlasSprite whiteTexture = VoltaicClientRegister.whiteSprite();
		float u0White = whiteTexture.getU0();
		float u1White = whiteTexture.getU1();
		float v0White = whiteTexture.getV0();
		float v1White = whiteTexture.getV1();

		IVertexBuilder armBuilder = buffer.getBuffer(Atlases.solidBlockSheet());

		stack.pushPose();
		stack.translate(-camPos.x, -camPos.y, -camPos.z);
		armsToRender.forEach((pos, data) -> {
			data.lightParts().forEach(pair -> {
				PrecisionVector vec = pair.getFirst();
				AxisAlignedBB aabb = vec.shiftRemainder(pair.getSecond());
				//if (!frustum.isVisible(vec.shiftWhole(aabb))) {
				//	return;
				//}
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorFrame.rFloat(), colorFrame.gFloat(), colorFrame.bFloat(), colorFrame.aFloat(), u0Frame, v0Frame, u1Frame, v1Frame, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), RenderingUtils.ALL_FACES);
				stack.popPose();
			});
			data.darkParts().forEach(pair -> {
				PrecisionVector vec = pair.getFirst();
				AxisAlignedBB aabb = vec.shiftRemainder(pair.getSecond());
				//if (!frustum.isVisible(vec.shiftWhole(aabb))) {
				//	return;
				//}
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorFrameDark.rFloat(), colorFrameDark.gFloat(), colorFrameDark.bFloat(), colorFrameDark.aFloat(), u0FrameDark, v0FrameDark, u1FrameDark, v1FrameDark, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), RenderingUtils.ALL_FACES);
				stack.popPose();
			});
			data.titaniumParts().forEach(pair -> {
				PrecisionVector vec = pair.getFirst();
				AxisAlignedBB aabb = vec.shiftRemainder(pair.getSecond());
				//if (!frustum.isVisible(vec.shiftWhole(aabb))) {
				//	return;
				//}
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorTitanium.rFloat(), colorTitanium.gFloat(), colorTitanium.bFloat(), colorTitanium.aFloat(), u0Titanium, v0Titanium, u1Titanium, v1Titanium, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), RenderingUtils.ALL_FACES);
				stack.popPose();
			});
			if (data.headType() != null) {
				PrecisionVector vec = data.drillHead().getFirst();
				AxisAlignedBB aabb = vec.shiftRemainder(data.drillHead().getSecond());
				//if (!frustum.isVisible(vec.shiftWhole(aabb))) {
				//	return;
				//}

				TextureAtlasSprite headText = minecraft.getTextureAtlas(ElectrodynamicsClientRegister.BLOCK_ATLAS).apply(data.headType().blockTextureLoc);
				float u0Head = headText.getU0();
				float u1Head = headText.getU1();
				float v0Head = headText.getV0();
				float v1Head = headText.getV1();
				Color colorHead = new Color(armTexture.getPixelRGBA(0, 10, 10));
				stack.pushPose();
				stack.translate(vec.x, vec.y, vec.z);
				if (data.running()) {
					float speed = (float) Math.max(data.speed(), 5.0);
					float progress = data.progress();
					if (speed >= 4.0) {
						progress = Math.abs(minecraft.level.getGameTime() % 5);
					}
					float degrees = 360.0F * (progress / speed);
					stack.translate(vec.remX + 0.5, 0, vec.remZ + 0.5);
					stack.mulPose(new Quaternion(0, degrees, 0, true));
					stack.translate(-vec.remX - 0.5, 0, -vec.remZ - 0.5);
				}
				RenderingUtils.renderFilledBoxNoOverlay(stack, armBuilder, aabb, colorHead.rFloat(), colorHead.gFloat(), colorHead.bFloat(), colorHead.aFloat(), u0Head, v0Head, u1Head, v1Head, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), RenderingUtils.ALL_FACES);
				stack.popPose();
			}

		});

		buffer.endBatch(Atlases.solidBlockSheet());

		armsToRender.forEach((pos, data) -> {
			IBakedModel wheelStill = Minecraft.getInstance().getModelManager().getModel(ElectrodynamicsClientRegister.MODEL_QUARRYWHEEL_STILL);
			IBakedModel wheelRot = Minecraft.getInstance().getModelManager().getModel(ElectrodynamicsClientRegister.MODEL_QUARRYWHEEL_ROT);

			stack.pushPose();

			PrecisionVector vec = data.leftWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.leftWheel().yAxisRotation(), 0, true));

			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.leftWheel().xAxisRotation(), 0, data.leftWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);

			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.popPose();

			stack.popPose();

			stack.pushPose();

			vec = data.rightWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.rightWheel().yAxisRotation(), 0, true));

			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.rightWheel().xAxisRotation(), 0, data.rightWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);

			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.popPose();

			stack.popPose();

			stack.pushPose();

			vec = data.bottomWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.bottomWheel().yAxisRotation(), 0, true));

			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.bottomWheel().xAxisRotation(), 0, data.bottomWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);

			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.popPose();

			stack.popPose();

			stack.pushPose();

			vec = data.topWheel().vector();
			stack.translate(vec.totX(), vec.totY(), vec.totZ());
			stack.mulPose(new Quaternion(0, data.topWheel().yAxisRotation(), 0, true));

			RenderingUtils.renderModel(wheelStill, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.pushPose();
			stack.translate(0.0, 0.0625, 0.0);
			stack.mulPose(new Quaternion(data.topWheel().xAxisRotation(), 0, data.topWheel().zAxisRotation(), true));
			stack.translate(0.0, -0.0625, 0.0);

			RenderingUtils.renderModel(wheelRot, null, RenderType.solid(), stack, buffer, WorldRenderer.getLightColor(minecraft.level, new BlockPos(vec.x, vec.y, vec.z)), OverlayTexture.NO_OVERLAY);

			stack.popPose();

			stack.popPose();
		});

		IVertexBuilder lineBuilder = buffer.getBuffer(Atlases.translucentCullBlockSheet());

		armsToRender.forEach((pos, data) -> {
			BlockPos start = data.corners().get(0);
			BlockPos nearCorner = data.corners().get(1);
			BlockPos farCorner = data.corners().get(2);
			BlockPos end = data.corners().get(3);

			int time = 200;
			int cutoff = 180;
			int half = (time - cutoff) / 2;

			float alpha = minecraft.level.getGameTime() % time;

			if (alpha < cutoff) {
				return;
			}

			alpha = time - alpha;
			if (alpha <= half) {
				alpha = alpha / half;
			} else {
				alpha = alpha - half;
				alpha = 1.0F - alpha / half;
			}
			double deltaX = nearCorner.getX() - start.getX();
			double deltaZ = nearCorner.getZ() - start.getZ();

			AxisAlignedBB beam = new AxisAlignedBB(0.4375, 0.5625, 0.4375, deltaX * data.signs()[0] + 0.5625, 0.6875, deltaZ * data.signs()[0] + 0.5625);

			stack.pushPose();
			stack.translate(start.getX(), start.getY(), start.getZ());
			RenderingUtils.renderFilledBoxNoOverlay(stack, lineBuilder, beam, 1.0F, 0, 0, alpha, u0White, v0White, u1White, v1White, 255, RenderingUtils.ALL_FACES);
			stack.popPose();

			deltaX = farCorner.getX() - start.getX();
			deltaZ = farCorner.getZ() - start.getZ();
			beam = new AxisAlignedBB(0.4375, 0.5625, 0.4375, deltaX * data.signs()[1] + 0.5625, 0.6875, deltaZ * data.signs()[1] + 0.5625);

			stack.pushPose();
			stack.translate(start.getX(), start.getY(), start.getZ());
			RenderingUtils.renderFilledBoxNoOverlay(stack, lineBuilder, beam, 1.0F, 0, 0, alpha, u0White, v0White, u1White, v1White, 255, RenderingUtils.ALL_FACES);
			stack.popPose();

			deltaX = end.getX() - nearCorner.getX();
			deltaZ = end.getZ() - nearCorner.getZ();
			beam = new AxisAlignedBB(0.4375, 0.5625, 0.4375, deltaX * data.signs()[2] + 0.5625, 0.6875, deltaZ * data.signs()[2] + 0.5625);

			stack.pushPose();
			stack.translate(end.getX(), end.getY(), end.getZ());
			RenderingUtils.renderFilledBoxNoOverlay(stack, lineBuilder, beam, 1.0F, 0, 0, alpha, u0White, v0White, u1White, v1White, 255, RenderingUtils.ALL_FACES);
			stack.popPose();

			deltaX = end.getX() - farCorner.getX();
			deltaZ = end.getZ() - farCorner.getZ();
			beam = new AxisAlignedBB(0.4375, 0.5625, 0.4375, deltaX * data.signs()[3] + 0.5625, 0.6875, deltaZ * data.signs()[3] + 0.5625);

			stack.pushPose();
			stack.translate(end.getX(), end.getY(), end.getZ());
			RenderingUtils.renderFilledBoxNoOverlay(stack, lineBuilder, beam, 1.0F, 0, 0, alpha, u0White, v0White, u1White, v1White, 255, RenderingUtils.ALL_FACES);
			stack.popPose();

		});

		buffer.endBatch(Atlases.translucentCullBlockSheet());

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

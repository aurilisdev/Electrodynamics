package electrodynamics.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketModeSwitchServer;
import electrodynamics.common.packet.types.PacketModeSwitchServer.Mode;
import electrodynamics.common.packet.types.PacketToggleOnServer;
import electrodynamics.common.packet.types.PacketToggleOnServer.Type;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void renderRailgunTooltip(RenderGameOverlayEvent.Post event) {
		if (ElementType.ALL.equals(event.getType())) {
			Player player = Minecraft.getInstance().player;
			ItemStack gunStackMainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
			ItemStack gunStackOffHand = player.getItemBySlot(EquipmentSlot.OFFHAND);

			if (gunStackMainHand.getItem() instanceof ItemRailgun) {
				renderHeatToolTip(event, gunStackMainHand);
			} else if (gunStackOffHand.getItem() instanceof ItemRailgun) {
				renderHeatToolTip(event, gunStackOffHand);
			}
		}
	}

	private static void renderHeatToolTip(RenderGameOverlayEvent.Post event, ItemStack stack) {
		Minecraft minecraft = Minecraft.getInstance();
		ItemRailgun railgun = (ItemRailgun) stack.getItem();
		double temperature = railgun.getTemperatureStored(stack);
		String correction = "";

		event.getMatrixStack().pushPose();

		if (temperature < 10) {
			correction = "00";
		} else if (temperature < 100) {
			correction = "0";
		} else {
			correction = "";
		}

		Component currTempText = new TranslatableComponent("tooltip.electrodynamics.railguntemp", new TextComponent(temperature + correction + " C")).withStyle(ChatFormatting.YELLOW);
		Component maxTempText = new TranslatableComponent("tooltip.electrodynamics.railgunmaxtemp", new TextComponent(railgun.getMaxTemp() + " C")).withStyle(ChatFormatting.YELLOW);

		GuiComponent.drawCenteredString(event.getMatrixStack(), minecraft.font, currTempText, 55, 2, 0);
		GuiComponent.drawCenteredString(event.getMatrixStack(), minecraft.font, maxTempText, 48, 11, 0);

		if (temperature >= railgun.getOverheatTemp()) {
			Component overheatWarn = new TranslatableComponent("tooltip.electrodynamics.railgunoverheat").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
			GuiComponent.drawCenteredString(event.getMatrixStack(), minecraft.font, overheatWarn, 70, 20, 0);
		}

		minecraft.getTextureManager().bindForSetup(GuiComponent.GUI_ICONS_LOCATION);

		event.getMatrixStack().popPose();
	}

	private static HashSet<Pair<Long, BlockPos>> blocks = new HashSet<>();

	public static HashMap<BlockPos, List<AABB>> markerLines = new HashMap<>();

	public static HashMap<BlockPos, List<AABB>> quarryArm = new HashMap<>();

	@SubscribeEvent
	public static void renderSelectedBlocks(RenderLevelLastEvent event) {
		PoseStack matrix = event.getPoseStack();
		MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
		VertexConsumer builder = buffer.getBuffer(RenderType.LINES);
		Minecraft minecraft = Minecraft.getInstance();
		GameRenderer renderer = minecraft.gameRenderer;
		Vec3 camera = renderer.getMainCamera().getPosition();
		Iterator<Pair<Long, BlockPos>> it = blocks.iterator();
		while (it.hasNext()) {
			Pair<Long, BlockPos> pair = it.next();
			AABB box = new AABB(pair.getSecond());
			matrix.pushPose();
			matrix.translate(-camera.x, -camera.y, -camera.z);
			LevelRenderer.renderLineBox(matrix, builder, box, 1.0F, 1.0F, 1.0F, 1.0F);
			matrix.popPose();
			if (System.currentTimeMillis() - pair.getFirst() > 10000) {
				it.remove();
			}
		}
		buffer.endBatch(RenderType.LINES);
		VertexConsumer sheetBuilder = buffer.getBuffer(RenderingUtils.beaconType());
		markerLines.forEach((pos, list) -> {
			list.forEach(aabb -> {
				matrix.pushPose();
				matrix.translate(-camera.x, -camera.y, -camera.z);
				RenderingUtils.renderSolidColorBox(matrix, minecraft, sheetBuilder, aabb, 1.0F, 0F, 0F, 1.0F, 255, 0);
				matrix.popPose();
			});
		});
		buffer.endBatch(RenderingUtils.beaconType());
		TextureAtlasSprite cornerFrame = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation("electrodynamics:block/quarryarm"));
		float u0Frame = cornerFrame.getU0();
		float u1Frame = cornerFrame.getU1();
		float v0Frame = cornerFrame.getV0();
		float v1Frame = cornerFrame.getV1();
		float[] colorsFrame = RenderingUtils.getColorArray(cornerFrame.getPixelRGBA(0, 10, 10));

		TextureAtlasSprite titanium = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation("electrodynamics:block/resource/resourceblocktitanium"));
		float u0Titanium = titanium.getU0();
		float u1Titanium = titanium.getU1();
		float v0Titanium = titanium.getV0();
		float v1Titanium = titanium.getV1();
		float[] colorsTitanium = RenderingUtils.getColorArray(cornerFrame.getPixelRGBA(0, 10, 10));

		VertexConsumer armBuilder = buffer.getBuffer(Sheets.solidBlockSheet());

		quarryArm.forEach((pos, list) -> {
			for (int i = 0; i < list.size(); i++) {
				AABB aabb = list.get(i);
				matrix.pushPose();
				matrix.translate(-camera.x, -camera.y, -camera.z);
				if (i < 5) {
					RenderingUtils.renderFilledBoxNoOverlay(matrix, armBuilder, aabb, colorsFrame[0], colorsFrame[1], colorsFrame[2], colorsFrame[3], u0Frame, v0Frame, u1Frame, v1Frame, 255);
				} else {
					RenderingUtils.renderFilledBoxNoOverlay(matrix, armBuilder, aabb, colorsTitanium[0], colorsTitanium[1], colorsTitanium[2], colorsTitanium[3], u0Titanium, v0Titanium, u1Titanium, v1Titanium, 255);
				}
				matrix.popPose();
			}
		});

		buffer.endBatch(Sheets.solidBlockSheet());

	}

	public static void addRenderLocation(BlockPos pos) {
		blocks.add(new Pair<>(System.currentTimeMillis(), pos));
	}

	@SubscribeEvent
	public static void keyPressEvents(KeyInputEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		if (KeyBinds.switchJetpackMode.matches(event.getKey(), event.getScanCode()) && KeyBinds.switchJetpackMode.isDown()) {
			ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
			if (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_COMBATCHESTPLATE.get())) {
				NetworkHandler.CHANNEL.sendToServer(new PacketModeSwitchServer(player.getUUID(), Mode.JETPACK));
			}
		}

		if (KeyBinds.toggleNvgs.matches(event.getKey(), event.getScanCode()) && KeyBinds.toggleNvgs.isDown()) {
			ItemStack playerHead = player.getItemBySlot(EquipmentSlot.HEAD);
			if (ItemUtils.testItems(playerHead.getItem(), DeferredRegisters.ITEM_NIGHTVISIONGOGGLES.get()) || ItemUtils.testItems(playerHead.getItem(), DeferredRegisters.ITEM_COMBATHELMET.get())) {
				NetworkHandler.CHANNEL.sendToServer(new PacketToggleOnServer(player.getUUID(), Type.NVGS));
			}
		}

		if (KeyBinds.switchServoLeggingsMode.matches(event.getKey(), event.getScanCode()) && KeyBinds.switchServoLeggingsMode.isDown()) {
			ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
			if (ItemUtils.testItems(legs.getItem(), DeferredRegisters.ITEM_SERVOLEGGINGS.get()) || ItemUtils.testItems(legs.getItem(), DeferredRegisters.ITEM_COMBATLEGGINGS.get())) {
				NetworkHandler.CHANNEL.sendToServer(new PacketModeSwitchServer(player.getUUID(), Mode.SERVOLEGS));
			}
		}

		if (KeyBinds.toggleServoLeggings.matches(event.getKey(), event.getScanCode()) && KeyBinds.toggleServoLeggings.isDown()) {
			ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
			if (ItemUtils.testItems(legs.getItem(), DeferredRegisters.ITEM_SERVOLEGGINGS.get()) || ItemUtils.testItems(legs.getItem(), DeferredRegisters.ITEM_COMBATLEGGINGS.get())) {
				NetworkHandler.CHANNEL.sendToServer(new PacketToggleOnServer(player.getUUID(), Type.SERVOLEGGINGS));
			}
		}

	}

}
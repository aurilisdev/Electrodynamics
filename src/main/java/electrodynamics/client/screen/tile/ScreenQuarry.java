package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.machines.quarry.TileCoolantResavoir;
import electrodynamics.common.tile.machines.quarry.TileMotorComplex;
import electrodynamics.common.tile.machines.quarry.TileQuarry;
import electrodynamics.common.tile.machines.quarry.TileSeismicRelay;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.math.Color;

public class ScreenQuarry extends GenericScreen<ContainerQuarry> {

	public ScreenQuarry(ContainerQuarry container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
		imageHeight += 58;
		inventoryLabelY += 58;
		addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, ScreenComponentSlot.IconType.MINING_LOCATION, this::getMiningLocationInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 5));
		addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, ScreenComponentSlot.IconType.QUARRY_COMPONENTS, this::getComponentInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 4));
		addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, ScreenComponentSlot.IconType.FLUID_BLUE, this::getFluidInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 3));
		addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, ScreenComponentSlot.IconType.ENCHANTMENT, this::getEnchantmentInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82 + 58, 8, 72 + 58);
	}

	private List<? extends IReorderingProcessor> getElectricInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileQuarry quarry = menu.getSafeHost();
		if (quarry != null) {
			ComponentElectrodynamic electro = quarry.getComponent(IComponentType.Electrodynamic);
			list.add(ElectroTextUtils.gui("quarry.ringusage", ChatFormatter.getChatDisplayShort(quarry.setupPowerUsage.getValue() * 20, DisplayUnits.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
			list.add(ElectroTextUtils.gui("quarry.miningusage", ChatFormatter.getChatDisplayShort(quarry.quarryPowerUsage.getValue() * 20, DisplayUnits.WATT).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
			list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	private List<? extends IReorderingProcessor> getEnchantmentInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileQuarry quarry = menu.getSafeHost();
		if (quarry == null) {
			return list;
		}
		list.add(ElectroTextUtils.gui("quarry.fortune", new StringTextComponent(quarry.fortuneLevel.getValue() + "").withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("quarry.silktouch", new StringTextComponent(quarry.silkTouchLevel.getValue() + "").withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("quarry.unbreaking", new StringTextComponent(quarry.unbreakingLevel.getValue() + "").withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		return list;

	}

	private List<? extends IReorderingProcessor> getFluidInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileQuarry quarry = menu.getSafeHost();
		if (quarry == null) {
			return list;
		}
		TileMotorComplex complex = quarry.getMotorComplex();
		IFormattableTextComponent text;
		if (complex == null) {
			text = new StringTextComponent("N/A");
		} else {
			text = ChatFormatter.getChatDisplayShort(complex.speed.getValue() * ElectroConstants.QUARRY_WATERUSAGE_PER_BLOCK, DisplayUnits.BUCKETS);
		}
		list.add(ElectroTextUtils.gui("quarry.wateruse", text.withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());
		return list;

	}

	private List<? extends IReorderingProcessor> getComponentInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileQuarry quarry = menu.getSafeHost();
		if (quarry == null) {
			return list;
		}

		TileMotorComplex complex = quarry.getMotorComplex();
		TextFormatting formatting;
		if (complex == null) {
			formatting = TextFormatting.RED;
		} else {
			ComponentElectrodynamic electro = complex.getComponent(IComponentType.Electrodynamic);
			if (electro.getJoulesStored() >= ElectroConstants.MOTORCOMPLEX_USAGE_PER_TICK * complex.powerMultiplier.getValue()) {
				formatting = TextFormatting.GREEN;
			} else {
				formatting = TextFormatting.YELLOW;
			}
		}
		list.add(ElectroTextUtils.gui("quarry.motorcomplex").withStyle(formatting).getVisualOrderText());

		TileSeismicRelay relay = quarry.getSeismicRelay();

		if (relay == null) {
			formatting = TextFormatting.RED;
		} else if (quarry.hasCorners()) {
			formatting = TextFormatting.GREEN;
		} else {
			formatting = TextFormatting.YELLOW;
		}

		list.add(ElectroTextUtils.gui("quarry.seismicrelay").withStyle(formatting).getVisualOrderText());

		TileCoolantResavoir resavoir = quarry.getFluidResavoir();

		if (resavoir == null) {
			formatting = TextFormatting.RED;
		} else if (complex == null || resavoir.hasEnoughFluid((int) (complex.powerMultiplier.getValue() * ElectroConstants.QUARRY_WATERUSAGE_PER_BLOCK))) {
			formatting = TextFormatting.GREEN;
		} else {
			formatting = TextFormatting.YELLOW;
		}

		list.add(ElectroTextUtils.gui("quarry.coolantresavoir").withStyle(formatting).getVisualOrderText());

		return list;

	}

	private List<? extends IReorderingProcessor> getMiningLocationInformation() {
		ArrayList<IReorderingProcessor> list = new ArrayList<>();
		TileQuarry quarry = menu.getSafeHost();
		if (quarry == null) {
			return list;
		}

		ITextComponent location;
		if (quarry.miningPos.getValue().equals(BlockEntityUtils.OUT_OF_REACH)) {
			location = ElectroTextUtils.gui("quarry.notavailable").withStyle(TextFormatting.RED);
		} else {
			location = new StringTextComponent(quarry.miningPos.getValue().toShortString()).withStyle(TextFormatting.GRAY);
		}

		list.add(ElectroTextUtils.gui("quarry.miningposition", location).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());

		if (quarry.hasHead.getValue()) {
			location = ElectroTextUtils.gui("quarry.hashead").withStyle(TextFormatting.GRAY);
		} else {
			location = ElectroTextUtils.gui("quarry.nohead").withStyle(TextFormatting.RED);
		}

		list.add(ElectroTextUtils.gui("quarry.drillhead", location).withStyle(TextFormatting.DARK_GRAY).getVisualOrderText());

		return list;

	}

	@Override
	protected void renderLabels(MatrixStack poseStack, int x, int y) {
		super.renderLabels(poseStack, x, y);
		TileQuarry quarry = menu.getSafeHost();
		if (quarry == null) {
			return;
		}
		// void card
		if (quarry.hasItemVoid.getValue()) {
			font.draw(poseStack, ElectroTextUtils.gui("quarry.voiditems"), 85, 14, Color.TEXT_GRAY.color());
		} else {
			font.draw(poseStack, ElectroTextUtils.gui("quarry.needvoidcard"), 85, 14, Color.TEXT_GRAY.color());
		}

		/* STATUS */

		font.draw(poseStack, ElectroTextUtils.gui("quarry.status"), 5, 32, Color.TEXT_GRAY.color());

		int height = 42;
		if (!quarry.isAreaCleared.getValue()) {
			font.draw(poseStack, ElectroTextUtils.gui("quarry.clearingarea"), 10, height, Color.TEXT_GRAY.color());
		} else if (!quarry.hasRing.getValue()) {
			font.draw(poseStack, ElectroTextUtils.gui("quarry.setup"), 10, height, Color.TEXT_GRAY.color());
		} else if (quarry.running.getValue()) {
			font.draw(poseStack, ElectroTextUtils.gui("quarry.mining"), 10, height, Color.TEXT_GRAY.color());
		} else if (quarry.isFinished.getValue()) {
			font.draw(poseStack, ElectroTextUtils.gui("quarry.finished"), 10, height, Color.TEXT_GRAY.color());
		} else {
			font.draw(poseStack, ElectroTextUtils.gui("quarry.notmining"), 10, height, Color.TEXT_GRAY.color());
		}

		/* ERRORS */

		font.draw(poseStack, ElectroTextUtils.gui("quarry.errors"), 5, 65, Color.TEXT_GRAY.color());
		font.draw(poseStack, ElectroTextUtils.gui(getErrorKey(quarry)), 10, 75, Color.TEXT_GRAY.color());

	}

	private String getErrorKey(TileQuarry quarry) {
		if (!quarry.hasSeismicRelay.getValue()) {
			return "quarry.norelay";
		}
		if (!quarry.hasMotorComplex.getValue()) {
			return "quarry.nomotorcomplex";
		}
		if (!quarry.hasCoolantResavoir.getValue()) {
			return "quarry.nocoolantresavoir";
		}
		if (!quarry.hasCorners()) {
			return "quarry.nocorners";
		}
		if (!quarry.isMotorComplexPowered()) {
			return "quarry.motorcomplexnotpowered";
		} else if (!quarry.isPowered.getValue()) {
			return "quarry.nopower";
		} else if (quarry.isTryingToMineFrame.getValue()) {
			return "quarry.miningframe";
		} else if (!quarry.isAreaCleared.getValue()) {
			return "quarry.areanotclear";
		} else if (!quarry.hasRing.getValue()) {
			return "quarry.noring";
		} else if (!quarry.hasHead.getValue()) {
			return "quarry.missinghead";
		} else if (!quarry.getFluidResavoir().hasEnoughFluid((int) (quarry.getMotorComplex().powerMultiplier.getValue() * ElectroConstants.QUARRY_WATERUSAGE_PER_BLOCK))) {
			return "quarry.nocoolant";
		} else if (!quarry.<ComponentInventory>getComponent(IComponentType.Inventory).areOutputsEmpty()) {
			return "quarry.inventoryroom";
		}

		return "quarry.noerrors";
	}

}

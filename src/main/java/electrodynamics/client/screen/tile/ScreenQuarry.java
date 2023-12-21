package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.machines.quarry.TileCoolantResavoir;
import electrodynamics.common.tile.machines.quarry.TileMotorComplex;
import electrodynamics.common.tile.machines.quarry.TileQuarry;
import electrodynamics.common.tile.machines.quarry.TileSeismicRelay;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentGuiTab.GuiInfoTabTextures;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class ScreenQuarry extends GenericScreen<ContainerQuarry> {

	public ScreenQuarry(ContainerQuarry container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		imageHeight += 58;
		inventoryLabelY += 58;
		addComponent(new ScreenComponentGuiTab(GuiInfoTabTextures.REGULAR, IconType.MINING_LOCATION, this::getMiningLocationInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 5));
		addComponent(new ScreenComponentGuiTab(GuiInfoTabTextures.REGULAR, IconType.QUARRY_COMPONENTS, this::getComponentInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 4));
		addComponent(new ScreenComponentGuiTab(GuiInfoTabTextures.REGULAR, IconType.FLUID_BLUE, this::getFluidInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 3));
		addComponent(new ScreenComponentGuiTab(GuiInfoTabTextures.REGULAR, IconType.ENCHANTMENT, this::getEnchantmentInformation, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82 + 58, 8, 72 + 58);
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry != null) {
			ComponentElectrodynamic electro = quarry.getComponent(IComponentType.Electrodynamic);
			list.add(ElectroTextUtils.gui("quarry.ringusage", ChatFormatter.getChatDisplayShort(quarry.setupPowerUsage.get() * 20, DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(ElectroTextUtils.gui("quarry.miningusage", ChatFormatter.getChatDisplayShort(quarry.quarryPowerUsage.get() * 20, DisplayUnit.WATT).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(ElectroTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	private List<? extends FormattedCharSequence> getEnchantmentInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return list;
		}
		list.add(ElectroTextUtils.gui("quarry.fortune", new TextComponent(quarry.fortuneLevel.get() + "").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("quarry.silktouch", new TextComponent(quarry.silkTouchLevel.get() + "").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(ElectroTextUtils.gui("quarry.unbreaking", new TextComponent(quarry.unbreakingLevel.get() + "").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		return list;

	}

	private List<? extends FormattedCharSequence> getFluidInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return list;
		}
		TileMotorComplex complex = quarry.getMotorComplex();
		MutableComponent text;
		if (complex == null) {
			text = new TextComponent("N/A");
		} else {
			text = ChatFormatter.getChatDisplayShort(complex.speed.get() * Constants.QUARRY_WATERUSAGE_PER_BLOCK, DisplayUnit.BUCKETS);
		}
		list.add(ElectroTextUtils.gui("quarry.wateruse", text.withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		return list;

	}

	private List<? extends FormattedCharSequence> getComponentInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return list;
		}

		TileMotorComplex complex = quarry.getMotorComplex();
		ChatFormatting formatting;
		if (complex == null) {
			formatting = ChatFormatting.RED;
		} else {
			ComponentElectrodynamic electro = complex.getComponent(IComponentType.Electrodynamic);
			if (electro.getJoulesStored() >= Constants.MOTORCOMPLEX_USAGE_PER_TICK * complex.powerMultiplier.get()) {
				formatting = ChatFormatting.GREEN;
			} else {
				formatting = ChatFormatting.YELLOW;
			}
		}
		list.add(ElectroTextUtils.gui("quarry.motorcomplex").withStyle(formatting).getVisualOrderText());

		TileSeismicRelay relay = quarry.getSeismicRelay();

		if (relay == null) {
			formatting = ChatFormatting.RED;
		} else if (quarry.hasCorners()) {
			formatting = ChatFormatting.GREEN;
		} else {
			formatting = ChatFormatting.YELLOW;
		}

		list.add(ElectroTextUtils.gui("quarry.seismicrelay").withStyle(formatting).getVisualOrderText());

		TileCoolantResavoir resavoir = quarry.getFluidResavoir();

		if (resavoir == null) {
			formatting = ChatFormatting.RED;
		} else if (complex == null || resavoir.hasEnoughFluid((int) (complex.powerMultiplier.get() * Constants.QUARRY_WATERUSAGE_PER_BLOCK))) {
			formatting = ChatFormatting.GREEN;
		} else {
			formatting = ChatFormatting.YELLOW;
		}

		list.add(ElectroTextUtils.gui("quarry.coolantresavoir").withStyle(formatting).getVisualOrderText());

		return list;

	}

	private List<? extends FormattedCharSequence> getMiningLocationInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return list;
		}

		Component location;
		if (quarry.miningPos.get().equals(TileQuarry.OUT_OF_REACH)) {
			location = ElectroTextUtils.gui("quarry.notavailable").withStyle(ChatFormatting.RED);
		} else {
			location = new TextComponent(quarry.miningPos.get().toShortString()).withStyle(ChatFormatting.GRAY);
		}

		list.add(ElectroTextUtils.gui("quarry.miningposition", location).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

		if (quarry.hasHead.get()) {
			location = ElectroTextUtils.gui("quarry.hashead").withStyle(ChatFormatting.GRAY);
		} else {
			location = ElectroTextUtils.gui("quarry.nohead").withStyle(ChatFormatting.RED);
		}

		list.add(ElectroTextUtils.gui("quarry.drillhead", location).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());

		return list;

	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return;
		}
		// void card
		if (quarry.hasItemVoid.get()) {
			font.draw(stack, ElectroTextUtils.gui("quarry.voiditems"), 85, 14, 4210752);
		} else {
			font.draw(stack, ElectroTextUtils.gui("quarry.needvoidcard"), 85, 14, 4210752);
		}

		/* STATUS */

		font.draw(stack, ElectroTextUtils.gui("quarry.status"), 5, 32, 4210752);

		int height = 42;
		if (!quarry.isAreaCleared.get()) {
			font.draw(stack, ElectroTextUtils.gui("quarry.clearingarea"), 10, height, 4210752);
		} else if (!quarry.hasRing.get()) {
			font.draw(stack, ElectroTextUtils.gui("quarry.setup"), 10, height, 4210752);
		} else if (quarry.running.get()) {
			font.draw(stack, ElectroTextUtils.gui("quarry.mining"), 10, height, 4210752);
		} else if (quarry.isFinished.get()) {
			font.draw(stack, ElectroTextUtils.gui("quarry.finished"), 10, height, 4210752);
		} else {
			font.draw(stack, ElectroTextUtils.gui("quarry.notmining"), 10, height, 4210752);
		}

		/* ERRORS */

		font.draw(stack, ElectroTextUtils.gui("quarry.errors"), 5, 65, 4210752);
		font.draw(stack, ElectroTextUtils.gui(getErrorKey(quarry)), 10, 75, 4210752);

	}

	private String getErrorKey(TileQuarry quarry) {
		if (!quarry.hasSeismicRelay.get()) {
			return "quarry.norelay";
		}
		if (!quarry.hasMotorComplex.get()) {
			return "quarry.nomotorcomplex";
		}
		if (!quarry.hasCoolantResavoir.get()) {
			return "quarry.nocoolantresavoir";
		}
		if (!quarry.hasCorners()) {
			return "quarry.nocorners";
		}
		if (!quarry.isMotorComplexPowered()) {
			return "quarry.motorcomplexnotpowered";
		} else if (!quarry.isPowered.get()) {
			return "quarry.nopower";
		} else if (quarry.isTryingToMineFrame.get()) {
			return "quarry.miningframe";
		} else if (!quarry.isAreaCleared.get()) {
			return "quarry.areanotclear";
		} else if (!quarry.hasRing.get()) {
			return "quarry.noring";
		} else if (!quarry.hasHead.get()) {
			return "quarry.missinghead";
		} else if (!quarry.getFluidResavoir().hasEnoughFluid((int) (quarry.getMotorComplex().powerMultiplier.get() * Constants.QUARRY_WATERUSAGE_PER_BLOCK))) {
			return "quarry.nocoolant";
		} else if (!quarry.<ComponentInventory>getComponent(IComponentType.Inventory).areOutputsEmpty()) {
			return "quarry.inventoryroom";
		}

		return "quarry.noerrors";
	}

}
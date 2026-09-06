package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.common.tile.machines.quarry.TileCoolantResavoir;
import electrodynamics.common.tile.machines.quarry.TileMotorComplex;
import electrodynamics.common.tile.machines.quarry.TileQuarry;
import electrodynamics.common.tile.machines.quarry.TileSeismicRelay;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
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

    public ScreenQuarry(ContainerQuarry container, Inventory inv, Component titleIn) {
	super(container, inv, titleIn);
	imageHeight += 58;
	inventoryLabelY += 58;
	addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR,
		ScreenComponentSlot.IconType.MINING_LOCATION, this::getMiningLocationInformation,
		-AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 5));
	addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR,
		ScreenComponentSlot.IconType.QUARRY_COMPONENTS, this::getComponentInformation,
		-AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 4));
	addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR,
		ScreenComponentSlot.IconType.FLUID_BLUE, this::getFluidInformation,
		-AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 3));
	addComponent(new ScreenComponentGuiTab(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR,
		ScreenComponentSlot.IconType.ENCHANTMENT, this::getEnchantmentInformation,
		-AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
	addComponent(new ScreenComponentElectricInfo(this::getElectricInformation,
		-AbstractScreenComponentInfo.SIZE + 1, 2));
	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		140, 8, 130);
    }

    private List<? extends FormattedCharSequence> getElectricInformation() {
	ArrayList<FormattedCharSequence> list = new ArrayList<>();
	Optional<TileQuarry> host = menu.getSafeHost();
	if (host.isEmpty())
	    return list;
	TileQuarry quarry = host.get();
	ComponentElectrodynamic electro = quarry
		.<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic);
	list.add(ElectroTextUtils
		.gui("quarry.ringusage",
			ChatFormatter.getChatDisplayShort(quarry.setupPowerUsage.getValue() * 20, DisplayUnits.WATT)
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	list.add(ElectroTextUtils
		.gui("quarry.miningusage",
			ChatFormatter.getChatDisplayShort(quarry.quarryPowerUsage.getValue() * 20, DisplayUnits.WATT)
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	list.add(ElectroTextUtils
		.gui("machine.voltage",
			ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnits.VOLTAGE)
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	return list;
    }

    private List<? extends FormattedCharSequence> getEnchantmentInformation() {
	ArrayList<FormattedCharSequence> list = new ArrayList<>();
	Optional<TileQuarry> host = menu.getSafeHost();
	if (host.isEmpty())
	    return list;
	TileQuarry quarry = host.get();
	list.add(ElectroTextUtils
		.gui("quarry.fortune",
			Component.literal(String.valueOf(quarry.fortuneLevel.getValue()))
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	list.add(ElectroTextUtils
		.gui("quarry.silktouch",
			Component.literal(String.valueOf(quarry.silkTouchLevel.getValue()))
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	list.add(ElectroTextUtils
		.gui("quarry.unbreaking",
			Component.literal(String.valueOf(quarry.unbreakingLevel.getValue()))
				.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	return list;
    }

    private List<? extends FormattedCharSequence> getFluidInformation() {
	ArrayList<FormattedCharSequence> list = new ArrayList<>();
	Optional<TileQuarry> host = menu.getSafeHost();
	if (host.isEmpty())
	    return list;
	TileQuarry quarry = host.get();
	Level level = quarry.getLevel();
	if (level == null)
	    return list;
	TileMotorComplex complex = quarry.getMotorComplex(level);
	MutableComponent text = complex == null ? Component.literal("N/A")
		: ChatFormatter.getChatDisplayShort(
			complex.speed.getValue() * ElectrodynamicsConfig.INSTANCE.QUARRY_WATERUSAGE_PER_BLOCK.get(),
			DisplayUnits.BUCKETS);
	list.add(ElectroTextUtils.gui("quarry.wateruse", text.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
	return list;
    }

    private List<? extends FormattedCharSequence> getComponentInformation() {
	ArrayList<FormattedCharSequence> list = new ArrayList<>();
	Optional<TileQuarry> host = menu.getSafeHost();
	if (host.isEmpty())
	    return list;
	TileQuarry quarry = host.get();
	Level level = quarry.getLevel();
	if (level == null)
	    return list;
	TileMotorComplex complex = quarry.getMotorComplex(level);
	ChatFormatting formatting;
	if (complex == null) {
	    formatting = ChatFormatting.RED;
	} else {
	    ComponentElectrodynamic electro = complex
		    .<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic);
	    formatting = electro.getJoulesStored() >= ElectrodynamicsConfig.INSTANCE.MOTORCOMPLEX_USAGE_PER_TICK.get()
		    * complex.powerMultiplier.getValue() ? ChatFormatting.GREEN : ChatFormatting.YELLOW;
	}
	list.add(ElectroTextUtils.gui("quarry.motorcomplex").withStyle(formatting).getVisualOrderText());
	TileSeismicRelay relay = quarry.getSeismicRelay(level);
	if (relay == null)
	    formatting = ChatFormatting.RED;
	else
	    formatting = quarry.hasCorners() ? ChatFormatting.GREEN : ChatFormatting.YELLOW;
	list.add(ElectroTextUtils.gui("quarry.seismicrelay").withStyle(formatting).getVisualOrderText());
	TileCoolantResavoir resavoir = quarry.getFluidResavoir(level);
	if (resavoir == null) {
	    formatting = ChatFormatting.RED;
	} else if (complex == null || resavoir.hasEnoughFluid((int) (complex.powerMultiplier.getValue()
		* ElectrodynamicsConfig.INSTANCE.QUARRY_WATERUSAGE_PER_BLOCK.get()))) {
	    formatting = ChatFormatting.GREEN;
	} else {
	    formatting = ChatFormatting.YELLOW;
	}
	list.add(ElectroTextUtils.gui("quarry.coolantresavoir").withStyle(formatting).getVisualOrderText());
	return list;
    }

    private List<? extends FormattedCharSequence> getMiningLocationInformation() {
	ArrayList<FormattedCharSequence> list = new ArrayList<>();
	Optional<TileQuarry> host = menu.getSafeHost();
	if (host.isEmpty())
	    return list;
	TileQuarry quarry = host.get();
	Component location;
	if (quarry.miningPos.getValue().equals(BlockEntityUtils.OUT_OF_REACH))
	    location = ElectroTextUtils.gui("quarry.notavailable").withStyle(ChatFormatting.RED);
	else
	    location = Component.literal(quarry.miningPos.getValue().toShortString()).withStyle(ChatFormatting.GRAY);
	list.add(ElectroTextUtils.gui("quarry.miningposition", location).withStyle(ChatFormatting.DARK_GRAY)
		.getVisualOrderText());
	if (quarry.hasHead.getValue())
	    location = ElectroTextUtils.gui("quarry.hashead").withStyle(ChatFormatting.GRAY);
	else
	    location = ElectroTextUtils.gui("quarry.nohead").withStyle(ChatFormatting.RED);
	list.add(ElectroTextUtils.gui("quarry.drillhead", location).withStyle(ChatFormatting.DARK_GRAY)
		.getVisualOrderText());
	return list;
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int x, int y) {
	super.renderLabels(graphics, x, y);
	Optional<TileQuarry> host = menu.getSafeHost();
	if (host.isEmpty())
	    return;
	TileQuarry quarry = host.get();
	String voidKey = quarry.hasItemVoid.getValue() ? "quarry.voiditems" : "quarry.needvoidcard";
	graphics.drawString(font, ElectroTextUtils.gui(voidKey), 85, 14, Color.TEXT_GRAY.color(), false);
	graphics.drawString(font, ElectroTextUtils.gui("quarry.status"), 5, 32, Color.TEXT_GRAY.color(), false);
	String statusKey;
	if (!quarry.isAreaCleared.getValue())
	    statusKey = "quarry.clearingarea";
	else if (!quarry.hasRing.getValue())
	    statusKey = "quarry.setup";
	else if (quarry.running.getValue())
	    statusKey = "quarry.mining";
	else if (quarry.isFinished.getValue())
	    statusKey = "quarry.finished";
	else
	    statusKey = "quarry.notmining";
	graphics.drawString(font, ElectroTextUtils.gui(statusKey), 10, 42, Color.TEXT_GRAY.color(), false);
	graphics.drawString(font, ElectroTextUtils.gui("quarry.errors"), 5, 65, Color.TEXT_GRAY.color(), false);
	graphics.drawString(font, ElectroTextUtils.gui(getErrorKey(quarry)), 10, 75, Color.TEXT_GRAY.color(), false);
    }

    private static String getErrorKey(TileQuarry quarry) {
	Level level = quarry.getLevel();
	if (level == null)
	    return "quarry.noerrors";
	if (!quarry.hasSeismicRelay.getValue())
	    return "quarry.norelay";
	if (!quarry.hasMotorComplex.getValue())
	    return "quarry.nomotorcomplex";
	if (!quarry.hasCoolantResavoir.getValue())
	    return "quarry.nocoolantresavoir";
	TileMotorComplex complex = quarry.getMotorComplex(level);
	if (complex == null)
	    return "quarry.nomotorcomplex";
	TileCoolantResavoir resavoir = quarry.getFluidResavoir(level);
	if (resavoir == null)
	    return "quarry.nocoolantresavoir";
	if (!quarry.hasCorners())
	    return "quarry.nocorners";
	if (!quarry.isMotorComplexPowered(level))
	    return "quarry.motorcomplexnotpowered";
	if (!quarry.isPowered.getValue())
	    return "quarry.nopower";
	if (quarry.isTryingToMineFrame.getValue())
	    return "quarry.miningframe";
	if (!quarry.isAreaCleared.getValue())
	    return "quarry.areanotclear";
	if (!quarry.hasRing.getValue())
	    return "quarry.noring";
	if (!quarry.hasHead.getValue())
	    return "quarry.missinghead";
	if (!resavoir.hasEnoughFluid((int) (complex.powerMultiplier.getValue()
		* ElectrodynamicsConfig.INSTANCE.QUARRY_WATERUSAGE_PER_BLOCK.get())))
	    return "quarry.nocoolant";
	if (!quarry.<ComponentInventory>requireComponent(IComponentType.Inventory).areOutputsEmpty())
	    return "quarry.inventoryroom";
	return "quarry.noerrors";
    }
}
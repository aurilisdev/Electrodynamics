package electrodynamics.client.screen.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.inventory.container.tile.ContainerQuarry;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileCoolantResavoir;
import electrodynamics.common.tile.TileMotorComplex;
import electrodynamics.common.tile.TileSeismicRelay;
import electrodynamics.common.tile.quarry.TileQuarry;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentEnchantment;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentQuarryComponents;
import electrodynamics.prefab.screen.component.ScreenComponentWater;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class ScreenQuarry extends GenericScreen<ContainerQuarry> {

	public ScreenQuarry(ContainerQuarry container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		imageHeight += 58;
		inventoryLabelY += 58;
		//TODO mining speed side label
		components.add(new ScreenComponentQuarryComponents(this::getComponentInformation, this, -ScreenComponentInfo.SIZE + 1, 2 + ScreenComponentInfo.SIZE * 3));
		components.add(new ScreenComponentWater(this::getFluidInformation, this, -ScreenComponentInfo.SIZE + 1, 2 + ScreenComponentInfo.SIZE * 2));
		components.add(new ScreenComponentEnchantment(this::getEnchantmentInformation, this, -ScreenComponentInfo.SIZE + 1, 2 + ScreenComponentInfo.SIZE));
		components.add(new ScreenComponentElectricInfo(this::getElectricInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry != null) {
			ComponentElectrodynamic electro = quarry.getComponent(ComponentType.Electrodynamic);
			list.add(TextUtils
					.gui("quarry.ringusage",
							Component.literal(ChatFormatter.getChatDisplayShort(quarry.setupPowerUsage.get() * 20,
									DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY))
					.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(TextUtils
					.gui("quarry.miningusage",
							Component.literal(ChatFormatter.getChatDisplayShort(quarry.quarryPowerUsage.get() * 20,
									DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY))
					.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(
					TextUtils
							.gui("machine.voltage",
									Component.literal(ChatFormatter.getChatDisplayShort(electro.getVoltage(),
											DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY))
							.withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}
	
	private List<? extends FormattedCharSequence> getEnchantmentInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return list;
		}
		list.add(TextUtils.gui("quarry.fortune", Component.literal(quarry.fortuneLevel.get() + "").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(TextUtils.gui("quarry.silktouch", Component.literal(quarry.silkTouchLevel.get() + "").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		list.add(TextUtils.gui("quarry.unbreaking", Component.literal(quarry.unbreakingLevel.get() + "").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		return list;
		
	}
	
	private List<? extends FormattedCharSequence> getFluidInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return list;
		}
		TileMotorComplex complex = quarry.getMotorComplex();
		String text;
		if(complex == null) {
			text = "N/A";
		} else {
			text = "" + (int) complex.speed.get() * Constants.QUARRY_WATERUSAGE_PER_BLOCK;
		}
		list.add(TextUtils.gui("quarry.wateruse", Component.literal(text).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
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
		if(complex == null) {
			formatting = ChatFormatting.RED;
		} else {
			ComponentElectrodynamic electro = complex.getComponent(ComponentType.Electrodynamic);
			if (electro.getJoulesStored() >= Constants.MOTORCOMPLEX_USAGE_PER_TICK * complex.powerMultiplier.get()) {
				formatting = ChatFormatting.GREEN;
			} else {
				formatting = ChatFormatting.YELLOW;
			}
		}
		list.add(TextUtils.gui("quarry.motorcomplex").withStyle(formatting).getVisualOrderText());
		
		TileSeismicRelay relay = quarry.getSeismicRelay();
		
		if(relay == null) {
			formatting = ChatFormatting.RED;
		} else {
			if(quarry.hasCorners()) {
				formatting = ChatFormatting.GREEN;
			} else {
				formatting = ChatFormatting.YELLOW;
			}
		}
		
		list.add(TextUtils.gui("quarry.seismicrelay").withStyle(formatting).getVisualOrderText());
		
		TileCoolantResavoir resavoir = quarry.getFluidResavoir();
		
		if(resavoir == null) {
			formatting = ChatFormatting.RED;
		} else {
			if(complex == null) {
				formatting = ChatFormatting.GREEN;
			} else {
				if(resavoir.hasEnoughFluid((int) (complex.powerMultiplier.get() * Constants.QUARRY_WATERUSAGE_PER_BLOCK))) {
					formatting = ChatFormatting.GREEN;
				} else {
					formatting = ChatFormatting.YELLOW;
				}
			}
		}
		
		list.add(TextUtils.gui("quarry.coolantresavoir").withStyle(formatting).getVisualOrderText());
		
		return list;
		
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		TileQuarry quarry = menu.getHostFromIntArray();
		if (quarry == null) {
			return;
		}
		//void card
		if (quarry.hasItemVoid.get()) {
			font.draw(stack, TextUtils.gui("quarry.voiditems"), 85, 14, 4210752);
		} else {
			font.draw(stack, TextUtils.gui("quarry.needvoidcard"), 85, 14, 4210752);
		}
		
		/* STATUS */
		
		font.draw(stack, TextUtils.gui("quarry.status"), 5, 22, 4210752);

		if(!quarry.isAreaCleared.get()) {
			font.draw(stack, TextUtils.gui("quarry.clearingarea"), 10, 32, 4210752);
		} else if (!quarry.hasRing.get()) {
			font.draw(stack, TextUtils.gui("quarry.setup"), 10, 32, 4210752);
		} else if (quarry.running.get()) {
			font.draw(stack, TextUtils.gui("quarry.mining"), 10, 32, 4210752);
		} else if (quarry.isFinished.get()) {
			font.draw(stack, TextUtils.gui("quarry.finished"), 10, 32, 4210752);
		} else {
			font.draw(stack, TextUtils.gui("quarry.notmining"), 10, 32, 4210752);
		}
		
		/* POSITION */
		
		font.draw(stack, TextUtils.gui("quarry.miningposition"), 5, 45, 4210752);

		if(quarry.miningPos.get().equals(TileQuarry.OUT_OF_REACH)) {
			font.draw(stack, TextUtils.gui("quarry.notavailable"), 10, 55, 4210752);
		} else {
			font.draw(stack, Component.literal(quarry.miningPos.get().toShortString()), 10, 55, 4210752);
		}

		
		/* DRILL HEAD */
		
		font.draw(stack, TextUtils.gui("quarry.drillhead"), 5, 68, 4210752);
		
		if (quarry.hasHead.get()) {
			font.draw(stack, TextUtils.gui("quarry.hashead"), 10, 78, 4210752);
		} else {
			font.draw(stack, TextUtils.gui("quarry.nohead"), 10, 78, 4210752);
		}
		
		/* ERRORS */

		font.draw(stack, TextUtils.gui("quarry.errors"), 5, 91, 4210752);
		font.draw(stack, TextUtils.gui(getErrorKey(quarry)), 10, 101, 4210752);
		
		
		
	}
	
	private String getErrorKey(TileQuarry quarry) {
		
		if(!quarry.hasSeismicRelay.get()) {
			return "quarry.norelay";
		} else if (!quarry.hasMotorComplex.get()) {
			return "quarry.nomotorcomplex";
		} else if (!quarry.hasCoolantResavoir.get()) {
			return "quarry.nocoolantresavoir";
		} else if (!quarry.hasCorners()) {
			return "quarry.nocorners";
		} else if (!quarry.isMotorComplexPowered()) {
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
		} 
		
		return "quarry.noerrors";
	}

}

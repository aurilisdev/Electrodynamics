package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.screen.ITexture.Textures;
import electrodynamics.common.inventory.container.tile.ContainerThermoelectricManipulator;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketSendUpdatePropertiesServer;
import electrodynamics.common.tile.gastransformer.GenericTileGasTransformer;
import electrodynamics.common.tile.gastransformer.thermoelectricmanipulator.TileThermoelectricManipulator;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentFluidInput;
import electrodynamics.prefab.screen.component.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.ScreenComponentGasInput;
import electrodynamics.prefab.screen.component.ScreenComponentGasPressure;
import electrodynamics.prefab.screen.component.ScreenComponentGasTemperature;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentTextInputBar;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.utilities.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenThermoelectricManipulator extends GenericScreen<ContainerThermoelectricManipulator> {

	private EditBox temperature;
	
	private boolean needsUpdate = true;
	
	public ScreenThermoelectricManipulator(ContainerThermoelectricManipulator container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
		imageHeight += 30;
		inventoryLabelY += 30;
		components.add(new ScreenComponentFluidInput(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, this, 10, 18));
		components.add(new ScreenComponentFluid(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getOutputTanks()[0];
			}
			return null;
		}, this, 96, 18));
		components.add(new ScreenComponentGasInput(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getInputTanks()[0];
			}
			return null;
		}, this, 46, 18));
		components.add(new ScreenComponentGasGauge(() -> {
			TileThermoelectricManipulator boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getOutputTanks()[0];
			}
			return null;
		}, this, 132, 18));
		components.add(new ScreenComponentGasTemperature(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE * 2));
		components.add(new ScreenComponentGasPressure(this, -AbstractScreenComponentInfo.SIZE + 1, 2 + AbstractScreenComponentInfo.SIZE));
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
		components.add(new ScreenComponentTextInputBar(this, 94, 75, 59, 16));
		components.add(new ScreenComponentGeneric(Textures.CONDENSER_COLUMN, this, 62, 19));
	}
	
	@Override
	protected void containerTick() {
		super.containerTick();
		temperature.tick();
	}
	
	@Override
	protected void init() {
		super.init();
		initFields();
	}
	
	private void initFields() {
		
		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		int i = (width - imageWidth) / 2;
		int j = (height - imageHeight) / 2;
		temperature = new EditBox(font, i + 97, j + 80, 55, 13, Component.empty());
		
		temperature.setTextColor(-1);
		temperature.setTextColorUneditable(-1);
		temperature.setBordered(false);
		temperature.setMaxLength(20);
		temperature.setResponder(this::setTemperature);
		temperature.setFilter(ScreenComponentTextInputBar.getValidator(ScreenComponentTextInputBar.POSITIVE_DECIMAL));
		
		addWidget(temperature);
	}
	
	private void setTemperature(String temp) {
		
		TileThermoelectricManipulator manipulator = menu.getHostFromIntArray();
		
		if(manipulator == null) {
			return;
		}
		
		if(temp.isEmpty()) {
			return;
		}
		
		double temperature = Gas.ROOM_TEMPERATURE;
		
		try {
			temperature = Double.parseDouble(temp);
		} catch (Exception e) {
			
		}
		
		if(temperature < GasStack.ABSOLUTE_ZERO) {
			temperature = Gas.ROOM_TEMPERATURE;
		} else if (temperature > GenericTileGasTransformer.OUTPUT_TEMPERATURE) {
			temperature = GenericTileGasTransformer.OUTPUT_TEMPERATURE;
			this.temperature.setValue("" + temperature);
		}
		
		manipulator.targetTemperature.set(temperature);
		
		NetworkHandler.CHANNEL.sendToServer(new PacketSendUpdatePropertiesServer(manipulator.targetTemperature.getPropertyManager().getProperties().indexOf(manipulator.targetTemperature), manipulator.targetTemperature, manipulator.getBlockPos()));
		
	}
	
	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String temp = temperature.getValue();
		init(minecraft, width, height);
		temperature.setValue(temp);
	}
	
	@Override
	public void removed() {
		super.removed();
		minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		if (needsUpdate) {
			needsUpdate = false;
			TileThermoelectricManipulator manipulator = menu.getHostFromIntArray();
			if (manipulator != null) {
				temperature.setValue("" + manipulator.targetTemperature.get());
			}
		}
		temperature.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		font.draw(stack, TextUtils.gui("thermoelectricmanipulator.temp"), 10, 80, 4210752);
		font.draw(stack, Component.literal(DisplayUnit.TEMPERATURE_KELVIN.symbol), 155, 80, 4210752);
	}

}

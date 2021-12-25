package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class TileSolarPanel extends GenericTile implements IElectricGenerator {

	private CachedTileOutput output;
	private boolean generating;
	private double multiplier = 1;

	@Override
	public double getMultiplier() {
		return multiplier;
	}

	@Override
	public void setMultiplier(double val) {
		multiplier = val;
	}

	public TileSolarPanel(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_SOLARPANEL.get(), worldPosition, blockState);
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN));
		addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.values()).shouldSendInfo()
				.valid((slot, stack, i) -> stack.getItem() instanceof ItemUpgrade));
		addComponent(new ComponentContainerProvider("container.solarpanel")
				.createMenu((id, player) -> new ContainerSolarPanel(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	protected void tickCommon(ComponentTickable tickable) {
		setMultiplier(1);
		for (ItemStack stack : this.<ComponentInventory>getComponent(ComponentType.Inventory).getItems()) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade) {
				for (int i = 0; i < stack.getCount(); i++) {
					upgrade.subtype.applyUpgrade.accept(this, null, null);
				}
			}
		}
	}

	protected void tickServer(ComponentTickable tickable) {
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}
		if (tickable.getTicks() % 20 == 0) {
			output.update();
			generating = level.canSeeSky(worldPosition.offset(0, 1, 0));
		}
		if (tickable.getTicks() % 50 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		if (level.isDay() && generating && output.valid()) {
			ElectricityUtilities.receivePower(output.getSafe(), Direction.UP, getProduced(), false);
		}
	}

	@Override
	public TransferPack getProduced() {
		float mod = 1.0f - Mth.clamp(1.0F - (Mth.cos(level.getTimeOfDay(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0f, 1.0f);
		mod *= 1.0f - level.getRainLevel(1f) * 5.0f / 16.0f;
		mod *= (1.0f - level.getThunderLevel(1f) * 5.0F / 16.0f) * 0.8f + 0.2f;
		Biome b = level.getBiomeManager().getBiome(getBlockPos());
		return TransferPack.ampsVoltage(
				getMultiplier() * Constants.SOLARPANEL_AMPERAGE * (b.getBaseTemperature() / 2.0) * mod
						* (level.isRaining() || level.isThundering() ? 0.7f : 1),
				this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	}
}

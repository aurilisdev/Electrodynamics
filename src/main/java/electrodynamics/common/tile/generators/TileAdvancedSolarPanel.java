package electrodynamics.common.tile.generators;

import java.util.HashSet;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TargetValue;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileAdvancedSolarPanel extends GenericGeneratorTile implements IMultiblockTileNode {

	protected CachedTileOutput output;
	public TargetValue currentRotation = new TargetValue(property(new Property<>(PropertyType.Double, "currentRotation", 0.0)));
	private Property<Boolean> generating = property(new Property<>(PropertyType.Boolean, "generating", false));
	private Property<Double> multiplier = property(new Property<>(PropertyType.Double, "multiplier", 1.0));

	@Override
	public double getMultiplier() {
		return multiplier.get();
	}

	@Override
	public void setMultiplier(double val) {
		multiplier.set(val);
	}

	public TileAdvancedSolarPanel(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_ADVANCEDSOLARPANEL.get(), worldPosition, blockState, 2.25, SubtypeItemUpgrade.improvedsolarcell);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().upgrades(1)).validUpgrades(ContainerSolarPanel.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.advancedsolarpanel, this).createMenu((id, player) -> new ContainerSolarPanel(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}
		if (tickable.getTicks() % 40 == 0) {
			output.update(worldPosition.relative(Direction.DOWN));
			generating.set(level.canSeeSky(worldPosition.offset(0, 1, 0)));
		}
		if (level.isDay() && generating.get() && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.UP, getProduced(), false);
		}
	}

	@Override
	public TransferPack getProduced() {
		double mod = 1.0f - Mth.clamp(1.0F - (Mth.cos(level.getTimeOfDay(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0f, 1.0f);
		double temp = level.getBiomeManager().getBiome(getBlockPos()).value().getBaseTemperature();
		double lerped = Mth.lerp((temp + 1) / 3.0, 1.5, 3) / 3.0;
		return TransferPack.ampsVoltage(getMultiplier() * Constants.ADVANCEDSOLARPANEL_AMPERAGE * lerped * mod * (level.isRaining() || level.isThundering() ? 0.8f : 1), this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	}

	@Override
	@OnlyIn(value = Dist.CLIENT)
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(2);
	}

	@Override
	public HashSet<Subnode> getSubNodes() {
		return BlockMachine.advancedsolarpanelsubnodes;
	}
	
	@Override
	public int getComparatorSignal() {
		return generating.get() ? 15 : 0;
	}
	
	
}

package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TargetValue;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileAdvancedSolarPanel extends GenericTile implements IMultiblockTileNode, IElectricGenerator {

	public TargetValue currentRotation = new TargetValue(0);
	protected CachedTileOutput output;
	private boolean generating = false;
	private double multiplier = 1;

	@Override
	public double getMultiplier() {
		return multiplier;
	}

	@Override
	public void setMultiplier(double val) {
		multiplier = val;
	}

	public TileAdvancedSolarPanel(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get(), worldPosition, blockState);
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentInventory(this).size(1).upgrades(1).slotFaces(0, Direction.values()).shouldSendInfo().validUpgrades(ContainerSolarPanel.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider("container.advancedsolarpanel").createMenu((id, player) -> new ContainerSolarPanel(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
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
		if (tickable.getTicks() % 40 == 0) {
			output.update();
			generating = level.canSeeSky(worldPosition.offset(0, 1, 0));
		}
		if (tickable.getTicks() % 50 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		if (level.isDay() && generating && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), Direction.UP, getProduced(), false);
		}
	}

	@Override
	public TransferPack getProduced() {
		double mod = 1.0f - Mth.clamp(1.0F - (Mth.cos(level.getTimeOfDay(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0f, 1.0f);
		double temp = level.getBiomeManager().getBiome(getBlockPos()).getBaseTemperature();
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

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.0625, 0.0625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0.9375, 1, 0.0625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0, 0.0625, 1, 0.0625, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0, 0.0625, 0.0625, 0.0625, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0, 1, 0.125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.125, 0.875, 0.1875, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.1875, 0.8125, 0.4375, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.4375, 0.375, 0.625, 1.8125, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 1.8125, 0.3125, 0.6875, 2, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.65375, 1.875, -0.73, 1.73, 1.9375, 1.73), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.73, 1.875, -0.73, 0.34625, 1.9375, 1.73), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.67875, 1.9375, -0.67875, -0.6275, 2, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.67875, 1.8125, -0.67875, -0.6275, 1.875, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.24375, 1.9375, -0.67875, 0.295, 2, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.24375, 1.8125, -0.67875, 0.295, 1.875, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.705, 1.9375, -0.67875, 0.75625, 2, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.705, 1.8125, -0.67875, 0.75625, 1.875, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.6275, 1.9375, -0.67875, 1.67875, 2, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.6275, 1.8125, -0.67875, 1.67875, 1.875, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75625, 1.9375, 1.6275, 1.6275, 2, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75625, 1.8125, 1.6275, 1.6275, 1.875, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.6275, 1.9375, 1.6275, 0.24375, 2, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.6275, 1.8125, 1.6275, 0.24375, 1.875, 1.67875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.6275, 1.9375, -0.67875, 0.24375, 2, -0.6275), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.6275, 1.8125, -0.67875, 0.24375, 1.875, -0.6275), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75625, 1.9375, -0.67875, 1.6275, 2, -0.6275), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75625, 1.8125, -0.67875, 1.6275, 1.875, -0.6275), BooleanOp.OR);
		VoxelShapes.registerShape(SubtypeMachine.advancedsolarpanel, shape, Direction.NORTH);
	}
}

package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.multiblock.Subnode;
import electrodynamics.api.multiblock.parent.IMultiblockParentTile;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileMultiSubnode;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.TargetValue;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileAdvancedSolarPanel extends TileSolarPanel implements IMultiblockParentTile {

	public final TargetValue currentRotation = new TargetValue();

	public TileAdvancedSolarPanel(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_ADVANCEDSOLARPANEL.get(), worldPosition, blockState, 2.25, SubtypeItemUpgrade.improvedsolarcell);
		// addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		// addComponent(new ComponentPacketHandler(this));
		// addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		// addComponent(new ComponentInventory(this, InventoryBuilder.newInv().upgrades(1)).validUpgrades(ContainerSolarPanel.VALID_UPGRADES).valid(machineValidator()));
		this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2).setEnergyProduction().setNoEnergyReception();
		forceComponent(new ComponentContainerProvider(SubtypeMachine.advancedsolarpanel, this).createMenu((id, player) -> new ContainerSolarPanel(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
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
	public Subnode[] getSubNodes() {
		return BlockMachine.advancedsolarpanelsubnodes;
	}

	@Override
	public void onSubnodeDestroyed(TileMultiSubnode subnode) {
		level.destroyBlock(worldPosition, true);
	}

	@Override
	public InteractionResult onSubnodeUse(Player player, InteractionHand hand, BlockHitResult hit, TileMultiSubnode subnode) {
		return use(player, hand, hit);
	}

	@Override
	public int getSubdnodeComparatorSignal(TileMultiSubnode subnode) {
		return getComparatorSignal();
	}

	@Override
	public Direction getFacingDirection() {
		return this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	}

	static {

		VoxelShape shape = Block.box(0, 0, 0, 16, 2, 16);
		shape = Shapes.join(shape, Block.box(2, 2, 2, 14, 3, 14), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(3, 3, 3, 13, 7, 13), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(6, 7, 6, 10, 16, 10), BooleanOp.OR);

		VoxelShapes.registerShape(SubtypeMachine.advancedsolarpanel, shape, Direction.NORTH);
	}

}

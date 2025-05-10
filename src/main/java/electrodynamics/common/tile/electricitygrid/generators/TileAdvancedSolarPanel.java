package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import voltaic.api.multiblock.subnodebased.TileMultiSubnode;
import voltaic.api.multiblock.subnodebased.parent.IMultiblockParentBlock;
import voltaic.api.multiblock.subnodebased.parent.IMultiblockParentTile;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.utilities.object.TargetValue;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileAdvancedSolarPanel extends TileSolarPanel implements IMultiblockParentTile {

	public final TargetValue currentRotation = new TargetValue();

	public TileAdvancedSolarPanel() {
		super(ElectrodynamicsTiles.TILE_ADVANCEDSOLARPANEL.get(), 2.25, SubtypeItemUpgrade.improvedsolarcell);
		this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2);
		forceComponent(new ComponentContainerProvider(SubtypeMachine.advancedsolarpanel.tag(), this).createMenu((id, player) -> new ContainerSolarPanel(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public TransferPack getProduced() {
		double mod = 1.0f - MathHelper.clamp(1.0F - (MathHelper.cos(level.getTimeOfDay(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0f, 1.0f);
		double temp = level.getBiomeManager().getBiome(getBlockPos()).getBaseTemperature();
		double lerped = MathHelper.lerp((temp + 1) / 3.0, 1.5, 3) / 3.0;
		return TransferPack.ampsVoltage(getMultiplier() * ElectroConstants.ADVANCEDSOLARPANEL_AMPERAGE * lerped * mod * (level.isRaining() || level.isThundering() ? 0.8f : 1), this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getVoltage());
	}

	@Override
	public IMultiblockParentBlock.SubnodeWrapper getSubNodes() {
		return SubtypeMachine.Subnodes.ADVANCEDSOLARPANEL;
	}

	@Override
	public void onSubnodeDestroyed(TileMultiSubnode subnode) {
		level.destroyBlock(worldPosition, true);
	}
	
	@Override
	public ActionResultType onSubnodeUse(PlayerEntity player, Hand hand, BlockRayTraceResult hit, TileMultiSubnode subnode) {
		return use(player, hand, hit);
	}

	@Override
	public int getSubdnodeComparatorSignal(TileMultiSubnode subnode) {
		return getComparatorSignal();
	}

	@Override
	public Direction getFacingDirection() {
		return getFacing();
	}
	
	@Override
	@OnlyIn(value = Dist.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(2);
	}

}

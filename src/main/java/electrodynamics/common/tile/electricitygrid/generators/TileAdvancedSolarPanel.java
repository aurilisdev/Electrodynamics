package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.multiblock.Subnode;
import electrodynamics.api.multiblock.parent.IMultiblockParentTile;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSolarPanel;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.TileMultiSubnode;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.TargetValue;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileAdvancedSolarPanel extends TileSolarPanel implements IMultiblockParentTile {

	public final TargetValue currentRotation = new TargetValue();

	public TileAdvancedSolarPanel() {
		super(ElectrodynamicsBlockTypes.TILE_ADVANCEDSOLARPANEL.get(), 2.25, SubtypeItemUpgrade.improvedsolarcell);
		this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2);
		forceComponent(new ComponentContainerProvider(SubtypeMachine.advancedsolarpanel, this).createMenu((id, player) -> new ContainerSolarPanel(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public TransferPack getProduced() {
		double mod = 1.0f - MathHelper.clamp(1.0F - (MathHelper.cos(level.getTimeOfDay(1f) * ((float) Math.PI * 2f)) * 2.0f + 0.2f), 0.0f, 1.0f);
		double temp = level.getBiomeManager().getBiome(getBlockPos()).getBaseTemperature();
		double lerped = MathHelper.lerp((temp + 1) / 3.0, 1.5, 3) / 3.0;
		return TransferPack.ampsVoltage(getMultiplier() * Constants.ADVANCEDSOLARPANEL_AMPERAGE * lerped * mod * (level.isRaining() || level.isThundering() ? 0.8f : 1), this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getVoltage());
	}

	@Override
	@OnlyIn(value = Dist.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
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
	
	
}

package electrodynamics.common.tile.machines.mineralcrusher;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.common.inventory.container.ContainerO2OProcessorTriple;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;

public class TileMineralCrusherTriple extends TileMineralCrusher {

    public TileMineralCrusherTriple(BlockPos pos, BlockState state) {
	super(ElectrodynamicsTiles.TILE_MINERALCRUSHERTRIPLE.get(), 3, pos, state);

	addComponent(new ComponentContainerProvider(SubtypeMachine.mineralcrushertriple.tag(), this)
		.createMenu((id, player) -> new ContainerO2OProcessorTriple(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
    }

}

package electrodynamics.common.block;

import java.util.Random;

import electrodynamics.common.block.subtype.SubtypeOre;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveBlockRegister;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.registers.VoltaicCapabilities;

public class BlockOre extends OreBlock {

	public final SubtypeOre ore;

	public BlockOre(SubtypeOre ore) {
		super(Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(ore.hardness, ore.resistance).harvestLevel(ore.harvestLevel).harvestTool(ToolType.PICKAXE).randomTicks());
		this.ore = ore;
	}
	
	@Override
	protected int xpOnDrop(Random random) {
		return MathHelper.nextInt(random, ore.minXP, ore.maxDrop);
	}
	
	@Override
	public void entityInside(BlockState state, World level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level.getLevelData().getGameTime() % 10 == 0 && !level.isClientSide && entity instanceof LivingEntity) {
        	LivingEntity living = (LivingEntity) entity;
            IRadiationRecipient cap = living.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT).orElse(CapabilityUtils.EMPTY_RADIATION_REPIPIENT);
            if (cap == CapabilityUtils.EMPTY_RADIATION_REPIPIENT) {
                return;
            }
            RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());
            if(rad.amount() <= 0) {
                return;
            }
            cap.recieveRadiation(living, rad.amount(), rad.strength());
        }
    }

    //adds permanent radiation source at this blocks location until the block is destroyed
    @Override
	public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);

        if(level.getLevelData().getGameTime() % 10 != 0) {
            return;
        }
        RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());

        if(rad.amount() <= 0 || RadiationSystem.getRadiationSources(level).contains(pos)) {
            return;
        }

        RadiationSystem.addRadiationSource(level, new SimpleRadiationSource(rad.amount(), rad.strength(), 10, false, 1, pos, false, false));
    }

    @Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if(!level.isClientSide) {
            RadiationSystem.removeRadiationSource(level, pos, false);
        }
    }

    @Override
	public void onPlace(BlockState state, World level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if(level.isClientSide) {
            return;
        }
        RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());

        if(rad.amount() <= 0 || RadiationSystem.getRadiationSources(level).contains(pos)) {
            return;
        }
        RadiationSystem.addRadiationSource(level, new SimpleRadiationSource(rad.amount(), rad.strength(), 10, false, 1, pos, false, false));
    }

}

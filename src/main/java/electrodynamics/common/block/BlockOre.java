package electrodynamics.common.block;

import java.util.Random;

import electrodynamics.common.block.subtype.SubtypeOre;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import voltaic.api.radiation.RadiationManager;
import voltaic.api.radiation.util.IRadiationRecipient;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveBlockRegister;
import voltaic.common.settings.VoltaicConstants;
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
	public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);

        if(!VoltaicConstants.ORES_EMIT_RADIATION || level.getLevelData().getGameTime() % VoltaicConstants.ORE_RADIATION_ADMIT_RATE != 0) {
            return;
        }

        RadioactiveObject rad = RadioactiveBlockRegister.getValue(state.getBlock());

        if(rad.amount() <= 0) {
            return;
        }

        for(Entity entity : level.getAllEntities()) {

            AxisAlignedBB box = new AxisAlignedBB(pos).inflate(5);

            if(!entity.isAlive() || !(entity instanceof LivingEntity) || !entity.getBoundingBox().intersects(box)) {
                continue;
            }

            IRadiationRecipient cap = entity.getCapability(VoltaicCapabilities.CAPABILITY_RADIATIONRECIPIENT).orElse(CapabilityUtils.EMPTY_RADIATION_REPIPIENT);
            if (cap == CapabilityUtils.EMPTY_RADIATION_REPIPIENT) {
                continue;
            }

            double recieved = RadiationManager.getAppliedRadiation(level, pos, entity.blockPosition().above(), rad.amount(), rad.strength());

            cap.recieveRadiation((LivingEntity) entity, recieved, rad.strength());

        }
    }

}

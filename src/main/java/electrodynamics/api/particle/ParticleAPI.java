package electrodynamics.api.particle;

import electrodynamics.client.particle.GrindedParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ParticleAPI {

	public static void addGrindedParticle(Level world, double d, double e, double f, double g, double h, double i, BlockState defaultState, BlockPos pos) {
		Minecraft.getInstance().particleEngine.add(new GrindedParticle((ClientLevel) world, d, e, f, g, h, i, defaultState).setBlockPos(pos));
	}

}

package electrodynamics.api.particle;

import electrodynamics.client.particle.GrindedParticle;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleAPI {

	public static void addGrindedParticle(World world, double d, double e, double f, double g, double h, double i, BlockState defaultState, BlockPos pos) {
		Minecraft.getInstance().particleEngine.add(new GrindedParticle((ClientWorld) world, d, e, f, g, h, i, defaultState).setBlockPos(pos));
	}

}

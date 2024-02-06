package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.client.particle.plasmaball.ParticleOptionPlasmaBall;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElectrodynamicsParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, References.ID);

	public static final DeferredHolder<ParticleType<?>, ParticleOptionPlasmaBall> PARTICLE_PLASMA_BALL = PARTICLES.register("plasmaball", ParticleOptionPlasmaBall::new);

}

package electrodynamics.registers;

import electrodynamics.api.References;
import electrodynamics.client.particle.plasmaball.ParticleOptionPlasmaBall;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, References.ID);

	public static final RegistryObject<ParticleOptionPlasmaBall> PARTICLE_PLASMA_BALL = PARTICLES.register("plasmaball", ParticleOptionPlasmaBall::new);

}

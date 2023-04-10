package electrodynamics.client.particle.plasmaball;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import electrodynamics.registers.ElectrodynamicsParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleOptionPlasmaBall extends ParticleType<ParticleOptionPlasmaBall> implements ParticleOptions {

	public float scale;
	public float gravity;
	public int maxAge;
	public int r;
	public int g;
	public int b;
	public int a;

	public static final Codec<ParticleOptionPlasmaBall> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				Codec.FLOAT.fieldOf("scale").forGetter(instance0 -> instance0.scale), 
				Codec.FLOAT.fieldOf("gravity").forGetter(instance0 -> instance0.gravity), 
				Codec.INT.fieldOf("maxage").forGetter(instance0 -> instance0.maxAge),
				Codec.INT.fieldOf("r").forGetter(instance0 -> instance0.r),
				Codec.INT.fieldOf("g").forGetter(instance0 -> instance0.g),
				Codec.INT.fieldOf("b").forGetter(instance0 -> instance0.b),
				Codec.INT.fieldOf("a").forGetter(instance0 -> instance0.a)
			).apply(instance, (scale, gravity, age, r, g, b, a) -> new ParticleOptionPlasmaBall().setParameters(scale, gravity, age, r, g, b, a));
	});

	public static final ParticleOptions.Deserializer<ParticleOptionPlasmaBall> DESERIALIZER = new ParticleOptions.Deserializer<ParticleOptionPlasmaBall>() {

		@Override
		public ParticleOptionPlasmaBall fromCommand(ParticleType<ParticleOptionPlasmaBall> type, StringReader reader) throws CommandSyntaxException {
			ParticleOptionPlasmaBall particle = new ParticleOptionPlasmaBall();

			reader.expect(' ');
			float scale = reader.readFloat();

			reader.expect(' ');
			float gravity = reader.readFloat();

			reader.expect(' ');
			int maxAge = reader.readInt();
			
			reader.expect(' ');
			int r = reader.readInt();
			
			reader.expect(' ');
			int g = reader.readInt();
			
			reader.expect(' ');
			int b = reader.readInt();
			
			reader.expect(' ');
			int a = reader.readInt();

			return particle.setParameters(scale, gravity, maxAge, r, g, b, a);

		}

		@Override
		public ParticleOptionPlasmaBall fromNetwork(ParticleType<ParticleOptionPlasmaBall> type, FriendlyByteBuf buffer) {
			return new ParticleOptionPlasmaBall().setParameters(buffer.readFloat(), buffer.readFloat(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
		}
	};

	public ParticleOptionPlasmaBall() {
		super(false, DESERIALIZER);
	}

	public ParticleOptionPlasmaBall setParameters(float scale, float gravity, int maxAge, int r, int g, int b, int a) {
		this.scale = scale;
		this.gravity = gravity;
		this.maxAge = maxAge;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	@Override
	public ParticleType<?> getType() {
		return ElectrodynamicsParticles.PARTICLE_PLASMA_BALL.get();
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeFloat(scale);
		buffer.writeFloat(gravity);
		buffer.writeInt(maxAge);
		buffer.writeInt(r);
		buffer.writeInt(g);
		buffer.writeInt(b);
		buffer.writeInt(a);
	}

	@Override
	public String writeToString() {
		return ForgeRegistries.PARTICLE_TYPES.getKey(getType()).toString() + ", scale: " + scale + ", gravity: " + gravity + ", maxage: " + maxAge + ", r: " + r + ", g: " + g + ", b: " + b + ", a: " + a; 
	}

	@Override
	public Codec<ParticleOptionPlasmaBall> codec() {
		return CODEC;
	}

}

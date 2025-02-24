package electrodynamics.prefab.utilities;

import java.util.Arrays;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.Util;
import net.minecraft.world.phys.Vec3;

public class CodecUtils {

	public static final Codec<Vec3> VEC3_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			//
			Codec.DOUBLE.fieldOf("x").forGetter(Vec3::x),
			//
			Codec.DOUBLE.fieldOf("y").forGetter(Vec3::y),
			//
			Codec.DOUBLE.fieldOf("z").forGetter(Vec3::z))
			//
			.apply(instance, Vec3::new));

	public static final Codec<UUID> UUID_CODEC = Codec.INT_STREAM.comapFlatMap((p_235884_) -> {
		return Util.fixedSize(p_235884_, 4).map(CodecUtils::uuidFromIntArray);
	}, (p_235888_) -> {
		return Arrays.stream(uuidToIntArray(p_235888_));
	});

	public static UUID uuidFromIntArray(int[] p_235886_) {
		return new UUID((long) p_235886_[0] << 32 | (long) p_235886_[1] & 4294967295L, (long) p_235886_[2] << 32 | (long) p_235886_[3] & 4294967295L);
	}

	public static int[] uuidToIntArray(UUID pUuid) {
		long i = pUuid.getMostSignificantBits();
		long j = pUuid.getLeastSignificantBits();
		return leastMostToIntArray(i, j);
	}

	private static int[] leastMostToIntArray(long pMost, long pLeast) {
		return new int[] { (int) (pMost >> 32), (int) pMost, (int) (pLeast >> 32), (int) pLeast };
	}

}

package electrodynamics.prefab.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class CodecUtils {

	public static final Codec<Vector3d> VEC3_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			//
			Codec.DOUBLE.fieldOf("x").forGetter(Vector3d::x),
			//
			Codec.DOUBLE.fieldOf("y").forGetter(Vector3d::y),
			//
			Codec.DOUBLE.fieldOf("z").forGetter(Vector3d::z))
			//
			.apply(instance, Vector3d::new));

	public static final Codec<UUID> UUID_CODEC = Codec.INT_STREAM.comapFlatMap((intstream) -> {
		return Util.fixedSize(intstream, 4).map(CodecUtils::uuidFromIntArray);
	}, (uuid) -> {
		return Arrays.stream(uuidToIntArray(uuid));
	});

	public static final Codec<Vector3f> VECTOR3F_CODEC = Codec.FLOAT.listOf().comapFlatMap((list) -> {
		return fixedSize(list, 3).map((floatlist) -> {
			return new Vector3f(floatlist.get(0), floatlist.get(1), floatlist.get(2));
		});
	}, (vector) -> {
		return ImmutableList.of(vector.x(), vector.y(), vector.z());
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

	public static <T> DataResult<List<T>> fixedSize(List<T> pList, int pExpectedSize) {
		if (pList.size() != pExpectedSize) {
			String s = "Input is not a list of " + pExpectedSize + " elements";
			return pList.size() >= pExpectedSize ? DataResult.error(s, pList.subList(0, pExpectedSize)) : DataResult.error(s);
		} else {
			return DataResult.success(pList);
		}
	}

}

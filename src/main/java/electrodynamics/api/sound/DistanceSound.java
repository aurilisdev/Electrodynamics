package electrodynamics.api.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;

public class DistanceSound extends AbstractTickableSoundInstance {
	private Block block;

	public DistanceSound(SoundEvent soundIn, SoundSource categoryIn, float volumeIn, float pitchIn, BlockPos pos) {
		super(soundIn, categoryIn, RandomSource.create());
		volume = volumeIn;
		pitch = pitchIn;
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		block = Minecraft.getInstance().level.getBlockState(pos).getBlock();
		attenuation = Attenuation.LINEAR;
	}

	@Override
	public float getVolume() {
		if (block == null || Minecraft.getInstance().player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5) > 16 * 16 || Minecraft.getInstance().level.getBlockState(new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z))).getBlock() != block) {
			block = null;
			return 0;
		}
		return super.getVolume() * (float) Math.min(1.0, 1.0f / Math.sqrt(Minecraft.getInstance().player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5)));
	}

	@Override
	public void tick() {
	}
}

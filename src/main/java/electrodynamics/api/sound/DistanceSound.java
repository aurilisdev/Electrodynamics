package electrodynamics.api.sound;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class DistanceSound extends TickableSound {
    private Block block;

    public DistanceSound(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, BlockPos pos) {
	super(soundIn, categoryIn);
	volume = volumeIn;
	pitch = pitchIn;
	x = pos.getX();
	y = pos.getY();
	z = pos.getZ();
	block = Minecraft.getInstance().world.getBlockState(pos).getBlock();
	attenuationType = AttenuationType.LINEAR;
    }

    @Override
    public float getVolume() {
	if (block == null || Minecraft.getInstance().player.getDistanceSq(x + 0.5, y + 0.5, z + 0.5) > 16 * 16
		|| Minecraft.getInstance().world.getBlockState(new BlockPos(x, y, z)).getBlock() != block) {
	    block = null;
	    return 0;
	}
	return super.getVolume() * (float) Math.min(1.0, 1.0f / (Minecraft.getInstance().player.getDistanceSq(x + 0.5, y + 0.5, z + 0.5)));
    }

    @Override
    public void tick() {
    }
}

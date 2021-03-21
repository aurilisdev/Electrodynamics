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
	this.volume = volumeIn;
	this.pitch = pitchIn;
	this.x = pos.getX();
	this.y = pos.getY();
	this.z = pos.getZ();
	this.block = Minecraft.getInstance().world.getBlockState(pos).getBlock();
	this.attenuationType = AttenuationType.LINEAR;
    }

    @Override
    public float getVolume() {
	if (block == null || Minecraft.getInstance().player.getDistanceSq(x + 0.5, y + 0.5, z + 0.5) > 16 * 16
		|| Minecraft.getInstance().world.getBlockState(new BlockPos(x, y, z)).getBlock() != block) {
	    this.block = null;
	    return 0;
	}
	return super.getVolume() / (float) (1.0f / (1 + Minecraft.getInstance().player.getDistanceSq(x + 0.5, y + 0.5, z + 0.5)));
    }

    @Override
    public void tick() {
    }
}

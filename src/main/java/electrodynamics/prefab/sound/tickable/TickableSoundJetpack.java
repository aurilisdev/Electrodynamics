package electrodynamics.prefab.sound.tickable;

import java.util.UUID;

import javax.annotation.Nullable;

import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.WorldUtils;
import voltaic.registers.VoltaicDataComponentTypes;

public class TickableSoundJetpack extends AbstractTickableSoundInstance {

    private static final int MAX_DISTANCE = 10;

    private final UUID originId;
    private @Nullable Player originPlayer;

    public TickableSoundJetpack(UUID originPlayer) {
	super(ElectrodynamicsSounds.SOUND_JETPACK.get(), SoundSource.PLAYERS, RandomSource.create());
	originId = originPlayer;
	volume = 0.5F;
	pitch = 1.0F;
	looping = true;
    }

    @Override
    public void tick() {
	Level level = Minecraft.getInstance().level;
	if (level == null) {
	    stop();
	    return;
	}
	originPlayer = level.getPlayerByUUID(originId);
	if (checkStop()) {
	    stop();
	    return;
	}
	volume = getPlayedVolume();
	pitch = 1.0F;
    }

    public float getPlayedVolume() {
	Player origin = originPlayer;
	Player listener = Minecraft.getInstance().player;
	if (origin == null || listener == null)
	    return 0;
	ItemStack jetpack = origin.getItemBySlot(EquipmentSlot.CHEST);
	if (!jetpack.getOrDefault(VoltaicDataComponentTypes.USED, false))
	    return 0;
	double distance = WorldUtils.distanceBetweenPositions(origin.blockPosition(), listener.blockPosition());
	if (distance > MAX_DISTANCE)
	    return 0;
	return distance > 0 ? (float) (0.5F / distance) : 0.5F;
    }

    protected boolean checkStop() {
	Player origin = originPlayer;
	if (origin == null || origin.isRemoved())
	    return true;
	ItemStack jetpack = origin.getItemBySlot(EquipmentSlot.CHEST);
	return jetpack.isEmpty() || !ItemUtils.testItems(jetpack.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get(),
		ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get());
    }
}
package electrodynamics.prefab.sound;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TickableSoundJetpack extends AbstractTickableSoundInstance {

	private static final int MAX_DISTANCE = 10;
	private Player originPlayer;
	
	public TickableSoundJetpack(Player originPlayer) {
		super(SoundRegister.SOUND_JETPACK.get(), SoundSource.PLAYERS);
		this.originPlayer = originPlayer;
		this.volume = 0.5F;
		this.pitch = 1.0F;
		this.looping = true;
	}
	
	@Override
	public void tick() {
		if(checkStop()) {
			stop();
			return;
		} 
		this.volume = getPlayedVolume();
	}
	
	public float getPlayedVolume() {
		ItemStack jetpack = originPlayer.getItemBySlot(EquipmentSlot.CHEST);
		if(jetpack.getOrCreateTag().getBoolean(NBTUtils.USED)) {
			double distance = WorldUtils.distanceBetweenPositions(originPlayer.blockPosition(), Minecraft.getInstance().player.blockPosition());
			if(distance > 0 && distance <= MAX_DISTANCE) {
				return (float) (0.5F / distance);
			} else if (distance > MAX_DISTANCE) {
				return 0;
			} else {
				return 0.5F;
			}
		}
		return 0;
	}
	
	protected boolean checkStop() {
		if(originPlayer.isRemoved()) {
			return true;
		}
		ItemStack jetpack = originPlayer.getItemBySlot(EquipmentSlot.CHEST);
		if(jetpack.isEmpty()) {
			return true;
		}
		if(!ItemUtils.testItems(jetpack.getItem(), DeferredRegisters.ITEM_JETPACK.get())) {
			if(!ItemUtils.testItems(jetpack.getItem(), DeferredRegisters.ITEM_COMBATCHESTPLATE.get())) {
				return true;
			}
		}
		return false;
	}

}

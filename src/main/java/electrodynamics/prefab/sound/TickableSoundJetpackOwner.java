package electrodynamics.prefab.sound;

import electrodynamics.Electrodynamics;
import electrodynamics.SoundRegister;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketJetpackSoundOwner;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TickableSoundJetpackOwner extends AbstractTickableSoundInstance {
	
	protected Player originPlayer;
	
	public TickableSoundJetpackOwner(Player originPlayer) {
		super(SoundRegister.SOUND_JETPACK.get(), SoundSource.PLAYERS);
		this.originPlayer = originPlayer;
		this.volume = 0.5F;
		this.pitch = 1.0F;
		this.looping = true;
	}

	@Override
	public void tick() {
		if(shouldStop(originPlayer)) {
			stop();
			return;
		} 
		this.volume = 0.5F;
		
	}
	
	public float getVolume() {
		
	}
	
	

}

package electrodynamics.prefab.sound;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TickableSoundJetpackNonOwner extends TickableSoundJetpackOwner {

	private static final int MAX_DISTANCE = 10;
	
	public TickableSoundJetpackNonOwner(Player originPlayer) {
		super(originPlayer);
	}
	
	@Override
	public void tick() {
		if(checkStop()) {
			stop();
			return;
		} 
		this.volume = getVolume();
	}
	
	@Override
	public float getVolume() {
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
		if(!(ItemUtils.testItems(jetpack.getItem(), DeferredRegisters.ITEM_JETPACK.get()) || ItemUtils.testItems(jetpack.getItem(), DeferredRegisters.ITEM_COMBATCHESTPLATE.get()))) {
			return true;
		}
		return false;
	}

}

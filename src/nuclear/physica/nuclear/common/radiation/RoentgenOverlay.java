package physica.nuclear.common.radiation;

import java.awt.Color;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import physica.CoreReferences;
import physica.api.core.IBaseUtilities;
import physica.nuclear.common.NuclearItemRegister;

@SideOnly(Side.CLIENT)
public class RoentgenOverlay implements IBaseUtilities {

	private float cachedRoentgen = 0;
	private long cachedTime;

	public static HashMap<Long, Float> cachedRadiationMap = new HashMap<>();

	public static void storeDataValue(long worldTime, float kiloRoentgen) {
		if (RoentgenOverlay.cachedRadiationMap.containsKey(worldTime)) {
			cachedRadiationMap.put(worldTime, kiloRoentgen + cachedRadiationMap.get(worldTime));
		} else {
			cachedRadiationMap.put(worldTime, kiloRoentgen);
		}
	}

	public static float getRoentgenTickClient() {
		long worldTime = Minecraft.getMinecraft().thePlayer.worldObj.getTotalWorldTime();
		Float value = cachedRadiationMap.get(worldTime - 1);
		return value == null ? -3 : value;
	}

	@SubscribeEvent
	public void onGameOverlay(RenderGameOverlayEvent.Text event) {
		EntityClientPlayerMP mp = Minecraft.getMinecraft().thePlayer;
		ItemStack use = mp.inventory.getCurrentItem();
		if (use != null && use.getItem() == NuclearItemRegister.itemGeigerCounter) {
			GL11.glPushMatrix();
			double rounded = NuclearItemRegister.itemGeigerCounter.getEnergyStored(use) > 5
					? roundPrecise(cachedRoentgen * RadiationSystem.toRealRoentgenConversionRate, 2) : roundPrecise(Math.min(3.6, cachedRoentgen * RadiationSystem.toRealRoentgenConversionRate), 2);
			String radiationText = "Roentgen per hour: " + (cachedRoentgen == 0 ? "< 0.01" : rounded);
			ScaledResolution get = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			Minecraft.getMinecraft().fontRenderer.drawString(radiationText, get.getScaledWidth() / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(radiationText) / 2,
					get.getScaledHeight() / 2 + 20,
					Color.green.getRGB());
			GL11.glPopMatrix();
			if (Minecraft.getMinecraft().thePlayer.worldObj.rand.nextFloat() * 50 * RadiationSystem.toRealRoentgenConversionRate < (rounded == 3.6 ? 180 : rounded)) {
				Minecraft.getMinecraft().getSoundHandler()
						.playSound(new PositionedSoundRecord(new ResourceLocation(CoreReferences.PREFIX + "block.geiger"), 0.3f, 0, (float) mp.posX, (float) mp.posY, (float) mp.posZ));
			}
		}
	}

	@SubscribeEvent
	public void onGameUpdate(ClientTickEvent event) {
		if (event.type == Type.CLIENT && event.phase == Phase.END) {
			EntityClientPlayerMP client = Minecraft.getMinecraft().thePlayer;
			if (client != null) {
				long worldTime = Minecraft.getMinecraft().thePlayer.worldObj.getTotalWorldTime();
				if (worldTime != cachedTime) {
					float roentgenTick = getRoentgenTickClient();
					if (roentgenTick != -3) {
						cachedTime = worldTime;
						cachedRoentgen = roentgenTick;
					} else if (worldTime - cachedTime > 5) {
						cachedRoentgen = Math.max(cachedRoentgen - 0.0813f, 0);
					}
					cachedRadiationMap.clear();
				}
			}
		}
	}
}

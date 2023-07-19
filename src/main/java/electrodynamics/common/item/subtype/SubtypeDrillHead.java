package electrodynamics.common.item.subtype;

import java.util.Locale;

import electrodynamics.api.ISubtype;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.resources.ResourceLocation;

public enum SubtypeDrillHead implements ISubtype {
	steel(200, false, RenderingUtils.getRGBA(255, 194, 194, 194), 1), //194, 194, 194
	stainlesssteel(400, false, RenderingUtils.getRGBA(255, 210, 255, 238), 1.5), //183, 199, 193
	hslasteel(600, false, RenderingUtils.getRGBA(255, 133, 190, 255), 1.75), //133, 190, 255
	titanium(1000, false, RenderingUtils.getRGBA(255, 186, 196, 205), 2), //186, 196, 205
	titaniumcarbide(1, true, RenderingUtils.getRGBA(255, 228, 198, 173), 2.5); //228, 198, 173

	public final int durability;
	public final boolean isUnbreakable;
	public final ResourceLocation blockTextureLoc;
	public final int color;
	public final double speedBoost;

	SubtypeDrillHead(int durability, boolean unbreakable, int color, double speedBoost) {
		this.durability = durability;
		isUnbreakable = unbreakable;
		blockTextureLoc = new ResourceLocation("electrodynamics:block/resource/resourceblock" + toString().toLowerCase(Locale.ROOT));
		this.color = color;
		this.speedBoost = speedBoost;
	}

	@Override
	public String tag() {
		return "drillhead" + name();
	}

	@Override
	public String forgeTag() {
		return "drillhead/" + name();
	}

	@Override
	public boolean isItem() {
		return true;
	}

}

package electrodynamics.common.item.subtype;

import java.util.Locale;

import electrodynamics.api.ISubtype;
import net.minecraft.resources.ResourceLocation;

public enum SubtypeDrillHead implements ISubtype {
	steel(200, false),
	stainlesssteel(400, false),
	hslasteel(600, false),
	titanium(1000, false),
	titaniumcarbide(1, true);

	public final int durability;
	public final boolean isUnbreakable;
	public final ResourceLocation blockTextureLoc;

	SubtypeDrillHead(int durability, boolean unbreakable) {
		this.durability = durability;
		isUnbreakable = unbreakable;
		blockTextureLoc = new ResourceLocation("electrodynamics:block/resource/resourceblock" + toString().toLowerCase(Locale.ROOT));
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

package electrodynamics.common.block.subtype;

import electrodynamics.api.ISubtype;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public enum SubtypeResourceBlock implements ISubtype {
	tin(2.0f, 3.0f, Material.METAL, SoundType.METAL), lead(2.0f, 3.0f, Material.METAL, SoundType.METAL),
	silver(2.0f, 3.0f, Material.METAL, SoundType.METAL), bronze(2.0f, 3.0f, Material.METAL, SoundType.METAL),
	steel(2.0f, 3.0f, Material.METAL, SoundType.METAL), aluminum(2.0f, 3.0f, Material.METAL, SoundType.METAL),
	chromium(2.0f, 3.0f, Material.METAL, SoundType.METAL), stainlesssteel(2.0f, 3.0f, Material.METAL, SoundType.METAL),
	vanadiumsteel(2.0f, 3.0f, Material.METAL, SoundType.METAL), hslasteel(2.0f, 3.0f, Material.METAL, SoundType.METAL),
	titanium(2.0f, 3.0f, Material.METAL, SoundType.METAL), titaniumcarbide(2.0f, 3.0f, Material.METAL, SoundType.METAL);

	private float hardness;
	private float resistance;
	private Material material;
	private SoundType soundType;

	SubtypeResourceBlock(float hardness, float resistance, Material material, SoundType soundType) {
		this.hardness = hardness;
		this.resistance = resistance;
		this.material = material;
		this.soundType = soundType;
	}

	public float getHardness() {
		return hardness;
	}

	public float getResistance() {
		return resistance;
	}

	public Material getMaterial() {
		return material;
	}

	public SoundType getSoundType() {
		return soundType;
	}

	@Override
	public String tag() {
		return "resourceblock" + name();
	}

	@Override
	public String forgeTag() {
		return "blocks/" + name();
	}

	@Override
	public boolean isItem() {
		return false;
	}

}

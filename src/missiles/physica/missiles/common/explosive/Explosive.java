package physica.missiles.common.explosive;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import physica.missiles.client.render.model.ModelMissileBase;
import physica.missiles.common.explosive.blast.BlastTemplate;
import physica.missiles.common.explosive.blast.types.BlastTemplateFire;
import physica.missiles.common.explosive.blast.types.BlastTemplatePrimed;
import physica.missiles.common.explosive.blast.types.BlastTemplatePush;
import physica.missiles.common.explosive.blast.types.BlastTemplateRejuvenation;
import physica.missiles.common.explosive.blast.types.BlastTemplateSpread;
import physica.missiles.common.explosive.blast.types.EnumShrapnel;

public class Explosive {
	public static ArrayList<Explosive> explosiveSet = new ArrayList<>();
	static
	{
		explosiveSet.add(new Explosive("condensive", true, true, true, "condensive_top", "condensive_side", "tier1_bottom").setBlastTemplate(new BlastTemplatePrimed(1, 1, 1, 4.5f)));
		explosiveSet.add(new Explosive("repulsive", true, true, true, "repulsive_top", "repulsive_side", "tier1_bottom").setBlastTemplate(new BlastTemplatePush(120, 1, 1, 2.0f, false)));
		explosiveSet.add(new Explosive("attractive", true, true, true, "attractive_top", "attractive_side", "tier1_bottom").setBlastTemplate(new BlastTemplatePush(120, 1, 1, 2.0f, true)));
		explosiveSet.add(new Explosive("shrapnel", true, true, true, "shrapnel_top", "shrapnel_side", "tier1_bottom").setBlastTemplate(new BlastTemplateSpread(120, 1, 1, EnumShrapnel.SHRAPNEL)));
		explosiveSet.add(new Explosive("anvil", true, true, true, "anvil_top", "anvil_side", "tier1_bottom").setBlastTemplate(new BlastTemplateSpread(120, 1, 1, EnumShrapnel.ANVIL)));
		explosiveSet.add(new Explosive("incendiary", true, true, true, "incendiary_top", "incendiary_side", "tier1_bottom").setBlastTemplate(new BlastTemplateFire(120, 1, 1, 9.0f)));
		explosiveSet.add(new Explosive("fragmentation", true, true, false, "fragmentation_top", "fragmentation_side", "tier2_bottom").setBlastTemplate(new BlastTemplateSpread(180, 2, 1, EnumShrapnel.FRAGMENT)));
		explosiveSet.add(new Explosive("breaching", true, true, false, "breaching_top", "breaching_side", "tier2_bottom").setBlastTemplate(new BlastTemplatePrimed(1, 2, 1, 7.5f)));
		explosiveSet.add(new Explosive("rejuvenation", true, true, false, "rejuvenation_top", "rejuvenation_side", "tier2_bottom").setBlastTemplate(new BlastTemplateRejuvenation(180, 2, 1)));
	}
	public final String			localeName;
	public final boolean		hasMissile;
	public final boolean		hasBlock;
	public final boolean		hasGrenade;
	private BlastTemplate		blastTemplate;
	@SideOnly(Side.CLIENT)
	private ModelMissileBase	renderModel;

	@SideOnly(Side.CLIENT)
	private ResourceLocation	renderResource;

	private String				topIcon, bottomIcon, sideIcon;
	private int					id;

	public Explosive(String localeName, boolean hasMissile, boolean hasBlock, boolean hasGrenade) {
		this.localeName = localeName;
		this.hasMissile = hasMissile;
		this.hasBlock = hasBlock;
		this.hasGrenade = hasGrenade;

	}

	public Explosive(String localeName, boolean hasMissile, boolean hasBlock, boolean hasGrenade, String topIcon, String sideIcon, String bottomIcon) {
		this.localeName = localeName;
		this.hasMissile = hasMissile;
		this.hasBlock = hasBlock;
		this.hasGrenade = hasGrenade;
		this.topIcon = topIcon;
		this.sideIcon = sideIcon;
		this.bottomIcon = bottomIcon;
		id = explosiveSet.size();
	}

	public int getId()
	{
		return id;
	}

	public BlastTemplate getBlastTemplate()
	{
		return blastTemplate;
	}

	public Explosive setBlastTemplate(BlastTemplate blastTemplate)
	{
		this.blastTemplate = blastTemplate;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public ModelMissileBase getRenderModel()
	{
		return renderModel;
	}

	@SideOnly(Side.CLIENT)
	public Explosive setRenderModel(ModelMissileBase renderModel)
	{
		this.renderModel = renderModel;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getRenderResource()
	{
		return renderResource;
	}

	@SideOnly(Side.CLIENT)
	public Explosive setRenderResource(ResourceLocation renderResource)
	{
		this.renderResource = renderResource;
		return this;
	}

	public String getTopIcon()
	{
		return topIcon;
	}

	public String getSideIcon()
	{
		return sideIcon;
	}

	public String getBottomIcon()
	{
		return bottomIcon;
	}

	public static Explosive get(int explosiveID)
	{
		for (Explosive ex : explosiveSet)
		{
			if (ex.id == explosiveID)
			{
				return ex;
			}
		}
		return null;
	}

}

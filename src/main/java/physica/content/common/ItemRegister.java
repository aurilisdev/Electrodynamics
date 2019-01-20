package physica.content.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import physica.Physica;
import physica.References;
import physica.api.base.IProxyContent;
import physica.api.lib.item.ItemUpdateable;
import physica.content.common.items.ItemInformation;
import physica.content.common.items.update.ItemUpdateAntimatter;
import physica.content.common.items.update.ItemUpdateDarkMatter;
import physica.content.common.items.update.ItemUpdateUranium;

public class ItemRegister implements IProxyContent {
	public static final ItemInformation	itemEmptyContainer					= (ItemInformation) new ItemInformation("emptyContainer").setMaxStackSize(64);
	public static final ItemInformation	itemAntimatterContainer125Milligram	= (ItemInformation) ((ItemUpdateable) new ItemInformation("antimatterContainer100Milligram", "125 Milligrams")
			.setTextureName(References.PREFIX + "antimatterContainer")).setUpdate(new ItemUpdateAntimatter());
	public static final ItemInformation	itemAntimatterContainer1Gram		= (ItemInformation) ((ItemUpdateable) new ItemInformation("antimatterContainer1Gram", "1 Gram")
			.setTextureName(References.PREFIX + "antimatterContainer")).setUpdate(new ItemUpdateAntimatter().setScale(3));
	public static final ItemInformation	itemDarkmatterContainer				= (ItemInformation) new ItemInformation("darkmatterContainer").setUpdate(new ItemUpdateDarkMatter()).setMaxDamage(12);
	public static final ItemInformation	itemFissileFuelContainer			= (ItemInformation) new ItemInformation("fuelContainer", "Fissile").setUpdate(new ItemUpdateUranium()).setMaxDamage(100)
			.setTextureName(References.PREFIX + "fissileFuelContainer");
	public static final ItemInformation	itemBreederFuelContainer			= (ItemInformation) new ItemInformation("fuelContainer", "Breeder").setUpdate(new ItemUpdateUranium()).setMaxDamage(100)
			.setTextureName(References.PREFIX + "breederFuelContainer");

	public static final ItemInformation	itemUranium235						= (ItemInformation) new ItemInformation("uranium", "Isotope 235").setUpdate(new ItemUpdateUranium().setScale(2f))
			.setTextureName(References.PREFIX + "uranium235");
	public static final ItemInformation	itemUranium238						= (ItemInformation) new ItemInformation("uranium", "Isotope 238").setUpdate(new ItemUpdateUranium())
			.setTextureName(References.PREFIX + "uranium238");

	public static final Item			itemPlateIron						= new Item().setCreativeTab(Physica.creativeTab).setUnlocalizedName("ironplate")
			.setTextureName(References.PREFIX + "ironplate");
	public static final Item			itemMotor							= new Item().setCreativeTab(Physica.creativeTab).setUnlocalizedName("motor").setTextureName(References.PREFIX + "motor");

	@Override
	public void preInit() {
		GameRegistry.registerItem(itemEmptyContainer, itemEmptyContainer.getUnlocalizedName());
		GameRegistry.registerItem(itemAntimatterContainer125Milligram, itemAntimatterContainer125Milligram.getUnlocalizedName());
		GameRegistry.registerItem(itemAntimatterContainer1Gram, itemAntimatterContainer1Gram.getUnlocalizedName());
		GameRegistry.registerItem(itemDarkmatterContainer, itemDarkmatterContainer.getUnlocalizedName());
		GameRegistry.registerItem(itemFissileFuelContainer, itemFissileFuelContainer.getUnlocalizedName() + "Fissile");
		GameRegistry.registerItem(itemBreederFuelContainer, itemBreederFuelContainer.getUnlocalizedName() + "Breeder");

		GameRegistry.registerItem(itemUranium235, itemUranium235.getUnlocalizedName() + "235");
		GameRegistry.registerItem(itemUranium238, itemUranium238.getUnlocalizedName() + "238");

		GameRegistry.registerItem(itemPlateIron, itemPlateIron.getUnlocalizedName());
		GameRegistry.registerItem(itemMotor, itemMotor.getUnlocalizedName());
	}
}

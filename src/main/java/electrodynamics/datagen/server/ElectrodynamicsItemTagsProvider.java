package electrodynamics.datagen.server;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ElectrodynamicsItemTagsProvider extends ItemTagsProvider {

	public ElectrodynamicsItemTagsProvider(DataGenerator generator, BlockTagsProvider provider, ExistingFileHelper existingFileHelper) {
		super(generator, provider, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		for (SubtypeCircuit circuit : SubtypeCircuit.values()) {
			tag(circuit.tag).add(ElectrodynamicsItems.getItem(circuit));
		}

		for (SubtypeDust dust : SubtypeDust.values()) {
			tag(dust.tag).add(ElectrodynamicsItems.getItem(dust));
		}

		for (SubtypeGear gear : SubtypeGear.values()) {
			tag(gear.tag).add(ElectrodynamicsItems.getItem(gear));
		}

		for (SubtypeImpureDust dust : SubtypeImpureDust.values()) {
			tag(dust.tag).add(ElectrodynamicsItems.getItem(dust));
		}

		for (SubtypeIngot ingot : SubtypeIngot.values()) {
			tag(ingot.tag).add(ElectrodynamicsItems.getItem(ingot));
		}

		for (SubtypeOre ore : SubtypeOre.values()) {
			tag(ore.itemTag).add(ElectrodynamicsItems.getItem(ore));
		}

		for (SubtypeOxide oxide : SubtypeOxide.values()) {
			tag(oxide.tag).add(ElectrodynamicsItems.getItem(oxide));
		}

		for (SubtypeRod rod : SubtypeRod.values()) {
			tag(rod.tag).add(ElectrodynamicsItems.getItem(rod));
		}

		for (SubtypePlate plate : SubtypePlate.values()) {
			tag(plate.tag).add(ElectrodynamicsItems.getItem(plate));
		}

		for (SubtypeOxide oxide : SubtypeOxide.values()) {
			tag(oxide.tag).add(ElectrodynamicsItems.getItem(oxide));
		}

		for (SubtypeResourceBlock storage : SubtypeResourceBlock.values()) {
			tag(storage.itemTag).add(ElectrodynamicsItems.getItem(storage));
		}

		Builder<Item> gears = tag(ElectrodynamicsTags.Items.GEARS);

		for (SubtypeGear gear : SubtypeGear.values()) {
			gears.addTag(gear.tag);
		}

		Builder<Item> ingots = tag(ElectrodynamicsTags.Items.INGOTS);

		for (SubtypeIngot ingot : SubtypeIngot.values()) {
			ingots.addTag(ingot.tag);
		}

		Builder<Item> ores = tag(ElectrodynamicsTags.Items.ORES);

		for (SubtypeOre ore : SubtypeOre.values()) {
			ores.addTag(ore.itemTag);
		}

		tag(ElectrodynamicsTags.Items.COAL_COKE).add(ElectrodynamicsItems.ITEM_COAL_COKE.get());

		tag(ElectrodynamicsTags.Items.PLASTIC).add(ElectrodynamicsItems.ITEM_SHEETPLASTIC.get());

		tag(ElectrodynamicsTags.Items.INSULATES_PLAYER_FEET).add(ElectrodynamicsItems.ITEM_RUBBERBOOTS.get(), ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get());

	}

}

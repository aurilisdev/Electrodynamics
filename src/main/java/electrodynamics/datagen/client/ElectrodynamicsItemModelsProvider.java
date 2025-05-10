package electrodynamics.datagen.client;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.common.block.subtype.SubtypeWire.WireMaterial;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.datagen.DataGenerators;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.common.data.ExistingFileHelper;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.datagen.utils.client.BaseItemModelsProvider;

public class ElectrodynamicsItemModelsProvider extends BaseItemModelsProvider {

	public ElectrodynamicsItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper, Electrodynamics.ID);
	}

	@Override
	protected void registerModels() {

		layeredItem(ElectrodynamicsItems.ITEM_COAL_COKE, Parent.GENERATED, itemLoc("coalcoke"));
		layeredItem(ElectrodynamicsItems.ITEM_CERAMICINSULATION, Parent.GENERATED, itemLoc("insulationceramic"));
		layeredBuilder(name(ElectrodynamicsItems.ITEM_COIL), Parent.GENERATED, itemLoc("coil")).transforms().transform(Perspective.GUI).scale(0.8F).end();
		layeredBuilder(name(ElectrodynamicsItems.ITEM_LAMINATEDCOIL), Parent.GENERATED, itemLoc("laminatedcoil")).transforms().transform(Perspective.GUI).scale(0.8F).end();
		layeredItem(ElectrodynamicsItems.ITEM_INSULATION, Parent.GENERATED, itemLoc("insulation"));
		layeredItem(ElectrodynamicsItems.ITEM_MOLYBDENUMFERTILIZER, Parent.GENERATED, itemLoc("molybdenumfertilizer"));
		layeredItem(ElectrodynamicsItems.ITEM_MOTOR, Parent.GENERATED, itemLoc("motor"));
		layeredItem(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING, Parent.GENERATED, itemLoc("compositeplatingraw"));
		layeredItem(ElectrodynamicsItems.ITEM_SHEETPLASTIC, Parent.GENERATED, itemLoc("sheetplastic"));
		layeredItem(ElectrodynamicsItems.ITEM_SLAG, Parent.GENERATED, itemLoc("slag"));
		layeredBuilder(name(ElectrodynamicsItems.ITEM_SOLARPANELPLATE), Parent.GENERATED, itemLoc("solarpanelplate")).transforms().transform(Perspective.GUI).scale(0.8F).end();
		layeredItem(ElectrodynamicsItems.ITEM_TITANIUM_COIL, Parent.GENERATED, itemLoc("titaniumheatcoil"));
		layeredItem(ElectrodynamicsItems.ITEM_PLASTIC_FIBERS, Parent.GENERATED, itemLoc("plasticfibers"));
		layeredItem(ElectrodynamicsItems.ITEM_CONCRETEMIX, Parent.GENERATED, itemLoc("concretemix"));

		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITEHELMET, Parent.GENERATED, itemLoc("armor/compositehelmet"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE, Parent.GENERATED, itemLoc("armor/compositechestplate"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS, Parent.GENERATED, itemLoc("armor/compositeleggings"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS, Parent.GENERATED, itemLoc("armor/compositeboots"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITEPLATING, Parent.GENERATED, itemLoc("compositeplating"));
		layeredItem(ElectrodynamicsItems.ITEM_RUBBERBOOTS, Parent.GENERATED, itemLoc("armor/rubberboots"));

		getBucketModel(ElectrodynamicsItems.ITEM_CANISTERREINFORCED, Parent.FORGE_DEFAULT).fluid(Fluids.WATER).applyFluidLuminosity(true).applyTint(true).end().texture("base", itemLoc("canisterreinforced/base")).texture("fluid", itemLoc("canisterreinforced/fluid"));
		layeredBuilder(name(ElectrodynamicsItems.ITEM_MULTIMETER), Parent.GENERATED, itemLoc("multimeter")).transforms().transform(Perspective.GUI).scale(0.9F).end();
		layeredBuilder(name(ElectrodynamicsItems.ITEM_SEISMICSCANNER), Parent.GENERATED, itemLoc("seismicscanner")).transforms().transform(Perspective.GUI).scale(0.75F).end();
		layeredItem(ElectrodynamicsItems.ITEM_BATTERY, Parent.GENERATED, itemLoc("battery"));
		layeredItem(ElectrodynamicsItems.ITEM_LITHIUMBATTERY, Parent.GENERATED, itemLoc("lithiumbattery"));
		layeredItem(ElectrodynamicsItems.ITEM_CARBYNEBATTERY, Parent.GENERATED, itemLoc("carbynebattery"));
		// TODO make this toggleable?
		toggleableItem(ElectrodynamicsItems.ITEM_ELECTRICBATON, "on", Parent.HANDHELD, Parent.HANDHELD, new ResourceLocation[] { itemLoc("tools/electricbaton") }, new ResourceLocation[] { itemLoc("tools/electricbatonon") });
		toggleableItem(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW, "on", Parent.HANDHELD, Parent.HANDHELD, new ResourceLocation[] { itemLoc("tools/electricchainsaw") }, new ResourceLocation[] { itemLoc("tools/electricchainsawon") });

		toggleableItem(ElectrodynamicsItems.ITEM_ELECTRICDRILL, "on", Parent.HANDHELD, Parent.HANDHELD, new ResourceLocation[] { itemLoc("tools/electricdrilloffbase"), itemLoc("tools/electricdrilloffhead") }, new ResourceLocation[] { itemLoc("tools/electricdrillonbase"), itemLoc("tools/electricdrillonhead") });

		layeredBuilder(name(ElectrodynamicsItems.ITEM_MECHANIZEDCROSSBOW), Parent.GENERATED, itemLoc("tools/mechanizedcrossbow")).transforms()
				//
				.transform(Perspective.THIRDPERSON_RIGHT).rotation(-90, 0, -60).translation(2F, 0.1F, -3F).scale(0.9F).end()
				//
				.transform(Perspective.THIRDPERSON_LEFT).rotation(-90, 0, 30).translation(2, 0.1F, -3).scale(0.9F).end()
				//
				.transform(Perspective.FIRSTPERSON_RIGHT).rotation(-90, 0, -55).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end()
				//
				.transform(Perspective.FIRSTPERSON_LEFT).rotation(-90, 0, 35).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end();
		//

		for (SubtypeCeramic ceramic : SubtypeCeramic.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_CERAMIC.getValue(ceramic), Parent.GENERATED, itemLoc("ceramic/" + ceramic.tag()));
		}

		for (SubtypeCircuit circuit : SubtypeCircuit.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_CIRCUIT.getValue(circuit), Parent.GENERATED, itemLoc("circuit/" + circuit.tag()));
		}

		for (SubtypeCrystal crystal : SubtypeCrystal.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(crystal), Parent.GENERATED, itemLoc("crystal/" + crystal.tag()));
		}

		for (SubtypeDrillHead drill : SubtypeDrillHead.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(drill), Parent.GENERATED, itemLoc("drillhead/drillhead"));
		}

		for (SubtypeDust dust : SubtypeDust.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_DUST.getValue(dust), Parent.GENERATED, itemLoc("dust/" + dust.tag()));
		}

		for (SubtypeGear gear : SubtypeGear.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_GEAR.getValue(gear), Parent.GENERATED, itemLoc("gear/" + gear.tag()));
		}

		for (SubtypeImpureDust impure : SubtypeImpureDust.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_IMPUREDUST.getValue(impure), Parent.GENERATED, itemLoc("impuredust/" + impure.tag()));
		}

		for (SubtypeIngot ingot : SubtypeIngot.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_INGOT.getValue(ingot), Parent.GENERATED, itemLoc("ingot/" + ingot.tag()));
		}

		for (SubtypeItemUpgrade upgrade : SubtypeItemUpgrade.values()) {
			layeredBuilder(name(ElectrodynamicsItems.ITEMS_UPGRADE.getValue(upgrade)), Parent.GENERATED, itemLoc("upgrade/" + upgrade.tag())).transforms().transform(Perspective.GUI).scale(0.8F).end();
		}

		for (SubtypeNugget nugget : SubtypeNugget.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_NUGGET.getValue(nugget), Parent.GENERATED, itemLoc("nugget/" + nugget.tag()));
		}

		for (SubtypeOxide oxide : SubtypeOxide.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_OXIDE.getValue(oxide), Parent.GENERATED, itemLoc("oxide/" + oxide.tag()));
		}

		for (SubtypePlate plate : SubtypePlate.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_PLATE.getValue(plate), Parent.GENERATED, itemLoc("plate/" + plate.tag()));
		}

		for (SubtypeRod rod : SubtypeRod.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_ROD.getValue(rod), Parent.GENERATED, itemLoc("rod/" + rod.tag()));
		}

		// bare
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE)) {
			layeredBuilder(name(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire)), Parent.GENERATED, itemLoc("wire/" + wire.tag())).transforms().transform(Perspective.GUI).scale(0.7F).end();
		}

		// insulated
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.values())) {
			layeredItem(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), Parent.GENERATED, itemLoc("wire/wireinsulated" + wire.getWireMaterial().toString()), itemLoc("wire/wireinsulatedcoil"));
		}

		// logistical
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.values())) {
			layeredItem(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), Parent.GENERATED, itemLoc("wire/wirelogistics" + wire.getWireMaterial().toString()), itemLoc("wire/wirelogisticscoil"), itemLoc("wire/wirelogisticsredstone"));
		}

		// ceramic
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.values())) {
			layeredItem(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), Parent.GENERATED, itemLoc("wire/wireceramicinsulated" + wire.getWireMaterial().toString()), itemLoc("wire/wireceramicinsulatedcolortips"), itemLoc("wire/wireceramicinsulatedcoil"));
		}

		// highly insulated
		for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.values())) {
			layeredItem(ElectrodynamicsItems.ITEMS_WIRE.getValue(wire), Parent.GENERATED, itemLoc("wire/wirehighlyinsulated" + wire.getWireMaterial().toString()), itemLoc("wire/wirehighlyinsulatedcoil"));
		}

		for (SubtypeFluidPipe pipe : SubtypeFluidPipe.values()) {
			layeredItem(ElectrodynamicsItems.ITEMS_PIPE.getValue(pipe), Parent.GENERATED, itemLoc("pipe/" + pipe.tag()));
		}

		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel), existingBlock(blockLoc("advancedsolarpanelitem"))).transforms()
				//
				.transform(Perspective.THIRDPERSON_RIGHT).rotation(35, 45, 0).translation(0, 2.5F, 0).scale(0.375F).end()
				//
				.transform(Perspective.THIRDPERSON_LEFT).rotation(35, 45, 0).translation(0, 2.5F, 0).scale(0.375F).end()
				//
				.transform(Perspective.FIRSTPERSON_RIGHT).rotation(0, 45, 0).scale(0.4F).end()
				//
				.transform(Perspective.FIRSTPERSON_LEFT).rotation(0, 225, 0).scale(0.4F).end()
				//
				.transform(Perspective.GUI).rotation(30, 225, 0).translation(0, -3F, 0).scale(0.265F).end();

		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator), existingBlock(blockLoc("hydroelectricgeneratoritem"))).transforms().transform(Perspective.GUI).rotation(30, 225, 0).translation(1.85F, 1.0F, 0).scale(0.55F).end();
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusher), existingBlock(blockLoc("mineralcrusheritem")));
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble), existingBlock(blockLoc("mineralcrusherdoubleitem")));
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple), existingBlock(blockLoc("mineralcrushertripleitem")));
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinder), existingBlock(blockLoc("mineralgrinderitem")));
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble), existingBlock(blockLoc("mineralgrinderdoubleitem")));
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple), existingBlock(blockLoc("mineralgrindertripleitem")));
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.motorcomplex), existingBlock(blockLoc("motorcomplexitem")));
		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.windmill), existingBlock(blockLoc("windmillitem")));

		simpleBlockItem(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipepump), existingBlock(blockLoc("fluidpipepumpitem")));

	}

}

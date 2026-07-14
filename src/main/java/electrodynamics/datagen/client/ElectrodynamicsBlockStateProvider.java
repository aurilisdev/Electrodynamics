package electrodynamics.datagen.client;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeConcrete;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.InsulationMaterial;
import electrodynamics.common.block.subtype.SubtypeWire.WireClass;
import electrodynamics.common.block.subtype.SubtypeWire.WireColor;
import electrodynamics.common.block.subtype.SubtypeWire.WireMaterial;
import electrodynamics.datagen.DataGenerators;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ExistingFileHelper.ResourceType;
import voltaic.Voltaic;
import voltaic.datagen.utils.client.BaseBlockstateProvider;

public class ElectrodynamicsBlockStateProvider extends BaseBlockstateProvider {

	public ElectrodynamicsBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, exFileHelper, Electrodynamics.ID);
		exFileHelper.trackGenerated(Voltaic.rl("block/steelcasing"), new ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures"));
	}
	
	@Override
	protected void registerStatesAndModels() {

		for (SubtypeGlass glass : SubtypeGlass.values()) {
            simpleBlock(ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(glass), blockLoc("glass/" + glass.tag()), true);
        }

        for (SubtypeOre ore : SubtypeOre.values()) {
            simpleBlock(ElectrodynamicsBlocks.BLOCKS_ORE.getValue(ore), blockLoc("ore/" + ore.tag()), true);
        }

        for (SubtypeOreDeepslate ore : SubtypeOreDeepslate.values()) {
            simpleBlock(ElectrodynamicsBlocks.BLOCKS_DEEPSLATEORE.getValue(ore), blockLoc("deepslateore/" + ore.tag()), true);
        }

        for (SubtypeRawOreBlock raw : SubtypeRawOreBlock.values()) {
            simpleBlock(ElectrodynamicsBlocks.BLOCKS_RAWORE.getValue(raw), blockLoc("raworeblock/" + raw.tag()), true);
        }

        for (SubtypeResourceBlock resource : SubtypeResourceBlock.values()) {
            simpleBlock(ElectrodynamicsBlocks.BLOCKS_RESOURCE.getValue(resource), blockLoc("resource/" + resource.tag()), true);
        }
        
        for (SubtypeConcrete concrete : SubtypeConcrete.values()) {
			simpleBlock(ElectrodynamicsBlocks.BLOCKS_CONCRETE.getValue(concrete), blockLoc("concrete/" + concrete.tag()), true);
		}

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedsolarpanel), existingBlock(blockLoc("advancedsolarpanelbase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.batterybox), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.batterybox)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lithiumbatterybox)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.carbynebatterybox), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.carbynebatterybox)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerlv), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerlv)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargermv), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargermv)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerhv), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chargerhv)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalcrystallizer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalcrystallizer)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalmixer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalmixer)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitbreaker), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitbreaker)), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coalgenerator), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coalgenerator)), existingBlock(blockLoc("coalgeneratorrunning")), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.cobblestonegenerator), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.cobblestonegenerator)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.combustionchamber), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.combustionchamber)), true).transforms().transform(TransformType.GUI).rotation(35, 40, 0).scale(0.665F).end();
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coolantresavoir), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coolantresavoir)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativefluidsource), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativefluidsource)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativepowersource), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativepowersource)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.downgradetransformer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.downgradetransformer)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.upgradetransformer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.upgradetransformer)), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnace), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnace)), existingBlock(blockLoc("electricfurnacerunning")), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacedouble)), existingBlock(blockLoc("electricfurnacedoublerunning")), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricfurnacetriple)), existingBlock(blockLoc("electricfurnacetriplerunning")), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnace), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnace)), existingBlock(blockLoc("electricarcfurnacerunning")), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacedouble)), existingBlock(blockLoc("electricarcfurnacedoublerunning")), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricarcfurnacetriple)), existingBlock(blockLoc("electricarcfurnacetriplerunning")), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricpump), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electricpump)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electrolyticseparator), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electrolyticseparator)), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.energizedalloyer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.energizedalloyer)), existingBlock(blockLoc("energizedalloyerrunning")), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fermentationplant), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fermentationplant)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvoid), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvoid)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCK_FRAME, existingBlock(ElectrodynamicsBlocks.BLOCK_FRAME.get()), true);
        simpleBlock(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER, existingBlock(ElectrodynamicsBlocks.BLOCK_FRAME_CORNER.get()), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.hydroelectricgenerator), existingBlock(blockLoc("hydroelectricgeneratorengine")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lathe), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.lathe)), true);
        wire(ElectrodynamicsBlocks.BLOCK_LOGISTICALMANAGER.get(), existingBlock(blockLoc("logisticalmanager_none")), existingBlock(blockLoc("logisticalmanager_inventory")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusher), existingBlock(blockLoc("mineralcrusherbase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrusherdouble), existingBlock(blockLoc("mineralcrusherdoublebase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralcrushertriple), existingBlock(blockLoc("mineralcrushertriplebase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinder), existingBlock(blockLoc("mineralgrinderbase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrinderdouble), existingBlock(blockLoc("mineralgrinderdoublebase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralgrindertriple), existingBlock(blockLoc("mineralgrindertriplebase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralwasher), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralwasher)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.motorcomplex), existingBlock(blockLoc("motorcomplexbase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.multimeterblock), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.multimeterblock)), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.oxidationfurnace), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.oxidationfurnace)), existingBlock(blockLoc("oxidationfurnacerunning")), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.quarry), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.quarry)), true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.reinforcedalloyer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.reinforcedalloyer)), existingBlock(blockLoc("reinforcedalloyerrunning")), true);
        simpleBlock(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER, existingBlock(ElectrodynamicsBlocks.BLOCK_SEISMICMARKER.get()), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.seismicrelay), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.seismicrelay)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.solarpanel), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.solarpanel)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankreinforced), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankreinforced)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankhsla), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankhsla)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.thermoelectricgenerator)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.windmill), existingBlock(blockLoc("windmillbase")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremill), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremill)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilldouble), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilldouble)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilltriple), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.wiremilltriple)), true);

        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvalve), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvalve)), existingBlock(blockLoc("fluidvalveon")), 90, 0, true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipepump), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipepump)), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipefilter), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipefilter)), true);

        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.relay), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.relay)), existingBlock(blockLoc("relayon")), 180, 0, true);

        simpleBlock(ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING, blockLoc("steelscaffold"), true);

        simpleBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.potentiometer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.potentiometer)), true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advanceddowngradetransformer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advanceddowngradetransformer)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedupgradetransformer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedupgradetransformer)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitmonitor), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitmonitor)), 90, 0, true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.currentregulator), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.currentregulator)), 180, 0, true);

        genWires();
        genFluidPipes();

    }

    private void genWires() {

        String parent = "parent/";
        String name = "block/wire/";
        String texture = "wire/";

        // bare
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE)) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "wire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString())).texture("particle", "#conductor"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "wire_side")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString())).texture("particle", "#conductor"), //
                    false);
        }

        // insulated
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "insulatedwire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString() + "_center")).texture("insulation", blockLoc(texture + "insulationwool_center")).texture("particle", "#insulation"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "insulatedwire_side")).texture("insulation", blockLoc(texture + "insulationwool")).texture("particle", "#insulation"), //
                    false);
        }

        // logistical
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "logisticswire_none")).texture("conductor", blockLoc(texture + "logisticswire" + wire.getWireMaterial().toString())).texture("insulation", blockLoc(texture + "logisticswireinsulation_center")).texture("particle", "#insulation").texture("redstone", blockLoc(texture + "logisticswireredstone_center")), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "logisticswire_side")).texture("insulation", blockLoc(texture + "logisticswireinsulation_side")).texture("particle", "#insulation").texture("redstone", blockLoc(texture + "logisticswireredstone_side")), //
                    false);
        }

        // ceramic
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "ceramicinsulatedwire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString() + "_center")).texture("insulation", blockLoc(texture + "insulationceramic_center_base")).texture("particle", "#insulation"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "ceramicinsulatedwire_side")).texture("insulationbase", blockLoc(texture + "insulationceramic_base")).texture("insulationcolor", blockLoc(texture + "insulationceramic")).texture("particle", "#insulationcolor"), //
                    false);
        }

        // highly insulated
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "highlyinsulatedwire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString() + "_center")).texture("insulation", blockLoc(texture + "insulationwool_center")).texture("particle", "#insulation"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "highlyinsulatedwire_side")).texture("insulation", blockLoc(texture + "insulationwool")).texture("particle", "#insulation"), //
                    false);
        }

    }

    private void genFluidPipes() {

        String parent = "parent/";
        String name = "block/pipe/";
        String texture = "pipe/";

        for (SubtypeFluidPipe pipe : SubtypeFluidPipe.values()) {
            wire(ElectrodynamicsBlocks.BLOCKS_FLUIDPIPE.getValue(pipe), //
                    models().withExistingParent(name + pipe.tag() + "_none", modLoc(parent + "pipe_none")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture"), //
                    models().withExistingParent(name + pipe.tag() + "_side", modLoc(parent + "pipe_side")).texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture"), //
                    false);
        }

    }

}

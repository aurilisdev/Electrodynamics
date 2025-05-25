package electrodynamics.datagen.client;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates.AddonTankNeighborType;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates.ManipulatorHeatingStatus;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
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
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import voltaic.Voltaic;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.datagen.utils.client.BaseBlockstateProvider;

public class ElectrodynamicsBlockStateProvider extends BaseBlockstateProvider {

    public ElectrodynamicsBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, exFileHelper, Electrodynamics.ID);
        exFileHelper.trackGenerated(Voltaic.rl("block/steelcasing"), ElectrodynamicsItemModelsProvider.TEXTURE);
    }

    @Override
    protected void registerStatesAndModels() {

        for (SubtypeGlass glass : SubtypeGlass.values()) {
            glassBlock(ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(glass), blockLoc("glass/" + glass.tag()), true);
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
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.combustionchamber), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.combustionchamber)), true).transforms().transform(ItemDisplayContext.GUI).rotation(35, 40, 0).scale(0.665F).end();
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coolantresavoir), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coolantresavoir)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativefluidsource), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativefluidsource)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativepowersource), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativepowersource)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativegassource), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.creativegassource)), true);
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

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastanksteel), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastanksteel)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastankreinforced), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastankreinforced)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastankhsla), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gastankhsla)), true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCK_ADVANCEDCOMPRESSOR, existingBlock(ElectrodynamicsBlocks.BLOCK_ADVANCEDCOMPRESSOR.get()), 180, 0, false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCK_ADVANCEDDECOMPRESSOR, existingBlock(ElectrodynamicsBlocks.BLOCK_ADVANCEDDECOMPRESSOR.get()), 180, 0, false);
        //Compressor Addon Tank
        getVariantBuilder(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK.get())//
                .partialState().with(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS, AddonTankNeighborType.BOTTOMANDTOPTANK).modelForState().modelFile(existingBlock(blockLoc("compressoraddontanktab"))).addModel()//
                .partialState().with(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS, AddonTankNeighborType.TOPTANK).modelForState().modelFile(existingBlock(blockLoc("compressoraddontankt"))).addModel()//
                .partialState().with(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS, AddonTankNeighborType.BOTTOMTANK).modelForState().modelFile(existingBlock(blockLoc("compressoraddontankb"))).addModel()//
                .partialState().with(ElectrodynamicsBlockStates.ADDONTANK_NEIGHBOR_STATUS, AddonTankNeighborType.NONE).modelForState().modelFile(existingBlock(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK)).addModel();

        blockItem(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK.get(), existingBlock(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK));

        //Compressor Side Block
        ModelFile none = existingBlock(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_SIDE.get());
        ModelFile top = existingBlock(blockLoc("compressorsidet"));

        getVariantBuilder(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_SIDE.get())//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(270).addModel();

        simpleBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gasvent), blockLoc("gasvent"), true);

        // Advanced Thermoelectric Manipulator
        ModelFile off = existingBlock(ElectrodynamicsBlocks.BLOCK_ADVANCED_THERMOELECTRICMANIPULATOR.get());
        ModelFile cool = existingBlock(blockLoc("advancedthermoelectricmanipulatorcool"));
        ModelFile heat = existingBlock(blockLoc("advancedthermoelectricmanipulatorheat"));

        getVariantBuilder(ElectrodynamicsBlocks.BLOCK_ADVANCED_THERMOELECTRICMANIPULATOR.get())
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).modelForState().modelFile(off).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).modelForState().modelFile(off).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).modelForState().modelFile(off).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).modelForState().modelFile(off).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).modelForState().modelFile(cool).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).modelForState().modelFile(cool).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).modelForState().modelFile(cool).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).modelForState().modelFile(cool).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).modelForState().modelFile(heat).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).modelForState().modelFile(heat).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).modelForState().modelFile(heat).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).modelForState().modelFile(heat).rotationY(0).addModel();

        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gasvalve), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gasvalve)), existingBlock(blockLoc("gasvalveon")), 90, 0, true);
        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvalve), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidvalve)), existingBlock(blockLoc("fluidvalveon")), 90, 0, true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gaspipepump), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gaspipepump)), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipepump), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipepump)), false);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gaspipefilter), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gaspipefilter)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipefilter), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fluidpipefilter)), true);

        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.relay), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.relay)), existingBlock(blockLoc("relayon")), 180, 0, true);

        glassBlock(ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING, blockLoc("steelscaffold"), true);

        simpleBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.potentiometer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.potentiometer)), true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advanceddowngradetransformer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advanceddowngradetransformer)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedupgradetransformer), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.advancedupgradetransformer)), true);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitmonitor), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitmonitor)), 90, 0, true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.currentregulator), existingBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.currentregulator)), 180, 0, true);

        horrRotatedLitBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.gascollector), //
                models().cube(SubtypeMachine.gascollector.tag(), blockLoc("gascollector_bottom"), blockLoc("gascollector_fanoff"), blockLoc("gascollector_fanoff"), blockLoc("gascollector_fanoff"), blockLoc("gascollector_fanoff"), blockLoc("gascollector_back")).texture("particle", Voltaic.rl("block/steelcasing")), //
                models().cube(SubtypeMachine.gascollector.tag() + "on", blockLoc("gascollector_bottom"), blockLoc("gascollector_fanon"), blockLoc("gascollector_fanon"), blockLoc("gascollector_fanon"), blockLoc("gascollector_fanon"), blockLoc("gascollector_back")).texture("particle", Voltaic.rl("block/steelcasing")), //
                true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCK_CHEMICALREACTOR, existingBlock(blockLoc("chemicalreactorbottom")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCK_CHEMICALREACTOREXTRA_MIDDLE, existingBlock(blockLoc("chemicalreactormiddle")), false);
        horrRotatedBlock(ElectrodynamicsBlocks.BLOCK_CHEMICALREACTOREXTRA_TOP, existingBlock(blockLoc("chemicalreactortop")), false);

        // Compressor
        none = existingBlock(ElectrodynamicsBlocks.BLOCK_COMPRESSOR.get());
        top = existingBlock(blockLoc("compressortoptank"));

        getVariantBuilder(ElectrodynamicsBlocks.BLOCK_COMPRESSOR.get())//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(270).addModel();
        blockItem(ElectrodynamicsBlocks.BLOCK_COMPRESSOR.get(), existingBlock(ElectrodynamicsBlocks.BLOCK_COMPRESSOR));

        //Decompressor
        none = existingBlock(ElectrodynamicsBlocks.BLOCK_DECOMPRESSOR.get());
        top = existingBlock(blockLoc("decompressortoptank"));

        getVariantBuilder(ElectrodynamicsBlocks.BLOCK_DECOMPRESSOR.get())//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(none).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(top).rotationY(270).addModel();
        blockItem(ElectrodynamicsBlocks.BLOCK_DECOMPRESSOR.get(), existingBlock(ElectrodynamicsBlocks.BLOCK_DECOMPRESSOR));

        //Thermoelectric Manipulator
        off = existingBlock(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR.get());
        cool = existingBlock(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR.get());
        heat = existingBlock(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR.get());

        ModelFile offtop = existingBlock(blockLoc("thermoelectricmanipulatortoptank"));
        ModelFile cooltop = existingBlock(blockLoc("thermoelectricmanipulatortoptank"));
        ModelFile heattop = existingBlock(blockLoc("thermoelectricmanipulatortoptank"));
        getVariantBuilder(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR.get())
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(off).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(off).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(off).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(off).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(cool).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(cool).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(cool).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(cool).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(heat).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(heat).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(heat).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, false).modelForState().modelFile(heat).rotationY(270).addModel()//
                //
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(offtop).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(offtop).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(offtop).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.OFF).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(offtop).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(cooltop).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(cooltop).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(cooltop).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.COOL).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(cooltop).rotationY(270).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.NORTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(heattop).rotationY(0).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.EAST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(heattop).rotationY(90).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.SOUTH).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(heattop).rotationY(180).addModel()//
                .partialState().with(VoltaicBlockStates.FACING, Direction.WEST).with(ElectrodynamicsBlockStates.MANIPULATOR_HEATING_STATUS, ManipulatorHeatingStatus.HEAT).with(ElectrodynamicsBlockStates.COMPRESSORSIDE_HAS_TOPTANK, true).modelForState().modelFile(heattop).rotationY(270).addModel();
        blockItem(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR.get(), existingBlock(ElectrodynamicsBlocks.BLOCK_THERMOELECTRICMANIPULATOR));

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electrolosischamber),
                //
                models().cube(
                                //
                                name(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electrolosischamber)),
                                //
                                blockLoc("multiblock/electrolosischamberbulkhead"),
                                //
                                blockLoc("multiblock/electrolosischamberbulkhead"),
                                //
                                blockLoc("electrolosischamberfront"),
                                //
                                blockLoc("multiblock/electrolosischambercontacts"),
                                //
                                blockLoc("multiblock/electrolosischamberbulkhead"),
                                //
                                blockLoc("multiblock/electrolosischamberbulkhead"))
                        //
                        .texture("particle", Voltaic.rl("block/steelcasing")),
                //
                90, 0, true);

        horrRotatedBlock(ElectrodynamicsBlocks.BLOCK_ROTARYUNIFIER, existingBlock(ElectrodynamicsBlocks.BLOCK_ROTARYUNIFIER), true);


        genWires();
        genFluidPipes();
        genGasPipes();

    }

    private void genWires() {

        String parent = "parent/";
        String name = "block/wire/";
        String texture = "wire/";

        // bare
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.BARE, WireClass.BARE, WireColor.NONE)) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "wire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString())).texture("particle", "#conductor").renderType("cutout"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "wire_side")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString())).texture("particle", "#conductor").renderType("cutout"), //
                    false);
        }

        // insulated
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.INSULATED, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "insulatedwire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString() + "_center")).texture("insulation", blockLoc(texture + "insulationwool_center")).texture("particle", "#insulation").renderType("cutout"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "insulatedwire_side")).texture("insulation", blockLoc(texture + "insulationwool")).texture("particle", "#insulation").renderType("cutout"), //
                    false);
        }

        // logistical
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.WOOL, WireClass.LOGISTICAL, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "logisticswire_none")).texture("conductor", blockLoc(texture + "logisticswire" + wire.getWireMaterial().toString())).texture("insulation", blockLoc(texture + "logisticswireinsulation_center")).texture("particle", "#insulation").texture("redstone", blockLoc(texture + "logisticswireredstone_center")).renderType("cutout"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "logisticswire_side")).texture("insulation", blockLoc(texture + "logisticswireinsulation_side")).texture("particle", "#insulation").texture("redstone", blockLoc(texture + "logisticswireredstone_side")).renderType("cutout"), //
                    false);
        }

        // ceramic
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.CERAMIC, WireClass.CERAMIC, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "ceramicinsulatedwire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString() + "_center")).texture("insulation", blockLoc(texture + "insulationceramic_center_base")).texture("particle", "#insulation").renderType("cutout"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "ceramicinsulatedwire_side")).texture("insulationbase", blockLoc(texture + "insulationceramic_base")).texture("insulationcolor", blockLoc(texture + "insulationceramic")).texture("particle", "#insulationcolor").renderType("cutout"), //
                    false);
        }

        // highly insulated
        for (SubtypeWire wire : DataGenerators.getWires(WireMaterial.values(), InsulationMaterial.THICK_WOOL, WireClass.THICK, WireColor.values())) {
            wire(ElectrodynamicsBlocks.BLOCKS_WIRE.getValue(wire), //
                    models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "highlyinsulatedwire_none")).texture("conductor", blockLoc(texture + wire.getWireMaterial().toString() + "_center")).texture("insulation", blockLoc(texture + "insulationwool_center")).texture("particle", "#insulation").renderType("cutout"), //
                    models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "highlyinsulatedwire_side")).texture("insulation", blockLoc(texture + "insulationwool")).texture("particle", "#insulation").renderType("cutout"), //
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

    private void genGasPipes() {
        gasPipeUninsulated(SubtypeGasPipe.UNINSULATEDCOPPER);
        gasPipeUninsulated(SubtypeGasPipe.UNINSULATEDSTEEL);
        gasPipeUninsulatedPlastic(SubtypeGasPipe.UNINSULATEDPLASTIC);

        // gasPipeWoolInsulated(SubtypeGasPipe.WOOLINSULATEDCOPPER);
        // gasPipeWoolInsulated(SubtypeGasPipe.WOOLINSULATEDSTEEL);
        // gasPipeWoolInsulatedPlastic(SubtypeGasPipe.WOOLINSULATEDPLASTIC);

        // gasPipeCeramicInsulated(SubtypeGasPipe.CERAMICINSULATEDCOPPER);
        // gasPipeCeramicInsulated(SubtypeGasPipe.CERAMICINSULATEDSTEEL);
        // gasPipeCeramicInsulatedPlastic(SubtypeGasPipe.CERAMICINSULATEDPLASTIC);
    }

    private void gasPipeUninsulated(SubtypeGasPipe pipe) {
        String parent = "parent/gaspipe";
        String name = "block/gaspipe/";
        String texture = "gaspipe/";
        wire(ElectrodynamicsBlocks.BLOCKS_GASPIPE.getValue(pipe), //
                models().withExistingParent(name + pipe.tag() + "center", modLoc(parent + "uninsulatedcenter")).texture("texture", blockLoc(texture + pipe.tag() + "center")).texture("particle", "#texture"), //
                models().withExistingParent(name + pipe.tag() + "side", modLoc(parent + "uninsulatedside")).texture("texture", blockLoc(texture + pipe.tag() + "side")).texture("particle", "#texture"), //
                false);
    }

    private void gasPipeUninsulatedPlastic(SubtypeGasPipe pipe) {
        String parent = "parent/gaspipe";
        String name = "block/gaspipe/";
        String texture = "gaspipe/";
        wire(ElectrodynamicsBlocks.BLOCKS_GASPIPE.getValue(pipe), //
                models().withExistingParent(name + pipe.tag() + "center", modLoc(parent + "uninsulatedcenter")).texture("texture", blockLoc(texture + pipe.tag() + "center")).texture("particle", "#texture"), //
                models().withExistingParent(name + pipe.tag() + "side", modLoc(parent + "uninsulatedplasticside")).texture("texture", blockLoc(texture + pipe.tag() + "side")).texture("particle", "#texture"), //
                false);
    }

    @SuppressWarnings("unused")
    private void gasPipeWoolInsulated(SubtypeGasPipe pipe) {
        String parent = "parent/gaspipe";
        String name = "block/gaspipe/";
        String texture = "gaspipe/";
        wire(ElectrodynamicsBlocks.BLOCKS_GASPIPE.getValue(pipe), //
                models().getExistingFile(modLoc(parent + "woolinsulatedcenter")), //
                models().withExistingParent(name + pipe.tag() + "side", modLoc(parent + "insulatedside")).texture("texture", blockLoc(texture + pipe.tag() + "side")).texture("particle", "#texture"), //
                false);
    }

    @SuppressWarnings("unused")
    private void gasPipeWoolInsulatedPlastic(SubtypeGasPipe pipe) {
        String parent = "parent/gaspipe";
        String name = "block/gaspipe/";
        String texture = "gaspipe/";
        wire(ElectrodynamicsBlocks.BLOCKS_GASPIPE.getValue(pipe), //
                models().getExistingFile(modLoc(parent + "woolinsulatedcenter")), //
                models().withExistingParent(name + pipe.tag() + "side", modLoc(parent + "insulatedplasticside")).texture("texture", blockLoc(texture + pipe.tag() + "side")).texture("particle", "#texture"), //
                false);
    }

    @SuppressWarnings("unused")
    private void gasPipeCeramicInsulated(SubtypeGasPipe pipe) {
        String parent = "parent/gaspipe";
        String name = "block/gaspipe/";
        String texture = "gaspipe/";
        wire(ElectrodynamicsBlocks.BLOCKS_GASPIPE.getValue(pipe), //
                models().getExistingFile(modLoc(parent + "ceramicinsulatedcenter")), //
                models().withExistingParent(name + pipe.tag() + "side", modLoc(parent + "insulatedside")).texture("texture", blockLoc(texture + pipe.tag() + "side")).texture("particle", "#texture"), //
                false);
    }

    @SuppressWarnings("unused")
    private void gasPipeCeramicInsulatedPlastic(SubtypeGasPipe pipe) {
        String parent = "parent/gaspipe";
        String name = "block/gaspipe/";
        String texture = "gaspipe/";
        wire(ElectrodynamicsBlocks.BLOCKS_GASPIPE.getValue(pipe),//
                models().getExistingFile(modLoc(parent + "ceramicinsulatedcenter")), //
                models().withExistingParent(name + pipe.tag() + "side", modLoc(parent + "insulatedplasticside")).texture("texture", blockLoc(texture + pipe.tag() + "side")).texture("particle", "#texture"), //
                false);
    }

    

}

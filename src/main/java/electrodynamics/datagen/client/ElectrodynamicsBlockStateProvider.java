package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.common.block.connect.EnumConnectType;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypePipe;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.block.subtype.SubtypeWire.WireType;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ElectrodynamicsBlockStateProvider extends BlockStateProvider {

	public ElectrodynamicsBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, References.ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		
		for(SubtypeGlass glass : SubtypeGlass.values()) {
			glassBlock(ElectrodynamicsBlocks.getBlock(glass), blockLoc("glass/" + glass.tag()), true);
		}
		
		for(SubtypeOre ore : SubtypeOre.values()) {
			simpleBlock(ElectrodynamicsBlocks.getBlock(ore), blockLoc("ore/" + ore.tag()), true);
		}
		
		for(SubtypeOreDeepslate ore : SubtypeOreDeepslate.values()) {
			simpleBlock(ElectrodynamicsBlocks.getBlock(ore), blockLoc("deepslateore/" + ore.tag()), true);
		}
		
		for(SubtypeRawOreBlock raw : SubtypeRawOreBlock.values()) {
			simpleBlock(ElectrodynamicsBlocks.getBlock(raw), blockLoc("raworeblock/" + raw.tag()), true);
		}
		
		for(SubtypeResourceBlock resource : SubtypeResourceBlock.values()) {
			simpleBlock(ElectrodynamicsBlocks.getBlock(resource), blockLoc("resource/" + resource.tag()), true);
		}
		
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.advancedsolarpanel), existingBlock(blockLoc("advancedsolarpanelbase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.batterybox), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.batterybox)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lithiumbatterybox), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lithiumbatterybox)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.carbynebatterybox), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.carbynebatterybox)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerlv), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerlv)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargermv), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargermv)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerhv), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chargerhv)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalcrystallizer), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalcrystallizer)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalmixer)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.circuitbreaker), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.circuitbreaker)), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coalgenerator), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coalgenerator)), existingBlock(blockLoc("coalgeneratorrunning")), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.combustionchamber), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.combustionchamber)), true).transforms().transform(TransformType.GUI).rotation(35, 40, 0).scale(0.665F).end();
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coolantresavoir), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coolantresavoir)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativefluidsource), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativefluidsource)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativepowersource), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.creativepowersource)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.downgradetransformer), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.downgradetransformer)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.upgradetransformer), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.upgradetransformer)), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnace), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnace)), existingBlock(blockLoc("electricfurnacerunning")), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedouble), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacedouble)), existingBlock(blockLoc("electricfurnacedoublerunning")), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriple), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricfurnacetriple)), existingBlock(blockLoc("electricfurnacetriplerunning")), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnace), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnace)), existingBlock(blockLoc("electricarcfurnacerunning")), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacedouble), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacedouble)), existingBlock(blockLoc("electricarcfurnacedoublerunning")), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacetriple), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricarcfurnacetriple)), existingBlock(blockLoc("electricarcfurnacetriplerunning")), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricpump), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electricpump)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electrolyticseparator), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.electrolyticseparator)), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.energizedalloyer), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.energizedalloyer)), existingBlock(blockLoc("energizedalloyerrunning")), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fluidvoid), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fluidvoid)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.blockFrame, existingBlock(ElectrodynamicsBlocks.blockFrame), true);
		simpleBlock(ElectrodynamicsBlocks.blockFrameCorner, existingBlock(ElectrodynamicsBlocks.blockFrameCorner), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.hydroelectricgenerator), existingBlock(blockLoc("hydroelectricgeneratorengine")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lathe), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.lathe)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.blockLogisticalManager, existingBlock(ElectrodynamicsBlocks.blockLogisticalManager), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusher), existingBlock(blockLoc("mineralcrusherbase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrusherdouble), existingBlock(blockLoc("mineralcrusherdoublebase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralcrushertriple), existingBlock(blockLoc("mineralcrushertriplebase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinder), existingBlock(blockLoc("mineralgrinderbase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrinderdouble), existingBlock(blockLoc("mineralgrinderdoublebase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralgrindertriple), existingBlock(blockLoc("mineralgrindertriplebase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralwasher), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralwasher)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.motorcomplex), existingBlock(blockLoc("motorcomplexbase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.multimeterblock), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.multimeterblock)), true);
		airBlock(ElectrodynamicsBlocks.multi, "block/multisubnode", false);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.oxidationfurnace), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.oxidationfurnace)), existingBlock(blockLoc("oxidationfurnacerunning")), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.quarry), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.quarry)), true);
		horrRotatedLitBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.reinforcedalloyer), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.reinforcedalloyer)), existingBlock(blockLoc("reinforcedalloyerrunning")), true);
		simpleBlock(ElectrodynamicsBlocks.blockSeismicMarker, modelLoc("seismicmarker"), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.seismicrelay), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.seismicrelay)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.solarpanel), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.solarpanel)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tanksteel), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tanksteel)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankreinforced), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankreinforced)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankhsla), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankhsla)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.thermoelectricgenerator), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.thermoelectricgenerator)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.windmill), existingBlock(blockLoc("windmillbase")), false);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremill), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremill)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilldouble), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilldouble)), true);
		horrRotatedBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilltriple), existingBlock(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremilltriple)), true);

		genWires();
		genPipes();
		
	
	}
	
	private void genWires() {
	
		String parent = "parent/";
		String name = "block/wire/" ;
		String texture = "wire/";
		
		BlockModelBuilder logisticalNone = models().withExistingParent(name + "logisticswire_none", modLoc(parent + "logisticswire_none"))
				.texture("texture", blockLoc(texture + "logisticswire_base")).texture("particle", "#texture");
		BlockModelBuilder logisticalSide = models().withExistingParent(name + "logisticswire_side", modLoc(parent + "logisticswire_side"))
				.texture("texture", blockLoc(texture + "logisticswire_base")).texture("particle", "#texture");
		
		for(SubtypeWire wire : SubtypeWire.getWiresForType(WireType.UNINSULATED)) {
			wire(
				ElectrodynamicsBlocks.getBlock(wire), 
				models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "wire_none"))
					.texture("texture", blockLoc(texture + wire.tag())).texture("particle", "#texture"), 
				models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "wire_side"))
					.texture("texture", blockLoc(texture + wire.tag())).texture("particle", "#texture"),
				false
			);
		} 
		
		for(SubtypeWire wire : SubtypeWire.getWiresForType(WireType.INSULATED)) {
			wire(
				ElectrodynamicsBlocks.getBlock(wire), 
				models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "insulatedwire_none"))
					.texture("texture", blockLoc(texture + wire.tag())).texture("particle", "#texture"), 
				models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "insulatedwire_side"))
					.texture("texture", blockLoc(texture + wire.tag())).texture("particle", "#texture"),
				false
			);
		}
		
		for(SubtypeWire wire : SubtypeWire.getWiresForType(WireType.LOGISTICAL)) {
			wire(
				ElectrodynamicsBlocks.getBlock(wire), 
				logisticalNone, 
				logisticalSide,
				false
			);
		} 
		
		for(SubtypeWire wire : SubtypeWire.getWiresForType(WireType.CERAMIC)) {
				wire(
					ElectrodynamicsBlocks.getBlock(wire), 
					models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "ceramicinsulatedwire_none"))
						.texture("texture", blockLoc(texture + wire.tag())).texture("particle", "#texture"), 
					models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "ceramicinsulatedwire_side"))
						.texture("texture", blockLoc(texture + wire.tag())).texture("particle", "#texture"),
					false
				);
		} 
		
		for(SubtypeWire wire : SubtypeWire.getWiresForType(WireType.HIGHLY_INSULATED)) {
			wire(
				ElectrodynamicsBlocks.getBlock(wire), 
				models().withExistingParent(name + wire.tag() + "_none", modLoc(parent + "highlyinsulatedwire_none"))
					.texture("texture", blockLoc(texture + wire.tag().replaceFirst("highly", ""))).texture("particle", "#texture"), 
				models().withExistingParent(name + wire.tag() + "_side", modLoc(parent + "highlyinsulatedwire_side"))
					.texture("texture", blockLoc(texture + wire.tag().replaceFirst("highly", ""))).texture("particle", "#texture"),
				false
			);
			
		}

	}

	private void genPipes() {
		
		String parent = "parent/";
		String name = "block/pipe/" ;
		String texture = "pipe/";
		
		for(SubtypePipe pipe : SubtypePipe.values()) {
			wire(
					ElectrodynamicsBlocks.getBlock(pipe), 
					models().withExistingParent(name + pipe.tag() + "_none", modLoc(parent + "pipe_none"))
						.texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture"), 
					models().withExistingParent(name + pipe.tag() + "_side", modLoc(parent + "pipe_side"))
						.texture("texture", blockLoc(texture + pipe.tag())).texture("particle", "#texture"),
					false
				);
		}
		
	}
	
	private ItemModelBuilder simpleBlock(Block block, ModelFile file, boolean registerItem) {
		simpleBlock(block, file);
		if (registerItem) {
			return blockItem(block, file);
		}
		return null;
	}

	private ItemModelBuilder simpleBlock(RegistryObject<Block> block, ResourceLocation texture, boolean registerItem) {
		return simpleBlock(block.get(), texture, registerItem);
	}
	
	private ItemModelBuilder simpleBlock(Block block, ResourceLocation texture, boolean registerItem) {
		return simpleBlock(block, models().cubeAll(name(block), texture), registerItem);
	}
	
	private ItemModelBuilder glassBlock(RegistryObject<Block> block, ResourceLocation texture, boolean registerItem) {
		return glassBlock(block.get(), texture, registerItem);
	}

	private ItemModelBuilder glassBlock(Block block, ResourceLocation texture, boolean registerItem) {
		BlockModelBuilder builder = models().cubeAll(name(block), texture).renderType("cutout");
		getVariantBuilder(block).partialState().setModels(new ConfiguredModel(builder));
		if (registerItem) {
			return blockItem(block, builder);
		}
		return null;
	}
	
	private ItemModelBuilder airBlock(RegistryObject<Block> block, String particleTexture, boolean registerItem) {
		return airBlock(block.get(), particleTexture, registerItem);
	}

	private ItemModelBuilder airBlock(Block block, String particleTexture, boolean registerItem) {
		BlockModelBuilder builder = models().getBuilder(name(block)).texture("particle", modLoc(particleTexture)).renderType("cutout");
		getVariantBuilder(block).partialState().setModels(new ConfiguredModel(builder));
		if (registerItem) {
			return blockItem(block, builder);
		}
		return null;
	}
	
	private ItemModelBuilder bottomSlabBlock(RegistryObject<Block> block, ResourceLocation side, ResourceLocation bottom,
			ResourceLocation top, boolean registerItem) {
		return bottomSlabBlock(block.get(), side, bottom, top, registerItem);
	}

	private ItemModelBuilder bottomSlabBlock(Block block, ResourceLocation side, ResourceLocation bottom,
			ResourceLocation top, boolean registerItem) {
		BlockModelBuilder builder = models().slab(name(block), side, bottom, top);
		getVariantBuilder(block).partialState().setModels(new ConfiguredModel(builder));
		if (registerItem) {
			return blockItem(block, builder);
		}
		return null;
	}
	
	private ItemModelBuilder horrRotatedBlock(RegistryObject<Block> block, ModelFile modelFile, boolean registerItem) {
		return horrRotatedBlock(block.get(), modelFile, registerItem);
	}
	
	private ItemModelBuilder horrRotatedBlock(Block block, ModelFile file, boolean registerItem) {
		getVariantBuilder(block).partialState().with(GenericEntityBlock.FACING, Direction.NORTH).modelForState()
				.modelFile(file).rotationY(270).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.EAST).modelForState().modelFile(file).rotationY(0)
				.addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).modelForState()
				.modelFile(file).rotationY(90).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.WEST).modelForState().modelFile(file).rotationY(180)
				.addModel();
		if (registerItem) {
			return blockItem(block, file);
		}
		return null;
	}

	private ItemModelBuilder horrRotatedLitBlock(RegistryObject<Block> block, ModelFile off, ModelFile on, boolean registerItem) {
		return horrRotatedLitBlock(block.get(), off, on, registerItem);
	}
	
	private ItemModelBuilder horrRotatedLitBlock(Block block, ModelFile off, ModelFile on, boolean registerItem) {
		getVariantBuilder(block).partialState().with(GenericEntityBlock.FACING, Direction.NORTH)
				.with(BlockStateProperties.LIT, false).modelForState().modelFile(off).rotationY(270).addModel()
				.partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
				.modelForState().modelFile(off).rotationY(0).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false).modelForState()
				.modelFile(off).rotationY(90).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST)
				.with(BlockStateProperties.LIT, false).modelForState().modelFile(off).rotationY(180).addModel()
				.partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
				.modelForState().modelFile(on).rotationY(270).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, true).modelForState()
				.modelFile(on).rotationY(0).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH)
				.with(BlockStateProperties.LIT, true).modelForState().modelFile(on).rotationY(90).addModel()
				.partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
				.modelForState().modelFile(on).rotationY(180).addModel();
		if (registerItem) {
			return blockItem(block, off);
		}
		return null;

	}
	
	private ItemModelBuilder redstoneToggleBlock(RegistryObject<Block> block, ModelFile off, ModelFile on, boolean registerItem) {
		return redstoneToggleBlock(block.get(), off, on, registerItem);
	}
	 
	private ItemModelBuilder redstoneToggleBlock(Block block, ModelFile off, ModelFile on, boolean registerItem) {
		getVariantBuilder(block).partialState().with(BlockStateProperties.LIT, false).modelForState()
				.modelFile(off).addModel().partialState().with(BlockStateProperties.LIT, true).modelForState()
				.modelFile(on).addModel();
		if (registerItem) {
			return blockItem(block, off);
		}
		return null;

		
	}
	/*
	private void omniDirBlock(RegistryObject<Block> block, ModelFile model, boolean registerItem) {
		getVariantBuilder(block.get()).partialState().with(GenericEntityBlock.FACING, Direction.NORTH)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.NONE).modelForState().modelFile(model)
				.rotationY(0).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.NONE).modelForState().modelFile(model)
				.rotationY(90).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.NONE).modelForState().modelFile(model)
				.rotationY(180).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.NONE).modelForState().modelFile(model)
				.rotationY(270).addModel().partialState().with(GenericEntityBlock.FACING, Direction.NORTH)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.UP).modelForState().modelFile(model)
				.rotationY(0).rotationX(270).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.UP).modelForState().modelFile(model)
				.rotationY(90).rotationX(270).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.UP).modelForState().modelFile(model)
				.rotationY(180).rotationX(270).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.UP).modelForState().modelFile(model)
				.rotationY(270).rotationX(270).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.NORTH)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.DOWN).modelForState().modelFile(model)
				.rotationY(0).rotationX(90).addModel().partialState().with(GenericEntityBlock.FACING, Direction.EAST)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.DOWN).modelForState().modelFile(model)
				.rotationY(90).rotationX(90).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.DOWN).modelForState().modelFile(model)
				.rotationY(180).rotationX(90).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST)
				.with(OverdriveBlockStates.VERTICAL_FACING, VerticalFacing.DOWN).modelForState().modelFile(model)
				.rotationY(270).rotationX(90).addModel();
		if (registerItem)
			simpleBlockItem(block.get(), model);
	}

	*/
	private void wire(Block block, ModelFile none, ModelFile side, boolean registerItem) {
		getMultipartBuilder(block)
			.part().modelFile(none).addModel().useOr()
				.condition(EnumConnectType.UP, EnumConnectType.NONE)
				.condition(EnumConnectType.DOWN, EnumConnectType.NONE)
				.condition(EnumConnectType.NORTH, EnumConnectType.NONE)
				.condition(EnumConnectType.EAST, EnumConnectType.NONE)
				.condition(EnumConnectType.SOUTH, EnumConnectType.NONE)
				.condition(EnumConnectType.WEST, EnumConnectType.NONE).end()
			.part().rotationX(270).modelFile(side).addModel().useOr()
				.condition(EnumConnectType.UP, EnumConnectType.WIRE, EnumConnectType.INVENTORY).end()
			.part().rotationX(90).modelFile(side).addModel().useOr()
				.condition(EnumConnectType.DOWN, EnumConnectType.WIRE, EnumConnectType.INVENTORY).end()
			.part().rotationY(0).modelFile(side).addModel().useOr()
				.condition(EnumConnectType.NORTH, EnumConnectType.WIRE, EnumConnectType.INVENTORY).end()
			.part().rotationY(90).modelFile(side).addModel().useOr()
				.condition(EnumConnectType.EAST, EnumConnectType.WIRE, EnumConnectType.INVENTORY).end()
			.part().rotationY(180).modelFile(side).addModel().useOr()
				.condition(EnumConnectType.SOUTH, EnumConnectType.WIRE, EnumConnectType.INVENTORY).end()
			.part().rotationY(270).modelFile(side).addModel().useOr()
				.condition(EnumConnectType.WEST, EnumConnectType.WIRE, EnumConnectType.INVENTORY).end();

		if (registerItem)
			simpleBlockItem(block, none);

	}
	

	private BlockModelBuilder getObjModel(String name, String modelLoc) {
		return models().withExistingParent("block/" + name, "cube").customLoader(ObjModelBuilder::begin).flipV(true)
				.modelLocation(modLoc("models/" + modelLoc + ".obj")).end();
	}

	private BlockModelBuilder blockTopBottom(RegistryObject<Block> block, String top, String bottom, String side) {
		return models().cubeBottomTop(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				new ResourceLocation(References.ID, side), new ResourceLocation(References.ID, bottom),
				new ResourceLocation(References.ID, top));
	}
	
	public ItemModelBuilder blockItem(Block block, ModelFile model) {
        return itemModels().getBuilder(key(block).getPath()).parent(model);
    }

	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	private String name(Block block) {
		return key(block).getPath();
	}

	private ExistingModelFile existingBlock(RegistryObject<Block> block) {
		return existingBlock(block.getId());
	}
	
	private ExistingModelFile existingBlock(Block block) {
		return existingBlock(ForgeRegistries.BLOCKS.getKey(block));
	}

	private ExistingModelFile existingBlock(ResourceLocation loc) {
		return models().getExistingFile(loc);
	}

	private ResourceLocation blockLoc(String texture) {
		return modLoc("block/" + texture);
	}
	
	private ResourceLocation modelLoc(String texture) {
		return modLoc("model/" + texture);
	}
	
}

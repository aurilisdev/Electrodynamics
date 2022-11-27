package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.block.subtype.SubtypeOreDeepslate;
import electrodynamics.common.block.subtype.SubtypeRawOreBlock;
import electrodynamics.common.block.subtype.SubtypeResourceBlock;
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

public class OverdriveBlockStateProvider extends BlockStateProvider {

	public OverdriveBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
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
		simpleBlock(ElectrodynamicsBlocks.blockFrameCorner, blockLoc("framecorner"), true);
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
		
		
		
		/*
		BlockModelBuilder cubeColoredAll = models()
				.withExistingParent("cube_colored_all", blockLoc("parent/cube_colored")).texture("particle", "#all")
				.texture("down", "#all").texture("up", "#all").texture("north", "#all").texture("east", "#all")
				.texture("south", "#all").texture("west", "#all");

		BlockModelBuilder floorTileColorless = models().getBuilder("floor_tile_colorless").parent(cubeColoredAll)
				.texture("all", blockLoc("decorative/floor_tile_colorless"));
		BlockModelBuilder floorTilesColorless = models().getBuilder("floor_tiles_colorless").parent(cubeColoredAll)
				.texture("all", blockLoc("decorative/floor_tiles_colorless"));
		BlockModelBuilder tritaniumPlatingColorless = models().getBuilder("tritanium_plating_colorless")
				.parent(cubeColoredAll).texture("all", blockLoc("decorative/tritanium_plating_colorless"));

		simpleBlock(BlockRegistry.BLOCK_REGULAR_TRITANIUM_PLATING,
				models().cubeAll("tritanium_plating", blockLoc("decorative/tritanium_plating")), true);
		for (OverdriveBlockColors color : OverdriveBlockColors.values()) {
			simpleBlock(BlockRegistry.BLOCK_COLORED_TRITANIUM_PLATING.get(color), tritaniumPlatingColorless, true);
			simpleBlock(BlockRegistry.BLOCK_FLOOR_TILE.get(color), floorTileColorless, true);
			simpleBlock(BlockRegistry.BLOCK_FLOOR_TILES.get(color), floorTilesColorless, true);
		}
		for (CrateColors color : CrateColors.values()) {
			String name = color.id();
			horrRotatedBlock(BlockRegistry.BLOCK_TRITANIUM_CRATES.get(color),
					getObjModel(name, "block/" + name, "block/crate/" + name), true);
		}
		glassBlock(BlockRegistry.BLOCK_INDUSTRIAL_GLASS, true);

		ModelFile ventOff = cubeAll(BlockRegistry.BLOCK_VENT_OPEN.get());
		ModelFile ventOn = cubeAll(BlockRegistry.BLOCK_VENT_CLOSED.get());

		redstoneToggleBlock(BlockRegistry.BLOCK_VENT_OPEN, ventOff, ventOn, true);
		redstoneToggleBlock(BlockRegistry.BLOCK_VENT_CLOSED, ventOn, ventOff, true);

		redstoneToggleBlock(BlockRegistry.BLOCK_CHUNKLOADER, getChunkloaderBase("off"), getChunkloaderBase("on"), true);
		simpleBlock(BlockRegistry.BLOCK_CHARGER_CHILD, true);
		simpleBlock(BlockRegistry.BLOCK_TRANSPORTER,
				blockTopBottom(BlockRegistry.BLOCK_TRANSPORTER, "block/transporter/transporter_top",
						"block/transporter/transporter_bottom", "block/transporter/transporter_side"),
				true);
		airBlock(BlockRegistry.BLOCK_CHARGER, "block/charger", false);
		horrRotatedBlock(BlockRegistry.BLOCK_INSCRIBER, getObjModel("inscriber", "block/inscriber", "block/inscriber"),
				true);
		bottomSlabBlock(BlockRegistry.BLOCK_SOLAR_PANEL, blockLoc("base"), blockLoc("base"), blockLoc("solar_panel"),
				true);
		horrRotatedLitBlock(BlockRegistry.BLOCK_MATTER_ANALYZER, getMatAnaBase("", "", "closed"), getMatAnaBase("_on", "_on", "open"),
				true);
		horrRotatedLitBlock(BlockRegistry.BLOCK_MATTER_DECOMPOSER, getMatDecomBase("", "empty"),
				getMatDecomBase("_on", "full"), true);
		horrRotatedLitBlock(BlockRegistry.BLOCK_MATTER_RECYCLER, getMatRecBase("", ""), getMatRecBase("_on", "_anim"),
				true);
		horrRotatedLitBlock(BlockRegistry.BLOCK_MATTER_REPLICATOR, getMatterRepBase("off", "closed"),
				getMatterRepBase("on", "open"), true);
		horrRotatedLitBlock(BlockRegistry.BLOCK_MICROWAVE, getMicroBase("", ""), getMicroBase("_on", "_on"), true);
		horrRotatedLitBlock(BlockRegistry.BLOCK_PATTERN_STORAGE, getPatternStorageBase("off", "closed"),
				getPatternStorageBase("on", "open"), true);
		horrRotatedBlock(BlockRegistry.BLOCK_SPACETIME_ACCELERATOR,
				existingBlock(BlockRegistry.BLOCK_SPACETIME_ACCELERATOR), true);
		omniDirBlock(BlockRegistry.BLOCK_PATTERN_MONITOR, existingBlock(BlockRegistry.BLOCK_PATTERN_MONITOR), true);
		// charger TileRenderer JSON
		getObjModel("charger_renderer", "block/charger", "block/charger");
		horrRotatedBlock(BlockRegistry.BLOCK_ANDROID_STATION, existingBlock(BlockRegistry.BLOCK_ANDROID_STATION), true);

		genMatterConduits();
		genNetworkCables();
		*/
	
	}
	/*
	private void genMatterConduits() {
	
		String id = TypeMatterConduit.REGULAR.id();
		String parent = "parent/" + id;
		String cable = "block/cable/" + id;
		String text = "matter_conduit/" + id;
		String noneText = text + "_none";

		cable(BlockRegistry.BLOCK_MATTER_CONDUITS.get(TypeMatterConduit.REGULAR),
				models().withExistingParent(cable + "_none", blockLoc(parent + "_none"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText)),
				models().withExistingParent(cable + "_side", blockLoc(parent + "_side"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(text + "_side")),
				models().withExistingParent(cable + "_none_seamless_ns", blockLoc(parent + "_none"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText + "_seamless_ns")),
				models().withExistingParent(cable + "_none_seamless_ew", blockLoc(parent + "_none"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText + "_seamless_ew")),
				models().withExistingParent(cable + "_none_seamless_ud", blockLoc(parent + "_none"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText + "_seamless_ud")),
				true);

		id = TypeMatterConduit.HEAVY.id();
		parent = "parent/" + id;
		cable = "block/cable/" + id;
		text = "matter_conduit/" + id;
		noneText = text + "_none";

		cable(BlockRegistry.BLOCK_MATTER_CONDUITS.get(TypeMatterConduit.HEAVY),
				models().withExistingParent(cable + "_none", blockLoc(parent + "_none"))
						.texture("particle", blockLoc("base_stripes")).texture("texture", blockLoc(noneText)),
				models().withExistingParent(cable + "_side", blockLoc(parent + "_side"))
						.texture("particle", blockLoc("base_stripes")).texture("texture", blockLoc(text + "_side")),
				models().withExistingParent(cable + "_none_seamless_ns", blockLoc(parent + "_none_seamless_ns"))
						.texture("particle", blockLoc("base_stripes"))
						.texture("texture", blockLoc(noneText + "_seamless_ns")),
				models().withExistingParent(cable + "_none_seamless_ew", blockLoc(parent + "_none_seamless_ew"))
						.texture("particle", blockLoc("base_stripes"))
						.texture("texture", blockLoc(noneText + "_seamless_ew")),
				models().withExistingParent(cable + "_none_seamless_ud", blockLoc(parent + "_none_seamless_ud"))
						.texture("particle", blockLoc("base_stripes"))
						.texture("texture", blockLoc(noneText + "_seamless_ud")),
				true);

	}

	private void genNetworkCables() {

		String id = TypeMatterNetworkCable.REGULAR.id();
		String parent = "parent/" + id;
		String cable = "block/cable/" + id;
		String text = "network_cable/" + id;
		String noneText = text + "_none";

		cable(BlockRegistry.BLOCK_MATTER_NETWORK_CABLES.get(TypeMatterNetworkCable.REGULAR),
				models().withExistingParent(cable + "_none", blockLoc(parent + "_none"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText)),
				models().withExistingParent(cable + "_side", blockLoc(parent + "_side"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(text + "_side")),
				models().withExistingParent(cable + "_none_seamless_ns", blockLoc(parent + "_none_seamless_ns"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText + "_seamless_ns")),
				models().withExistingParent(cable + "_none_seamless_ew", blockLoc(parent + "_none_seamless_ew"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText + "_seamless_ew")),
				models().withExistingParent(cable + "_none_seamless_ud", blockLoc(parent + "_none_seamless_ud"))
						.texture("particle", blockLoc("base")).texture("texture", blockLoc(noneText + "_seamless_ud")),
				true);

	}
	*/
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

	private void cable(RegistryObject<Block> block, ModelFile none, ModelFile side, ModelFile ns, ModelFile ew,
			ModelFile ud, boolean registerItem) {
		getMultipartBuilder(block.get()).part().modelFile(none).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_UP, CableConnectionType.NONE)
				.condition(OverdriveBlockStates.CABLE_DOWN, CableConnectionType.NONE)
				.condition(OverdriveBlockStates.CABLE_NORTH, CableConnectionType.NONE)
				.condition(OverdriveBlockStates.CABLE_EAST, CableConnectionType.NONE)
				.condition(OverdriveBlockStates.CABLE_SOUTH, CableConnectionType.NONE)
				.condition(OverdriveBlockStates.CABLE_WEST, CableConnectionType.NONE).end().part().modelFile(ns)
				.addModel().useOr().condition(OverdriveBlockStates.CABLE_EAST, CableConnectionType.NONE_SEAMLESS)
				.condition(OverdriveBlockStates.CABLE_WEST, CableConnectionType.NONE_SEAMLESS).end().part()
				.modelFile(ew).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_UP, CableConnectionType.NONE_SEAMLESS)
				.condition(OverdriveBlockStates.CABLE_DOWN, CableConnectionType.NONE_SEAMLESS).end().part()
				.modelFile(ud).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_NORTH, CableConnectionType.NONE_SEAMLESS)
				.condition(OverdriveBlockStates.CABLE_SOUTH, CableConnectionType.NONE_SEAMLESS).end().part()
				.rotationX(270).modelFile(side).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_UP, CableConnectionType.CABLE, CableConnectionType.INVENTORY)
				.end().part().rotationX(90).modelFile(side).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_DOWN, CableConnectionType.CABLE, CableConnectionType.INVENTORY)
				.end().part().rotationY(0).modelFile(side).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_NORTH, CableConnectionType.CABLE, CableConnectionType.INVENTORY)
				.end().part().rotationY(90).modelFile(side).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_EAST, CableConnectionType.CABLE, CableConnectionType.INVENTORY)
				.end().part().rotationY(180).modelFile(side).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_SOUTH, CableConnectionType.CABLE, CableConnectionType.INVENTORY)
				.end().part().rotationY(270).modelFile(side).addModel().useOr()
				.condition(OverdriveBlockStates.CABLE_WEST, CableConnectionType.CABLE, CableConnectionType.INVENTORY)
				.end();

		if (registerItem)
			simpleBlockItem(block.get(), ns);

	}
	*/
	private BlockModelBuilder getObjModel(String name, String modelLoc, String texture) {
		return models().withExistingParent("block/" + name, "cube").customLoader(ObjModelBuilder::begin).flipV(true)
				.modelLocation(modLoc("models/" + modelLoc + ".obj")).end().texture("texture0", texture)
				.texture("particle", "#texture0");
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

	private BlockModelBuilder getMatAnaBase(String name, String frontText, String vent) {
		return models().withExistingParent("block/matter_analyzer" + name, modLoc("block/parent/matter_analyzer_base"))
				.texture("bottom", modLoc("block/base"))
				.texture("top", modLoc("block/matter_analyzer/matter_analyzer_top"))
				.texture("side", modLoc("block/vent_" + vent)).texture("back", modLoc("block/network_port"))
				.texture("particle", "block/matter_analyzer/matter_analyzer_front")
				.texture("front", modLoc("block/matter_analyzer/matter_analyzer_front" + frontText));
	}

	private BlockModelBuilder getMatDecomBase(String name, String frontText) {
		return models().orientableWithBottom("block/matter_decomposer" + name, modLoc("block/base_stripes"),
				modLoc("block/tank_" + frontText), modLoc("block/vent_closed"), modLoc("block/decomposer_top"));
	}

	private BlockModelBuilder getMatRecBase(String name, String frontText) {
		return models().orientableWithBottom("block/matter_recycler" + name, modLoc("block/base_stripes"),
				modLoc("block/recycler_front" + frontText), modLoc("block/vent_closed"),
				modLoc("block/decomposer_top"));
	}

	private BlockModelBuilder getMicroBase(String name, String frontText) {
		return models().withExistingParent("block/microwave" + name, modLoc("block/parent/microwave_base"))
				.texture("0", modLoc("block/microwave/microwave"))
				.texture("1", modLoc("block/microwave/microwave_front" + frontText))
				.texture("2", modLoc("block/microwave/microwave_back")).texture("particle", "#0");
	}

	private BlockModelBuilder getChunkloaderBase(String name) {
		return models().withExistingParent("block/chunkloader_" + name, modLoc("block/parent/chunkloader_base"))
				.texture("0", modLoc("block/chunkloader/chunkloader_" + name))
				.texture("particle", modLoc("block/base_stripes"));
	}

	private BlockModelBuilder getPatternStorageBase(String name, String vent) {
		return getObjModel("pattern_storage_" + name, "block/pattern_storage")
				.texture("base", blockLoc("pattern_storage")).texture("vent", blockLoc("vent_" + vent))
				.texture("particle", "#base");
	}

	private BlockModelBuilder getMatterRepBase(String name, String vent) {
		return getObjModel("matter_replicator_" + name, "block/matter_replicator").texture("bottom", blockLoc("base"))
				.texture("back", blockLoc("network_port")).texture("sides", blockLoc("vent_" + vent))
				.texture("front", blockLoc("matter_replicator")).texture("particle", "#bottom").renderType("cutout");
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

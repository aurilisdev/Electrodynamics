package electrodynamics.datagen.client;

import electrodynamics.api.References;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
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

	private void simpleBlock(RegistryObject<Block> block, ModelFile file, boolean registerItem) {
		simpleBlock(block.get(), file);
		if (registerItem)
			simpleBlockItem(block.get(), file);
	}

	private void simpleBlock(RegistryObject<Block> block, boolean registerItem) {
		simpleBlock(block, cubeAll(block.get()), registerItem);
	}

	private void glassBlock(RegistryObject<Block> block, boolean registerItem) {
		BlockModelBuilder builder = models().cubeAll(name(block.get()), blockTexture(block.get())).renderType("cutout");
		getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(builder));
		if (registerItem)
			simpleBlockItem(block.get(), builder);
	}

	private void airBlock(RegistryObject<Block> block, String particleTexture, boolean registerItem) {
		BlockModelBuilder builder = models().getBuilder(name(block.get())).texture("particle", modLoc(particleTexture));
		getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(builder));
		if (registerItem)
			simpleBlockItem(block.get(), builder);
	}

	public void bottomSlabBlock(RegistryObject<Block> block, ResourceLocation side, ResourceLocation bottom,
			ResourceLocation top, boolean registerItem) {
		BlockModelBuilder builder = models().slab(name(block.get()), side, bottom, top);
		getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(builder));
		if (registerItem)
			simpleBlockItem(block.get(), builder);
	}

	private void horrRotatedBlock(RegistryObject<Block> block, ModelFile modelFile, boolean registerItem) {
		getVariantBuilder(block.get()).partialState().with(GenericEntityBlock.FACING, Direction.NORTH).modelForState()
				.modelFile(modelFile).rotationY(0).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.EAST).modelForState().modelFile(modelFile).rotationY(90)
				.addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH).modelForState()
				.modelFile(modelFile).rotationY(180).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.WEST).modelForState().modelFile(modelFile).rotationY(270)
				.addModel();
		if (registerItem)
			simpleBlockItem(block.get(), modelFile);
	}

	private void horrRotatedLitBlock(RegistryObject<Block> block, ModelFile off, ModelFile on, boolean registerItem) {
		getVariantBuilder(block.get()).partialState().with(GenericEntityBlock.FACING, Direction.NORTH)
				.with(BlockStateProperties.LIT, false).modelForState().modelFile(off).rotationY(0).addModel()
				.partialState().with(GenericEntityBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
				.modelForState().modelFile(off).rotationY(90).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false).modelForState()
				.modelFile(off).rotationY(180).addModel().partialState().with(GenericEntityBlock.FACING, Direction.WEST)
				.with(BlockStateProperties.LIT, false).modelForState().modelFile(off).rotationY(270).addModel()
				.partialState().with(GenericEntityBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
				.modelForState().modelFile(on).rotationY(0).addModel().partialState()
				.with(GenericEntityBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, true).modelForState()
				.modelFile(on).rotationY(90).addModel().partialState().with(GenericEntityBlock.FACING, Direction.SOUTH)
				.with(BlockStateProperties.LIT, true).modelForState().modelFile(on).rotationY(180).addModel()
				.partialState().with(GenericEntityBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
				.modelForState().modelFile(on).rotationY(270).addModel();
		if (registerItem)
			simpleBlockItem(block.get(), off);

	}

	private void redstoneToggleBlock(RegistryObject<Block> block, ModelFile off, ModelFile on, boolean registerItem) {
		getVariantBuilder(block.get()).partialState().with(BlockStateProperties.LIT, false).modelForState()
				.modelFile(off).addModel().partialState().with(BlockStateProperties.LIT, true).modelForState()
				.modelFile(on).addModel();
		if (registerItem)
			simpleBlockItem(block.get(), off);

	}

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

	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	private String name(Block block) {
		return key(block).getPath();
	}

	private ExistingModelFile existingBlock(RegistryObject<Block> block) {
		return existingBlock(block.getId());
	}

	private ExistingModelFile existingBlock(ResourceLocation loc) {
		return models().getExistingFile(loc);
	}

	private ResourceLocation blockLoc(String texture) {
		return modLoc("block/" + texture);
	}
	
	*/

}

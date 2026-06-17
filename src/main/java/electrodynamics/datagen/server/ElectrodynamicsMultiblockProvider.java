package electrodynamics.datagen.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import electrodynamics.Electrodynamics;
import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.machines.TileElectrolosisChamber;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import voltaic.api.multiblock.assemblybased.MultiblockSlaveNode;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.datagen.utils.server.multiblock.BaseMultiblockProvider;
import voltaic.registers.VoltaicBlocks;

public class ElectrodynamicsMultiblockProvider extends BaseMultiblockProvider {

    public ElectrodynamicsMultiblockProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	    ExistingFileHelper existingFileHelper) {
	super(output, lookupProvider, existingFileHelper, Electrodynamics.ID);
    }

    @Override
    protected void gather() {

	BlockState slave = VoltaicBlocks.BLOCK_MULTIBLOCK_SLAVE.get().defaultBlockState()
		.setValue(VoltaicBlockStates.WATERLOGGED, false);
	BlockState scaffold = ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING.get().defaultBlockState();

	ResourceLocation ecWindow = Electrodynamics.rl("multiblockmodels/electrolosischamberwindow");
	ResourceLocation ecWindowTopC1 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindowc1");
	ResourceLocation ecWindowTopC2 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindowc2");
	ResourceLocation ecWindowTopC3 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindowc3");
	ResourceLocation ecWindowTopC4 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindowc4");
	ResourceLocation ecWindowTopS1 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindows1");
	ResourceLocation ecWindowTopS2 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindows2");
	ResourceLocation ecWindowTopS3 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindows3");
	ResourceLocation ecWindowTopS4 = Electrodynamics.rl("multiblockmodels/electrolosischamberwindows4");
	ResourceLocation ecCorner = Electrodynamics.rl("multiblockmodels/electrolosischambercorner");
	ResourceLocation ecVertSide = Electrodynamics.rl("multiblockmodels/electrolosischambervertside");
	ResourceLocation ecHorSideFB = Electrodynamics.rl("multiblockmodels/electrolosischamberhorsidefb");
	ResourceLocation ecHorSideLR = Electrodynamics.rl("multiblockmodels/electrolosischamberhorsidelr");
	ResourceLocation ecBottom = Electrodynamics.rl("multiblockmodels/electrolosischamberbottom");
	ResourceLocation ecPowerIn = Electrodynamics.rl("multiblockmodels/electrolosischamberpowerin");
	ResourceLocation ecFluidIn = Electrodynamics.rl("multiblockmodels/electrolosischamberfluidin");
	ResourceLocation ecFluidOut = Electrodynamics.rl("multiblockmodels/electrolosischamberfluidout");
	ResourceLocation ecCoilC1 = Electrodynamics.rl("multiblockmodels/electrolosischambercoilc1");
	ResourceLocation ecCoilC2 = Electrodynamics.rl("multiblockmodels/electrolosischambercoilc2");
	ResourceLocation ecCoilC3 = Electrodynamics.rl("multiblockmodels/electrolosischambercoilc3");
	ResourceLocation ecCoilC4 = Electrodynamics.rl("multiblockmodels/electrolosischambercoilc4");
	ResourceLocation ecCoilS1 = Electrodynamics.rl("multiblockmodels/electrolosischambercoils1");
	ResourceLocation ecCoilS2 = Electrodynamics.rl("multiblockmodels/electrolosischambercoils2");
	ResourceLocation ecCoilS3 = Electrodynamics.rl("multiblockmodels/electrolosischambercoils3");
	ResourceLocation ecCoilS4 = Electrodynamics.rl("multiblockmodels/electrolosischambercoils4");

	addMultiblock(Electrodynamics.rl("testing"),
		List.of(new MultiblockSlaveNode(slave, Blocks.OAK_LOG.defaultBlockState(), BlockTags.ACACIA_LOGS,
			new Vec3i(0, 0, 1), Shapes.block(), ElectrodynamicsClientRegister.MODEL_BATTERYBOX.id())));

	addMultiblock(TileElectrolosisChamber.ID, List.of(

		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, -1, -4),
			Shapes.block(), ecCorner),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-1, -1, -4),
			Shapes.block(), ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(0, -1, -4),
			Shapes.block(), ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(1, -1, -4),
			Shapes.block(), ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, -1, -4),
			Shapes.block(), ecCorner),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, 0, -4),
			Shapes.block(), ecVertSide),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 0, -4), Shapes.block(), ecWindow),
		//
		new MultiblockSlaveNode(slave, Blocks.GOLD_BLOCK.defaultBlockState(), MultiblockSlaveNode.NOTAG,
			new Vec3i(0, 0, -4), Shapes.block(), ecPowerIn),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 0, -4), Shapes.block(), ecWindow),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, 0, -4), Shapes.block(),
			ecVertSide),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, 1, -4),
			Shapes.block(), ecCorner),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-1, 1, -4),
			Shapes.block(), ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(0, 1, -4), Shapes.block(),
			ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(1, 1, -4), Shapes.block(),
			ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, 1, -4), Shapes.block(),
			ecCorner),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, -1, -3),
			Shapes.block(), ecHorSideLR),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-1, -1, -3),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(0, -1, -3),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(1, -1, -3),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, -1, -3),
			Shapes.block(), ecHorSideLR),
		//
		//
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(-2, 0, -3), Shapes.block(), ecWindow),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 0, -3), Shapes.block(), ecCoilC1),
		//
		new MultiblockSlaveNode(slave, Blocks.GOLD_BLOCK.defaultBlockState(), MultiblockSlaveNode.NOTAG,
			new Vec3i(0, 0, -3), Shapes.block(), ecCoilS1),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 0, -3), Shapes.block(), ecCoilC2),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(2, 0, -3), Shapes.block(), ecWindow),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, 1, -3),
			Shapes.block(), ecHorSideLR),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 1, -3), Shapes.block(), ecWindowTopC3),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(0, 1, -3), Shapes.block(), ecWindowTopS4),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 1, -3), Shapes.block(), ecWindowTopC4),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, 1, -3), Shapes.block(),
			ecHorSideLR),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, -1, -2),
			Shapes.block(), ecHorSideLR),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-1, -1, -2),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(0, -1, -2),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(1, -1, -2),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, -1, -2),
			Shapes.block(), ecHorSideLR),
		//
		//
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(-2, 0, -2), Shapes.block(), ecFluidIn),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 0, -2), Shapes.block(), ecCoilS4),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.electrolyticseparator)
				.defaultBlockState().setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(0, 0, -2), Shapes.block(), MultiblockSlaveNode.NOMODEL),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 0, -2), Shapes.block(), ecCoilS2),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(2, 0, -2), Shapes.block(), ecFluidOut),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, 1, -2),
			Shapes.block(), ecHorSideLR),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 1, -2), Shapes.block(), ecWindowTopS3),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(0, 1, -2), Shapes.block(), MultiblockSlaveNode.NOMODEL),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 1, -2), Shapes.block(), ecWindowTopS1),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, 1, -2), Shapes.block(),
			ecHorSideLR),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, -1, -1),
			Shapes.block(), ecHorSideLR),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-1, -1, -1),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(0, -1, -1),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(1, -1, -1),
			Shapes.block(), ecBottom),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, -1, -1),
			Shapes.block(), ecHorSideLR),
		//
		//
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(-2, 0, -1), Shapes.block(), ecWindow),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 0, -1), Shapes.block(), ecCoilC4),
		//
		new MultiblockSlaveNode(slave, Blocks.GOLD_BLOCK.defaultBlockState(), MultiblockSlaveNode.NOTAG,
			new Vec3i(0, 0, -1), Shapes.block(), ecCoilS3),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel).defaultBlockState()
				.setValue(VoltaicBlockStates.WATERLOGGED, false)
				.setValue(VoltaicBlockStates.LIT, false),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 0, -1), Shapes.block(), ecCoilC3),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(2, 0, -1), Shapes.block(), ecWindow),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, 1, -1),
			Shapes.block(), ecHorSideLR),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 1, -1), Shapes.block(), ecWindowTopC2),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(0, 1, -1), Shapes.block(), ecWindowTopS2),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 1, -1), Shapes.block(), ecWindowTopC1),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, 1, -1), Shapes.block(),
			ecHorSideLR),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, -1, 0),
			Shapes.block(), ecCorner),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-1, -1, 0),
			Shapes.block(), ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(0, -1, 0), Shapes.block(),
			ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(1, -1, 0), Shapes.block(),
			ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, -1, 0), Shapes.block(),
			ecCorner),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, 0, 0), Shapes.block(),
			ecVertSide),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(-1, 0, 0), Shapes.block(), ecWindow),
		//
		new MultiblockSlaveNode(slave,
			ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum).defaultBlockState(),
			MultiblockSlaveNode.NOTAG, new Vec3i(1, 0, 0), Shapes.block(), ecWindow),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, 0, 0), Shapes.block(),
			ecVertSide),
		//
		//
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-2, 1, 0), Shapes.block(),
			ecCorner),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(-1, 1, 0), Shapes.block(),
			ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(0, 1, 0), Shapes.block(),
			ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(1, 1, 0), Shapes.block(),
			ecHorSideFB),
		//
		new MultiblockSlaveNode(slave, scaffold, MultiblockSlaveNode.NOTAG, new Vec3i(2, 1, 0), Shapes.block(),
			ecCorner)

	));

    }

}

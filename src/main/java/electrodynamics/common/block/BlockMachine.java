package electrodynamics.common.block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.damage.DamageSources;
import electrodynamics.common.multiblock.IMultiblockNode;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.tile.TileTransformer;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockMachine extends BlockGenericMachine implements IMultiblockNode {
    public static final HashSet<Subnode> advancedsolarpanelsubnodes = new HashSet<>();
    static {
	int radius = 1;
	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		if (i == 0 && j == 0) {
		    advancedsolarpanelsubnodes.add(new Subnode(new BlockPos(i, 1, j), VoxelShapes.fullCube()));
		} else {
		    advancedsolarpanelsubnodes
			    .add(new Subnode(new BlockPos(i, 1, j), VoxelShapes.create(0, 13.0 / 16.0, 0, 1, 1, 1)));
		}
	    }
	}
    }
    private static final VoxelShape transformershape = VoxelShapes.create(0, 0, 0, 1, 15.0 / 16.0, 1);
    private static final VoxelShape solargenshape = VoxelShapes.create(0, 0, 0, 1, 9.0 / 16.0, 1);

    public final SubtypeMachine machine;

    public BlockMachine(SubtypeMachine machine) {
	this.machine = machine;
    }

    @Override
    @Deprecated
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
	if (machine == SubtypeMachine.downgradetransformer || machine == SubtypeMachine.upgradetransformer) {
	    TileTransformer tile = (TileTransformer) worldIn.getTileEntity(pos);
	    if (tile != null) {
		entityIn.attackEntityFrom(DamageSources.ELECTRICITY, (float) (tile.lastTransfer / 120.0f));
	    }
	}
    }

    @Deprecated
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	return machine == SubtypeMachine.downgradetransformer || machine == SubtypeMachine.upgradetransformer
		? transformershape
		: machine == SubtypeMachine.solarpanel ? solargenshape : super.getShape(state, worldIn, pos, context);
    }

    @Override
    @Deprecated
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
	return isValidMultiblockPlacement(state, worldIn, pos,
		machine == SubtypeMachine.advancedsolarpanel ? advancedsolarpanelsubnodes : new HashSet<Subnode>());
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	if (machine.showInItemGroup) {
	    items.add(new ItemStack(this));
	}
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
	return machine == SubtypeMachine.advancedsolarpanel || machine == SubtypeMachine.batterybox
		? BlockRenderType.ENTITYBLOCK_ANIMATED
		: BlockRenderType.MODEL;
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	ItemStack addstack = new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS
		.get(machine == SubtypeMachine.coalgeneratorrunning ? SubtypeMachine.coalgenerator
			: machine == SubtypeMachine.electricfurnacerunning ? SubtypeMachine.electricfurnace
				: machine == SubtypeMachine.oxidationfurnacerunning ? SubtypeMachine.oxidationfurnace
					: machine));
	TileEntity tile = builder.get(LootParameters.BLOCK_ENTITY);
	tile.getCapability(CapabilityElectrodynamic.ELECTRODYNAMIC).ifPresent(el -> {
	    double joules = el.getJoulesStored();
	    if (joules > 0) {
		addstack.getOrCreateTag().putDouble("joules", joules);
	    }
	});
	return Arrays.asList(addstack);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
	return machine == SubtypeMachine.coalgeneratorrunning ? 12
		: machine == SubtypeMachine.electricfurnacerunning ? 8
			: machine == SubtypeMachine.oxidationfurnacerunning ? 6
				: super.getLightValue(state, world, pos);
    }

    @Override
    @Deprecated
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	TileEntity tile = worldIn.getTileEntity(pos);
	if (hasMultiBlock() && tile instanceof IMultiblockTileNode) {
	    IMultiblockTileNode multi = (IMultiblockTileNode) tile;
	    multi.onNodePlaced(worldIn, pos, state, placer, stack);
	}
    }

    @Deprecated
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	boolean update = SubtypeMachine.shouldBreakOnReplaced(state, newState);
	if (hasMultiBlock()) {
	    TileEntity tile = worldIn.getTileEntity(pos);
	    if (tile instanceof IMultiblockTileNode) {
		IMultiblockTileNode multi = (IMultiblockTileNode) tile;
		multi.onNodeReplaced(worldIn, pos, !update);
	    }
	}
	if (update) {
	    super.onReplaced(state, worldIn, pos, newState, isMoving);
	} else {
	    worldIn.markBlockRangeForRenderUpdate(pos, state, newState);
	}
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return machine.createTileEntity(world);
    }

    @Override
    public boolean hasMultiBlock() {
	return machine == SubtypeMachine.advancedsolarpanel;
    }
}
